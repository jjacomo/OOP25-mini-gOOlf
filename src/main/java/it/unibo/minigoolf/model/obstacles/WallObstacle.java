package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a rectangular wall obstacle in the minigolf course.
 * This obstacle is defined by its top-left position, width, and height.
 */
public final class WallObstacle extends AbstractObstacle implements Obstacle {
    private static final double MIN_WIDTH = 5.0;
    private static final double MAX_WIDTH = 100.0;
    private static final double MIN_HEIGHT = 5.0;
    private static final double MAX_HEIGHT = 100.0;

    private final double minX;
	private final double maxX;
	private final double minY;
	private final double maxY;
    private final Vector2D normalLeft;
	private final Vector2D normalRight;
	private final Vector2D normalTop;
	private final Vector2D normalBottom;

	/**
     * Constructs a rectangular wall.
     * 
     * @param position the 2D vector representing the top-left corner of the wall
     * @param width    the width of the wall
     * @param height   the height of the wall
	 * @throws IllegalArgumentException if the width is outside [MIN_WIDTH, 
	 *			MAX_WIDTH] or if the height is outside [MIN_HEIGHT, MAX_HEIGHT]
     */
    public WallObstacle(final Vector2D position, final double width, final double height) {
        super(position);
        if (width < MIN_WIDTH || width > MAX_WIDTH || height < MIN_HEIGHT || height > MAX_HEIGHT) {
            throw new IllegalArgumentException("Invalid dimensions. Width: " + width
			+ "; Height: " + height + ".\nWidth must be between [" + MIN_WIDTH + ", "
			+ MAX_WIDTH + "].\n Height must be between [" + MIN_HEIGHT + ", "
			+ MAX_HEIGHT + "].");
		}
        this.minX = position.getX();
        this.maxX = position.getX() + width;
        this.minY = position.getY();
        this.maxY = position.getY() + height;
        this.normalLeft   = new Vector2D(-1, 0);
        this.normalRight  = new Vector2D( 1, 0);
        this.normalTop    = new Vector2D( 0,-1);
        this.normalBottom = new Vector2D( 0, 1);
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
        final double closestX = Math.max(minX, Math.min(position.getX(), maxX));
        final double closestY = Math.max(minY, Math.min(position.getY(), maxY));
        final double dx = position.getX() - closestX;
        final double dy = position.getY() - closestY;
        final double distSq = dx * dx + dy * dy;
        return distSq <= ball.getRadius() * ball.getRadius();
    }

    /**
     * Resolves the physical collision between the ball and the obstacle calculating
     * the bounce based on the obstacle's shape and applies the new direction to the ball.
     * 
     * @param ball the Ball object that has collided with the obstacle
     */
    @Override
    public void resolveCollision(final Ball ball) {
        final Vector2D ballPosition = ball.getPosition();
        final double positionX = ballPosition.getX();
        final double positionY = ballPosition.getY();
        final double closestX = Math.max(minX, Math.min(positionX, maxX));
        final double closestY = Math.max(minY, Math.min(positionY, maxY));
        final boolean inside = (positionX > minX && positionX < maxX && positionY > minY && positionY < maxY);
        Vector2D normal;
        double penetrationDepth;

        if (inside) {
            final double distanceLeft   = positionX - minX;
            final double distanceRight  = maxX - positionX;
            final double distanceTop    = positionY - minY;
            final double distanceBottom = maxY - positionY;
            double minDistance = Math.min(Math.min(distanceLeft, distanceRight), Math.min(distanceTop, distanceBottom));
			
            if (minDistance == distanceLeft) {
				normal = normalLeft;
			} else if (minDistance == distanceRight) {
				normal = normalRight;
            } else if (minDistance == distanceTop) {
				normal = normalTop;
            } else {
				normal = normalBottom;
			}
            penetrationDepth = ball.getRadius() + minDistance;
        } else {
            final Vector2D closestPoint = new Vector2D(closestX, closestY);
            final Vector2D toCenter = ballPosition.subtract(closestPoint);
            final double distance = toCenter.getNorm();
            penetrationDepth = ball.getRadius() - distance;

            if (distance < EPSILON) {
                final boolean onLeft   = Math.abs(positionX - minX) < EPSILON;
                final boolean onRight  = Math.abs(positionX - maxX) < EPSILON;
                final boolean onTop    = Math.abs(positionY - minY) < EPSILON;
                final boolean onBottom = Math.abs(positionY - maxY) < EPSILON;
                if ((onLeft || onRight) && (onTop || onBottom)) {
                    double accX = 0, accY = 0;
                    if (onLeft) {
						accX -= 1;
					}
                    if (onRight) {
						accX += 1;
					}
                    if (onTop)  {
						accY -= 1;
					}
                    if (onBottom) {
						accY += 1;
					}
                    normal = new Vector2D(accX, accY).normalize();
                } else {
                    if (onLeft) {
						normal = normalLeft;
					}
                    else if (onRight) {
						normal = normalRight;
					}
                    else if (onTop) {
						normal = normalTop;
					}
                    else {
						normal = normalBottom;
					}
                }
            } else {
                normal = toCenter.normalize();
            }
        }

        if (penetrationDepth > 0) {
            correctPosition(ball, ballPosition, normal, penetrationDepth);
        }
        reflectVelocity(ball, normal);
    }
}
