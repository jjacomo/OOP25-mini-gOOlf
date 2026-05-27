package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameStateTest {

    private static final String PLAYER_ALICE = "Alice";
    private static final String PLAYER_BOB = "Bob";
    private static final String PLAYER_CHARLIE = "Charlie";
    private static final int THREE_PLAYERS = 3;
    private static final int SHOT_BELOW_THRESHOLD = 5;
    private static final Vector2D VALID_SHOT = new Vector2D(20, 0); // normSq = 400 >= 100

    // --- Constructor ---

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
        final var gs = new GameState(List.of(PLAYER_ALICE, PLAYER_BOB, PLAYER_CHARLIE));
        assertEquals(THREE_PLAYERS, gs.getPlayers().size());
    }

    // --- Initial state ---

    @Test
    void testInitialCurrentPlayerIsFirst() {
        final var gs = new GameState(List.of(PLAYER_ALICE, PLAYER_BOB));
        assertEquals(PLAYER_ALICE, gs.getCurrentPlayer().getName());
    }

    @Test
    void testInitialPlayerIndexIsZero() {
        final var gs = new GameState(List.of(PLAYER_ALICE, PLAYER_BOB));
        assertEquals(0, gs.getCurrentPlayerIndex());
    }

    @Test
    void testInitialBallNotMoving() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        assertFalse(gs.isBallMoving());
    }

    // --- getPlayers ---

    @Test
    void testGetPlayersIsUnmodifiable() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        assertThrows(UnsupportedOperationException.class,
                () -> gs.getPlayers().add(new Player(PLAYER_BOB)));
    }

    // --- nextTurn ---

    @Test
    void testNextTurnAdvancesToNextPlayer() {
        final var gs = new GameState(List.of(PLAYER_ALICE, PLAYER_BOB));
        gs.nextTurn();
        assertEquals(PLAYER_BOB, gs.getCurrentPlayer().getName());
        assertEquals(1, gs.getCurrentPlayerIndex());
    }

    @Test
    void testNextTurnWrapsAround() {
        final var gs = new GameState(List.of(PLAYER_ALICE, PLAYER_BOB));
        gs.nextTurn();
        gs.nextTurn();
        assertEquals(PLAYER_ALICE, gs.getCurrentPlayer().getName());
        assertEquals(0, gs.getCurrentPlayerIndex());
    }

    @Test
    void testNextTurnClearsBallMoving() {
        final var gs = new GameState(List.of(PLAYER_ALICE, PLAYER_BOB));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.nextTurn();
        assertFalse(gs.isBallMoving());
    }

    // --- setPendingShot / update ---

    @Test
    void testUpdateReturnsShotWhenPending() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        gs.setPendingShot(VALID_SHOT);
        assertTrue(gs.update().isPresent());
    }

    @Test
    void testUpdateReturnsEmptyWithNoPendingShot() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        assertTrue(gs.update().isEmpty());
    }

    @Test
    void testUpdateMarksBallMoving() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        assertTrue(gs.isBallMoving());
    }

    @Test
    void testUpdateIncrementsPlayerShotCount() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        assertEquals(1, gs.getCurrentPlayer().getShots());
    }

    @Test
    void testSetPendingShotIgnoredWhenBallMoving() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.setPendingShot(VALID_SHOT);
        assertTrue(gs.update().isEmpty());
    }

    @Test
    void testSetPendingShotIgnoredBelowThreshold() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        gs.setPendingShot(new Vector2D(SHOT_BELOW_THRESHOLD, 0)); // normSq = 25 < 100
        assertTrue(gs.update().isEmpty());
    }

    // --- onBallStopped ---

    @Test
    void testOnBallStoppedClearsBallMoving() {
        final var gs = new GameState(List.of(PLAYER_ALICE));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.onBallStopped();
        assertFalse(gs.isBallMoving());
    }

    // --- resetAllShots ---

    @Test
    void testResetAllShotsZerosAllCounters() {
        final var gs = new GameState(List.of(PLAYER_ALICE, PLAYER_BOB));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        gs.resetAllShots();
        gs.getPlayers().forEach(p -> assertEquals(0, p.getShots()));
    }
}
