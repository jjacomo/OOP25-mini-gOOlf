package it.unibo.minigoolf.model.obstacles;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a circular obstacle in the minigolf course.
 * This obstacle is defined by its center position and radius.
 * 
 * @author Mattia
 */
public final class RoundObstacle extends AbstractObstacle {
    private static final double MIN_RADIUS = 5.0;
    private static final double MAX_RADIUS = 150.0;
    private final double radius;
    private final Circle shape;

    /**
     * Constructs a circular obstacle.
     *
     * @param radius   the radius of the circular obstacle
     * @param position the position of the obstacle
     * @throws IllegalArgumentException if radius is not between [MIN_RADIUS,
     *                                  MAX_RADIUS]
     */
    public RoundObstacle(final Vector2D position, final double radius) {
        this(position, radius, DEFAULT_BOUNCINESS);
    }

    /**
     * Constructs a circular obstacle.
     *
     * @param radius   the radius of the circular obstacle
     * @param position the position of the obstacle
     * @param bounciness the bounciness of the bouncy circular obstacle
     * @throws IllegalArgumentException if radius is not between [MIN_RADIUS,
     *                                  MAX_RADIUS]
     */
    public RoundObstacle(final Vector2D position, final double radius, 
                         final double bounciness) {
        super(position, bounciness);
        if (radius < MIN_RADIUS || radius > MAX_RADIUS) {
            throw new IllegalArgumentException("The radius must be between "
                    + MIN_RADIUS + " and " + MAX_RADIUS);
        }
        this.radius = radius;
        this.shape = new Circle(position, this.radius);
    }

    /**
     * Resolves the physical collision between the ball and the obstacle calculating
     * the bounce based on the obstacle's shape and applies the new direction to the
     * ball.
     * 
     * @param ball the Ball object that has collided with the obstacle
     */
    @Override
    public void resolveCollision(final Ball ball) {
        final Vector2D ballPos = ball.getPosition();
        final Vector2D toBall = ballPos.subtract(getPosition());
        final double distance = toBall.getNorm(); 
        final double penetrationDepth = getSumRadii(ball) - distance;
        final Vector2D normal = toBall.normalize();
        correctPosition(ball, ballPos, normal, penetrationDepth);
        reflectVelocity(ball, normal);
    }

    /**
     * Calculates the sum of the ball's radius and this obstacle's radius.
     *
     * @param ball the ball to check
     * @return the sum of the radii
     */
    private double getSumRadii(final Ball ball) {
        return ball.getRadius() + this.radius;
    }

    /**
     * Calculates the distance between the center of the ball and the center
     * of this obstacle.
     *
     * @param ball the ball to check
     * @return the distance between centers
     */
    private double getCenterDistance(final Ball ball) {
        return ball.getPosition().distance(getPosition());
    }

    /** {@inheritDoc} */
    @Override
    public boolean isColliding(final Ball ball) {
        return getCenterDistance(ball) <= getSumRadii(ball);
    }

    /** {@inheritDoc} */
    @Override
    public double getPenetrationDepth(final Ball ball) {
        final double distance = getCenterDistance(ball);
        final double sumRadii = getSumRadii(ball);

        return distance < sumRadii ? sumRadii - distance : 0.0;
    }

    /** {@inheritDoc} */
    @Override
    public Circle getShape() {
        return this.shape;
    }
}
