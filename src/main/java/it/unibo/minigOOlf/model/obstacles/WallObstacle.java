package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Represents a rectangular wall obstacle in the minigolf course.
 * This obstacle is defined by its top-left position, width, and height.
 */
public class WallObstacle extends Obstacle {

    private final double width;
    private final double height;

    /**
     * Constructs a rectangular wall.
     * @param position The 2D vector representing the top-left corner of the wall.
     * @param width The width of the wall.
     * @param height The height of the wall.
     */
    public WallObstacle(Vector2D position, double width, double height) {
        super(position);
        this.width = width;
        this.height = height;
    }

    public boolean isColliding(Ball ball) {
        return true;
    }

    public void resolveCollision(Ball ball) {}
}
