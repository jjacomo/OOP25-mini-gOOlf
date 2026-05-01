package it.unibo.minigoolf.model.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Handles persistence of a minigolf match to a JSON file using Gson.
 *
 * <p>Usage example:
 * <pre>
 *   // saving
 *   saveManager.save(SaveData.from(gameState, mapId));
 *
 *   // loading
 *   if (saveManager.hasSave()) {
 *       SaveData data = saveManager.load();
 *       // restore game from data …
 *   }
 * </pre>
 *
 * @author fede
 */
public final class SaveManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String SAVE_DIR = "saves";
    private static final String SAVE_FILE = SAVE_DIR + "/savefile.json";

    /**
     * Persists the given snapshot to disk.
     *
     * @param data the match snapshot to save
     * @throws IOException if the file cannot be written
     */
    public void save(final SaveData data) throws IOException {
        final File dir = new File(SAVE_DIR);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Could not create save directory: " + SAVE_DIR);
        }
        try (Writer writer = new FileWriter(SAVE_FILE, StandardCharsets.UTF_8)) {
            GSON.toJson(data, writer);
        }
    }

    /**
     * Loads the previously saved snapshot from disk.
     *
     * @return the deserialized {@link SaveData}
     * @throws IOException if the file cannot be read or does not exist
     */
    public SaveData load() throws IOException {
        try (Reader reader = new FileReader(SAVE_FILE, StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, SaveData.class);
        }
    }

    /**
     * Returns {@code true} if a save file exists on disk.
     *
     * @return true if a save is available to load
     */
    public boolean hasSave() {
        return new File(SAVE_FILE).exists();
    }

    /**
     * Deletes the save file if it exists.
     * Call this when the player finishes the match so a stale save is not offered.
     */
    public void deleteSave() {
        final File file = new File(SAVE_FILE);
        if (file.exists() && !file.delete()) {
            throw new IllegalStateException("Could not delete save file: " + SAVE_FILE);
        }
    }
}
