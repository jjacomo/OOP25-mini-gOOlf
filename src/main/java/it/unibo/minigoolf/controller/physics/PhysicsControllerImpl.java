package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.physics.PhysicsEngine;
import it.unibo.minigoolf.model.physics.velocity.BallVelocityStrategy;

/**
 * Simple controller that delegates the physics update to the domain engine.
 *
 * <p>The controller remains lightweight and does not contain the actual
 * physics logic, which is implemented in {@link PhysicsEngine}.</p>
 */
public final class PhysicsControllerImpl implements PhysicsController {

    private final GameMap gameMap;

    public PhysicsControllerImpl(final GameMap gameMap) {
        this.gameMap = gameMap;
    }

    @Override
    public void update(final double deltaTime) {
        PhysicsEngine.update(gameMap, deltaTime);
        //TODO
        // PhysicsEngine.update(gameMapController, deltaTime);
        // vedi commenti in PhysicsEngine: bisognerebbe rifare tutte le classi che
        // accedono direttamente al GameMap (e non al controller)
    }

    @Override
    public void setVelocityStrategy(final BallVelocityStrategy strategy) {
        PhysicsEngine.setVelocityStrategy(strategy);
    }

}
