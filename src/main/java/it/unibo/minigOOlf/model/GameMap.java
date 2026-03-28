package it.unibo.minigOOlf.model;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigOOlf.model.surfaces.Surface;

public interface GameMap {
    /**
     * Returns the surface with highest z index at the given position.
     * @param position the position to check
     * @return the surface with highest z index at the given position.
     */
    Surface getSurfaceAt(Vector2D position);
}
