package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.model.physics.PhysicsEngine;
import it.unibo.minigoolf.model.physics.velocity.BallVelocityStrategy;

/**
 * Implementation of the {@link PhysicsController} interface that manages the
 * physics of the mini golf game, including updating the ball's velocity and
 * position based on the surfaces it interacts with. Delegates the actual
 * physics calculations to {@link PhysicsEngine}.
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

    /** {@inheritDoc} */
    @Override
    public void update(final double deltaTime) {
        final BallControllerAdapter ballAdapter = new BallControllerAdapter(gameMapController.getBallController());
        PhysicsEngine.update(
                ballAdapter,
                gameMapController.getSurfaceAt(ballAdapter.getPosition()),
                gameMapController.getObstacles(),
                deltaTime);
    }

    /** {@inheritDoc} */
    @Override
    public void setVelocityStrategy(final BallVelocityStrategy strategy) {
        PhysicsEngine.setVelocityStrategy(strategy);
    }

}
