package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.map.GameMap;

public class PhysicsControllerImpl implements PhysicsController {
    private final Ball ball;
    private final GameMap gameMap;
    
    public PhysicsControllerImpl(Ball ball, GameMap gameMap) {
        this.ball = ball;
        this.gameMap = gameMap;
    }

    @Override
    public void update(double deltaTime) {
        // update ball position based on velocity,
        // apply friction.

        // collisioni? 
    }

}
