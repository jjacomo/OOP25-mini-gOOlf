package it.unibo.minigoolf.controller.shot;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.util.Vector2D;

import java.awt.Point;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Implementation of {@link ShotController}.
 * Uses functional callbacks to avoid EI2 warnings while keeping coupling minimal.
 *
 * @author fedesparvo1-a11y
 */
public final class ShotControllerImpl implements ShotController {

    /**
     * Converts the shot vector from logical pixel units (max {@link ShotState#MAX_POWER})
     * to physics velocity units.
     */
    private static final double SHOT_SCALE = 10.0;

    private final ShotState shotState;
    private final Consumer<Vector2D> pendingShotSubmitter;
    private final Supplier<Optional<Vector2D>> shotUpdater;
    private final GameMapController gameMapController;
    private final ShotView shotView;

    /**
     * Creates the shot controller wiring the model to the game logic.
     * 
     * @param shotState            the model holding shot intent and confirmation state
     * @param pendingShotSubmitter callback to submit a pending shot to the turn logic
     * @param shotUpdater          callback to consume the confirmed shot each tick
     * @param gameMapController    the map controller facade
     * @param shotView             the view interface used to re-enable shot input
     */
    public ShotControllerImpl(
            final ShotState shotState,
            final Consumer<Vector2D> pendingShotSubmitter,
            final Supplier<Optional<Vector2D>> shotUpdater,
            final GameMapController gameMapController,
            final ShotView shotView) {
        this.shotState = shotState;
        this.pendingShotSubmitter = pendingShotSubmitter;
        this.shotUpdater = shotUpdater;
        this.gameMapController = gameMapController;
        this.shotView = shotView;
    }

    /** {@inheritDoc} */
    @Override
    public boolean tick() {
        shotState.consume().ifPresent(pendingShotSubmitter::accept);

        return shotUpdater.get()
            .map(shot -> {
                gameMapController.getBallController()
                    .updateVelocity(shot.scalarMultiply(SHOT_SCALE));
                return true;
            })
            .orElse(false);
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
