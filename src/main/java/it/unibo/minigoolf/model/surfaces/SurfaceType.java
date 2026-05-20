package it.unibo.minigoolf.model.surfaces;
// TODO: puoi anche togliere il path e ricostruirlo dal nome della superficie

/**
 * Enumeration of different surface types in the mini-golf game.
 * Each surface type has a friction coefficient and a display name.
 * 
 * @author jack
 */
public enum SurfaceType {
    GRASS(2.50, "Grass", "surfaces/grass.png"),
    SAND(11.50, "Sand", "surfaces/sand.png"),
    DIRT(6.75, "Dirt", "surfaces/dirt.png"),
    ICE(0.25, "Ice", "surfaces/ice.png");

    private final double friction;
    private final String name;
    private final String texturePath;

    /**
     * Constructs a SurfaceType with the specified friction and name.
     * 
     * @param friction    the friction coefficient
     * @param name        the display name
     * @param texturePath the texture path
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
     * Returns the texture path of this surface type.
     * 
     * @return the texture path
     */
    public String getTexturePath() {
        return texturePath;
    }
}
