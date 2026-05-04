package it.unibo.minigoolf.controller.physics;

import java.util.List;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.util.Vector2D;

public class PhysicsControllerImpl implements PhysicsController {
    // private static final double FRICTION = 0.14; // example friction coefficient
    /**Max number of iteration to check collision between the ball and the obstacles */
    private static final int MAX_COLLISION_ITERATIONS = 3;

    private final Ball ball;
    private final GameMap gameMap;
    
    public PhysicsControllerImpl(Ball ball, GameMap gameMap) {
        this.ball = ball;
        this.gameMap = gameMap;
    }

    @Override
    public void update(double deltaTime) {
        // apply friction.
        updateBallVelocity(deltaTime);

        // update ball position based on velocity,
        updateBallPosition(deltaTime);

        // collisioni?
        handleCollisions(gameMap.getBall(), gameMap.getObstacles());
    }

    private void updateBallVelocity(double deltaTime) {
        Vector2D velocity = ball.getVelocity();
        Vector2D friction = velocity.scalarMultiply(-gameMap.getSurfaceAt(ball.getPosition()).getFriction()); 
        Vector2D newVelocity = velocity.add(friction.scalarMultiply(deltaTime));
        ball.setVelocity(newVelocity);
    }

    private void updateBallPosition(double deltaTime) {
        Vector2D newPosition = ball.getPosition().add(ball.getVelocity().scalarMultiply(deltaTime));
        ball.setPosition(newPosition);
    }

    /**
     * For every obstacle, checks if it's colliding with the ball. Them have to be
     * checked multiple times to avoid potioning errors in double collision at the same time
     * 
     * @param ball the ball to be checked
     * @param obstacles list with all the obstacles of the map 
     */
    private void handleCollisions(Ball ball, List<Obstacle> obstacles) {
        for (int iter = 0; iter < MAX_COLLISION_ITERATIONS; iter++) {
            boolean anyCollision = false;
            for (Obstacle obs : obstacles) {
                if (obs.isColliding(ball)) {
                    obs.resolveCollision(ball);
                    anyCollision = true;
                }
            }
            if (!anyCollision) break;
        }
    }

}
