package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.model.map.factories.FirstMap;
import it.unibo.minigoolf.model.map.factories.MapSequence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link GameFactory#buildMatch}.
 * Verifies that the factory correctly wires all components of a match
 * and that the returned {@link GameController} is in a valid initial state.
 */
class GameFactoryTest {

    private static final String PLAYER_ALICE = "Alice";
    private static final String PLAYER_BOB = "Bob";
    private static final String PLAYER_P1 = "P1";
    private static final String PLAYER_P2 = "P2";
    private static final String PLAYER_P3 = "P3";
    private static final double ONE_FRAME = 0.016;

    /**
     * A no-op callback used wherever a hole-completed runnable is required
     * but the test does not exercise that path.
     */
    private static final Runnable NO_OP = () -> { };

    /** The factory must always return a non-null controller. */
    @Test
    void testBuildMatchReturnNonNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_ALICE), singleMapSequence(), NO_OP));
    }

    /** ShotState must be initialised and ready to receive input. */
    @Test
    void testBuildMatchShotStateNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_ALICE), singleMapSequence(), NO_OP)
                .getShotState());
    }

    /** GameMapController must be wired so the view can render the map. */
    @Test
    void testBuildMatchMapControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_ALICE), singleMapSequence(), NO_OP)
                .getGameMapController());
    }

    /** BallController must exist so physics and input can read/write ball state. */
    @Test
    void testBuildMatchBallControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_ALICE), singleMapSequence(), NO_OP)
                .getGameMapController().getBallController());
    }

    /** HoleController must exist so the game can detect when the ball enters the hole. */
    @Test
    void testBuildMatchHoleControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_ALICE), singleMapSequence(), NO_OP)
                .getGameMapController().getHoleController());
    }

    /** The first player in the list must be the current player at match start. */
    @Test
    void testBuildMatchInitialPlayerName() {
        final var ctrl = GameFactory.buildMatch(
                List.of(PLAYER_ALICE, PLAYER_BOB), singleMapSequence(), NO_OP);
        assertEquals(PLAYER_ALICE, ctrl.getCurrentPlayerName());
    }

    /** With multiple players the turn order must match the constructor order. */
    @Test
    void testBuildMatchMultiplePlayersCorrectOrder() {
        final var ctrl = GameFactory.buildMatch(
                List.of(PLAYER_P1, PLAYER_P2, PLAYER_P3), singleMapSequence(), NO_OP);
        assertEquals(PLAYER_P1, ctrl.getCurrentPlayerName());
    }

    /** ShotState must start empty — no pending shot before any input. */
    @Test
    void testBuildMatchShotStateInitiallyEmpty() {
        assertTrue(GameFactory.buildMatch(List.of(PLAYER_ALICE), singleMapSequence(), NO_OP)
                .getShotState().consume().isEmpty());
    }

    /**
     * Before {@link GameController#setShotView} is called, the internal
     * ShotController is null. updateTick must return early without throwing.
     */
    @Test
    void testBuildMatchUpdateTickDoesNotThrowBeforeShotView() {
        assertDoesNotThrow(() ->
            GameFactory.buildMatch(List.of(PLAYER_ALICE), singleMapSequence(), NO_OP)
                .updateTick(ONE_FRAME));
    }

    /**
     * Builds a minimal {@link MapSequence} containing only the first map,
     * suitable for tests that do not exercise multi-map progression.
     *
     * @return a single-map sequence
     */
    private static MapSequence singleMapSequence() {
        return new MapSequence(List.of(new FirstMap()));
    }
}
