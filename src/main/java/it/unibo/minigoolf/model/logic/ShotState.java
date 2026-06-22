package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;

import java.util.Optional;

/**
 * Model class that holds the state of the current shot input.
 * Decouples shot management from {@link GameState} and from the view layer.
 * The flow is:
 * The view calls {@link #updateIntent(Vector2D)} while the user drags.
 * The view calls {@link #confirmShot()} on mouse release.
 * The controller calls {@link #consume()} each tick to retrieve the shot.
 * 
 * @author fedesparvo1-a11y
 */
public final class ShotState {

    /** Maximum shot power in logical pixels. */
    public static final double MAX_POWER = 150.0;

    // Minimum squared power for a shot to be accepted.
    private static final double MIN_SQUARE_POWER = 100.0;

    // Current drag vector set by the view while the user is dragging.
    private Vector2D intent;

    /**
     * True only after the user has released the mouse with a valid drag.
     * Prevents the controller from consuming the shot mid-drag.
     */
    private boolean shotReady;

    // Current ball centre in logical coordinates, used to enable input.
    private Vector2D ballPosition;

    /**
     * Creates an empty shot state with no pending shot and no ball position.
     */
    public ShotState() {
        // initial state: no intent, no ball position, shot not ready
    }

    /**
     * Updates the current drag vector.
     * Called by the view on every mouse-drag event.
     *
     * @param direction the current drag vector (already negated to shot direction)
     */
    public synchronized void updateIntent(final Vector2D direction) {
        this.intent = direction;
        this.shotReady = false;
    }

    /**
     * Confirms the shot on mouse release.
     * Ignored if the current intent is below the minimum power threshold.
     */
    public synchronized void confirmShot() {
        if (isValid()) {
            this.shotReady = true;
        }
    }

    /**
     * Consumes and returns the pending shot if one is ready.
     * The shot vector is clamped to {@link #MAX_POWER}.
     * Clears internal state after consumption.
     *
     * @return an Optional containing the clamped shot vector, or empty if none is ready
     */
    public synchronized Optional<Vector2D> consume() {
        if (shotReady && isValid()) {
            final Vector2D shot = intent.clampedTo(MAX_POWER);
            intent = null;
            shotReady = false;
            return Optional.of(shot);
        }
        return Optional.empty();
    }

    /**
     * Returns the current drag intent vector, or empty if no drag is in progress.
     * Used by the view to draw the indicator.
     *
     * @return an Optional containing the current drag vector
     */
    public synchronized Optional<Vector2D> getIntent() {
        return Optional.ofNullable(intent);
    }

    /**
     * Returns the current ball position in logical coordinates.
     *
     * @return an Optional containing the ball position, or empty if not set
     */
    public synchronized Optional<Vector2D> getBallPosition() {
        return Optional.ofNullable(ballPosition);
    }

    /**
     * Sets the ball position and resets shot state.
     * Called when the ball stops and a new turn begins.
     *
     * @param position the new ball centre in logical coordinates
     */
    public synchronized void reset(final Vector2D position) {
        this.ballPosition = position;
        this.intent = null;
        this.shotReady = false;
    }

    /**
     * Returns true if the current intent is above the minimum power threshold.
     *
     * @return true if the shot is valid
     */
    public synchronized boolean isValid() {
        return intent != null && intent.getNormSquared() >= MIN_SQUARE_POWER;
    }
}
