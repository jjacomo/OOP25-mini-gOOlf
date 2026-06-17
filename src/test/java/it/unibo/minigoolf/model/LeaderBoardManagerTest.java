package it.unibo.minigoolf.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link LeaderBoardManager}.
 * Verifies that all the scores in the leaderboard are
 * correctly updated and saved.
 * 
 * @author dbakko
 */
class LeaderBoardManagerTest {

    private static final int SCOREA = 50;
    private static final int SCOREB = 60;
    private static final int SCOREC = 40; 
    private static final String PLAYER = "Player";

    /**
     * Cleans up the test environment by deleting the save file
     * before and after each test to ensure a clean slate.
     */
    @BeforeEach
    @AfterEach
    void cleanUp() {
        final File file = new File("saves/leaderboard.txt");
        if (file.exists() && !file.delete()) {
            throw new IllegalStateException("Failed to delete the test leaderboard file!");
        }
    }

    @Test
    void testScoreUpdatesOnlyIfLower() {
        final LeaderBoardManager manager = new LeaderBoardManager();
        final Map<String, Integer> initialScores = new HashMap<>();
        initialScores.put(PLAYER, SCOREA);

        // Saving the first score
        manager.updateAndSaveScores(initialScores);

        // Trying to save a worse (higher) score
        final Map<String, Integer> worseScores = new HashMap<>();
        worseScores.put(PLAYER, SCOREB);
        manager.updateAndSaveScores(worseScores);

        // Verifing if it stays at 50
        Map<String, Integer> loadedScores = manager.loadBestScores();
        assertEquals(SCOREA, loadedScores.get(PLAYER));

        // Trying to save a better (lower) score
        final Map<String, Integer> betterScores = new HashMap<>();
        betterScores.put(PLAYER, SCOREC);
        manager.updateAndSaveScores(betterScores);

        // Verifing if it updates to 40
        loadedScores = manager.loadBestScores();
        assertEquals(SCOREC, loadedScores.get(PLAYER));
    }
}
