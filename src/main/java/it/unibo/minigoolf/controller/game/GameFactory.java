package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapControllerImpl;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.physics.PhysicsControllerImpl;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.factories.MapSequence;
import it.unibo.minigoolf.model.save.SaveData;
import it.unibo.minigoolf.util.Vector2D;

import java.util.List;
import java.util.Optional;

/**
 * Factory that builds a {@link GameController} from a {@link MapSequence}.
 * Accepts an optional {@link SaveData} to restore a previously saved match.
 *
 * @author fedesparvo1-a11y
 */
public final class GameFactory {

    private GameFactory() {
        // utility class
    }

    /**
     * Builds a{@link GameController} for the current map in the sequence.
     *
     * @param playerNames     ordered list of player display names
     * @param mapSequence     the map sequence managing available maps
     * @param onHoleCompleted callback invoked when the ball enters the hole
     * @return a fully wired {@link GameController}
     */
    public static GameController buildMatch(
            final List<String> playerNames,
            final MapSequence mapSequence,
            final Runnable onHoleCompleted) {
        return buildMatch(playerNames, mapSequence, onHoleCompleted, Optional.empty());
    }

    /**
     * Builds a {@link GameController} and eventually restores state from a {@link SaveData} snapshot.
     *
     * @param playerNames     ordered list of player display names
     * @param mapSequence     the map sequence managing available maps
     * @param onHoleCompleted callback invoked when the ball enters the hole
     * @param saveData        snapshot to restore; empty for a fresh match
     * @return a fully wired {@link GameController} with state restored
     */
    public static GameController buildMatch(
            final List<String> playerNames,
            final MapSequence mapSequence,
            final Runnable onHoleCompleted,
            final Optional<SaveData> saveData) {
        final GameState gameState = new GameState(playerNames);
        final GameMap map = mapSequence.buildCurrent();
        final GameMapController gameMapController = new GameMapControllerImpl(map);
        final ShotState shotState = new ShotState();
        final PhysicsController physicsController = new PhysicsControllerImpl(gameMapController);
        final GameControllerImpl match =
            new GameControllerImpl(gameState, gameMapController, shotState, physicsController);
        match.setOnHoleCompleted(onHoleCompleted);

        // Restore saved state if present.
        saveData.ifPresent(data -> {
            gameState.restoreFrom(data);
            gameMapController.getBallController()
                .updatePosition(new Vector2D(data.ballX(), data.ballY()));
        });

        return match;
    }
}
