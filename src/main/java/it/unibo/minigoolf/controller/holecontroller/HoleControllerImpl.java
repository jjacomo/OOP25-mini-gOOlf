package it.unibo.minigoolf.controller.holecontroller;

import it.unibo.minigoolf.model.hole.Hole;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Shape;
import java.awt.Color;

public class HoleControllerImpl implements HoleController {
    private Hole hole;

    public HoleControllerImpl(final Hole hole) {
        this.hole = hole;
    }

    @Override
    public Shape getHoleShape() {
        return new Circle(hole.getPosition(), hole.getRadius(), Color.BLACK);
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
