package it.unibo.minigoolf.model.surfaces;

import java.awt.Color;

/**
 * Enumeration of different surface types in the mini-golf game.
 * Each surface type has a friction coefficient, a display name, and a color.
 * 
 * @author jack
 */
public enum SurfaceType {
    GRASS(0.25, "Grass", Color.GREEN),
    SAND(0.85, "Sand", Color.YELLOW),
    DIRT(0.65, "Dirt", Color.ORANGE),
    ICE(0.05, "Ice", Color.CYAN);

    private final double friction;
    private final String name;
    private final Color color;

    /**
     * Constructs a SurfaceType with the specified friction, name, and color.
     * 
     * @param friction the friction coefficient
     * @param name the display name
     * @param color the color representation
     */
    SurfaceType(final double friction, final String name, final Color color) {
        this.friction = friction;
        this.name = name;
        this.color = color;
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
     * Returns the color representation of this surface type.
     * 
     * @return the color
     */
    public Color getColor() {
        return color;
    }
}
