package it.unibo.minigoolf.util.shapes;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public record Circle(Vector2D position, double radius) implements Shape{

    @Override
    public boolean contains(Vector2D pos) {
        return Vector2D.distance(position, pos) < radius;
    }

}
