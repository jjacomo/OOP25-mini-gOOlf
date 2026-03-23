package it.unibo.minigOOlf.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class GameMapImpl implements GameMap{

    List<Surface> surfaces = new ArrayList<>();

    public GameMapImpl(List<Surface> surfaces) {
        this.surfaces = surfaces;
    }

    @Override
    public Surface getSurfaceAt(Vector2D position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSurfaceAt'");
    }
    

}
