package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.shot.ShotController;
import it.unibo.minigoolf.controller.shot.ShotControllerImpl;
import it.unibo.minigoolf.controller.shot.ShotView;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.util.Vector2D;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Implementation of {@link GameController}.
 * Uses functional callbacks instead of storing stateful collaborators directly,
 *
 * @author fede
 */
public final class GameControllerImpl implements GameController {

    /** Squared speed below which the ball is considered stopped. */
    private static final double STOP_THRESHOLD_SQ = 0.5;

    private final GameMapController gameMapController;
    private final ShotState shotState;

    /** {@code gameState::isBallMoving} — avoids storing GameState directly. */
    private final BooleanSupplier ballMovingChecker;

    /** {@code gameState::onBallStopped} — avoids storing GameState directly. */
    private final Runnable ballStoppedNotifier;

    /** {@code gameState::setPendingShot} — passed to ShotControllerImpl. */
    private final Consumer<Vector2D> pendingShotSubmitter;

    /** {@code gameState::update} — passed to ShotControllerImpl. */
    private final Supplier<Optional<Vector2D>> shotUpdater;

    /** {@code physicsController::update} — avoids storing PhysicsController directly. */
    private final Consumer<Double> physicsUpdater;

    /** {@code () -> gameState.getCurrentPlayer().getName()} — avoids storing GameState. */
    private final Supplier<String> currentPlayerNameSupplier;

    private ShotController shotController;

    /**
     * @param gameState         the central game logic
     * @param gameMapController the map controller facade
     * @param shotState         the shot input state
     * @param physicsController the physics engine
     */
    public GameControllerImpl(
            final GameState gameState,
            final GameMapController gameMapController,
            final ShotState shotState,
            final PhysicsController physicsController) {
        this.gameMapController = gameMapController;
        this.shotState = shotState;
        // Extract only the needed behaviors from gameState — avoids EI2.
        this.ballMovingChecker = gameState::isBallMoving;
        this.ballStoppedNotifier = gameState::onBallStopped;
        this.pendingShotSubmitter = gameState::setPendingShot;
        this.shotUpdater = gameState::update;
        this.currentPlayerNameSupplier = () -> gameState.getCurrentPlayer().getName();
        // Extract only the update behavior from physicsController — avoids EI2.
        physicsController.setVelocityStrategy(new BasicFrictionStrategy());
        this.physicsUpdater = physicsController::update;
    }

    /** {@inheritDoc} */
    @Override
    public void setShotView(final ShotView shotView) {
        this.shotController = new ShotControllerImpl(
            shotState,
            pendingShotSubmitter,
            shotUpdater,
            gameMapController,
            shotView);
        shotController.onBallStopped(
            gameMapController.getBallController().getPosition());
    }

    /** {@inheritDoc} */
    @Override
    public void updateTick(final double deltaTime) {
        if (shotController == null) {
            return;
        }
        shotController.tick();

        if (ballMovingChecker.getAsBoolean()) {
            physicsUpdater.accept(deltaTime);

            final Vector2D vel = gameMapController.getBallController().getVelocity();
            if (vel.getNormSquared() < STOP_THRESHOLD_SQ) {
                gameMapController.getBallController().updateVelocity(new Vector2D(0, 0));
                ballStoppedNotifier.run();
                shotController.onBallStopped(
                    gameMapController.getBallController().getPosition());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentPlayerName() {
        return currentPlayerNameSupplier.get();
    }

    /** {@inheritDoc} */
    @Override
    public ShotState getShotState() {
        return shotState;
    }

    /** {@inheritDoc} */
    @Override
    public GameMapController getGameMapController() {
        return gameMapController;
    }
}
