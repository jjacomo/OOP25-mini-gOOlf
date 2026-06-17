package it.unibo.minigoolf.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles saving and loading the global leaderboard to a text file.
 * 
 * @author dbakko
 */
public final class LeaderBoardManager {

    private static final String FILE_PATH = "saves/leaderboard.txt";

    /**
     * Constructs a new LeaderBoardManager.
     * This manager can be instantiated by controllers to load and save leaderboard data.
     */
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
            throw new IllegalStateException("Error reading the leaderboard", e); 
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

        // Creates the folder "save" if not present
        final File file = new File(FILE_PATH);
        final File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IllegalStateException("Failed to create the save directory!");
        }

        // Writing on the txt file
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH), StandardCharsets.UTF_8)) {
            for (final Map.Entry<String, Integer> entry : historicalScores.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Error saving leaderboard data", e);
        }
    }
}

