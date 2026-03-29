package it.unibo.minigoolf.model.logic;

/**
 * Represents a single player in the minigolf game.
 * Tracks the player's name and how many shots they have taken on the current hole.
 *
 * @author fede
 */
public class Player {
 
    private final String name;
    private int shots;
 
    /**
     * @param name the display name of this player
     */
    public Player(String name) {
        this.name = name;
        this.shots = 0;
    }
 
    /** 
     * @return this player's display name 
     */
    public String getName() {
        return name;
    }
 
    /** 
     * @return the number of shots this player has taken on the current hole 
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
     * Resets the shot counter to zero (e.g. when moving to the next hole).
     */
    public void resetShots() {
        this.shots = 0;
    }
 
    @Override
    public String toString() {
        return name + " | Shots: " + shots;
    }
}