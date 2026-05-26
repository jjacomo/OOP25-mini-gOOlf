package it.unibo.minigoolf.controller.save;

import it.unibo.minigoolf.model.save.SaveData;
import it.unibo.minigoolf.model.save.SaveManager;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages persistence of the match state independently from the match lifecycle.
 * Created once at application startup so save/load is available before any match
 * is started, without requiring {@link it.unibo.minigoolf.controller.MainControllerImpl}
 * to know anything about serialisation.
 *
 * @author fede
 */
public final class SaveController {

    private static final Logger LOGGER = Logger.getLogger(SaveController.class.getName());

    /** Supplies the snapshot to persist — set by MatchManager when a match is active. */
    private java.util.function.Supplier<SaveData> snapshotSupplier = () -> null;

    /** Called after a successful load to start the restored match. */
    private java.util.function.Consumer<SaveData> restoreCallback = data -> { };

    private final SaveManager saveManager;

    /**
     * @param saveManager handles the actual file I/O
     */
    public SaveController(final SaveManager saveManager) {
        this.saveManager = saveManager;
    }

    /**
     * Returns true if a save file is available to load.
     *
     * @return true if a save exists on disk
     */
    public boolean hasSave() {
        return saveManager.hasSave();
    }

    /**
     * Sets the supplier that produces the current match snapshot.
     * Called by {@link it.unibo.minigoolf.controller.game.MatchManager}
     * once a match is active.
     *
     * @param snapshotSupplier supplies the current {@link SaveData}
     */
    public void setSnapshotSupplier(
            final java.util.function.Supplier<SaveData> snapshotSupplier) {
        this.snapshotSupplier = snapshotSupplier;
    }

    /**
     * Sets the callback that restores a match from a {@link SaveData} snapshot.
     * Called by {@link it.unibo.minigoolf.controller.game.MatchManager}
     * once a match is active.
     *
     * @param restoreCallback receives the loaded snapshot and starts the match
     */
    public void setRestoreCallback(
            final java.util.function.Consumer<SaveData> restoreCallback) {
        this.restoreCallback = restoreCallback;
    }

    /**
     * Saves the current match state to disk.
     * No-op if no snapshot supplier has been registered yet.
     */
    public void save() {
        final SaveData data = snapshotSupplier.get();
        if (data == null) {
            return;
        }
        try {
            saveManager.save(data);
        } catch (final IOException e) {
            LOGGER.log(Level.WARNING, "Could not save match", e);
        }
    }

    /**
     * Loads the saved match from disk and hands it to the restore callback.
     * No-op if no save file exists.
     *
     * @return the loaded {@link SaveData}, or empty if no save exists or load fails
     */
    public Optional<SaveData> load() {
        if (!saveManager.hasSave()) {
            return Optional.empty();
        }
        try {
            final SaveData data = saveManager.load();
            restoreCallback.accept(data);
            return Optional.of(data);
        } catch (final IOException e) {
            LOGGER.log(Level.WARNING, "Could not load match", e);
            return Optional.empty();
        }
    }

    /**
     * Deletes the save file if it exists.
     * Called after a successful load so the save is not offered again.
     */
    public void deleteSave() {
        saveManager.deleteSave();
    }
}
