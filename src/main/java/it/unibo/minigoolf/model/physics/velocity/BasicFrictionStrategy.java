package it.unibo.minigoolf.model.physics.velocity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;

public class BasicFrictionStrategy implements BallVelocityStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicFrictionStrategy.class);

    @Override
    public void updateVelocity(Ball ball, Surface surface, double deltaTime) {
        final Vector2D velocity = ball.getVelocity();
        final double velocityNorm = velocity.getNorm();
        final double surfaceFriction = surface.getFriction();
        
        if (velocityNorm > 50) {
            // Attrito diretto dalla superficie e modulato dalla velocità:
            // basso quando la palla è veloce, crescente man mano che rallenta.
            final double friction = 10000 * surfaceFriction / velocityNorm; // per test, da tarare meglio

            final Vector2D frictionForce = velocity.normalize().scalarMultiply(-friction);
            LOGGER.debug("Velocity norm: {}, surface friction: {}, frictionForce: {}, deltaTime: {}", velocityNorm, surfaceFriction, frictionForce, deltaTime);
            final Vector2D newVelocity = velocity.add(frictionForce.scalarMultiply(deltaTime));
            ball.setVelocity(newVelocity);
        } else if (velocityNorm > 5) {
            // Attrito più forte quando la palla è lenta, per evitare che si trascini troppo.
            final double friction = 500 * surfaceFriction; // per test, da tarare meglio
            final Vector2D frictionForce = velocity.normalize().scalarMultiply(-friction);
            LOGGER.debug("Low speed: Velocity norm: {}, surface friction: {}, frictionForce: {}, deltaTime: {}", velocityNorm, surfaceFriction, frictionForce, deltaTime);
            final Vector2D newVelocity = velocity.add(frictionForce.scalarMultiply(deltaTime));
            ball.setVelocity(newVelocity);
        } else {
            ball.setVelocity(new Vector2D(0, 0));
        }
    }

    // da mettere quando non verra' piu' modificata la velocita' in MainControllerImpl

    // @Override
    // public void updateVelocity(Ball ball, Surface surface, double deltaTime) {
    //     final Vector2D velocity = ball.getVelocity();
    //     final double velocityNorm = velocity.getNorm();
    //     final double surfaceFriction = surface.getFriction();
    //     double friction = 0;

    //     if(velocityNorm == 0) {
    //         return;
    //     }
    //     if (velocityNorm > 600) {
    //         // Attrito dinamico più forte a velocità molto elevate, per evitare che la palla diventi incontrollabile.
    //         friction = 15000 * surfaceFriction; // per test, da tarare meglio
    //         LOGGER.debug("High speed: Velocity norm: {}, surface friction: {}, deltaTime: {}", velocityNorm, surfaceFriction, deltaTime);
    //     } else if (velocityNorm > 50) {
    //         // Attrito diretto dalla superficie e modulato dalla velocità:
    //         // basso quando la palla è veloce, crescente man mano che rallenta.
    //         friction = 10000 * surfaceFriction / velocityNorm; // per test, da tarare meglio
    //         LOGGER.debug("Velocity norm: {}, surface friction: {}, deltaTime: {}", velocityNorm, surfaceFriction, deltaTime);
    //     } else if (velocityNorm > 5) {
    //         // Attrito più forte quando la palla è lenta, per evitare che si trascini troppo.
    //         friction = 500 * surfaceFriction; // per test, da tarare meglio
    //         LOGGER.debug("Low speed: Velocity norm: {}, surface friction: {}, deltaTime: {}", velocityNorm, surfaceFriction, deltaTime);
    //     } else {
    //         LOGGER.debug("Very low speed: setting velocity to zero.");
    //         ball.setVelocity(new Vector2D(0, 0));
    //     }

    //     final Vector2D frictionForce = velocity.normalize().scalarMultiply(-friction);
    //     final Vector2D newVelocity = velocity.add(frictionForce.scalarMultiply(deltaTime));
    //     ball.setVelocity(newVelocity);
    // }

}
