package it.unibo.minigoolf.model.surfaces;

import java.awt.Color;

/**
 * Enumeration of different surface types in the mini-golf game.
 * Each surface type has a friction coefficient, a display name, and a texture path.
 * 
 * @author jack
 */
public enum SurfaceType {
    GRASS(0.25, "Grass", "surfaces/grass.png"),
    SAND(0.85, "Sand", "surfaces/sand.png"),
    DIRT(0.65, "Dirt", "surfaces/dirt.png"),
    ICE(0.05, "Ice", "surfaces/ice.png");

    private final double friction;
    private final String name;
    private final String texturePath;

    /**
     * Constructs a SurfaceType with the specified friction, name, and texture path.
     * 
     * @param friction the friction coefficient
     * @param name the display name
     * @param texturePath the path to the texture image in resources
     */
    SurfaceType(final double friction, final String name, final String texturePath) {
        this.friction = friction;
        this.name = name;
        this.texturePath = texturePath;
    }

    /**
     * Returns the friction coefficient of this surface type.
     * 
     * @return the friction coefficient
     */
    public double getFriction() {
        return friction;
    }

    /**
     * Returns the display name of this surface type.
     * 
     * @return the display name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the texture path for this surface type.
     * 
     * @return the texture path
     */
    public String getTexturePath() {
        return texturePath;
    }
}
