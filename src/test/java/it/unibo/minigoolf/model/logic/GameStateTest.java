package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author fedesparvo1-a11y
 */
class GameStateTest {

    private static final String PLAYER_1 = "Fede";
    private static final String PLAYER_2 = "Mattia";
    private static final String PLAYER_3 = "Giacomo";
    private static final int THREE_PLAYERS = 3;
    private static final int SHOT_BELOW_THRESHOLD = 5;
    private static final Vector2D VALID_SHOT = new Vector2D(20, 0);

    // Constructor

    @Test
    void testConstructorWithNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> new GameState(null));
    }

    @Test
    void testConstructorWithEmptyListThrows() {
        assertThrows(IllegalArgumentException.class, () -> new GameState(List.of()));
    }

    @Test
    void testConstructorCreatesCorrectNumberOfPlayers() {
        final var gs = new GameState(List.of(PLAYER_1, PLAYER_2, PLAYER_3));
        assertEquals(THREE_PLAYERS, gs.getPlayers().size());
    }

    // Initial state

    @Test
    void testInitialCurrentPlayerIsFirst() {
        final var gs = new GameState(List.of(PLAYER_1, PLAYER_2));
        assertEquals(PLAYER_1, gs.getCurrentPlayer().getName());
    }

    @Test
    void testInitialPlayerIndexIsZero() {
        final var gs = new GameState(List.of(PLAYER_1, PLAYER_2));
        assertEquals(0, gs.getCurrentPlayerIndex());
    }

    @Test
    void testInitialBallNotMoving() {
        final var gs = new GameState(List.of(PLAYER_1));
        assertFalse(gs.isBallMoving());
    }

    // Players list

    @Test
    void testGetPlayersIsUnmodifiable() {
        final var gs = new GameState(List.of(PLAYER_1));
        assertThrows(UnsupportedOperationException.class,
                () -> gs.getPlayers().add(new Player(PLAYER_2)));
    }

    // Next turn

    @Test
    void testNextTurnAdvancesToNextPlayer() {
        final var gs = new GameState(List.of(PLAYER_1, PLAYER_2));
        gs.nextTurn();
        assertEquals(PLAYER_2, gs.getCurrentPlayer().getName());
        assertEquals(1, gs.getCurrentPlayerIndex());
    }

    @Test
    void testNextTurnWrapsAround() {
        final var gs = new GameState(List.of(PLAYER_1, PLAYER_2));
        gs.nextTurn();
        gs.nextTurn();
        assertEquals(PLAYER_1, gs.getCurrentPlayer().getName());
        assertEquals(0, gs.getCurrentPlayerIndex());
    }

    @Test
    void testNextTurnClearsBallMoving() {
        final var gs = new GameState(List.of(PLAYER_1, PLAYER_2));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.nextTurn();
        assertFalse(gs.isBallMoving());
    }

    // Shot

    @Test
    void testUpdateReturnsShotWhenPending() {
        final var gs = new GameState(List.of(PLAYER_1));
        gs.setPendingShot(VALID_SHOT);
        assertTrue(gs.update().isPresent());
    }

    @Test
    void testUpdateReturnsEmptyWithNoPendingShot() {
        final var gs = new GameState(List.of(PLAYER_1));
        assertTrue(gs.update().isEmpty());
    }

    @Test
    void testUpdateMarksBallMoving() {
        final var gs = new GameState(List.of(PLAYER_1));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        assertTrue(gs.isBallMoving());
    }

    @Test
    void testUpdateIncrementsPlayerShotCount() {
        final var gs = new GameState(List.of(PLAYER_1));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        assertEquals(1, gs.getCurrentPlayer().getShots());
    }

    @Test
    void testSetPendingShotIgnoredWhenBallMoving() {
        final var gs = new GameState(List.of(PLAYER_1));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.setPendingShot(VALID_SHOT);
        assertTrue(gs.update().isEmpty());
    }

    @Test
    void testSetPendingShotIgnoredBelowThreshold() {
        final var gs = new GameState(List.of(PLAYER_1));
        gs.setPendingShot(new Vector2D(SHOT_BELOW_THRESHOLD, 0));
        assertTrue(gs.update().isEmpty());
    }

    // Ball stopped

    @Test
    void testOnBallStoppedClearsBallMoving() {
        final var gs = new GameState(List.of(PLAYER_1));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.onBallStopped();
        assertFalse(gs.isBallMoving());
    }

    // Reset shots

    @Test
    void testResetAllShotsZerosAllCounters() {
        final var gs = new GameState(List.of(PLAYER_1, PLAYER_2));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.resetAllShots();
        gs.getPlayers().forEach(p -> assertEquals(0, p.getShots()));
    }
}
