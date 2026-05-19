package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapControllerImpl;
import it.unibo.minigoolf.controller.physics.PhysicsController;
import it.unibo.minigoolf.controller.physics.PhysicsControllerImpl;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.factories.FirstMap;

import java.util.List;

/**
 * Factory that builds and wires all objects needed for a match,
 * returning a ready-to-use {@link GameController}.
 *
 * @author fede
 */
public final class GameFactory {

    private GameFactory() {
        // utility class — not instantiable
    }

    /**
     * Builds a new {@link GameController} for a match with the given players.
     * The returned controller awaits a
     * {@link GameController#setShotView(it.unibo.minigoolf.controller.shot.ShotView)}
     * call before the first tick.
     *
     * @param playerNames ordered list of player display names (at least one)
     * @return a fully initialised {@link GameController}
     */
    public static GameController buildMatch(final List<String> playerNames) {
        final GameState gameState = new GameState(playerNames);
        // TODO: choose map based on game mode / level selection
        final GameMap map = new FirstMap().buildGameMap();
        final GameMapController gameMapController = new GameMapControllerImpl(map);
        final ShotState shotState = new ShotState();
        final PhysicsController physicsController = new PhysicsControllerImpl(gameMapController);
        return new GameControllerImpl(gameState, gameMapController, shotState, physicsController);
    }
}
