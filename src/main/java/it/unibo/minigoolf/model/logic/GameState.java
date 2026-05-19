package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Central game logic for a minigolf match.
 * Implements {@link TurnState} so controllers can depend on the narrow
 * interface rather than this full class, avoiding EI2 warnings.
 *
 * @author fede and dani
 */
public final class GameState implements TurnState {

    /** Minimum squared length a shot vector must have to be considered valid. */
    private static final double MIN_SQUARE_POWER = 100.0;

    private final List<Player> players;
    private int currentPlayerIndex;

    /** The shot queued by ShotController; null if no shot is waiting. */
    private Vector2D pendingShot;

    /** Whether the ball is still rolling. */
    private volatile boolean ballMoving;

    /**
     * Creates a new GameState with the given list of player names.
     *
     * @param playerNames ordered list of player display names
     * @throws IllegalArgumentException if the list is empty
     */
    public GameState(final List<String> playerNames) {
        if (playerNames == null || playerNames.isEmpty()) {
            throw new IllegalArgumentException("At least one player is required.");
        }
        this.players = new ArrayList<>();
        for (final String name : playerNames) {
            this.players.add(new Player(name));
        }
        this.currentPlayerIndex = 0;
    }

    /**
     * Returns the player whose turn it currently is.
     *
     * @return the current player
     */
    public synchronized Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Returns the index of the current player (used for save/load).
     *
     * @return the current player index
     */
    public synchronized int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Returns an unmodifiable view of all players.
     *
     * @return the player list
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isBallMoving() {
        return ballMoving;
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void setPendingShot(final Vector2D shot) {
        if (!ballMoving && shot != null && shot.getNormSquared() >= MIN_SQUARE_POWER) {
            this.pendingShot = shot;
        }
    }

    /** {@inheritDoc} */
    @Override
    public synchronized Optional<Vector2D> update() {
        if (pendingShot != null) {
            final Vector2D shot = pendingShot;
            pendingShot = null;
            ballMoving = true;
            getCurrentPlayer().addShot();
            return Optional.of(shot);
        }
        return Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public synchronized void onBallStopped() {
        this.ballMoving = false;
    }

    /**
     * Advances play to the next player in turn order.
     */
    public synchronized void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        ballMoving = false;
        pendingShot = null;
    }

    /**
     * Resets shot counters for all players.
     */
    public void resetAllShots() {
        for (final Player p : players) {
            p.resetShots();
        }
    }
}
