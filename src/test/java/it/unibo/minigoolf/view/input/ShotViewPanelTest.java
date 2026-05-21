package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.util.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        panel.enableShot(new Point(500, 300));
        final Optional<Vector2D> pos = shotState.getBallPosition();
        assertTrue(pos.isPresent());
        assertEquals(500.0, pos.get().getX(), 1e-9);
        assertEquals(300.0, pos.get().getY(), 1e-9);
    }

    @Test
    void testEnableShotClearsExistingIntent() {
        shotState.updateIntent(new Vector2D(20, 0));
        panel.enableShot(new Point(100, 100));
        assertTrue(shotState.getIntent().isEmpty());
    }

    @Test
    void testEnableShotCancelsPendingShot() {
        shotState.updateIntent(new Vector2D(20, 0));
        shotState.confirmShot();
        panel.enableShot(new Point(100, 100));
        assertTrue(shotState.consume().isEmpty());
    }

    // --- disableShot ---

    @Test
    void testDisableShotPreventsNewShotViaEvents() {
        panel.enableShot(new Point(960, 540));
        panel.disableShot();
        dispatch(MouseEvent.MOUSE_PRESSED,  960, 540);
        dispatch(MouseEvent.MOUSE_DRAGGED,  990, 540);
        dispatch(MouseEvent.MOUSE_RELEASED, 990, 540);
        assertTrue(shotState.consume().isEmpty());
    }

    // --- isNearBall ---

    @Test
    void testIsNearBallWithinRadius() {
        panel.enableShot(new Point(100, 100));
        assertTrue(panel.isNearBall(new Point(110, 110), 20.0)); // dist ≈ 14.1 < 20
    }

    @Test
    void testIsNearBallOutsideRadius() {
        panel.enableShot(new Point(100, 100));
        assertFalse(panel.isNearBall(new Point(200, 200), 20.0)); // dist ≈ 141 > 20
    }

    @Test
    void testIsNearBallAtExactRadius() {
        panel.enableShot(new Point(100, 100));
        assertTrue(panel.isNearBall(new Point(120, 100), 20.0)); // dist = 20, uses <=
    }

    @Test
    void testIsNearBallWithNoBallPositionReturnsFalse() {
        // enableShot not called → getBallPosition() is empty
        assertFalse(panel.isNearBall(new Point(0, 0), 1000.0));
    }

    // --- toLogical ---

    @Test
    void testToLogicalIdentityAtFullSize() {
        final Point result = panel.toLogical(new Point(960, 540));
        assertEquals(960, result.x);
        assertEquals(540, result.y);
    }

    @Test
    void testToLogicalDoubledAtHalfSize() {
        panel.setSize(960, 540); // factor = 2 on both axes
        final Point result = panel.toLogical(new Point(480, 270));
        assertEquals(960, result.x);
        assertEquals(540, result.y);
    }

    // --- updateShotIntent ---

    @Test
    void testUpdateShotIntentDelegatesToState() {
        final var v = new Vector2D(20, 0);
        panel.updateShotIntent(v);
        assertEquals(Optional.of(v), shotState.getIntent());
    }

    // --- shoot ---

    @Test
    void testShootConfirmsValidIntent() {
        shotState.updateIntent(new Vector2D(20, 0)); // normSq = 400 >= 100
        panel.shoot();
        assertTrue(shotState.consume().isPresent());
    }

    @Test
    void testShootIgnoresBelowThresholdIntent() {
        shotState.updateIntent(new Vector2D(5, 0)); // normSq = 25 < 100
        panel.shoot();
        assertTrue(shotState.consume().isEmpty());
    }

    // --- ShotListener integration via event dispatch ---

    /**
     * Full press-drag-release cycle from the ball centre.
     * Dragging 30px right → raw vector (30, 0) → opposite (-30, 0) → normSq = 900 ≥ 100.
     * The shot must be consumable after release.
     */
    @Test
    void testPressDragReleaseFiresShot() {
        panel.enableShot(new Point(960, 540));
        dispatch(MouseEvent.MOUSE_PRESSED,  960, 540);
        dispatch(MouseEvent.MOUSE_DRAGGED,  990, 540);
        dispatch(MouseEvent.MOUSE_RELEASED, 990, 540);
        assertTrue(shotState.consume().isPresent());
    }

    /**
     * Pressing 200px away from the ball centre is outside CLICK_RADIUS (40px).
     * The drag must not start, so no shot is produced.
     */
    @Test
    void testPressFarFromBallDoesNotStartDrag() {
        panel.enableShot(new Point(960, 540));
        dispatch(MouseEvent.MOUSE_PRESSED,  200, 200);
        dispatch(MouseEvent.MOUSE_DRAGGED,  300, 200);
        dispatch(MouseEvent.MOUSE_RELEASED, 300, 200);
        assertTrue(shotState.consume().isEmpty());
    }

    /**
     * Dragging only 3px → opposite normSq = 9 < 100 → below minimum power.
     * confirmShot must be ignored and consume must return empty.
     */
    @Test
    void testDragTooShortProducesNoShot() {
        panel.enableShot(new Point(960, 540));
        dispatch(MouseEvent.MOUSE_PRESSED,  960, 540);
        dispatch(MouseEvent.MOUSE_DRAGGED,  963, 540);
        dispatch(MouseEvent.MOUSE_RELEASED, 963, 540);
        assertTrue(shotState.consume().isEmpty());
    }

    // --- helper ---

    /**
     * Dispatches a synthetic {@link MouseEvent} directly to the panel,
     * bypassing the OS event queue so tests run synchronously in headless mode.
     */
    private void dispatch(final int eventId, final int x, final int y) {
        panel.dispatchEvent(new MouseEvent(panel, eventId,
                System.currentTimeMillis(), 0, x, y, 1, false));
    }
}
