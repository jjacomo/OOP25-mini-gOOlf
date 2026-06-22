package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

/**
 * Interface that {@link it.unibo.minigoolf.view.panels.MapPanel} implements
 * to receive data from the Controller for rendering the map.
 */
@FunctionalInterface
public interface MapElementsView {

    /**
     * Updates the graphics with the new state of the game map elements.
     *
     * @param surfaces  the list of surface data
     * @param obstacles the list of obstacle data
     * @param hole      the hole data
     * @param ball      the ball data
     */
    void updateGraphics(
            List<SurfaceData> surfaces,
            List<ObstacleData> obstacles,
            HoleData hole,
            BallData ball);
}
