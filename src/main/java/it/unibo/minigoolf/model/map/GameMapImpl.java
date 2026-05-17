package it.unibo.minigoolf.model.map;

import java.util.List;

import it.unibo.minigoolf.util.Vector2D;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.Hole;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.obstacles.Obstacle;

/**
 * Implementation of the GameMap interface.
 * Manages a collection of surfaces and provides methods to query surfaces at
 * specific positions.
 * 
 * @author jack
 */
public final class GameMapImpl implements GameMap {

    /** The list of surfaces in the game map. */
    private final List<Surface> surfaces;

    /** The list of obstacles in the game map. */
    private final List<Obstacle> obstacles;

    /** The ball in the game map. */
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
        this.surfaces = List.copyOf(surfaces);
        this.ball = new BallImpl(ball.getPosition(), ball.getRadius());
        this.obstacles = List.copyOf(obstacles);
        this.hole = hole;
    }

    /**
     * {@inheritDoc}
     * This implementation finds the surface with the highest z-index that contains
     * the position.
     */
    @Override
    public Surface getSurfaceAt(final Vector2D position) {
        final Surface highestSurface;
        try {
            highestSurface = surfaces.stream()
                    .filter(s -> s.contains(position))
                    .max((s1, s2) -> Integer.compare(s1.getZIndex(), s2.getZIndex()))
                    .orElseThrow(() -> new IllegalStateException("No surface found at the given position"));
        } catch (final IllegalStateException e) {
            throw new IllegalStateException("No surface found at the given position: " + position, e);
        }
        return highestSurface;
    }

    /**
     * Returns the list of surfaces in the game map.
     * 
     * @return the list of surfaces
     */
    @Override
    public List<Surface> getSurfaces() {
        return List.copyOf(this.surfaces);
        // TODO farlo qui o in GameMapControllerImpl?
        // return this.surfaces;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ball getBall() {
        final Ball copy = new BallImpl(this.ball.getPosition(), this.ball.getRadius());
        copy.setVelocity(this.ball.getVelocity());
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hole getHole() {
        return new HoleImpl(this.hole.getPosition(), this.hole.getRadius());
    }

    /**
     * Returns the list of obstacles in the game map.
     *
     * @return the list of obstacles
     */
    @Override
    public List<Obstacle> getObstacles() {
        return List.copyOf(this.obstacles);
        // return this.obstacles;
    }

}
