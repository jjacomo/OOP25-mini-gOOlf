package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.util.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link ShotViewPanel}.
 *
 * <p>The headless property must be set before any AWT class is loaded.
 * The static initialiser below runs before the class body and guarantees
 * this even when the JVM loads AWT eagerly.</p>
 */
class ShotViewPanelTest {

    // Must be set before any AWT class is loaded (static initialiser runs first).
    static {
        System.setProperty("java.awt.headless", "true");
    }

    private static final int LOGICAL_W = 1920;
    private static final int LOGICAL_H = 1080;
    private static final int BALL_X = 960;
    private static final int BALL_Y = 540;
    private static final int DRAG_X = 990;
    private static final int DRAG_SHORT_X = 963;
    private static final int FAR_X = 200;
    private static final int FAR_Y = 200;
    private static final int FAR_DRAG_X = 300;
    private static final int ENABLE_X = 500;
    private static final int ENABLE_Y = 300;
    private static final int NEAR_X = 110;
    private static final int NEAR_Y = 110;
    private static final int FAR_CHECK_X = 200;
    private static final int FAR_CHECK_Y = 200;
    private static final int EXACT_X = 120;
    private static final int BASE_X = 100;
    private static final int BASE_Y = 100;
    private static final double CLICK_RADIUS = 20.0;
    private static final double EPSILON = 1e-9;
    private static final double VALID_NORM = 20.0;
    private static final double BELOW_THRESHOLD_NORM = 5.0;
    private static final int HALF_W = 960;
    private static final int HALF_H = 540;

    private ShotState shotState;
    private ShotViewPanel panel;

    @BeforeEach
    void setUp() {
        shotState = new ShotState();
        panel = new ShotViewPanel(shotState);
        // 1:1 physical-to-logical mapping keeps all coordinate arithmetic trivial.
        panel.setSize(LOGICAL_W, LOGICAL_H);
    }

    // --- enableShot ---

    @Test
    void testEnableShotSetsBallPosition() {
        panel.enableShot(new Point(ENABLE_X, ENABLE_Y));
        final Optional<Vector2D> pos = shotState.getBallPosition();
        assertTrue(pos.isPresent());
        assertEquals((double) ENABLE_X, pos.get().getX(), EPSILON);
        assertEquals((double) ENABLE_Y, pos.get().getY(), EPSILON);
    }

    @Test
    void testEnableShotClearsExistingIntent() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        panel.enableShot(new Point(BASE_X, BASE_Y));
        assertTrue(shotState.getIntent().isEmpty());
    }

    @Test
    void testEnableShotCancelsPendingShot() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0));
        shotState.confirmShot();
        panel.enableShot(new Point(BASE_X, BASE_Y));
        assertTrue(shotState.consume().isEmpty());
    }

    // --- disableShot ---

    @Test
    void testDisableShotPreventsNewShotViaEvents() {
        panel.enableShot(new Point(BALL_X, BALL_Y));
        panel.disableShot();
        dispatch(MouseEvent.MOUSE_PRESSED, BALL_X, BALL_Y);
        dispatch(MouseEvent.MOUSE_DRAGGED, DRAG_X, BALL_Y);
        dispatch(MouseEvent.MOUSE_RELEASED, DRAG_X, BALL_Y);
        assertTrue(shotState.consume().isEmpty());
    }

    // --- isNearBall ---

    @Test
    void testIsNearBallWithinRadius() {
        panel.enableShot(new Point(BASE_X, BASE_Y));
        assertTrue(panel.isNearBall(new Point(NEAR_X, NEAR_Y), CLICK_RADIUS)); // dist ≈ 14.1 < 20
    }

    @Test
    void testIsNearBallOutsideRadius() {
        panel.enableShot(new Point(BASE_X, BASE_Y));
        assertFalse(panel.isNearBall(new Point(FAR_CHECK_X, FAR_CHECK_Y), CLICK_RADIUS)); // dist ≈ 141 > 20
    }

    @Test
    void testIsNearBallAtExactRadius() {
        panel.enableShot(new Point(BASE_X, BASE_Y));
        assertTrue(panel.isNearBall(new Point(EXACT_X, BASE_Y), CLICK_RADIUS)); // dist = 20, uses <=
    }

    @Test
    void testIsNearBallWithNoBallPositionReturnsFalse() {
        // enableShot not called → getBallPosition() is empty
        assertFalse(panel.isNearBall(new Point(0, 0), LOGICAL_W));
    }

    // --- toLogical ---

    @Test
    void testToLogicalIdentityAtFullSize() {
        final Point result = panel.toLogical(new Point(BALL_X, BALL_Y));
        assertEquals(BALL_X, result.x);
        assertEquals(BALL_Y, result.y);
    }

    @Test
    void testToLogicalDoubledAtHalfSize() {
        panel.setSize(HALF_W, HALF_H); // factor = 2 on both axes
        final Point result = panel.toLogical(new Point(HALF_W / 2, HALF_H / 2));
        assertEquals(BALL_X, result.x);
        assertEquals(BALL_Y, result.y);
    }

    // --- updateShotIntent ---

    @Test
    void testUpdateShotIntentDelegatesToState() {
        final var v = new Vector2D(VALID_NORM, 0);
        panel.updateShotIntent(v);
        assertEquals(Optional.of(v), shotState.getIntent());
    }

    // --- shoot ---

    @Test
    void testShootConfirmsValidIntent() {
        shotState.updateIntent(new Vector2D(VALID_NORM, 0)); // normSq = 400 >= 100
        panel.shoot();
        assertTrue(shotState.consume().isPresent());
    }

    @Test
    void testShootIgnoresBelowThresholdIntent() {
        shotState.updateIntent(new Vector2D(BELOW_THRESHOLD_NORM, 0)); // normSq = 25 < 100
        panel.shoot();
        assertTrue(shotState.consume().isEmpty());
    }

    // --- ShotListener integration via event dispatch ---

    /**
     * Full press-drag-release cycle from the ball centre.
     * Dragging 30px right → raw vector (30, 0) → opposite (-30, 0) → normSq = 900 >= 100.
     * The shot must be consumable after release.
     */
    @Test
    void testPressDragReleaseFiresShot() {
        panel.enableShot(new Point(BALL_X, BALL_Y));
        dispatch(MouseEvent.MOUSE_PRESSED, BALL_X, BALL_Y);
        dispatch(MouseEvent.MOUSE_DRAGGED, DRAG_X, BALL_Y);
        dispatch(MouseEvent.MOUSE_RELEASED, DRAG_X, BALL_Y);
        assertTrue(shotState.consume().isPresent());
    }

    /**
     * Pressing 200px away from the ball centre is outside CLICK_RADIUS (40px).
     * The drag must not start, so no shot is produced.
     */
    @Test
    void testPressFarFromBallDoesNotStartDrag() {
        panel.enableShot(new Point(BALL_X, BALL_Y));
        dispatch(MouseEvent.MOUSE_PRESSED, FAR_X, FAR_Y);
        dispatch(MouseEvent.MOUSE_DRAGGED, FAR_DRAG_X, FAR_Y);
        dispatch(MouseEvent.MOUSE_RELEASED, FAR_DRAG_X, FAR_Y);
        assertTrue(shotState.consume().isEmpty());
    }

    /**
     * Dragging only 3px → opposite normSq = 9 lt 100 → below minimum power.
     * confirmShot must be ignored and consume must return empty.
     */
    @Test
    void testDragTooShortProducesNoShot() {
        panel.enableShot(new Point(BALL_X, BALL_Y));
        dispatch(MouseEvent.MOUSE_PRESSED, BALL_X, BALL_Y);
        dispatch(MouseEvent.MOUSE_DRAGGED, DRAG_SHORT_X, BALL_Y);
        dispatch(MouseEvent.MOUSE_RELEASED, DRAG_SHORT_X, BALL_Y);
        assertTrue(shotState.consume().isEmpty());
    }

    // --- helper ---

    /**
     * Dispatches a synthetic {@link MouseEvent} directly to the panel,
     * bypassing the OS event queue so tests run synchronously in headless mode.
     *
     * @param eventId the mouse event id (e.g. {@link MouseEvent#MOUSE_PRESSED})
     * @param x       the x coordinate in panel-physical pixels
     * @param y       the y coordinate in panel-physical pixels
     */
    private void dispatch(final int eventId, final int x, final int y) {
        panel.dispatchEvent(new MouseEvent(panel, eventId,
                System.currentTimeMillis(), 0, x, y, 1, false));
    }
}
