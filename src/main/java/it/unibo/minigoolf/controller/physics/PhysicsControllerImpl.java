package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.model.physics.PhysicsEngine;
import it.unibo.minigoolf.model.physics.velocity.BallVelocityStrategy;

/**
 * Implementation of the {@link PhysicsController} interface that manages the
 * physics of the mini-gOOlf game.
 *
 * @author jack
 */
public final class PhysicsControllerImpl implements PhysicsController {

    private final GameMapController gameMapController;

    /**
     * Constructs a new PhysicsControllerImpl with the specified game map
     * controller.
     *
     * @param gameMapController the controller used to access map state for physics
     */
    public PhysicsControllerImpl(final GameMapController gameMapController) {
        this.gameMapController = gameMapController;
    }

    @Override
    public void update(final double deltaTime) {
        final BallControllerAdapter ballAdapter = new BallControllerAdapter(gameMapController.getBallController());
        PhysicsEngine.update(
                ballAdapter,
                gameMapController.getSurfaceAt(ballAdapter.getPosition()),
                gameMapController.getObstacleController().getObstacles(),
                deltaTime);
    }

    @Override
    public void setVelocityStrategy(final BallVelocityStrategy strategy) {
        PhysicsEngine.setVelocityStrategy(strategy);
    }

}
