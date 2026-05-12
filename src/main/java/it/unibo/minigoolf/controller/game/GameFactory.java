package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapControllerImpl;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.factories.FirstMap;

import java.util.List;

/**
 * Factory that builds and wires all model objects needed for a match,
 * returning them as a single {@link GameContext}.
 *
 * <p>Centralising construction here keeps {@link MainControllerImpl} free of
 * model-creation logic and makes it easy to swap maps or game modes later.</p>
 *
 * @author fede
 */
public final class GameFactory {

    private GameFactory() {
        // utility class — not instantiable
    }

    /**
     * Builds a new {@link GameContext} for a match with the given players.
     *
     * @param playerNames ordered list of player display names (at least one)
     * @return a fully initialised {@link GameContext}
     */
    public static GameContext build(final List<String> playerNames) {
        final GameState gameState = new GameState(playerNames);
        // TODO: choose map based on game mode / level selection
        final GameMap map = new FirstMap().buildGameMap();
        final GameMapController gameMapController = new GameMapControllerImpl(map);
        final ShotState shotState = new ShotState();
        return new GameContext(gameState, map, gameMapController, shotState);
    }
}
