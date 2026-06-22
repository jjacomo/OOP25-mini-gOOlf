package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.model.map.factories.MapA;
import it.unibo.minigoolf.model.map.factories.MapSequence;
import it.unibo.minigoolf.model.map.factories.MapB;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for {@link GameFactory#buildMatch}.
 * Verifies that the factory correctly links all components
 * and that the returned {@link GameController} starts in a valid state.
 * 
 * @author fedesparvo1-a11y
 */
class GameFactoryTest {

    private static final String PLAYER_1 = "Fede";
    private static final String PLAYER_2 = "Daniel";
    private static final String PLAYER_3 = "Giacomo";
    private static final String PLAYER_4 = "Mattia";

    // no-op callback for tests that do not exercise the hole-completed path
    private static final Runnable NO_OP = () -> { };

    // Single map sequence
    private static MapSequence singleMapSequence() {
        return new MapSequence(List.of(new MapA()));
    }

    // Two map sequence
    private static MapSequence twoMapSequence() {
        return new MapSequence(List.of(new MapA(), new MapB()));
    }

    @Test
    void testBuildMatchReturnNonNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_1), singleMapSequence(), NO_OP));
    }

    @Test
    void testBuildMatchShotStateNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_1), singleMapSequence(), NO_OP)
                .getShotState());
    }

    @Test
    void testBuildMatchMapControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_1), singleMapSequence(), NO_OP)
                .getGameMapController());
    }

    @Test
    void testBuildMatchBallControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_1), singleMapSequence(), NO_OP)
                .getGameMapController().getBallController());
    }

    @Test
    void testBuildMatchHoleControllerNotNull() {
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_1), singleMapSequence(), NO_OP)
                .getGameMapController().getHoleController());
    }

    // The first player in the list must be the active player at match start
    @Test
    void testBuildMatchInitialPlayerName() {
        final var ctrl = GameFactory.buildMatch(
                List.of(PLAYER_1, PLAYER_2), singleMapSequence(), NO_OP);
        assertEquals(PLAYER_1, ctrl.getCurrentPlayerName());
    }

    // The turn order must match the constructor order
    @Test
    void testBuildMatchMultiplePlayersCorrectOrder() {
        final var ctrl = GameFactory.buildMatch(
                List.of(PLAYER_1, PLAYER_2, PLAYER_3, PLAYER_4), singleMapSequence(), NO_OP);
        assertEquals(PLAYER_1, ctrl.getCurrentPlayerName());
    }

    // No pending shot before any input
    @Test
    void testBuildMatchShotStateInitiallyEmpty() {
        assertTrue(GameFactory.buildMatch(List.of(PLAYER_1), singleMapSequence(), NO_OP)
                .getShotState().consume().isEmpty());
    }

    // The second map must also return a non null controller
    @Test
    void testBuildMatchOnSecondMapReturnNonNull() {
        final MapSequence seq = twoMapSequence();
        seq.advance();
        assertNotNull(GameFactory.buildMatch(List.of(PLAYER_1), seq, NO_OP));
    }

    // Ball and hole controllers must be valid on the second map too
    @Test
    void testBuildMatchOnSecondMapControllersNotNull() {
        final MapSequence seq = twoMapSequence();
        seq.advance();
        final var ctrl = GameFactory.buildMatch(List.of(PLAYER_1), seq, NO_OP);
        assertNotNull(ctrl.getGameMapController().getBallController());
        assertNotNull(ctrl.getGameMapController().getHoleController());
    }

    // Shot state must start empty on the second map too
    @Test
    void testBuildMatchOnSecondMapShotStateInitiallyEmpty() {
        final MapSequence seq = twoMapSequence();
        seq.advance();
        assertTrue(GameFactory.buildMatch(List.of(PLAYER_1), seq, NO_OP)
                .getShotState().consume().isEmpty());
    }
}
