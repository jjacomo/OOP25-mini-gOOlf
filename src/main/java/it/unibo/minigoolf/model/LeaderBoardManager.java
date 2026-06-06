package it.unibo.minigoolf.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Handles saving and loading the global leaderboard to a text file.
 */
public final class LeaderBoardManager {

    private static final String FILE_PATH = "saves/leaderboard.txt";

    public LeaderBoardManager() {
        // Utility behavior, but can be instantiated if needed by controllers
    }

    /**
     * Loads the scores from the file.
     * 
     * @return a map of player names and their best (lowest) scores
     */
    public Map<String, Integer> loadBestScores() {
        final Map<String, Integer> scores = new LinkedHashMap<>();
        final File file = new File(FILE_PATH);

        if (!file.exists()) {
            return scores; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] parts = line.split(":");
                if (parts.length == 2) {
                    scores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the leaderboard: " + e.getMessage());
        }

        return scores;
    }

    /**
     * Updates the leaderboard file with new match scores.
     * Only saves the score if it's lower (better) than the previously saved one.
     * 
     * @param matchScores the scores from the just finished match.
     */
    public void updateAndSaveScores(final Map<String, Integer> matchScores) {

        final Map<String, Integer> historicalScores = this.loadBestScores();

        // It updates with the new scores following some rules.
        for (final Map.Entry<String, Integer> entry : matchScores.entrySet()) {
            final String playerName = entry.getKey();
            final int currentMatchScore = entry.getValue();

            // If the player is new or an existing player has a lowerscore, it updates it.
            if (!historicalScores.containsKey(playerName) || currentMatchScore < historicalScores.get(playerName)) {
                historicalScores.put(playerName, currentMatchScore);
            }
        }

        // Writing on the txt file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (final Map.Entry<String, Integer> entry : historicalScores.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (final IOException e) {
            System.err.println("Error saving leadearboard data: " + e.getMessage());
        }
    }
}
