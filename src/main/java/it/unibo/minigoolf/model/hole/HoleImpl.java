package it.unibo.minigoolf.model.hole;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;

public class HoleImpl implements Hole {
    private final Vector2D position;
    private final Circle circle;

    /**
     * Constructs a new HoleImpl with the specified position and radius.
     * 
     * @param position the position of the hole in the game coordinate system
     * @param radius   the radius of the hole in game units
     */
    public HoleImpl(Vector2D position, double radius) {
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
