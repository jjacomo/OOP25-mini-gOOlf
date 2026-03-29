/*package it.unibo.minigoolf.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Optional;
import javax.swing.JPanel;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Lascio i commenti in italiano perchè tanto so già che dovrò modificarla in futuro
 * A JPanel used for the game input.
 * Accumula lo stato del tiro (direzione, potenza) e lo espone
 * tramite pollShot() al game loop di MainControllerImpl.
 * @author fede
 
public class ShotViewPanel extends JPanel implements ShotVisualizer {

    private static final long serialVersionUID = 1L;

    private static final double SQUARE_MINIMUM_SHOT_POWER = 100;

    // Soglie di potenza per il colore della linea
    private static final double LOW_POWER_THRESHOLD = 1000;    // sotto: verde
    private static final double MEDIUM_POWER_THRESHOLD = 5000; // tra 1000-5000: giallo, sopra: rosso

    // Limite massimo di lunghezza per la linea tratteggiata (in pixel)
    private static final double MAX_LINE_LENGTH = 150.0;

    // Pattern per linea tratteggiata
    private static final float[] DASH_PATTERN = {10f, 5f};
    private static final Stroke DASHED_STROKE = new BasicStroke(
        2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10f, DASH_PATTERN, 0f
    );

    private final ShotListener mouseListener = new ShotListener(this);

    private Point applicationPoint;
    private Vector2D direction;

    // Segnala al game loop che c'è un tiro pronto da leggere
    private volatile boolean shotReady = false;

    /**
     * @param controller il MainController del gioco
     
    public ShotViewPanel(final MainController controller) {
        super();
        this.addMouseListener(this.mouseListener);
        this.addMouseMotionListener(this.mouseListener);
    }

    //TODO quando verrà implementato Vector2D devo aggiungere questi 5 metodi

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        synchronized (this) {
            if (this.applicationPoint != null && validShot()) {
                final Vector2D originalDirection = this.direction;
                final double originalLength = originalDirection.getModule();

                // Limita la lunghezza della linea visualizzata
                final Vector2D displayDirection;
                if (originalLength > MAX_LINE_LENGTH) {
                    displayDirection = new Vector2D(originalDirection.getAngle(), MAX_LINE_LENGTH);
                } else {
                    displayDirection = originalDirection;
                }

                final Point directionTip = displayDirection.translate(this.applicationPoint);
                final double power = originalDirection.getSquareModule();

                // Determina il colore in base alla potenza
                final Color lineColor;
                if (power < LOW_POWER_THRESHOLD) {
                    lineColor = Color.GREEN;
                } else if (power < MEDIUM_POWER_THRESHOLD) {
                    lineColor = Color.YELLOW;
                } else {
                    lineColor = Color.RED;
                }

                final Graphics2D g2d = (Graphics2D) g;
                final Stroke originalStroke = g2d.getStroke();

                g2d.setStroke(DASHED_STROKE);
                g2d.setColor(lineColor);
                g2d.drawLine(
                    this.applicationPoint.x, this.applicationPoint.y,
                    directionTip.x, directionTip.y
                );

                g2d.setStroke(originalStroke);
            }
        }
    }

    /**
     * Aggiorna l'indicatore visivo di direzione e potenza del tiro.
     * Chiamato dal ShotListener durante il drag del mouse.
     * NON chiama repaint(): ci pensa il game loop.
     
    public void updateShotIntent(final Vector2D direction) {
        synchronized (this) {
            this.direction = direction.getOppositeVector();
        }
    }

    /**
     * Abilita un nuovo tiro, impostando la posizione della pallina.
     *
     * @param ballPosition posizione della pallina sullo schermo
     
    public void enableShot(final Point ballPosition) {
        this.mouseListener.setEnable(true);
        this.applicationPoint = ballPosition;
    }

    /**
     * Chiamato dal ShotListener al mouseReleased.
     * NON chiama il controller direttamente: segna che c'è un tiro pronto.
     * Il game loop lo leggerà tramite pollShot().
     *
    public void shoot() {
        if (validShot()) {
            synchronized (this) {
                this.shotReady = true;
            }
            this.mouseListener.setEnable(false);
        } else {
            synchronized (this) {
                this.applicationPoint = null;
                this.direction = null;
            }
        }
    }

    /**
     * Chiamato dal game loop (MainControllerImpl.actionPerformed) ad ogni tick.
     * Se c'è un tiro pronto, lo restituisce e resetta lo stato interno.
     *
     * @return Optional con il vettore del tiro, o empty se nessun tiro disponibile
     *
    public Optional<Vector2D> pollShot() {
        synchronized (this) {
            if (this.shotReady && this.direction != null) {
                final Vector2D shot = this.direction;
                this.shotReady = false;
                this.applicationPoint = null;
                this.direction = null;
                return Optional.of(shot);
            }
        }
        return Optional.empty();
    }

    /**
     * @return true se il tiro corrente è valido (supera la potenza minima)
     *
    private boolean validShot() {
        if (this.direction == null) {
            return false;
        }
        return this.direction.getSquareModule() > SQUARE_MINIMUM_SHOT_POWER;
    }
}
*/