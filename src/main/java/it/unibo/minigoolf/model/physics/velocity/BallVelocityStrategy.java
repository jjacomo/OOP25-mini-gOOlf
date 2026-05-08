package it.unibo.minigoolf.model.physics.velocity;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.surfaces.Surface;

public interface BallVelocityStrategy {
    void updateVelocity(Ball ball, Surface surface, double deltaTime);
}
