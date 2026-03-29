package it.unibo.minigoolf.model.surfaces;

import java.awt.Color;

public enum SurfaceType {
    GRASS(0.25, "Grass", Color.GREEN),
    SAND(0.85, "Sand", Color.YELLOW),
    DIRT(0.65, "Dirt", Color.ORANGE),
    ICE(0.05, "Ice", Color.CYAN);

    private final double friction;
    private final String name;
    private final Color color;

    SurfaceType(double friction, String name, Color color) {
        this.friction = friction;
        this.name = name;
        this.color = color;
    }

    public double getFriction() {
        return friction;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
