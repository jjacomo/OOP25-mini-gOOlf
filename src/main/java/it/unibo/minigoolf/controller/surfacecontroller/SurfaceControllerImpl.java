package it.unibo.minigoolf.controller.surfacecontroller;

import java.util.Optional;

import it.unibo.minigoolf.model.surfaces.Surface;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Implementation of the SurfaceController.
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
    public String getTexturePath() {
        return switch (surface.getTypeId()) {
            case "grass" -> "surfaces/grass.png";
            case "sand" -> "surfaces/sand.png";
            case "dirt" -> "surfaces/dirt.png";
            case "ice" -> "surfaces/ice.png";
            default -> "surfaces/default.png";
        };
    }

    @Override
    public String getWindOverlayTexturePath() {
        final Optional<Vector2D> windOpt = surface.getWind();
        if (windOpt.isEmpty()) {
            return null;
        }
        final Vector2D wind = windOpt.get();
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
    public String getTypeId() {
        return surface.getTypeId();
    }

    @Override
    public Optional<Vector2D> getWind() {
        return surface.getWind();
    }
}
