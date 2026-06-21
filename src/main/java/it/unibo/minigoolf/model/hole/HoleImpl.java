package it.unibo.minigoolf.model.hole;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;

/**
 * Implementation of the Hole interface representing the hole in the mini-gOOlf
 * game.
 * 
 * @author jack
 */
public final class HoleImpl implements Hole {
    private final Vector2D position;
    private final Circle circle;

    /**
     * Constructs a new HoleImpl with the specified position and radius.
     * 
     * @param position the position of the hole
     * @param radius   the radius of the hole
     */
    public HoleImpl(final Vector2D position, final double radius) {
        this.position = position;
        this.circle = new Circle(position, radius);
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public double getRadius() {
        return this.circle.radius();
    }

}
