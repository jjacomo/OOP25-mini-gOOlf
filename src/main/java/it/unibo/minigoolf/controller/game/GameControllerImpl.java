package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.shot.ShotController;
import it.unibo.minigoolf.controller.shot.ShotControllerImpl;
import it.unibo.minigoolf.controller.shot.ShotView;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.HoleChecker;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;//TODO: implementa switch automatico tra strategie di attrito a seconda del terreno
import it.unibo.minigoolf.util.Vector2D;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Implementation of {@link GameController}.
 * Uses functional callbacks instead of storing stateful collaborators directly.
 *
 * @author fede
 */
public final class GameControllerImpl implements GameController {
 
    /**
     * Maximum squared speed at which the ball can enter the hole while still moving.
     * Set to (MAX_POWER * SHOT_SCALE / 3)² = (1500 / 3)² = 500² = 250_000.
     * Above this speed the ball is too fast to fall in.
     */
    private static final double HOLE_ENTRY_MAX_SPEED_SQ = 250_000.0;
 
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
 
    /** Checks whether the ball has reached the hole. */
    private final HoleChecker holeChecker;
 
    /** Called when the ball enters the hole. Default is a no-op. */
    private Runnable onHoleCompleted = () -> { };
 
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
        // Build the hole checker from the map controller — no direct reference stored.
        this.holeChecker = new HoleChecker(
            gameMapController.getHoleController().getPosition(),
            gameMapController.getHoleController().getRadius());
    }
 
    /** {@inheritDoc} */
    @Override
    public void setOnHoleCompleted(final Runnable onHoleCompleted) {
        this.onHoleCompleted = onHoleCompleted;
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
 
            final Vector2D ballPos = gameMapController.getBallController().getPosition();
            final Vector2D vel = gameMapController.getBallController().getVelocity();
            final boolean slowEnoughForHole = vel.getNormSquared() <= HOLE_ENTRY_MAX_SPEED_SQ;
 
            if (!gameMapController.getBallController().isBallMoving()) {
                // Ball has stopped — check hole then re-enable input.
                ballStoppedNotifier.run();
                if (holeChecker.isBallInHole(ballPos)) {
                    onHoleCompleted.run();
                } else {
                    shotController.onBallStopped(ballPos);
                }
            } else if (slowEnoughForHole && holeChecker.isBallInHole(ballPos)) {
                // Ball is still moving but slow enough and over the hole.
                ballStoppedNotifier.run();
                onHoleCompleted.run();
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
