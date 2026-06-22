package it.unibo.minigoolf.view.input;

import java.awt.Point;

/**
 * Provides coordinate conversion and ball-proximity checking for shot input.
 * Keeping this separate from {@link ShotVisualizer} allows {@link ShotListener}
 * to depend only on the narrow interface it needs, avoiding EI2 warnings.
 *
 * @author fedesparvo1-a11y
 */
public interface ShotCoordinateConverter {

    /**
     * Converts a physical mouse point to logical coordinates.
     *
     * @param physical the raw point from a MouseEvent
     * @return the point in logical space
     */
    Point toLogical(Point physical);

    /**
     * Returns true if the given logical point is within {@code radius} pixels
     * of the ball centre.
     *
     * @param logical the point to test, in logical coordinates
     * @param radius  maximum allowed distance in logical pixels
     * @return true if the point is close enough to the ball
     */
    boolean isNearBall(Point logical, double radius);
}
