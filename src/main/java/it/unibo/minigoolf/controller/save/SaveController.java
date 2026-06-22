package it.unibo.minigoolf.controller.save;

import it.unibo.minigoolf.model.save.SaveData;
import it.unibo.minigoolf.model.save.SaveManager;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages persistence of the match state independently from the match lifecycle.
 * Created once at application startup so save/load is available before any match
 * get started.
 *
 * @author fedesparvo1-a11y
 */
public final class SaveController {

    private static final Logger LOGGER = Logger.getLogger(SaveController.class.getName());

    // Supplies the snapshot. 
    private Supplier<SaveData> snapshotSupplier = () -> null;

    /// Called after a successful load to start the restored match. 
    private Consumer<SaveData> restoreCallback = data -> { };

    // Saves, loads, checks and deletes save files, stored only as behavior callbacks. 
    private final Runnable saver;
    private final Runnable deleter;
    private final Supplier<Boolean> existsChecker;
    private final Supplier<Optional<SaveData>> loader;

    /**
     * Builds the save controller with the given save manager.
     * 
     * @param saveManager handles the actual file 
     */
    public SaveController(final SaveManager saveManager) {
        // Extract only behaviors from saveManager.
        this.saver = () -> Optional.ofNullable(snapshotSupplier.get())
            .ifPresent(data -> {
                try {
                    saveManager.save(data);
                } catch (final IOException e) {
                    LOGGER.log(Level.WARNING, "Could not save match", e);
                }
            });
        this.existsChecker = saveManager::hasSave;
        this.deleter = saveManager::deleteSave;
        this.loader = () -> {
            try {
                return Optional.of(saveManager.load());
            } catch (final IOException e) {
                LOGGER.log(Level.WARNING, "Could not load match", e);
                return Optional.empty();
            }
        };
    }

    /**
     * Returns true if a save file is available to load.
     *
     * @return true if a save exists on disk
     */
    public boolean hasSave() {
        return existsChecker.get();
    }

    /**
     * Sets the supplier that produces the current match snapshot.
     *
     * @param snapshotSupplier supplies the current {@link SaveData}
     */
    public void setSnapshotSupplier(final Supplier<SaveData> snapshotSupplier) {
        this.snapshotSupplier = snapshotSupplier;
    }

    /**
     * Sets the callback that restores a match from a {@link SaveData} snapshot.
     *
     * @param restoreCallback receives the loaded snapshot and starts the match
     */
    public void setRestoreCallback(final Consumer<SaveData> restoreCallback) {
        this.restoreCallback = restoreCallback;
    }

    /**
     * Saves the current match state to disk.
     * No-op if no snapshot supplier has been registered yet.
     */
    public void save() {
        saver.run();
    }

    /**
     * Loads the saved match from disk and hands it to the restore callback.
     */
    public void load() {
        if (!hasSave()) {
            return;
        }
        loader.get().ifPresent(data -> {
            restoreCallback.accept(data);
            deleter.run();
        });
    }
}
