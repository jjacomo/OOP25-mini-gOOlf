package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;
import java.util.Optional;
import it.unibo.minigoolf.util.shapes.Shape;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Data Transfer Object for rendering a surface.
 *
 * @param zIndex the z-index of the surface for sorting
 * @param typeIds the texture identifiers
 * @param shape the shape of the surface
 * @param wind the wind vector affecting the surface
 */
public record SurfaceData(int zIndex, List<String> typeIds, Shape shape, Optional<Vector2D> wind) {

    /**
     * Compact constructor that creates a defensive copy of the mutable list.
     *
     * @param zIndex the z-index of the surface for sorting
     * @param typeIds the texture identifiers
     * @param shape the shape of the surface
     * @param wind the wind vector affecting the surface
     */
    public SurfaceData {
        typeIds = List.copyOf(typeIds);
    }
}
