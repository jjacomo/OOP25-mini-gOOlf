package it.unibo.minigoolf.controller.surfacecontroller;

import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Implementation of the SurfaceController.
 */
public class SurfaceControllerImpl implements SurfaceController {

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
    public String getTexturePath() {
        return surface.getType().getTexturePath();
    }

    @Override
    public String getWindOverlayTexturePath() {
        if (surface.getWind().getNorm() > 0) {
            return "surfaces/wind/up_arrows.png";
        }
        return null;
    }
}
