package it.unibo.minigoolf.model.physics.velocity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Basic implementation of the BallVelocityStrategy interface that applies a
 * friction force to the ball's velocity based on the surface's friction
 * coefficient and the ball's current speed. The friction force is stronger at
 * higher speeds to prevent the ball from becoming uncontrollable, and it
 * becomes weaker as the ball slows down.
 *
 * @author jack
 */
public final class BasicFrictionStrategy implements BallVelocityStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicFrictionStrategy.class);

    private static final int HIGH_SPEED_THRESHOLD = 1_000;
    private static final int MEDIUM_SPEED_THRESHOLD = 200;
    private static final double LOW_SPEED_THRESHOLD = 0.5;
    private static final int HIGH_FRICTION_MULTIPLIER = 15_000;
    private static final int MEDIUM_FRICTION_MULTIPLIER = 10_000;
    private static final int LOW_FRICTION_MULTIPLIER = 500;
    private static final int WIND_MULTIPLIER = 150;
    private static final double MAX_BOOST_SPEED = 1500.0;

    /**
     * Default constructor.
     */
    public BasicFrictionStrategy() {
        // No initialization needed
    }

    /**
     * Updates the velocity of the ball based on the surface it is currently on and
     * the time elapsed since the last update.
     * This method applies a friction force to the ball's velocity, which is
     * calculated based on the surface's friction coefficient and the ball's current
     * speed.
     *
     * @param ball      the ball whose velocity is to be updated
     * @param surface   the surface on which the ball is currently located, which
     *                  may affect its velocity (e.g., different friction or bounce
     *                  properties)
     * @param deltaTime the time elapsed since the last update, used to calculate
     *                  the change in velocity based on acceleration and other
     *                  factors
     */
    @Override
    public void updateVelocity(final Ball ball, final Surface surface, final double deltaTime) {
        final Vector2D velocity = ball.getVelocity();
        final double velocityNorm = velocity.getNorm();
        final double surfaceFriction = surface.getFriction();
        double friction = 0;

        if (surfaceFriction < 0) {
            friction = LOW_FRICTION_MULTIPLIER * surfaceFriction;
        } else if (velocityNorm > HIGH_SPEED_THRESHOLD) {
            friction = HIGH_FRICTION_MULTIPLIER * surfaceFriction / Math.sqrt(velocityNorm);
            LOGGER.debug("High speed: Velocity norm: {}, surface friction: {}, deltaTime: {}", velocityNorm,
                    surfaceFriction, deltaTime);
        } else if (velocityNorm > MEDIUM_SPEED_THRESHOLD) {
            friction = MEDIUM_FRICTION_MULTIPLIER * surfaceFriction / velocityNorm;
            LOGGER.debug("Velocity norm: {}, surface friction: {}, deltaTime: {}", velocityNorm, surfaceFriction,
                    deltaTime);
        } else if (velocityNorm > LOW_SPEED_THRESHOLD) {
            friction = LOW_FRICTION_MULTIPLIER * surfaceFriction;
            LOGGER.debug("Low speed: Velocity norm: {}, surface friction: {}, deltaTime: {}", velocityNorm,
                    surfaceFriction, deltaTime);
        } else if (velocityNorm != 0 && surface.getWind().map(w -> w.getNormSquared() == 0).orElse(true)) {
            LOGGER.debug("Very low speed and no wind: setting velocity to zero.");
            ball.setVelocity(Vector2D.ZERO);
        }

        if (velocityNorm > 0 && surfaceFriction < 0) {
            final Vector2D frictionForce = velocity.normalize().scalarMultiply(-friction);
            final Vector2D deltaV = frictionForce.scalarMultiply(deltaTime);
            Vector2D newVelocity = velocity.add(deltaV);
            if (newVelocity.getNorm() > MAX_BOOST_SPEED) {
                newVelocity = newVelocity.normalize().scalarMultiply(MAX_BOOST_SPEED);
            }
            ball.setVelocity(newVelocity);
        } else if (velocityNorm > LOW_SPEED_THRESHOLD) {
            final Vector2D frictionForce = velocity.normalize().scalarMultiply(-friction);
            final Vector2D deltaV = frictionForce.scalarMultiply(deltaTime);
            if (deltaV.getNorm() >= velocityNorm) {
                ball.setVelocity(Vector2D.ZERO);
            } else {
                ball.setVelocity(velocity.add(deltaV));
            }
        }

        surface.getWind().filter(w -> w.getNormSquared() > 0).ifPresent(wind -> {
            ball.setVelocity(ball.getVelocity().add(wind.scalarMultiply(WIND_MULTIPLIER).scalarMultiply(deltaTime)));
        });
    }

}
