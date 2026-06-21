package it.unibo.minigoolf.model.map.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Factory utility class responsible for generating the map sequence for a match.
 * It encapsulates the logic of which maps are played and in what order.
 * 
 * @author dbakko
 */
public final class GameMapSequenceFactory {

    private GameMapSequenceFactory() {
        // Utility class, no instantiation required
    }

    /**
     * Creates a randomized map sequence starting with a specific fixed map.
     *
     * @return a properly configured {@link MapSequence} ready for the match
     */
    public static MapSequence createRandomizedSequence() {
        // The tutorial map is always first
        final GameMapFactory firstMap = new MapT();

        // List of the remaing maps
        final List<GameMapFactory> remainingMaps = new ArrayList<>(List.of(
                new MapC(), 
                new MapA(), 
                new MapB(),
                new MapD(),
                new MapE(),
                new MapF(),
                new MapG()
        ));

        // This method shuffles the list
        Collections.shuffle(remainingMaps);

        // Here we join the 2 lists
        final List<GameMapFactory> finalSequence = new ArrayList<>();
        finalSequence.add(firstMap);
        finalSequence.addAll(remainingMaps);

        return new MapSequence(finalSequence);
    }
}
