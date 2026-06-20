package it.unibo.minigoolf.controller.holecontroller;

import it.unibo.minigoolf.model.hole.Hole;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Implementation of the HoleController interface that manages the hole in the
 * mini-gOOlf game.
 * It provides methods to retrieve the hole's shape, position, and radius.
 * 
 * @author jack
 */
public final class HoleControllerImpl implements HoleController {
    private final Hole hole;

    /**
     * Constructs a new HoleControllerImpl with the specified hole.
     *
     * @param hole the hole to manage
     */
    public HoleControllerImpl(final Hole hole) {
        this.hole = hole;
    }

    @Override
    public Shape getShape() {
        return new Circle(hole.getPosition(), hole.getRadius());
    }

    @Override
    public Vector2D getPosition() {
        return hole.getPosition();
    }

    @Override
    public double getRadius() {
        return hole.getRadius();
    }

}
