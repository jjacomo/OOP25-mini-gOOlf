package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Shape;

public class GameMapControllerImpl implements GameMapController {
    GameMap map;

    public GameMapControllerImpl(GameMap map) {
        this.map = map;
    }

    @Override
    public List<Surface> getSurfaces() {
        return map.getSurfaces();
    }

    @Override
    public Shape getBallShape() {
        return new Circle(map.getBall().getPosition(), map.getBall().getRadius());
    }

    @Override
    public void updateBallPosition(Vector2D position) {
        this.map.getBall().setPosition(position);
    }

    @Override
    public void updateBallVelocity(Vector2D velocity) {
        this.map.getBall().setVelocity(velocity);
    }

}
