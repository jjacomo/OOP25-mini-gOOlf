package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.controller.game.GameContext;
import it.unibo.minigoolf.controller.game.GameFactory;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.physics.PhysicsControllerImpl;
import it.unibo.minigoolf.controller.shot.ShotController;
import it.unibo.minigoolf.controller.shot.ShotControllerImpl;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.view.MainWindow;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main controller.
 * Wires sub-controllers together and runs the game loop.
 * Model creation is delegated to {@link GameFactory}.
 *
 * @author dani and fede
 */
public final class MainControllerImpl implements MainController, ActionListener {

    private static final int FPS = 60;
    private static final double STOP_THRESHOLD_SQ = 0.5;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;
    private static final double MAX_DELTA_TIME = 0.1;

    private long lastTime = System.nanoTime();

    private final Timer timer;
    private final GameContext ctx;
    private final PhysicsController physicsController;
    private final ShotController shotController;
    private final MainWindow mainWindow;
    private final NavigationController navigationController;

    /**
     * Creates and wires all components.
     */
    public MainControllerImpl() {
        // TODO: pass real player names
        this.ctx = GameFactory.build(List.of("Player 1"));

        this.physicsController = new PhysicsControllerImpl(ctx.map());
        this.navigationController = new NavigationController(this);
        this.mainWindow = new MainWindow(this, navigationController, ctx);
        this.navigationController.setMainWindow(mainWindow);

        this.shotController = new ShotControllerImpl(
            ctx.shotState(), ctx.gameState(), ctx.map(), mainWindow.getShotViewPanel());

        // Enable shot input at the initial ball position.
        shotController.onBallStopped(ctx.map().getBall().getPosition());

        this.timer = new Timer(1000 / FPS, this);
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final long now = System.nanoTime();
        double deltaTime = (now - lastTime) / NANOS_PER_SECOND;
        if (deltaTime > MAX_DELTA_TIME) {
            deltaTime = 1.0 / FPS;
        }
        lastTime = now;

        shotController.tick();

        if (ctx.gameState().isBallMoving()) {
            physicsController.update(deltaTime);

            final Vector2D vel = ctx.map().getBall().getVelocity();
            if (vel.getNormSquared() < STOP_THRESHOLD_SQ) {
                ctx.map().getBall().setVelocity(new Vector2D(0, 0));
                ctx.gameState().onBallStopped();
                shotController.onBallStopped(ctx.map().getBall().getPosition());
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
