package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.util.Vector2D;

public class PhysicsControllerImpl implements PhysicsController {
    private final Ball ball;
    private final GameMap gameMap;
    
    public PhysicsControllerImpl(Ball ball, GameMap gameMap) {
        this.ball = ball;
        this.gameMap = gameMap;
    }

    @Override
    public void update(double deltaTime) {
        // apply friction.
        Vector2D velocity = ball.getVelocity();
        Vector2D friction = velocity.scalarMultiply(-0.14); // simple linear friction
        Vector2D newVelocity = velocity.add(friction.scalarMultiply(deltaTime));
        ball.setVelocity(newVelocity);

        // update ball position based on velocity,

        Vector2D newPosition = ball.getPosition().add(ball.getVelocity().scalarMultiply(deltaTime));
        ball.setPosition(newPosition);


        // collisioni? 
    }

}
