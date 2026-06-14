package it.unibo.minigoolf.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles saving and loading the global leaderboard to a text file.
 * 
 * @author @dbakko
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

        try {
            final List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            for (final String line : lines) {
                final String[] parts = line.split(":");
                if (parts.length == 2) {
                    scores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
            
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the leaderboard: " + e.getMessage()); //TODO: Usare un logger? Spotbugs si lamenta con messaggi di errori scritti così
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
