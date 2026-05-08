package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.physics.PhysicsEngine;
import it.unibo.minigoolf.model.physics.velocity.BallVelocityStrategy;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;

/**
 * Simple controller that delegates the physics update to the domain engine.
 *
 * <p>The controller remains lightweight and does not contain the actual
 * physics logic, which is implemented in {@link PhysicsEngine}.</p>
 */
public class PhysicsControllerImpl implements PhysicsController {

    private final GameMap gameMap;

    public PhysicsControllerImpl(final GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void update(final double deltaTime) {
        PhysicsEngine.update(gameMap, deltaTime);
    }

    @Override
    public void setVelocityStrategy(BallVelocityStrategy strategy) {
        PhysicsEngine.setVelocityStrategy(strategy);
    }

}
