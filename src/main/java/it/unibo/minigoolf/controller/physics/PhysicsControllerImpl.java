package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.model.map.GameMap;
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

    private final GameMap gameMap;

    /**
     * Constructs a new PhysicsControllerImpl with the specified game map.
     *
     * @param gameMap the game map to use for physics calculations
     */
    public PhysicsControllerImpl(final GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /** {@inheritDoc} */
    @Override
    public void update(final double deltaTime) {
        PhysicsEngine.update(gameMap, deltaTime);
        //TODO
        // PhysicsEngine.update(gameMapController, deltaTime);
        // vedi commenti in PhysicsEngine: bisognerebbe rifare tutte le classi che
        // accedono direttamente al GameMap (e non al controller)
    }

    /** {@inheritDoc} */
    @Override
    public void setVelocityStrategy(final BallVelocityStrategy strategy) {
        PhysicsEngine.setVelocityStrategy(strategy);
    }

}
