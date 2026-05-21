package it.unibo.minigoolf.controller.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link GameFactory#buildMatch}.
 * Verifies that the factory correctly wires all components of a match
 * and that the returned {@link GameController} is in a valid initial state.
 */
class GameFactoryTest {

    /**
     * A no-op callback used wherever a hole-completed runnable is required
     * but the test does not exercise that path.
     */
    private static final Runnable NO_OP = () -> { };

    /** The factory must always return a non-null controller. */
    @Test
    void testBuildMatchReturnNonNull() {
        assertNotNull(GameFactory.buildMatch(List.of("Alice"), singleMapSequence(), NO_OP));
    }

    /** ShotState must be initialised and ready to receive input. */
    @Test
    void testBuildMatchShotStateNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of("Alice"), singleMapSequence(), NO_OP)
                .getShotState());
    }

    /** GameMapController must be wired so the view can render the map. */
    @Test
    void testBuildMatchMapControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of("Alice"), singleMapSequence(), NO_OP)
                .getGameMapController());
    }

    /** BallController must exist so physics and input can read/write ball state. */
    @Test
    void testBuildMatchBallControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of("Alice"), singleMapSequence(), NO_OP)
                .getGameMapController().getBallController());
    }

    /** HoleController must exist so the game can detect when the ball enters the hole. */
    @Test
    void testBuildMatchHoleControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of("Alice"), singleMapSequence(), NO_OP)
                .getGameMapController().getHoleController());
    }

    /** The first player in the list must be the current player at match start. */
    @Test
    void testBuildMatchInitialPlayerName() {
        final var ctrl = GameFactory.buildMatch(
                List.of("Alice", "Bob"), singleMapSequence(), NO_OP);
        assertEquals("Alice", ctrl.getCurrentPlayerName());
    }

    /** With multiple players the turn order must match the constructor order. */
    @Test
    void testBuildMatchMultiplePlayersCorrectOrder() {
        final var ctrl = GameFactory.buildMatch(
                List.of("P1", "P2", "P3"), singleMapSequence(), NO_OP);
        assertEquals("P1", ctrl.getCurrentPlayerName());
    }

    /** ShotState must start empty — no pending shot before any input. */
    @Test
    void testBuildMatchShotStateInitiallyEmpty() {
        assertTrue(GameFactory.buildMatch(List.of("Alice"), singleMapSequence(), NO_OP)
                .getShotState().consume().isEmpty());
    }

    /**
     * Before {@link GameController#setShotView} is called, the internal
     * ShotController is null. updateTick must return early without throwing.
     */
    @Test
    void testBuildMatchUpdateTickDoesNotThrowBeforeShotView() {
        assertDoesNotThrow(() ->
            GameFactory.buildMatch(List.of("Alice"), singleMapSequence(), NO_OP)
                .updateTick(0.016));
    }

    /**
     * Builds a minimal {@link it.unibo.minigoolf.model.map.factories.MapSequence}
     * containing only the first map, suitable for tests that do not exercise
     * multi-map progression.
     */
    private static it.unibo.minigoolf.model.map.factories.MapSequence singleMapSequence() {
        return new it.unibo.minigoolf.model.map.factories.MapSequence(
                List.of(new it.unibo.minigoolf.model.map.factories.FirstMap()));
    }
}
