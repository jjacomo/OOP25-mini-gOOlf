package it.unibo.minigoolf.controller.surfacecontroller;

import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.SurfaceType;
import it.unibo.minigoolf.util.Vector2D;
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
        final Vector2D wind = surface.getWind();
        if (wind.getNorm() == 0) {
            return null;
        }
        if (Math.abs(wind.getX()) >= Math.abs(wind.getY())) {
            return wind.getX() > 0
                    ? "surfaces/wind/right_arrow.png"
                    : "surfaces/wind/left_arrow.png";
        } else {
            return wind.getY() > 0
                    ? "surfaces/wind/down_arrow.png"
                    : "surfaces/wind/up_arrow2.png";
        }
    }

    @Override
    public SurfaceType getType() {
        return surface.getType();
    }

    @Override
    public Vector2D getWind() {
        return surface.getWind();
    }
}
