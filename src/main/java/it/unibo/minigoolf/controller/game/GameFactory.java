package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapControllerImpl;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.physics.PhysicsControllerImpl;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.factories.MapSequence;

import java.util.List;

/**
 * Factory that builds a {@link GameController} from a {@link MapSequence}.
 * Accepts an {@code onHoleCompleted} callback so that match-level navigation
 * logic stays outside {@code MainControllerImpl}.
 *
 * @author fede
 */
public final class GameFactory {

    private GameFactory() {
        // utility class
    }

    /**
     * Builds a {@link GameController} for the current map in the sequence.
     *
     * @param playerNames      ordered list of player display names
     * @param mapSequence      the map sequence managing available maps
     * @param onHoleCompleted  callback invoked when the ball enters the hole
     * @return a fully wired {@link GameController}
     */
    public static GameController buildMatch(
            final List<String> playerNames,
            final MapSequence mapSequence,
            final Runnable onHoleCompleted) {
        final GameState gameState = new GameState(playerNames);
        final GameMap map = mapSequence.buildCurrent();
        final GameMapController gameMapController = new GameMapControllerImpl(map);
        final ShotState shotState = new ShotState();
        final PhysicsController physicsController = new PhysicsControllerImpl(gameMapController);
        final GameControllerImpl match =
            new GameControllerImpl(gameState, gameMapController, shotState, physicsController);
        match.setOnHoleCompleted(onHoleCompleted);
        return match;
    }
}
