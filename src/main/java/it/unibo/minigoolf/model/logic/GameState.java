package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vec2D;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
 
/**
 * Central game logic for a minigolf match.
 *
 * The game loop in MainControllerImpl calls the method update() once per tick.
 *
 * @author fede
 */
public class GameState {
 
    //Minimum squared length a shot vector must have to be considered valid.
    private static final double MIN_SQUARE_POWER = 100.0;
 
    private final List<Player> players;
    private int currentPlayerIndex;
 
    // The shot queued by the view; null if no shot is waiting.
    private Vec2D pendingShot;
 
    // Whether the ball is still rolling.
    private boolean ballMoving;
 
    /**
     * Creates a new GameState with the given list of player names.
     * At least one name must be provided.
     *
     * @param playerNames ordered list of player display names
     * @throws IllegalArgumentException if the list is empty
     */
    public GameState(List<String> playerNames) {
        if (playerNames == null || playerNames.isEmpty()) {
            throw new IllegalArgumentException("At least one player is required.");
        }
        this.players = new ArrayList<>();
        for (String name : playerNames) {
            this.players.add(new Player(name));
        }
        this.currentPlayerIndex = 0;
        this.pendingShot = null;
        this.ballMoving = false;
    }

    /** 
     * @return the player whose turn it currently is 
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
 
    /** 
     * @return an unmodifiable view of all players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }
 
    /** 
     * @return true if the ball is currently moving //TODO da sistemare quando la pallina si muoverà
     */
    public boolean isBallMoving() {
        return ballMoving;
    }
 
    /**
     * Called by the view layer when the user releases the mouse.
     * Ignored if the ball is still moving from the previous shot.
     *
     * @param shot the direction/power vector of the intended shot
     */
    public synchronized void setPendingShot(Vec2D shot) {
        if (!ballMoving && shot != null && shot.getSquareModule() >= MIN_SQUARE_POWER) {
            this.pendingShot = shot;
        }
    }
 
    /**
     * Called by the game loop each tick.
     *
     * @return an Optional containing the shot vector, or empty if no shot this tick
     */
    public synchronized Optional<Vec2D> update() {
        if (pendingShot != null) {
            Vec2D shot = pendingShot;
            pendingShot = null;
            ballMoving = true;
            getCurrentPlayer().addShot();
            return Optional.of(shot);
        }
        return Optional.empty();
    }
 
    /**
     * Must be called by the physics layer once the ball has stopped moving.
     * Re-enables shot input for the current player.
     */
    public void onBallStopped() {
        this.ballMoving = false;
    }

    /**
     * Advances play to the next player in turn order.
     */
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        ballMoving = false;
        pendingShot = null;
    }
 
    /**
     * Resets shot counters for all player.
     */
    public void resetAllShots() {
        for (Player p : players) {
            p.resetShots();
        }
    }
}
