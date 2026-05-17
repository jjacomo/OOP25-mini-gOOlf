package it.unibo.minigoolf.controller.shot;

import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.util.Vector2D;

import java.awt.Point;
import java.util.Optional;

/**
 * Implementation of {@link ShotController}.
 * Coordinates {@link ShotState} (model), {@link GameState} (game logic),
 * {@link GameMapController}
 * (ball velocity), and {@link ShotView} (view interface).
 *
 * @author fede
 */
public final class ShotControllerImpl implements ShotController {

    /**
     * Converts the shot vector from logical-pixel units (max {@link ShotState#MAX_POWER})
     * to physics velocity units. Adjust this value to tune the overall shot speed.
     */
    private static final double SHOT_SCALE = 10.0;

    private final ShotState shotState;
    private final GameState gameState;
    private final GameMapController mapController;
    private final ShotView shotView;

    /**
     * @param shotState     the model holding shot intent and confirmation state
     * @param gameState     the game logic (turn management, ball-moving flag)
     * @param mapController the game map controller used to apply velocity to the ball
     * @param shotView      the view interface used to re-enable shot input
     */
    public ShotControllerImpl(
            final ShotState shotState,
            final GameState gameState,
            final GameMapController mapController,
            final ShotView shotView) {
        this.shotState = shotState;
        this.gameState = gameState;
        this.mapController = mapController;
        this.shotView = shotView;
    }

    /** {@inheritDoc} */
    @Override
    public boolean tick() {
        final Optional<Vector2D> pending = shotState.consume();
        if (pending.isPresent()) {
            gameState.setPendingShot(pending.get());
        }

        final Optional<Vector2D> shot = gameState.update();
        if (shot.isPresent()) {
            mapController.getBallController().updateVelocity(shot.get().scalarMultiply(SHOT_SCALE));
            return true;
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void onBallStopped(final Vector2D ballPosition) {
        shotState.reset(ballPosition);
        shotView.enableShot(toPoint(ballPosition));
    }

    /**
     * Converts a logical Vector2D to an AWT Point.
     *
     * @param pos the position in logical coordinates
     * @return the corresponding AWT Point
     */
    private static Point toPoint(final Vector2D pos) {
        return new Point((int) pos.getX(), (int) pos.getY());
    }
}
