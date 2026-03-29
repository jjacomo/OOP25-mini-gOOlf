package it.unibo.minigOOlf.view.input;

import it.unibo.minigOOlf.util.Vec2D;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse listener that translates drag gestures into shot vectors.
 *
 * @author fede
 */
public class ShotListener extends MouseAdapter implements ShotInput {

    private final ShotVisualizer visualizer;

    //Where the drag started (null when no drag is in progress)
    private Point startingPoint = null;

    //Whether this listener is accepting input
    private boolean enable = false;

    /**
     * @param visualizer the panel that draws the indicator and handles shots
     */
    public ShotListener(ShotVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    @Override
    public synchronized void mousePressed(MouseEvent e) {
        if (this.enable) {
            this.startingPoint = e.getPoint();
        }
    }

    @Override
    public synchronized void mouseDragged(MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            // Build the raw drag vector (start → current cursor position)
            Vec2D raw = new Vec2D(this.startingPoint, e.getPoint());
            // Negate it: dragging the club backward means shooting the ball forward
            Vec2D shotDirection = raw.getOppositeVector();
            this.visualizer.updateShotIntent(shotDirection);
        }
    }

    @Override
    public synchronized void mouseReleased(MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            this.visualizer.shoot();
            this.startingPoint = null;
        }
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
        if (!enable) {
            // Clear any in-progress drag so stale state does not bleed into the next turn.
            this.startingPoint = null;
        }
    }
}