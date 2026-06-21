package it.unibo.minigoolf.model.map;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.Hole;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Manages a collection of surfaces, obstacles, a ball and a hole, and provides
 * methods to query them.
 * 
 * @author jack
 */
public final class GameMapImpl implements GameMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMapImpl.class);

    private final List<Surface> surfaces;

    private final List<Obstacle> obstacles;

    private final Ball ball;

    private final Hole hole;

    /**
     * Constructs a GameMapImpl with the given list of surfaces, ball, hole and
     * obstacles.
     * 
     * @param surfaces  the list of surfaces
     * @param ball      the ball in the game map
     * @param hole      the hole in the game map
     * @param obstacles the list of obstacles
     */
    public GameMapImpl(final List<Surface> surfaces, final Ball ball, final Hole hole, final List<Obstacle> obstacles) {
        if (surfaces == null || ball == null || hole == null || obstacles == null) {
            LOGGER.error("Attempted to create a GameMapImpl with null parameters");
            throw new IllegalArgumentException("Surfaces, ball, hole and obstacles cannot be null");
        }
        this.surfaces = List.copyOf(surfaces);
        this.ball = new BallImpl(ball.getPosition(), ball.getRadius());
        this.obstacles = List.copyOf(obstacles);
        this.hole = hole;
    }

    @Override
    public Surface getSurfaceAt(final Vector2D position) {
        final Surface highestSurface;
        try {
            highestSurface = surfaces.stream()
                    .filter(s -> s.contains(position))
                    .max((s1, s2) -> Integer.compare(s1.getZIndex(), s2.getZIndex()))
                    .orElseThrow(() -> new IllegalStateException("No surface found at the given position"));
        } catch (final IllegalStateException e) {
            LOGGER.error("No surface found at the given position: " + position);
            throw new IllegalStateException("No surface found at the given position: " + position, e);
        }
        return highestSurface;
    }

    /**
     * Returns a copy of the list of surfaces in the game map.
     * 
     * @return the list of surfaces
     */
    @Override
    public List<Surface> getSurfaces() {
        return List.copyOf(this.surfaces);
    }

    /**
     * Returns a copy of the ball in the game map.
     *
     * @return the ball
     */
    @Override
    public Ball getBall() {
        final Ball copy = new BallImpl(this.ball.getPosition(), this.ball.getRadius());
        copy.setVelocity(this.ball.getVelocity());
        return copy;
    }

    /**
     * Returns a copy of the hole in the game map.
     *
     * @return the hole
     */
    @Override
    public Hole getHole() {
        return new HoleImpl(this.hole.getPosition(), this.hole.getRadius());
    }

    /**
     * Returns a copy of the list of obstacles in the game map.
     *
     * @return the list of obstacles
     */
    @Override
    public List<Obstacle> getObstacles() {
        return List.copyOf(this.obstacles);
    }

}
