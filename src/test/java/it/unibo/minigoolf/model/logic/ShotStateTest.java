package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShotStateTest {

    private ShotState shotState;

    @BeforeEach
    void setUp() {
        shotState = new ShotState();
    }

    // --- Initial state ---

    @Test
    void testInitialIntentIsEmpty() {
        assertTrue(shotState.getIntent().isEmpty());
    }

    @Test
    void testInitialBallPositionIsEmpty() {
        assertTrue(shotState.getBallPosition().isEmpty());
    }

    @Test
    void testInitialIsNotValid() {
        assertFalse(shotState.isValid());
    }

    @Test
    void testInitialConsumeIsEmpty() {
        assertTrue(shotState.consume().isEmpty());
    }

    // --- updateIntent ---

    @Test
    void testUpdateIntentStoresVector() {
        final var v = new Vector2D(10, 0);
        shotState.updateIntent(v);
        assertEquals(Optional.of(v), shotState.getIntent());
    }

    @Test
    void testUpdateIntentResetsReadyFlag() {
        // confirm then update again → shotReady reset, consume returns empty
        shotState.updateIntent(new Vector2D(20, 0));
        shotState.confirmShot();
        shotState.updateIntent(new Vector2D(20, 0));
        assertTrue(shotState.consume().isEmpty());
    }

    // --- isValid ---

    @Test
    void testIsValidAboveThreshold() {
        shotState.updateIntent(new Vector2D(11, 0)); // normSq = 121 >= 100
        assertTrue(shotState.isValid());
    }

    @Test
    void testIsValidAtExactThreshold() {
        shotState.updateIntent(new Vector2D(10, 0)); // normSq = 100 >= 100
        assertTrue(shotState.isValid());
    }

    @Test
    void testIsValidBelowThreshold() {
        shotState.updateIntent(new Vector2D(5, 0)); // normSq = 25 < 100
        assertFalse(shotState.isValid());
    }

    // --- confirmShot / consume ---

    @Test
    void testConfirmValidIntentMakesConsumable() {
        shotState.updateIntent(new Vector2D(20, 0));
        shotState.confirmShot();
        assertTrue(shotState.consume().isPresent());
    }

    @Test
    void testConfirmInvalidIntentNotConsumed() {
        shotState.updateIntent(new Vector2D(5, 0)); // below threshold
        shotState.confirmShot();
        assertTrue(shotState.consume().isEmpty());
    }

    @Test
    void testConfirmWithoutIntentNotConsumed() {
        shotState.confirmShot();
        assertTrue(shotState.consume().isEmpty());
    }

    @Test
    void testConsumeReturnsCorrectVector() {
        final var v = new Vector2D(20, 0); // norm 20 <= MAX_POWER, no clamping
        shotState.updateIntent(v);
        shotState.confirmShot();
        assertEquals(Optional.of(v), shotState.consume());
    }

    @Test
    void testConsumeClampsToPower() {
        shotState.updateIntent(new Vector2D(200, 0)); // norm 200 > MAX_POWER 150
        shotState.confirmShot();
        final var result = shotState.consume();
        assertTrue(result.isPresent());
        assertTrue(result.get().getNorm() <= ShotState.MAX_POWER + 1e-9);
    }

    @Test
    void testConsumeIsOneShot() {
        shotState.updateIntent(new Vector2D(20, 0));
        shotState.confirmShot();
        shotState.consume();
        assertTrue(shotState.consume().isEmpty());
    }

    @Test
    void testConsumeResetsIntent() {
        shotState.updateIntent(new Vector2D(20, 0));
        shotState.confirmShot();
        shotState.consume();
        assertTrue(shotState.getIntent().isEmpty());
    }

    // --- reset ---

    @Test
    void testResetSetsBallPosition() {
        final var pos = new Vector2D(100, 200);
        shotState.reset(pos);
        assertEquals(Optional.of(pos), shotState.getBallPosition());
    }

    @Test
    void testResetClearsIntent() {
        shotState.updateIntent(new Vector2D(20, 0));
        shotState.reset(new Vector2D(0, 0));
        assertTrue(shotState.getIntent().isEmpty());
    }

    @Test
    void testResetCancelsPendingShot() {
        shotState.updateIntent(new Vector2D(20, 0));
        shotState.confirmShot();
        shotState.reset(new Vector2D(0, 0));
        assertTrue(shotState.consume().isEmpty());
    }
}
