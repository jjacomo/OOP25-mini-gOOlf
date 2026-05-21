## Capitolo 1:  Analisi

### 1.1 Descrizione e requisiti


#### Requisiti funzionali
* Gestione dell'input
* Menu di gioco
* Mappa e ostacoli
* Movimento della pallina

#### Requisiti non funzionali
* Leaderboard
* Possibilità di salvare e caricare la partita
* Terreni ed ostacoli di tipo avanzato 

### 1.2 Modello del Dominio


---

## Capitolo 2: Design

### 2.1 Architettura


### 2.2 Design dettagliato

#### Daniel Patryk Bak
**Topic**
*Problema*:

*Soluzione*: 

*Pro e Contro*: 

#### Giacomo Mengozzi
**Topic**
*Problema*: 

*Soluzione*:

*Pro e Contro*: 

#### Federico Sparvoli

**Input del colpo**

*Problema*:
Il gioco richiede che il giocatore indichi direzione e potenza del colpo tramite un gesto di drag del mouse sulla pallina. Il sistema deve: (1) riconoscere dove inizia il drag (solo se vicino alla pallina), (2) calcolare il vettore del colpo durante il drag, (3) mostrare visivamente l'indicatore con colore proporzionale alla potenza, (4) confermare il colpo solo al rilascio del mouse, senza che il game loop lo consumi prima.

*Soluzione*:
La logica è suddivisa in tre livelli distinti seguendo il pattern MVC:
- Model (ShotState): tiene lo stato del colpo in corso (intent, shotReady, ballPosition). Il vettore viene limitato a una lunghezza massima di MAX_POWER =  150 pixel logici al momento del rilascio, garantendo che la fisica non riceva mai più potenza di quanto mostri l'indicatore visivo.
- View (ShotViewPanel, ShotListener, ShotCoordinateConverter): ShotViewPanel disegna la linea tratteggiata con punta a freccia, scalando le coordinate fisiche del mouse in coordinate logiche 1920×1080. ShotListener traduce gli eventi mouse in chiamate su ShotState tramite le interfacce ShotVisualizer e ShotCoordinateConverter, senza dipendere dalla classe concreta ShotViewPanel.
- Controller (ShotControllerImpl): ogni tick interroga ShotState.consume() e, se un colpo è pronto, lo inoltra alla logica di turno tramite callback funzionali (Consumer<Vector2D>, Supplier<Optional<Vector2D>>), senza memorizzare riferimenti a oggetti mutabili.

Il colpo viene considerato valido solo dopo mouseReleased, grazie al flag shotReady in ShotState: durante il drag shotReady è sempre false, impedendo al game loop di consumare il colpo prima che il giocatore abbia rilasciato il mouse.

*Pro e Contro*:
Pro: separazione netta tra stato (model), rendering (view) e coordinamento (controller); nessun @SuppressWarnings necessario grazie all'uso di interfacce strette e callback funzionali; il click viene accettato solo entro un raggio configurabile dalla pallina, evitando colpi accidentali.

Contro: la soglia CLICK_RADIUS = 40 pixel logici è una costante hardcoded. Su schermi molto grandi o piccoli potrebbe risultare poco precisa e richiederebbe un adattamento proporzionale alla dimensione della pallina.


**Ciclo di vita del match e progressione delle mappe**

*Problema*:
Il gioco deve supportare una sequenza ordinata di mappe: quando la pallina entra in buca il match termina e si avanza alla mappa successiva; se non ce ne sono, si torna al menu principale. La logica di progressione non deve appesantire MainControllerImpl, che deve restare un semplice orchestratore del timer.

*Soluzione*:
La progressione di gioco è gestita da tre componenti distinti:
-MapSequence (model): lista ordinata di GameMapFactory, con advance(), hasNext() e reset(). Incapsula completamente il concetto di "quale mappa è la corrente".
-MatchManager (controller): costruisce ogni match tramite GameFactory, passa il callback onHoleCompleted e gestisce la transizione. Non memorizza collaboratori mutabili: usa esclusivamente Runnable e Consumer<GameController> per interagire con navigation e view.
-HoleChecker (model): controlla ogni tick se il centro della pallina è dentro il raggio della buca. Il controllo avviene sia a palla ferma sia a palla in movimento, purché la velocità sia inferiore a un terzo di quella massima, replicando il comportamento realistico in cui una pallina lenta può cadere in buca.
MainControllerImpl si riduce a creare MapSequence, MatchManager e il timer, delegando tutto il resto.

*Pro e Contro*:
Pro: aggiungere una nuova mappa richiede una sola riga (new MapSequence(List.of(new FirstMap(), new SecondMap()))). Il reset della sequenza al ritorno al menu è automatico. Nessun warning SpotBugs grazie all'uso esclusivo di callback.

Contro: MatchManager ricostruisce l'intero GameController (e quindi la GameMap) ad ogni cambio mappa, il che potrebbe essere costoso se le mappe fossero molto grandi. 

#### Mattia D'Ambrosio
**Topic**
*Problema*: 

*Soluzione*:

*Pro e Contro*: 

---

## Capitolo 3: Sviluppo

### 3.1 Testing automatizzato
* **Daniel Patryk Bak:** 
* **Giacomo Mengozzi:** 
* **Federico Sparvoli:** 
I componenti testati sono ShotState, GameState, ShotViewPanel e GameFactory.
ShotStateTest: verifica le invarianti del model del colpo: stato iniziale vuoto, soglia minima di potenza, riduzione velocità a MAX_POWER, consumo singolo del colpo e reset completo dello stato.
GameStateTest: verifica la logica di turno: costruttore, avanzamento e wrap-around dei turni, conteggio dei colpi, ignoramento di colpi sotto soglia o mentre la pallina è in movimento.
ShotViewPanelTest: testa l'integrazione tra ShotViewPanel e ShotListener tramite eventi mouse, verificando che il ciclo press-drag-release produca un colpo valido, che click lontani dalla pallina non avviino il drag, e che drag troppo corti vengano ignorati.
GameFactoryTest: verifica che il match costruito dalla factory sia in uno stato iniziale coerente: tutti i controller non nulli, primo giocatore corretto, updateTick null-safe prima di setShotView.

* **Mattia D'Ambrosio:** 

### 3.2 Note di sviluppo
* **Daniel Patryk Bak:** 
* **Giacomo Mengozzi:** 
* **Federico Sparvoli:** 
- Interfacce funzionali e @FunctionalInterface: ShotView, ShotCoordinateConverter e ShotInput sono annotate @FunctionalInterface. Permalink: src/main/java/it/unibo/minigoolf/controller/shot/ShotView.java.
           src/main/java/it/unibo/minigoolf/view/input/ShotCoordinateConverter.java.
           src/main/java/it/unibo/minigoolf/view/input/ShotInput.java.

- Lambda expressions e method reference per dependency injection funzionale: GameControllerImpl non memorizza GameState né PhysicsController direttamente, ma ne estrae i comportamenti come BooleanSupplier, Runnable, Consumer<Vector2D> e Supplier<Optional<Vector2D>>, usati pervasivamente per evitare di memorizzare oggetti mutabili e per eliminare warning SpotBugs EI_EXPOSE_REP2 senza @SuppressWarnings. 
Permalink: src/main/java/it/unibo/minigoolf/controller/game/MatchManager.java.
           src/main/java/it/unibo/minigoolf/controller/game/GameControllerImpl.java

- Record Java: SaveData e PlayerSaveData sono record, sfruttando la sintassi compatta per DTO immutabili. 
Permalink: src/main/java/it/unibo/minigoolf/model/save/SaveData.java

-Stream API: usata in SaveData.from() per costruire la lista di snapshot dei giocatori con stream().map(...).toList(). 
Permalink: src/main/java/it/unibo/minigoolf/model/save/SaveData.java

-Libreria Gson (com.google.code.gson): usata in SaveManager per serializzare e deserializzare lo stato della partita in JSON. Permalink: src/main/java/it/unibo/minigoolf/model/save/SaveManager.java

-Test headless con JUnit 5: ShotViewPanelTest usa System.setProperty("java.awt.headless", "true") in un blocco static e Component.dispatchEvent per simulare eventi mouse senza display fisico.
Permalink: src/test/java/it/unibo/minigoolf/view/input/ShotViewPanelTest.java

* **Mattia D'Ambrosio:** 

---

## Capitolo 4: Commenti finali

### 4.1 Autovalutazione e lavori futuri
* **Daniel Patryk Bak:** 
* **Giacomo Mengozzi:** 
* **Federico Sparvoli:** 
Il mio contributo principale riguarda il sistema di input del colpo e la gestione del ciclo di vita dei match. Sono soddisfatto della separazione raggiunta tra model, view e controller, in particolare dell'eliminazione di tutti i warning SpotBugs senza ricorrere a @SuppressWarnings, ottenuta tramite interfacce strette e callback funzionali.
Un aspetto migliorabile è la soglia di click sulla pallina (CLICK_RADIUS), che al momento è fissa in pixel logici e non si adatta alla dimensione effettiva della pallina a runtime. In futuro sarebbe opportuno calcolarla dinamicamente dal raggio della pallina.
Sul fronte della progressione delle mappe, la ricostruzione completa del GameController ad ogni cambio mappa potrebbe diventare un collo di bottiglia con mappe molto complesse: un sistema di pre-caricamento asincrono sarebbe il passo naturale successivo.
Il sistema di salvataggio (SaveManager, SaveData) //TODO

* **Mattia D'Ambrosio:** 

---

## Appendice A: Guida Utente

* **Menu Iniziale:** 
* **Azioni di Gioco:** 
* **Controlli In-Game:** 
* **Fine Partita:**

(per me è abbastanza banale e si potrebbe anche omettere, al massimo spieghiamo le regole del gioco)