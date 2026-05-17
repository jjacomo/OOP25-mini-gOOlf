package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.controller.ballcontroller.BallController;
import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Adapts a {@link BallController} to the {@link Ball} interface required by
 * model-layer components ({@code BallVelocityStrategy}, {@code Obstacle}).
 *
 * <p>
 * All reads and writes are forwarded to the controller, so the physics
 * engine always operates on the single authoritative ball state managed by
 * the controller layer — no duplicate model objects are created.
 *
 * <p>
 * This class lives in the <em>controller</em> package intentionally: it is
 * the controller layer's responsibility to bridge its own abstractions with
 * model interfaces, not the other way around.
 *
 * @author jack
 */
final class BallControllerAdapter implements Ball {

    private final BallController controller;

    /**
     * Constructs a new adapter wrapping the given ball controller.
     *
     * @param controller the ball controller to delegate all operations to
     */
    BallControllerAdapter(final BallController controller) {
        this.controller = controller;
    }

    /** {@inheritDoc} */
    @Override
    public Vector2D getPosition() {
        return controller.getPosition();
    }

    /** {@inheritDoc} */
    @Override
    public Vector2D getVelocity() {
        return controller.getVelocity();
    }

    /** {@inheritDoc} */
    @Override
    public double getRadius() {
        return controller.getRadius();
    }

    /** {@inheritDoc} */
    @Override
    public void setPosition(final Vector2D position) {
        controller.updatePosition(position);
    }

    /** {@inheritDoc} */
    @Override
    public void setVelocity(final Vector2D velocity) {
        controller.updateVelocity(velocity);
    }
}
