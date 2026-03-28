package it.unibo.minigoolf.model;

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
        Surface highestSurface;
        try {
            highestSurface = surfaces.stream()
                .filter(s -> s.contains(position))
                .max((s1, s2) -> Integer.compare(s1.getZIndex(), s2.getZIndex()))
                .orElseThrow(() -> new IllegalStateException("No surface found at the given position"));
        } catch (IllegalStateException e) {
            throw new IllegalStateException("No surface found at the given position: " + position, e);
        }
        return highestSurface;
    }
    

}
