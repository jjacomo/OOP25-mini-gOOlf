package it.unibo.minigoolf.util;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public record Rectangle(Vector2D position, double width, double height) {

    public boolean contains(Vector2D position) {
        return position.getX() >= this.position.getX() && position.getX() <= this.position.getX() + width &&
               position.getY() >= this.position.getY() && position.getY() <= this.position.getY() + height;
    }
}