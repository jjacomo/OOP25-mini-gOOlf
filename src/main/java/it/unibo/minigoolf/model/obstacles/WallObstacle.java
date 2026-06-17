package it.unibo.minigoolf.model.obstacles;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Rectangle;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a rectangular wall obstacle in the minigolf course.
 * This obstacle is defined by its top-left position, width, and height.
 * 
 * @author Mattia
 */
public final class WallObstacle extends AbstractObstacle {
    private static final double MIN_WIDTH = 5.0;
    private static final double MAX_WIDTH = 1920.0;
    private static final double MIN_HEIGHT = 5.0;
    private static final double MAX_HEIGHT = 1080.0;

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final Vector2D normalLeft;
    private final Vector2D normalRight;
    private final Vector2D normalTop;
    private final Vector2D normalBottom;
    private final Rectangle shape;

    /**
     * Constructs a rectangular wall.
     * 
     * @param position the 2D vector representing the top-left corner of the wall
     * @param width    the width of the wall
     * @param height   the height of the wall
     * @throws IllegalArgumentException if the width is outside [MIN_WIDTH,
     *                                  MAX_WIDTH] or if the height is outside
     *                                  [MIN_HEIGHT, MAX_HEIGHT]
     */
    public WallObstacle(final Vector2D position, final double width, 
                        final double height) {
        this(position, width, height, DEFAULT_BOUNCINESS);
    }

    /**
     * Constructs a bouncy rectangular wall.
     * 
     * @param position the 2D vector representing the top-left corner of the wall
     * @param width    the width of the wall
     * @param height   the height of the wall
     * @param bounciness the bounciness of the bouncy rectangular wall
     * @throws IllegalArgumentException if the width is outside [MIN_WIDTH,
     *                                  MAX_WIDTH] or if the height is outside
     *                                  [MIN_HEIGHT, MAX_HEIGHT]
     */
    public WallObstacle(final Vector2D position, final double width, final double height, 
                        final double bounciness) {
        super(position, bounciness);
        if (width < MIN_WIDTH || width > MAX_WIDTH || height < MIN_HEIGHT
             || height > MAX_HEIGHT) {
            throw new IllegalArgumentException("Invalid dimensions. Width: " + width
                    + "; Height: " + height + ".\nWidth must be between [" + MIN_WIDTH
                    + ", " + MAX_WIDTH + "].\n Height must be between [" + MIN_HEIGHT
                    + ", " + MAX_HEIGHT + "].");
        }
        this.minX = position.getX();
        this.maxX = position.getX() + width;
        this.minY = position.getY();
        this.maxY = position.getY() + height;
        this.normalLeft = new Vector2D(-1, 0);
        this.normalRight = new Vector2D(1, 0);
        this.normalTop = new Vector2D(0, -1);
        this.normalBottom = new Vector2D(0, 1);
        this.shape = new Rectangle(position, width, height);
    }

    /**
     * Checks if the ball is colliding with the obstacle's boundaries.
     *
     * @param ball the ball object to be checked for collisions
     * @return true if the ball's boundaries touch the obstacle, false otherwise
     */
    @Override
    public boolean isColliding(final Ball ball) {
        final Vector2D position = ball.getPosition();
        final Vector2D closestPoint = getClosestPoint(position);
        final double dx = position.getX() - closestPoint.getX();
        final double dy = position.getY() - closestPoint.getY();
        return (dx * dx + dy * dy) <= ball.getRadius() * ball.getRadius();
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
        final Vector2D pos = ball.getPosition();
        final Vector2D normal;
        final double penetrationDepth;

        if (isInside(pos)) {
            normal = getInsideNormal(pos);
            penetrationDepth = ball.getRadius() + getMinDistanceToEdge(pos);
        } else {
            final Vector2D closestPoint = getClosestPoint(pos);
            final Vector2D toCenter = pos.subtract(closestPoint);
            final double distance = toCenter.getNorm();
            penetrationDepth = ball.getRadius() - distance;

            if (distance < EPSILON) {
                normal = getBoundaryNormal(pos);
            } else {
                normal = toCenter.normalize();
            }
        }

        if (penetrationDepth > 0) {
            correctPosition(ball, pos, normal, penetrationDepth);
        }
        reflectVelocity(ball, normal);
    }

    /**
     * Finds the closest point on the rectangle's boundary or within its surface
     * to the given position.
     *
     * @param position the position to project onto the rectangle
     * @return a new {@link Vector2D} representing the closest point
     */
    private Vector2D getClosestPoint(final Vector2D position) {
        final double closestX = Math.max(minX, Math.min(position.getX(), maxX));
        final double closestY = Math.max(minY, Math.min(position.getY(), maxY));
        return new Vector2D(closestX, closestY);
    }

    /**
     * Checks if a given position is strictly inside the rectangle's boundaries.
     *
     * @param position the position to check
     * @return true if the position is inside the boundaries, false otherwise
     */
    private boolean isInside(final Vector2D position) {
        return position.getX() > minX && position.getX() < maxX 
            && position.getY() > minY && position.getY() < maxY;
    }

    /**
     * Calculates the minimum perpendicular distance from a position inside the
     * rectangle to its closest edge.
     *
     * @param pos the position inside the rectangle
     * @return the minimum distance to an edge
     */
    private double getMinDistanceToEdge(final Vector2D pos) {
        final double distLeft = pos.getX() - minX;
        final double distRight = maxX - pos.getX();
        final double distTop = pos.getY() - minY;
        final double distBottom = maxY - pos.getY();
        return Math.min(Math.min(distLeft, distRight), Math.min(distTop, distBottom));
    }

    /**
     * Determines the collision normal vector when the ball's center is inside
     * the obstacle, pointing outward from the closest edge.
     *
     * @param pos the position of the ball's center
     * @return the outward normal vector corresponding to the closest edge
     */
    private Vector2D getInsideNormal(final Vector2D pos) {
        final double distLeft = pos.getX() - minX;
        final double distRight = maxX - pos.getX();
        final double distTop = pos.getY() - minY;
        final double distBottom = maxY - pos.getY();
        final double minDist = Math.min(Math.min(distLeft, distRight),
                               Math.min(distTop, distBottom));

        if (Math.abs(minDist - distLeft) < EPSILON) {
            return normalLeft;
        }
        if (Math.abs(minDist - distRight) < EPSILON) {
            return normalRight;
        }
        if (Math.abs(minDist - distTop) < EPSILON) {
            return normalTop;
        }

        return normalBottom;
    }

    /**
     * Determines the collision normal vector when the ball hits exactly on a
     * boundary edge or corner of the rectangle.
     *
     * @param pos the position of the ball's center
     * @return the normalized vector representing the collision normal
     */
    private Vector2D getBoundaryNormal(final Vector2D pos) {
        final boolean onLeft = Math.abs(pos.getX() - minX) < EPSILON;
        final boolean onRight = Math.abs(pos.getX() - maxX) < EPSILON;
        final boolean onTop = Math.abs(pos.getY() - minY) < EPSILON;
        final boolean onBottom = Math.abs(pos.getY() - maxY) < EPSILON;

        if ((onLeft || onRight) && (onTop || onBottom)) {
            final double accX = onLeft ? -1.0 : 1.0;
            final double accY = onTop ? -1.0 : 1.0;
            return new Vector2D(accX, accY).normalize();
        } else if (onLeft) {
            return normalLeft;
        } else if (onRight) {
            return normalRight;
        } else if (onTop) {
            return normalTop;
        } else {
            return normalBottom;
        }
    }

    /** {@inheritDoc} */
    @Override
    public double getPenetrationDepth(final Ball ball) {
        final Vector2D pos = ball.getPosition();
        if (isInside(pos)) {
            return ball.getRadius() + getMinDistanceToEdge(pos);
        } else {
            final double distance = pos.distance(getClosestPoint(pos));
            return distance < ball.getRadius() ? ball.getRadius() - distance : 0;
        }
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getShape() {
        return this.shape;
    }
}
