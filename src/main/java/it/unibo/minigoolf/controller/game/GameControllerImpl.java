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

/**
 * Implementation of {@link GameController}.
 * Owns all match-specific objects and orchestrates shot input,
 * physics updates and ball-stop detection each frame.
 * Internal objects are fully encapsulated — only what the view
 * and the main controller strictly need is exposed via the interface.
 *
 * @author fede
 */
public final class GameControllerImpl implements GameController {

    /** Squared speed below which the ball is considered stopped. */
    private static final double STOP_THRESHOLD_SQ = 0.5;

    private final GameState gameState;
    private final GameMapController gameMapController;
    private final ShotState shotState;
    private final PhysicsController physicsController;
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
        this.gameState = gameState;
        this.gameMapController = gameMapController;
        this.shotState = shotState;
        this.physicsController = physicsController;
        // Velocity strategy is match-specific: set here, never exposed outside.
        this.physicsController.setVelocityStrategy(new BasicFrictionStrategy());
    }

    /** {@inheritDoc} */
    @Override
    public void setShotView(final ShotView shotView) {
        this.shotController = new ShotControllerImpl(
            shotState, gameState, gameMapController, shotView);
        // Enable shot input at the initial ball position.
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

        if (gameState.isBallMoving()) {
            physicsController.update(deltaTime);

            final Vector2D vel = gameMapController.getBallController().getVelocity();
            if (vel.getNormSquared() < STOP_THRESHOLD_SQ) {
                gameMapController.getBallController().updateVelocity(new Vector2D(0, 0));
                gameState.onBallStopped();
                shotController.onBallStopped(
                    gameMapController.getBallController().getPosition());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public it.unibo.minigoolf.model.logic.ShotState getShotState() {
        return shotState;
    }

    /** {@inheritDoc} */
    @Override
    public GameMapController getGameMapController() {
        return gameMapController;
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentPlayerName() {
        return gameState.getCurrentPlayer().getName();
    }

    /** {@inheritDoc} */
    @Override
    public ShotView getShotView() {
        return shotController != null ? shotController.getShotView() : null;
    }
}
