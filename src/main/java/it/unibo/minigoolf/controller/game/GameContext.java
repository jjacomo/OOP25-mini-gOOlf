package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;

/**
 * Immutable container that groups all the objects needed to run a match.
 * Lives in the {@code app} package so neither the controller nor the view
 * layer owns it, keeping the MVC boundaries clean.
 *
 * @author fede and dani
 *
 * @param gameState         the central game logic (turn order, ball-moving
 *                          flag)
 * @param map               the game map (surfaces, ball, obstacles)
 * @param gameMapController the controller facade over the map
 * @param shotState         the shot input state shared between view and
 *                          controller
 */
@SuppressWarnings({ "EI_EXPOSE_REP", "EI_EXPOSE_REP2" }) // TODO: warning necessario:
public record GameContext(
        GameState gameState,
        GameMapController gameMapController,
        ShotState shotState) {
}