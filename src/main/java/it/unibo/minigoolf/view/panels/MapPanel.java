package it.unibo.minigoolf.view.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

import it.unibo.minigoolf.controller.gamemapcontroller.MapElementsView;
import it.unibo.minigoolf.controller.gamemapcontroller.ObstacleData;
import it.unibo.minigoolf.controller.gamemapcontroller.SurfaceData;
import it.unibo.minigoolf.controller.gamemapcontroller.BallData;
import it.unibo.minigoolf.controller.gamemapcontroller.HoleData;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Oval;
import it.unibo.minigoolf.util.shapes.Rectangle;
import it.unibo.minigoolf.util.shapes.Shape;
import it.unibo.minigoolf.util.shapes.Triangle;
import it.unibo.minigoolf.view.texturemanager.SurfaceTextureMapper;
import it.unibo.minigoolf.view.texturemanager.TextureManager;

/**
 * Panel responsible for rendering the game map, including surfaces, obstacles,
 * hole and the ball.
 * This panel uses a logical coordinate system (1920×1080) that scales to the
 * actual panel size for consistent on-screen positioning across different
 * resolutions.
 *
 * @author jack e mattia
 */
public final class MapPanel extends JPanel implements MapElementsView {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int LOGICAL_WIDTH = 1920;
    private static final int LOGICAL_HEIGHT = 1080;
    private transient List<SurfaceData> surfaces = List.of();
    private transient List<ObstacleData> obstacles = List.of();
    private transient HoleData hole;
    private transient BallData ball;

    /**
     * Constructs a new MapPanel.
     */
    public MapPanel() {
        super();
    }

    @Override
    public void updateGraphics(
            final List<SurfaceData> newSurfaces,
            final List<ObstacleData> newObstacles,
            final HoleData newHole,
            final BallData newBall) {
        this.surfaces = List.copyOf(newSurfaces);
        this.obstacles = List.copyOf(newObstacles);
        this.hole = newHole;
        this.ball = newBall;
    }

    /**
     * Restores the state of the MapPanel from the input stream during
     * deserialization.
     *
     * @param stream the object input stream
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized object cannot be
     *                                found
     */
    @Serial
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.surfaces = List.of();
        this.obstacles = List.of();
    }

    /**
     * Paints the game map on the panel by rendering all surfaces sorted by z-index
     * and the ball shape.
     *
     * @param g the graphics context provided by Swing
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        // Scale from logical (1920×1080) to actual panel size.
        // This MUST match ShotViewPanel's own scale so that any coordinate
        // used here (ball position, obstacles, …) maps to exactly the same
        // physical pixel on screen as the shot-indicator overlay.
        g2d.scale((double) getWidth() / LOGICAL_WIDTH, (double) getHeight() / LOGICAL_HEIGHT);

        surfaces.stream()
                .sorted((s1, s2) -> Integer.compare(s1.zIndex(), s2.zIndex()))
                .forEach(surfaceData -> {
                    for (final String typeId : surfaceData.typeIds()) {
                        final String texturePath = SurfaceTextureMapper.getTexturePath(typeId);
                        final BufferedImage texture = TextureManager.loadTexture(texturePath);
                        if (texture != null) {
                            drawShape(surfaceData.shape(), g2d, texture);
                        } else {
                            throw new IllegalStateException(
                                    "Texture not found for surface type: " + typeId);
                        }
                    }
                    final String windOverlay = SurfaceTextureMapper
                            .getWindOverlayTexturePath(surfaceData.wind());
                    if (windOverlay != null) {
                        final BufferedImage windTexture = TextureManager.loadTexture(windOverlay);
                        drawShape(surfaceData.shape(), g2d, windTexture);
                    }
                });

        for (final ObstacleData obstacleData : obstacles) {
            final Color obstacleColor;

            if (obstacleData.isPortal()) {
                obstacleColor = Color.BLUE;
            } else if (obstacleData.bounciness() > 1.0) {
                obstacleColor = Color.RED;
            } else if (obstacleData.bounciness() < 1.0) {
                obstacleColor = Color.GREEN;
            } else {
                obstacleColor = Color.DARK_GRAY;
            }

            g2d.setColor(obstacleColor);
            drawShape(obstacleData.shape(), g2d, null);
        }

        g2d.setColor(Color.BLACK);
        if (hole.shape() != null) {
            drawShape(hole.shape(), g2d, null);
        }

        g2d.setColor(Color.WHITE);
        if (ball.shape() != null) {
            drawShape(ball.shape(), g2d, null);
        }

        if (hole != null && hole.position() != null) {
            // we assume holeShape is a circle and we can get its radius. Wait, in original
            // code it was holeController.getRadius().
            // We only have holeShape and holePosition here.
            // In original code: mapController.getHoleController().getPosition().getX() +
            // mapController.getHoleController().getRadius()
            // We can check if holeShape is a Circle to get the radius.
            double radius = 0;
            if (hole.shape() instanceof Circle c) {
                radius = c.radius();
            }
            drawFlag(g2d, new Vector2D(hole.position().getX() + radius, hole.position().getY()));
        }
    }

    private void drawFlag(final Graphics2D g2d, final Vector2D position) {
        final int poleHeight = 100;
        final int poleWidth = 10;
        final int poleOffsetX = 5;
        final int flagWidth = 55;
        final int flagHeight = 45;
        g2d.setColor(Color.lightGray);
        drawShape(new Rectangle(new Vector2D(position.getX() - poleOffsetX, position.getY() - poleHeight),
                poleWidth, poleHeight), g2d, null);
        g2d.setColor(Color.RED);
        drawShape(new Triangle(new Vector2D(position.getX() + poleOffsetX, position.getY() - poleHeight),
                new Vector2D(position.getX() + flagWidth, position.getY() - poleHeight + flagHeight / 2),
                new Vector2D(position.getX() + poleOffsetX, position.getY() - poleHeight + flagHeight)),
                g2d, null);
    }

    /**
     * Draws a shape with the given texture. This method handles different shape
     * types by checking their concrete type and applying the appropriate drawing
     * logic.
     *
     * @param shape   the shape to draw
     * @param g2d     the graphics context
     * @param texture the texture to apply
     */
    private void drawShape(final Shape shape, final Graphics2D g2d, final BufferedImage texture) {
        final java.awt.Paint originalPaint = g2d.getPaint();
        if (texture != null) {
            final Rectangle2D anchor = new Rectangle2D.Double(0, 0, LOGICAL_WIDTH, LOGICAL_HEIGHT);
            final TexturePaint paint = new TexturePaint(texture, anchor);
            g2d.setPaint(paint);
        }

        if (shape instanceof Rectangle rect) {
            g2d.fill(new Rectangle2D.Double(rect.position().getX(), rect.position().getY(), rect.width(),
                    rect.height()));
        } else if (shape instanceof Circle circ) {
            g2d.fillOval((int) circ.position().getX() - (int) circ.radius(),
                    (int) circ.position().getY() - (int) circ.radius(), (int) circ.radius() * 2,
                    (int) circ.radius() * 2);
        } else if (shape instanceof Triangle tria) {
            final int[] xPoints = {
                    (int) tria.vertex1().getX(),
                    (int) tria.vertex2().getX(),
                    (int) tria.vertex3().getX(),
            };
            final int[] yPoints = {
                    (int) tria.vertex1().getY(),
                    (int) tria.vertex2().getY(),
                    (int) tria.vertex3().getY(),
            };
            g2d.fillPolygon(xPoints, yPoints, 3);
        } else if (shape instanceof Oval oval) {
            g2d.fillOval((int) (oval.position().getX() - oval.radiusX()),
                    (int) (oval.position().getY() - oval.radiusY()),
                    (int) (oval.radiusX() * 2),
                    (int) (oval.radiusY() * 2));
        } else {
            // For future shape types, add more cases here
            throw new UnsupportedOperationException("Drawing not implemented for shape type: " + shape.getClass());
        }

        if (!Objects.isNull(texture)) {
            g2d.setPaint(originalPaint);
        }
    }
}
