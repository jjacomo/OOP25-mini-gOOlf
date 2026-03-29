package it.unibo.minigoolf.model.logic;

/**
 * Represents a single player in the minigolf game.
 * Tracks the player's name and how many shots they have taken on the current hole.
 *
 * @author fede
 */
public final class Player {

    private final String name;
    private int shots;

    /**
     * @param name the display name of this player
     */
    public Player(final String name) {
        this.name = name;
    }

    /**
     * Returns this player's display name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of shots this player has taken on the current hole.
     *
     * @return the shot count
     */
    public int getShots() {
        return shots;
    }

    /**
     * Increments the shot counter by one.
     * Called by GameState every time a valid shot is made.
     */
    public void addShot() {
        this.shots++;
    }

    /**
     * Resets the shot counter to zero (example: when moving to the next hole).
     */
    public void resetShots() {
        this.shots = 0;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return name + " | Shots: " + shots;
    }
}