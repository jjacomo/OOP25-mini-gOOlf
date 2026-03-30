package it.unibo.minigoolf.model.map;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.model.surfaces.Surface;

/**
 * Implementation of the GameMap interface.
 * Manages a collection of surfaces and provides methods to query surfaces at
 * specific positions.
 */
public final class GameMapImpl implements GameMap {

    /** The list of surfaces in the game map. */
    private List<Surface> surfaces = new ArrayList<>();

    /**
     * Constructs a GameMapImpl with the given list of surfaces.
     * 
     * @param surfaces the list of surfaces
     */
    public GameMapImpl(final List<Surface> surfaces) {
        this.surfaces = surfaces;
    }

    /**
     * {@inheritDoc}
     * This implementation finds the surface with the highest z-index that contains the position.
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
    public List<Surface> getSurfaces() {
        return surfaces;
    }
}
