package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.shot.ShotController;
import it.unibo.minigoolf.controller.shot.ShotControllerImpl;
import it.unibo.minigoolf.controller.shot.ShotView;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.HoleChecker;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.controller.gamemapcontroller.BallData;
import it.unibo.minigoolf.controller.gamemapcontroller.HoleData;
import it.unibo.minigoolf.controller.gamemapcontroller.MapElementsView;
import it.unibo.minigoolf.controller.gamemapcontroller.ObstacleData;
import it.unibo.minigoolf.controller.gamemapcontroller.SurfaceData;
import it.unibo.minigoolf.model.save.PlayerSaveData;
import it.unibo.minigoolf.model.save.SaveData;
import it.unibo.minigoolf.util.Vector2D;

import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Implementation of {@link GameController}.
 * Uses functional callbacks instead of storing stateful collaborators directly.
 *
 * @author fedesparvo1-a11y
 */
public final class GameControllerImpl implements GameController {

    /**
     * Maximum squared speed at which the ball can enter the hole while still
     * moving.
     * Set to (MAX_POWER * SHOT_SCALE / 2)² = (1500 / 2)² = 750² = 562_500.
     */
    private static final double HOLE_ENTRY_MAX_SPEED_SQ = 562_500.0;

    // Maximum shots a player can take before their turn ends automatically.
    private static final int MAX_SHOTS = 7;

    private final GameMapController gameMapController;
    private final ShotState shotState;

    /** {@code gameState::isBallMoving} — avoids storing GameState directly. */
    private final BooleanSupplier ballMovingChecker;

    /** {@code gameState::onBallStopped} — avoids storing GameState directly. */
    private final Runnable ballStoppedNotifier;

    /** {@code gameState::setPendingShot} — passed to ShotControllerImpl. */
    private final Consumer<Vector2D> pendingShotSubmitter;

    /** {@code gameState::update} — passed to ShotControllerImpl. */
    private final Supplier<Optional<Vector2D>> shotUpdater;

    /**
     * {@code physicsController::update} — avoids storing PhysicsController
     * directly.
     */
    private final Consumer<Double> physicsUpdater;

    /**
     * {@code () -> gameState.getCurrentPlayer().getName()} — avoids storing
     * GameState.
     */
    private final Supplier<String> currentPlayerNameSupplier;

    /** {@code gameState::getCurrentPlayerIndex} — used for save/load. */
    private final IntSupplier currentPlayerIndexSupplier;

    /**
     * {@code () -> gameState.getCurrentPlayer().getShots()} — used for HUD and turn
     * limit.
     */
    private final IntSupplier currentShotsSupplier;

    /** {@code gameState::nextTurn} — advances to the next player. */
    private final Runnable nextTurnTrigger;

    // Checks whether the current player is the last in the list.
    private final BooleanSupplier lastPlayerChecker;

    // Ball start position for the current map — used to reset position on next
    // turn.
    private final Vector2D initialBallPosition;

    // Supplies player save snapshots without storing GameState directly.
    private final Supplier<List<PlayerSaveData>> playerSaveDataSupplier;

    // Supplies current ball X in logical coordinates.
    private final Supplier<Double> ballXSupplier;

    // Supplies current ball Y in logical coordinates.
    private final Supplier<Double> ballYSupplier;

    /// Checks whether the ball has reached the hole.
    private final HoleChecker holeChecker;

    // Called when all players have completed the hole. By default it does nothing
    // (no-op).
    private Runnable onHoleCompleted = () -> {
    };

    /** {@code () -> { ... }} estrae tutti i giocatori e i loro tiri */
    private final Supplier<Map<String, Integer>> allScoresSupplier;

    private ShotController shotController;
    private MapElementsView mapElementsView;

    /**
     * Builds the controller extracting behaviors from the given collaborators.
     * 
     * @param gameState         the central game logic
     * @param gameMapController the map controller
     * @param shotState         the shot input state
     * @param physicsController the physics engine
     */
    public GameControllerImpl(
            final GameState gameState,
            final GameMapController gameMapController,
            final ShotState shotState,
            final PhysicsController physicsController) {
        this.gameMapController = gameMapController;
        this.shotState = shotState;
        this.ballMovingChecker = gameState::isBallMoving;
        this.ballStoppedNotifier = gameState::onBallStopped;
        this.pendingShotSubmitter = gameState::setPendingShot;
        this.shotUpdater = gameState::update;
        this.currentPlayerNameSupplier = () -> gameState.getCurrentPlayer().getName();
        this.currentPlayerIndexSupplier = gameState::getCurrentPlayerIndex;
        this.currentShotsSupplier = () -> gameState.getCurrentPlayer().getShots();
        this.nextTurnTrigger = gameState::nextTurn;
        this.lastPlayerChecker = () -> gameState.getCurrentPlayerIndex() == gameState.getPlayers().size() - 1;
        this.playerSaveDataSupplier = () -> gameState.getPlayers().stream()
                .map(p -> new PlayerSaveData(p.getName(), p.getShots()))
                .toList();
        this.ballXSupplier = () -> gameMapController.getBallController().getPosition().getX();
        this.ballYSupplier = () -> gameMapController.getBallController().getPosition().getY();
        physicsController.setVelocityStrategy(new BasicFrictionStrategy());
        this.physicsUpdater = physicsController::update;
        this.holeChecker = new HoleChecker(
                gameMapController.getHoleController().getPosition(),
                gameMapController.getHoleController().getRadius());
        this.initialBallPosition = gameMapController.getBallController().getPosition();
        // Scores supplier to get the scores of each player from a particular map
        this.allScoresSupplier = () -> {
            final Map<String, Integer> scores = new LinkedHashMap<>();
            for (final var p : gameState.getPlayers()) {
                scores.put(p.getName(), p.getShots());
            }
            return scores;
        };
    }

    /** {@inheritDoc} */
    @Override
    public void setOnHoleCompleted(final Runnable onHoleCompleted) {
        this.onHoleCompleted = onHoleCompleted;
    }

    @Override
    public void setMapElementsView(final MapElementsView view) {
        this.mapElementsView = view;
        pushMapElementsData();
    }

    private void pushMapElementsData() {
        if (mapElementsView == null) {
            return;
        }
        final List<SurfaceData> surfaces = gameMapController.getSurfaceControllers().stream()
                .map(sc -> new SurfaceData(sc.getZIndex(), sc.getTypeIds(), sc.getShape(), sc.getWind()))
                .toList();
        final List<ObstacleData> obstacles = gameMapController.getObstacleController()
                .getObstacles().stream().map(o -> new ObstacleData(o.getShape(), 
                o.getBounciness(), o instanceof it.unibo.minigoolf.model.obstacles
                .PortalObstacle)).toList();
        final HoleData hole = new HoleData(
                gameMapController.getHoleController().getShape(),
                gameMapController.getHoleController().getPosition(),
                gameMapController.getHoleController().getRadius());
        final BallData ball = new BallData(gameMapController.getBallController().getBallShape());
        mapElementsView.updateGraphics(surfaces, obstacles, hole, ball);
    }

    /** {@inheritDoc} */
    @Override
    public void setShotView(final ShotView shotView) {
        this.shotController = new ShotControllerImpl(
                shotState,
                pendingShotSubmitter,
                shotUpdater,
                gameMapController,
                shotView);
        shotController.onBallStopped(
                gameMapController.getBallController().getPosition());
    }

    /** {@inheritDoc} */
    @Override
    public void updateTick(final double deltaTime) {
        pushMapElementsData();
        if (shotController == null) {
            return;
        }
        shotController.tick();

        if (!ballMovingChecker.getAsBoolean()) {
            return;
        }
        physicsUpdater.accept(deltaTime);

        final Vector2D ballPos = gameMapController.getBallController().getPosition();
        if (!gameMapController.getBallController().isBallMoving()) {
            ballStoppedNotifier.run();
            final boolean turnOver = holeChecker.isBallInHole(ballPos)
                    || currentShotsSupplier.getAsInt() >= MAX_SHOTS;
            handleTurnEnd(ballPos, turnOver);
        } else if (isSlowEnoughForHole() && holeChecker.isBallInHole(ballPos)) {
            ballStoppedNotifier.run();
            handleTurnEnd(ballPos, true);
        }
    }

    /**
     * Returns true if the ball's current speed is low enough to fall into the hole.
     *
     * @return true if speed squared is within the hole-entry threshold
     */
    private boolean isSlowEnoughForHole() {
        return gameMapController.getBallController()
                .getVelocity().getNormSquared() <= HOLE_ENTRY_MAX_SPEED_SQ;
    }

    /**
     * Handles the end of a player's turn.
     * If the turn condition is met (hole scored or max shots reached), advances
     * to the next player or runs {@code onHoleCompleted} if it was the last.
     * Otherwise simply re-enables input for the current player.
     *
     * @param ballPos      current ball position
     * @param turnFinished true if this player's turn is over
     */
    private void handleTurnEnd(final Vector2D ballPos, final boolean turnFinished) {
        if (turnFinished) {
            if (lastPlayerChecker.getAsBoolean()) {
                onHoleCompleted.run();
            } else {
                nextTurnTrigger.run();
                gameMapController.getBallController()
                        .updatePosition(initialBallPosition);
                gameMapController.getBallController()
                        .updateVelocity(Vector2D.ZERO);
                if (shotController != null) {
                    shotController.onBallStopped(initialBallPosition);
                }
            }
        } else if (shotController != null) {
            shotController.onBallStopped(ballPos);
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentPlayerName() {
        return currentPlayerNameSupplier.get();
    }

    /** {@inheritDoc} */
    @Override
    public int getCurrentPlayerShots() {
        return currentShotsSupplier.getAsInt();
    }

    /** {@inheritDoc} */
    @Override
    public ShotState getShotState() {
        return shotState;
    }

    /** {@inheritDoc} */
    @Override
    public GameMapController getGameMapController() {
        return gameMapController;
    }

    /** {@inheritDoc} */
    @Override
    public SaveData createSaveData(final String mapId) {
        return new SaveData(
                currentPlayerIndexSupplier.getAsInt(),
                mapId,
                playerSaveDataSupplier.get(),
                ballXSupplier.get(),
                ballYSupplier.get());
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, Integer> getHoleScores() {
        return allScoresSupplier.get();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isBallMoving() {
        return ballMovingChecker.getAsBoolean();
    }
}
