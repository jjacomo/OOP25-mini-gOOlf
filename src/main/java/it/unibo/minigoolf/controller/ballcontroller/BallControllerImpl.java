package it.unibo.minigoolf.controller.ballcontroller;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Implementation of {@link BallController}.
 * Manages ball state and coordinates updates to the model.
 * 
 * @author jack
 */
public class BallControllerImpl implements BallController {
    private Ball ball;

    /**
     * Creates a new BallController for the given ball.
     * 
     * @param ball the ball model to control
     */
    public BallControllerImpl(Ball ball) {
        this.ball = ball;
    }

    @Override
    public Shape getBallShape() {
        return new Circle(ball.getPosition(), ball.getRadius());
    }

    @Override
    public void updatePosition(Vector2D position) {
        ball.setPosition(position);
    }

    @Override
    public void updateVelocity(Vector2D velocity) {
        ball.setVelocity(velocity);
    }
}
