package it.unibo.minigoolf.view.panels;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.TestGameMapFactory;
import it.unibo.minigoolf.util.Rectangle;

public class MapPanel extends JPanel{
    // mappa di test, per ora dichiarata qui, ma in futuro dovrebbe essere passata
    // da chi costruisce la scena, o comunque caricata da file
    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;
    private static final int LOGICAL_WIDTH = 1920;
    private static final int LOGICAL_HEIGHT = 1080;

    private final GameMap map = new TestGameMapFactory().buildGameMap();

    public MapPanel() {
        this.setBounds(0, 0, START_WIDTH, START_HEIGHT);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Scale from logical (1920×1080) to actual panel size.
        // This MUST match ShotViewPanel's own scale so that any coordinate
        // used here (ball position, obstacles, …) maps to exactly the same
        // physical pixel on screen as the shot-indicator overlay.
        g2d.scale((double) getWidth() / LOGICAL_WIDTH, (double) getHeight() / LOGICAL_HEIGHT);
        // la roba sopra l'ho presa dalla vecchia fieldPanel di GamePanel, per scalare
        // tutto in modo che le coordinate logiche (1920×1080) corrispondano sempre alla
        // stessa posizione fisica sullo schermo, indipendentemente dalle dimensioni del
        // pannello
        // potrebbe rompersi in futuro forse se dani cambia qualcosa


        // disegno la mappa, per ora solo i rettangoli, in futuro anche ostacoli e buche (utilizzo degli stream)
        map.getSurfaces().stream()
                .sorted((s1, s2) -> Integer.compare(s1.getZIndex(), s2.getZIndex()))
                .forEach(surface -> {
                    g2d.setColor(surface.getColor());
                    Rectangle bounds = surface.getBounds(); // caso superfici rettangolari, oppure potrei fare una
                                                            // interfaccia (pero' sembra complicato) per disegnare
                                                            // superfici di forme diverse
                    g2d.fillRect((int) bounds.position().getX(), (int) bounds.position().getY(),
                            (int) bounds.width(), (int) bounds.height());
                });
    }
}
