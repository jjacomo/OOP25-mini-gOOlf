package it.unibo.minigoolf.model.obstacles;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import java.util.List;
import java.util.Arrays;

/**
 * Represents a pair of teleportation portals. When the ball enters a portal,
 * it is teleported to the other while maintaining its direction and speed unchanged.
 * 
 * @author Mattia
 */
public final class PortalObstacle extends AbstractObstacle {
        private static final long COOLDOWN_MS = 1000;

    private PortalObstacle linkedPortal;
    private long cooldownUntil;
    private final double portalRadius;
    private final Circle shape;

    /**
     * Private Constructor. 
     * Portals can only be created in pairs using the createPair() factory method.
     * 
     * @param position the position of the portal
     * @param portalRadius the radius of the portal
     */
    private PortalObstacle(final Vector2D position, final double portalRadius) {
        super(position, 1.0); 
        this.shape = new Circle(position, portalRadius);
        this.portalRadius = portalRadius;
        this.cooldownUntil = 0;
    }

    /**
     * Factory method that creates and bidirectionally links a pair of portals.
     *
     * @param posA the center position of the first portal
     * @param posB the center position of the second portal
     * @param portalRadius the radius of the two portals
     * @return a List containing exactly the two linked PortalObstacle instances
     */
    public static List<PortalObstacle> createPair(final Vector2D posA, 
                                                  final Vector2D posB, 
                                                  final double portalRadius) {
        final PortalObstacle portalA = new PortalObstacle(posA, portalRadius);
        final PortalObstacle portalB = new PortalObstacle(posB, portalRadius);
        portalA.linkedPortal = portalB;
        portalB.linkedPortal = portalA;

        return Arrays.asList(portalA, portalB);
    }

    /**
     * Updates the future timestamp until which this portalObstacle will ignore collisions.
     */
    private void triggerCooldown() {
        this.cooldownUntil = System.currentTimeMillis() + COOLDOWN_MS;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isColliding(final Ball ball) {
        return getPenetrationDepth(ball) > 0;
    }

    /** {@inheritDoc} */
    @Override
    public double getPenetrationDepth(final Ball ball) {
        if (System.currentTimeMillis() < this.cooldownUntil) {
            return 0.0;
        }

        final double distance = ball.getPosition().distance(getPosition());
        final double sumRadii = ball.getRadius() + portalRadius;

        return distance < sumRadii ? sumRadii - distance : 0.0;
    }

    /** {@inheritDoc} */
    @Override
    public void resolveCollision(final Ball ball) {
        if (this.linkedPortal != null) {
            ball.setPosition(this.linkedPortal.getPosition());
            this.linkedPortal.triggerCooldown();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Circle getShape() {
        return this.shape;
    }
}
