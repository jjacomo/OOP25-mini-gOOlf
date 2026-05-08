package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.model.physics.velocity.BallVelocityStrategy;

public interface PhysicsController {
    void update(double deltaTime);

    void setVelocityStrategy(BallVelocityStrategy strategy);
}
