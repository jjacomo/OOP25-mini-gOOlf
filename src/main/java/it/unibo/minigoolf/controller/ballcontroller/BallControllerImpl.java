package it.unibo.minigoolf.controller.ballcontroller;

import java.awt.Color;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Implementation of {@link BallController}.
 * Manages ball state and coordinates updates to the model.
 *
 * @author jack
 */
public final class BallControllerImpl implements BallController {

    private final Ball ball;

    /**
     * Creates a new BallController for the given ball.
     *
     * @param ball the ball model to control
     */
    public BallControllerImpl(final Ball ball) {
        this.ball = ball;
        // this.ball = new BallImpl(ball.getPosition(), ball.getRadius());

        //TODO
        // si rompe tutto pero':
        // (credo) per risolvere questo errore di spotbugs bisognerebbe smettere di
        // usare il ball del model come riferimento e usare solo il ball del
        // controller, che è  quello che viene aggiornato dalla fisica. In questo modo
        // si eviterebbe di dover creare un nuovo BallImpl ogni volta che si chiama
        // getBall() per evitare che venga modificato da altri componenti.
    }

    /** {@inheritDoc} */
    @Override
    public Shape getBallShape() {
        return new Circle(ball.getPosition(), ball.getRadius(), Color.WHITE);
    }

    /** {@inheritDoc} */
    @Override
    public Vector2D getPosition() {
        return ball.getPosition();
    }

    /** {@inheritDoc} */
    @Override
    public Vector2D getVelocity() {
        return ball.getVelocity();
    }

    /** {@inheritDoc} */
    @Override
    public double getRadius() {
        return ball.getRadius();
    }

    /** {@inheritDoc} */
    @Override
    public void updatePosition(final Vector2D position) {
        ball.setPosition(position);
    }

    /** {@inheritDoc} */
    @Override
    public void updateVelocity(final Vector2D velocity) {
        ball.setVelocity(velocity);
    }
}
