package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

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
        final var gs = new GameState(List.of("Alice", "Bob", "Charlie"));
        assertEquals(3, gs.getPlayers().size());
    }

    // --- Initial state ---

    @Test
    void testInitialCurrentPlayerIsFirst() {
        final var gs = new GameState(List.of("Alice", "Bob"));
        assertEquals("Alice", gs.getCurrentPlayer().getName());
    }

    @Test
    void testInitialPlayerIndexIsZero() {
        final var gs = new GameState(List.of("Alice", "Bob"));
        assertEquals(0, gs.getCurrentPlayerIndex());
    }

    @Test
    void testInitialBallNotMoving() {
        final var gs = new GameState(List.of("Alice"));
        assertFalse(gs.isBallMoving());
    }

    // --- getPlayers ---

    @Test
    void testGetPlayersIsUnmodifiable() {
        final var gs = new GameState(List.of("Alice"));
        assertThrows(UnsupportedOperationException.class,
                () -> gs.getPlayers().add(new Player("Bob")));
    }

    // --- nextTurn ---

    @Test
    void testNextTurnAdvancesToNextPlayer() {
        final var gs = new GameState(List.of("Alice", "Bob"));
        gs.nextTurn();
        assertEquals("Bob", gs.getCurrentPlayer().getName());
        assertEquals(1, gs.getCurrentPlayerIndex());
    }

    @Test
    void testNextTurnWrapsAround() {
        final var gs = new GameState(List.of("Alice", "Bob"));
        gs.nextTurn();
        gs.nextTurn();
        assertEquals("Alice", gs.getCurrentPlayer().getName());
        assertEquals(0, gs.getCurrentPlayerIndex());
    }

    @Test
    void testNextTurnClearsBallMoving() {
        final var gs = new GameState(List.of("Alice", "Bob"));
        gs.setPendingShot(VALID_SHOT);
        gs.update(); // ballMoving = true
        gs.nextTurn();
        assertFalse(gs.isBallMoving());
    }

    // --- setPendingShot / update ---

    @Test
    void testUpdateReturnsShotWhenPending() {
        final var gs = new GameState(List.of("Alice"));
        gs.setPendingShot(VALID_SHOT);
        assertTrue(gs.update().isPresent());
    }

    @Test
    void testUpdateReturnsEmptyWithNoPendingShot() {
        final var gs = new GameState(List.of("Alice"));
        assertTrue(gs.update().isEmpty());
    }

    @Test
    void testUpdateMarksBallMoving() {
        final var gs = new GameState(List.of("Alice"));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        assertTrue(gs.isBallMoving());
    }

    @Test
    void testUpdateIncrementsPlayerShotCount() {
        final var gs = new GameState(List.of("Alice"));
        gs.setPendingShot(VALID_SHOT);
        gs.update();
        assertEquals(1, gs.getCurrentPlayer().getShots());
    }

    @Test
    void testSetPendingShotIgnoredWhenBallMoving() {
        final var gs = new GameState(List.of("Alice"));
        gs.setPendingShot(VALID_SHOT);
        gs.update(); // ballMoving = true
        gs.setPendingShot(VALID_SHOT); // should be ignored
        assertTrue(gs.update().isEmpty());
    }

    @Test
    void testSetPendingShotIgnoredBelowThreshold() {
        final var gs = new GameState(List.of("Alice"));
        gs.setPendingShot(new Vector2D(5, 0)); // normSq = 25 < 100
        assertTrue(gs.update().isEmpty());
    }

    // --- onBallStopped ---

    @Test
    void testOnBallStoppedClearsBallMoving() {
        final var gs = new GameState(List.of("Alice"));
        gs.setPendingShot(VALID_SHOT);
        gs.update(); // ballMoving = true
        gs.onBallStopped();
        assertFalse(gs.isBallMoving());
    }

    // --- resetAllShots ---

    @Test
    void testResetAllShotsZerosAllCounters() {
        final var gs = new GameState(List.of("Alice", "Bob"));
        gs.setPendingShot(VALID_SHOT);
        gs.update(); // Alice shoots once
        gs.resetAllShots();
        gs.getPlayers().forEach(p -> assertEquals(0, p.getShots()));
    }
}
