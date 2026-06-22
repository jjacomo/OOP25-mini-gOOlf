package it.unibo.minigoolf.model.map.factories;

import it.unibo.minigoolf.model.map.GameMap;

import java.util.List;

/**
 * Manages an ordered sequence of {@link GameMapFactory} instances.
 * Tracks the current map index and provides the next map when available.
 *
 * @author fedesparvo1-a11y
 */
public final class MapSequence {

    private final List<GameMapFactory> factories;
    private int currentIndex;

    /**
     * Creates a new MapSequence with the given list of factories.
     *
     * @param factories ordered list of map factories (at least one)
     * @throws IllegalArgumentException if the list is empty
     */
    public MapSequence(final List<GameMapFactory> factories) {
        if (factories == null || factories.isEmpty()) {
            throw new IllegalArgumentException("At least one map factory is required.");
        }
        this.factories = List.copyOf(factories);
        this.currentIndex = 0;
    }

    /**
     * Builds and returns the current map.
     *
     * @return the current {@link GameMap}
     */
    public GameMap buildCurrent() {
        return factories.get(currentIndex).buildGameMap();
    }

    /**
     * Returns true if there is a next map in the sequence.
     *
     * @return true if a next map exists
     */
    public boolean hasNext() {
        return currentIndex < factories.size() - 1;
    }

    /**
     * Advances to the next map in the sequence.
     * Must only be called when {@link #hasNext()} returns true.
     *
     * @throws IllegalStateException if there is no next map
     */
    public void advance() {
        if (!hasNext()) {
            throw new IllegalStateException("No more maps in the sequence.");
        }
        currentIndex++;
    }

    /**
     * Returns the current map index (zero based).
     *
     * @return the current index
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Resets the sequence back to the first map.
     */
    public void reset() {
        this.currentIndex = 0;
    }
}
