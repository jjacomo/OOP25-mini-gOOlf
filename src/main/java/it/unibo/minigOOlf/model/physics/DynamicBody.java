package it.unibo.minigoolf.model.physics;

// consider using a custom Vector2D to avoid external dependencies
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Represents a dynamic physical body in 2D space with a position and velocity.
 *
 * <p>This interface is used by physics simulation components to read and update
 * the motion state of moving objects.</p>
 */
public interface DynamicBody {

    /**
     * Returns the current position of the body in 2D space.
     *
     * @return the position as a {@link Vector2D}
     */
    Vector2D getPosition();

    /**
     * Returns the current velocity of the body in 2D space.
     *
     * @return the velocity as a {@link Vector2D}
     */
    Vector2D getVelocity();

    /**
     * Updates the body's velocity.
     *
     * @param velocity the new velocity as a {@link Vector2D}
     */
    void setVelocity(Vector2D velocity);
}
