package it.unibo.minigoolf.model;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link LeaderBoardManager}.
 * Verifies that all the scores in the leaderboard are
 * correctly updated and saved.
 * 
 * @author @dbakko
 */
class LeaderBoardManagerTest {

    @Test
    void testScoreUpdatesOnlyIfLower() {
        final LeaderBoardManager manager = new LeaderBoardManager();
        final Map<String, Integer> initialScores = new HashMap<>();
        initialScores.put("Dani", 50);
        
        // Saving the first score
        manager.updateAndSaveScores(initialScores);
        
        // Trying to save a worse (higher) score
        final Map<String, Integer> worseScores = new HashMap<>();
        worseScores.put("Dani", 60);
        manager.updateAndSaveScores(worseScores);
        
        // Verifing if it stays at 50
        Map<String, Integer> loadedScores = manager.loadBestScores();
        assertEquals(50, loadedScores.get("Dani"));
        
        // Trying to save a better (lower) score
        final Map<String, Integer> betterScores = new HashMap<>();
        betterScores.put("Dani", 40);
        manager.updateAndSaveScores(betterScores);
        
        // Verifing if it updates to 40
        loadedScores = manager.loadBestScores();
        assertEquals(40, loadedScores.get("Dani"));
    }
}