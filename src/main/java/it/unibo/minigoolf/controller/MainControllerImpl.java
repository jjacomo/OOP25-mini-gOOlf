package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapControllerImpl;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.physics.PhysicsControllerImpl;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.factories.FirstMap;
// import it.unibo.minigoolf.model.map.factories.TestGameMapFactory;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.view.MainWindow;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

/**
 * Main controller.
 *
 * @author dani and fede
 */
public final class MainControllerImpl implements MainController, ActionListener {

    private static final int FPS = 60;
    private long lastTime = System.nanoTime();

    /** Squared speed below which the ball is considered stopped. */
    private static final double STOP_THRESHOLD_SQ = 0.5;

    private final Timer timer;
    private final GameState gameState;
    private final GameMap map;
    private final GameMapController gameMapController;
    private final PhysicsController physicsController;
    private final MainWindow mainWindow;
    private final NavigationController navigationController;

    /**
     * Creates the controller, the game state and the main window.
     */
    public MainControllerImpl() {
        // TODO: let the player write their own nickname
        this.gameState = new GameState(List.of("Player 1"));
        // this.map = new TestGameMapFactory().buildGameMap();
        this.map = new FirstMap().buildGameMap();
        this.gameMapController = new GameMapControllerImpl(map);
        this.physicsController = new PhysicsControllerImpl(map);
        this.navigationController = new NavigationController(this);
        this.mainWindow = new MainWindow(this, this.navigationController, gameState, gameMapController);
        this.navigationController.setMainWindow(this.mainWindow);
        this.timer = new Timer(1000 / FPS, this);
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(final ActionEvent e) {
        long startTime = System.nanoTime();
        double deltaTime = (startTime - lastTime) / 1_000_000_000.0;
        if (deltaTime > 0.1) { // Cap deltaTime to avoid issues when the game is paused.
            deltaTime = 1.0 / FPS;
        }
        lastTime = startTime;
        // Poll the view for a pending shot and apply it to the ball.
        final Optional<Vector2D> pendingShot = mainWindow.consumePendingShot();
        if (pendingShot.isPresent()) {
            gameState.setPendingShot(pendingShot.get());
        }

        final Optional<Vector2D> shot = gameState.update();
        if (shot.isPresent()) {
            map.getBall().setVelocity(shot.get());
        }

        // Run physics and detect stop only while ball is moving.
        if (gameState.isBallMoving()) {
            physicsController.update(deltaTime);

            final Vector2D vel = map.getBall().getVelocity();
            if (vel.getNormSquared() < STOP_THRESHOLD_SQ) {
                map.getBall().setVelocity(new Vector2D(0, 0));
                gameState.onBallStopped();
                // Pass the real ball position so the overlay aligns correctly.
                mainWindow.onBallStopped(map.getBall().getPosition());
            }
        }

        mainWindow.repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        physicsController.setVelocityStrategy(new BasicFrictionStrategy());
        timer.start();
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        timer.stop();
    }
}
