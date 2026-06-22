package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author fedesparvo1-a11y
 */
class ShotStateTest {

    private static final double VALID_NORM = 20.0;
    private static final double ABOVE_THRESHOLD_NORM = 11.0;
    private static final double AT_THRESHOLD_NORM = 10.0;
    private static final double BELOW_THRESHOLD_NORM = 5.0;
    private static final double OVER_MAX_POWER_NORM = 200.0;
    private static final double EPSILON = 1e-9; //tolerance for double type comparison

    private ShotState shotState;

    @BeforeEach
    void setUp() {
        shotState = new ShotState();
    }

    // Initial state

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

    // UpdateIntent

    @Test
    void testUpdateIntentStoresVector() {
        final var v = new Vector2D(AT_THRESHOLD_NORM, 0);
        shotState.updateIntent(v);
        assertEquals(Optional.of(v), shotState.getIntent());
    }

    @Test
    void testUpdateIntentResetsReadyFlag() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        shotState.confirmShot();
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        assertTrue(shotState.consume().isEmpty());
    }

    // IsValid

    @Test
    void testIsValidAboveThreshold() {
        shotState.updateIntent(new Vector2D(ABOVE_THRESHOLD_NORM, 0));
        assertTrue(shotState.isValid());
    }

    @Test
    void testIsValidAtExactThreshold() {
        shotState.updateIntent(new Vector2D(AT_THRESHOLD_NORM, 0));
        assertTrue(shotState.isValid());
    }

    @Test
    void testIsValidBelowThreshold() {
        shotState.updateIntent(new Vector2D(BELOW_THRESHOLD_NORM, 0));
        assertFalse(shotState.isValid());
    }

    // ConfirmShot and consume

    @Test
    void testConfirmValidIntentMakesConsumable() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        shotState.confirmShot();
        assertTrue(shotState.consume().isPresent());
    }

    @Test
    void testConfirmInvalidIntentNotConsumed() {
        shotState.updateIntent(new Vector2D(BELOW_THRESHOLD_NORM, 0));
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
        final var v = new Vector2D(VALID_NORM, 0);
        shotState.updateIntent(v);
        shotState.confirmShot();
        assertEquals(Optional.of(v), shotState.consume());
    }

    @Test
    void testConsumeClampsToPower() {
        shotState.updateIntent(new Vector2D(OVER_MAX_POWER_NORM, 0));
        shotState.confirmShot();
        final var result = shotState.consume();
        assertTrue(result.isPresent());
        assertTrue(result.get().getNorm() <= ShotState.MAX_POWER + EPSILON);
    }

    @Test
    void testConsumeIsOneShot() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        shotState.confirmShot();
        shotState.consume();
        assertTrue(shotState.consume().isEmpty());
    }

    @Test
    void testConsumeResetsIntent() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        shotState.confirmShot();
        shotState.consume();
        assertTrue(shotState.getIntent().isEmpty());
    }

    // Reset

    @Test
    void testResetSetsBallPosition() {
        final var pos = new Vector2D(100, 200);
        shotState.reset(pos);
        assertEquals(Optional.of(pos), shotState.getBallPosition());
    }

    @Test
    void testResetClearsIntent() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        shotState.reset(new Vector2D(0, 0));
        assertTrue(shotState.getIntent().isEmpty());
    }
}
