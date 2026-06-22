package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Checks whether the ball has reached the hole.
 * The ball is considered "in the hole" when the distance between its centre
 * and the hole centre is less than or equal to the hole radius.
 *
 * @author fedesparvo1-a11y
 */
public final class HoleChecker {

    private final Vector2D holePosition;
    private final double holeRadius;

    /**
     * Builds a hole checker for the given hole position and radius.
     * 
     * @param holePosition the centre of the hole in logical coordinates
     * @param holeRadius   the radius of the hole in logical coordinates
     */
    public HoleChecker(final Vector2D holePosition, final double holeRadius) {
        this.holePosition = holePosition;
        this.holeRadius = holeRadius;
    }

    /**
     * Returns true if the ball centre is within the hole radius.
     *
     * @param ballPosition the current ball centre in logical coordinates
     * @return true if the ball is in the hole
     */
    public boolean isBallInHole(final Vector2D ballPosition) {
        final double dx = ballPosition.getX() - holePosition.getX();
        final double dy = ballPosition.getY() - holePosition.getY();
        return dx * dx + dy * dy <= holeRadius * holeRadius;
    }
}
