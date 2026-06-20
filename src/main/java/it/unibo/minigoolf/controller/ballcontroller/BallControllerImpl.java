package it.unibo.minigoolf.controller.ballcontroller;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

    private static final double VELOCITY_THRESHOLD = 0.001;

    private final Supplier<Vector2D> positionSupplier;
    private final Consumer<Vector2D> positionConsumer;
    private final Supplier<Vector2D> velocitySupplier;
    private final Consumer<Vector2D> velocityConsumer;
    private final Supplier<Double> radiusSupplier;

    /**
     * Creates a new BallController for the given ball.
     *
     * @param ball the ball model to control
     */
    public BallControllerImpl(final Ball ball) {
        Objects.requireNonNull(ball);
        this.positionSupplier = ball::getPosition;
        this.positionConsumer = ball::setPosition;
        this.velocitySupplier = ball::getVelocity;
        this.velocityConsumer = ball::setVelocity;
        this.radiusSupplier = ball::getRadius;
    }

    @Override
    public Shape getBallShape() {
        return new Circle(positionSupplier.get(), radiusSupplier.get());
    }

    @Override
    public Vector2D getPosition() {
        return positionSupplier.get();
    }

    @Override
    public Vector2D getVelocity() {
        return velocitySupplier.get();
    }

    @Override
    public double getRadius() {
        return radiusSupplier.get();
    }

    @Override
    public void updatePosition(final Vector2D position) {
        positionConsumer.accept(position);
    }

    @Override
    public void updateVelocity(final Vector2D velocity) {
        velocityConsumer.accept(velocity);
    }

    @Override
    public boolean isBallMoving() {
        return velocitySupplier.get().getNormSquared() > VELOCITY_THRESHOLD;
    }
}
