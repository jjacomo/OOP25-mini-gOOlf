package it.unibo.minigoolf.view.panels;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Rectangle;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Panel responsible for rendering the game map, including surfaces, obstacles, and the ball.
 * This panel uses a logical coordinate system (1920×1080) that scales to the actual panel size
 * for consistent on-screen positioning across different resolutions.
 * 
 * @author jack
 */
public class MapPanel extends JPanel{
    // mappa di test, per ora dichiarata qui, ma in futuro dovrebbe essere passata
    // da chi costruisce la scena, o comunque caricata da file
    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;
    private static final int LOGICAL_WIDTH = 1920;
    private static final int LOGICAL_HEIGHT = 1080;

    // vorrei non dover importare niente dal model
    private final GameMapController mapController;
    private final Map<String, BufferedImage> textureCache = new HashMap<>();

    /**
     * Constructs a MapPanel with the specified game map controller.
     * Initializes the panel with default start dimensions (960×540) and sets up
     * the internal state for rendering the game map.
     * 
     * @param mapController the controller managing the game map data
     */
    public MapPanel(GameMapController mapController) {
        this.setBounds(0, 0, START_WIDTH, START_HEIGHT);
        this.mapController = mapController;
    }

    /**
     * Loads a texture from the resource cache or from disk if not cached.
     * Uses a HashMap to store previously loaded textures for improved performance.
     * 
     * @param texturePath the relative path to the texture file in src/main/resources/
     * @return the loaded BufferedImage, or null if loading fails
     */
    private BufferedImage loadTexture(String texturePath) {
        if (textureCache.containsKey(texturePath)) {
            return textureCache.get(texturePath);
        }
        try {
            // Simple file loading from src/main/resources/ (for development)
            File file = new File("src/main/resources/" + texturePath);
            BufferedImage image = ImageIO.read(file);
            textureCache.put(texturePath, image);
            return image;
        } catch (IOException e) {
            System.err.println("Failed to load texture: " + texturePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Paints the game map on the panel by rendering all surfaces sorted by z-index
     * and the ball shape.
     * 
     * @param g the graphics context provided by Swing
     */
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


        // qui va disegnato tutto quello che si vede nella mappa, quindi superfici, ostacoli, buche, palla, ...

        // mappa (utilizzo degli stream)
        mapController.getSurfaces().stream()
                .sorted((s1, s2) -> Integer.compare(s1.getZIndex(), s2.getZIndex()))
                .forEach(surface -> {
                    BufferedImage texture = loadTexture(surface.getTexturePath());
                    if (texture != null) {
                        drawShape(surface.getBounds(), g2d, texture);
                    } else {
                        throw new IllegalStateException("Texture not found for surface: " + surface.getTexturePath());
                    }
                });

        // palla
        // cosi' forse non e' bellissimo (c'e' probabilmente qualcosa da cambiare)
        g2d.setColor(Color.WHITE);
        drawShape(mapController.getBallController().getBallShape(), g2d, null);
    }

    /**
     * Draws a shape with the given texture. This method handles different shape types
     * by checking their concrete type and applying the appropriate drawing logic.
     * 
     * @param shape the shape to draw
     * @param g2d the graphics context
     * @param texture the texture to apply
     */
    private void drawShape(Shape shape, Graphics2D g2d, BufferedImage texture) { //avrebbe senso farne una anceh senza texture?
        if (shape instanceof Rectangle rect) { // ma poi in generale fai la review di sto codice (non e' che sarebbe
                                               // meglio fare diverse funzioni invece che una sola che disegna tutte le
                                               // superfici (che tra l'altro alcune hanno texture altre no))
            g2d.drawImage(texture, (int) rect.position().getX(), (int) rect.position().getY(),
                    (int) rect.width(), (int) rect.height(), null);
        } else if (shape instanceof Circle circ){
            g2d.fillOval((int) circ.position().getX(), (int) circ.position().getY(), (int) circ.radius() * 2,
                    (int) circ.radius() * 2);
        } else {
            // For future shape types, add more cases here
            throw new UnsupportedOperationException("Drawing not implemented for shape type: " + shape.getClass());
        }
    }
}
