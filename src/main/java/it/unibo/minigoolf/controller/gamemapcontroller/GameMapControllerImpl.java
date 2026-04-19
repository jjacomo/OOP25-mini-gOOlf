package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import it.unibo.minigoolf.controller.ballcontroller.BallController;
import it.unibo.minigoolf.controller.ballcontroller.BallControllerImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.surfaces.Surface;

public class GameMapControllerImpl implements GameMapController {
    private final GameMap map;
    private final BallController ballController;

    public GameMapControllerImpl(GameMap map) {
        this.map = map;
        this.ballController = new BallControllerImpl(map.getBall());
    }

    @Override
    public List<Surface> getSurfaces() {
        return map.getSurfaces();
    }

    @Override
    public BallController getBallController() {
        return ballController;
    }

}
