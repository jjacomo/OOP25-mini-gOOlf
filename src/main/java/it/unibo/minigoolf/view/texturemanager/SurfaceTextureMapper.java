package it.unibo.minigoolf.view.texturemanager;

import java.util.Optional;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Utility class to map surface model properties to texture path resources.
 */
public final class SurfaceTextureMapper {

    private SurfaceTextureMapper() {
        throw new UnsupportedOperationException("SurfaceTextureMapper is a utility class");
    }

    /**
     * Gets the texture path for a given surface type identifier.
     *
     * @param typeId the type identifier of the surface
     * @return the texture path
     */
    public static String getTexturePath(final String typeId) {
        return switch (typeId) {
            case "grass" -> "surfaces/grass.png";
            case "sand" -> "surfaces/sand.png";
            case "dirt" -> "surfaces/dirt.png";
            case "ice" -> "surfaces/ice.png";
            case "boost" -> "surfaces/boost/boost.png";
            default -> "surfaces/default.png";
        };
    }

    /**
     * Gets the texture path for the wind overlay based on the wind vector.
     *
     * @param windOpt the optional wind vector
     * @return the texture path, or null if there is no wind/no overlay
     */
    public static String getWindOverlayTexturePath(final Optional<Vector2D> windOpt) {
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
}
