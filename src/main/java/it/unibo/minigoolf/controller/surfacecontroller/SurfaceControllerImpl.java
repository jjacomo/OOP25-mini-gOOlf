package it.unibo.minigoolf.controller.surfacecontroller;

import java.util.List;
import java.util.Optional;

import it.unibo.minigoolf.model.surfaces.Surface;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Implementation of the SurfaceController.
 *
 * @author jack
 */
public final class SurfaceControllerImpl implements SurfaceController {

    private final Surface surface;

    /**
     * Constructs a SurfaceControllerImpl for the given surface.
     *
     * @param surface the model surface to control
     */
    public SurfaceControllerImpl(final Surface surface) {
        this.surface = surface;
    }

    @Override
    public Shape getShape() {
        return surface.getShape();
    }

    @Override
    public int getZIndex() {
        return surface.getZIndex();
    }

    @Override
    public List<String> getTypeIds() {
        return surface.getTypeIds();
    }

    @Override
    public Optional<Vector2D> getWind() {
        return surface.getWind();
    }
}
