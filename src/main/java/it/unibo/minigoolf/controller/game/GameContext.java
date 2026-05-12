package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.map.GameMap;

/**
 * Immutable container that groups all the objects needed to run a match.
 * Passed from {@link GameFactory} to {@link it.unibo.minigoolf.controller.MainControllerImpl}
 * and {@link it.unibo.minigoolf.view.MainWindow},
 * so neither class needs to import individual model types directly.
 *
 * @author fede and dani
 *
 * @param gameState         the central game logic (turn order, ball-moving flag)
 * @param map               the game map (surfaces, ball, obstacles)
 * @param gameMapController the controller facade over the map
 * @param shotState         the shot input state shared between view and controller
 */
@SuppressWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public record GameContext(
    GameState gameState,
    GameMap map,
    GameMapController gameMapController,
    ShotState shotState
) {
}
