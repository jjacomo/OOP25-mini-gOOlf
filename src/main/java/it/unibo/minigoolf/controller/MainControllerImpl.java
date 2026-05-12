package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapControllerImpl;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.physics.PhysicsControllerImpl;
import it.unibo.minigoolf.controller.shot.ShotController;
import it.unibo.minigoolf.controller.shot.ShotControllerImpl;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.factories.FirstMap;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.view.MainWindow;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main controller.
 * Wires all components together and runs the game loop.
 *
 * @author dani and fede
 */
public final class MainControllerImpl implements MainController, ActionListener {

    private static final int FPS = 60;

    /** Squared speed below which the ball is considered stopped. */
    private static final double STOP_THRESHOLD_SQ = 0.5;

    private long lastTime = System.nanoTime();

    private final Timer timer;
    private final GameState gameState;
    private final GameMap map;
    private final PhysicsController physicsController;
    private final ShotController shotController;
    private final MainWindow mainWindow;
    private final NavigationController navigationController;

    /**
     * Creates and wires all components.
     */
    public MainControllerImpl() {
        // TODO: let the player write their own nickname
        this.gameState = new GameState(List.of("Player 1"));
        this.map = new FirstMap().buildGameMap();
        final GameMapController gameMapController = new GameMapControllerImpl(map);
        this.physicsController = new PhysicsControllerImpl(map);
        this.navigationController = new NavigationController(this);

        final ShotState shotState = new ShotState();

        // MainWindow riceve lo stesso ShotState così ShotViewPanel e ShotControllerImpl
        // condividono la stessa istanza.
        this.mainWindow = new MainWindow(this, this.navigationController, gameState,
                gameMapController, shotState);
        this.navigationController.setMainWindow(this.mainWindow);

        this.shotController = new ShotControllerImpl(
            shotState, gameState, map, mainWindow.getShotViewPanel());

        // Enable shot input at the initial ball position.
        shotController.onBallStopped(map.getBall().getPosition());

        this.timer = new Timer(1000 / FPS, this);
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final long startTime = System.nanoTime();
        double deltaTime = (startTime - lastTime) / 1_000_000_000.0;
        if (deltaTime > 0.1) {
            deltaTime = 1.0 / FPS;
        }
        lastTime = startTime;

        // ShotController handles shot queuing and application to the ball.
        shotController.tick();

        // Run physics and detect stop only while ball is moving.
        if (gameState.isBallMoving()) {
            physicsController.update(deltaTime);

            final Vector2D vel = map.getBall().getVelocity();
            if (vel.getNormSquared() < STOP_THRESHOLD_SQ) {
                map.getBall().setVelocity(new Vector2D(0, 0));
                gameState.onBallStopped();
                shotController.onBallStopped(map.getBall().getPosition());
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
