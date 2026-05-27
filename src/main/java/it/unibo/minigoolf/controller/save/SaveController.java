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
 * is started, without requiring MainControllerImpl to know anything about serialisation.
 *
 * @author fede
 */
public final class SaveController {

    private static final Logger LOGGER = Logger.getLogger(SaveController.class.getName());

    /** Supplies the snapshot to persist — set by MatchManager when a match is active. */
    private Supplier<SaveData> snapshotSupplier = () -> null;

    /** Called after a successful load to start the restored match. */
    private Consumer<SaveData> restoreCallback = data -> { };

    /** Saves and checks for save files — stored only as behavior callbacks. */
    private final Runnable persistAction;
    private final Runnable deleteAction;
    private final Supplier<Boolean> saveExistsSupplier;
    private final Supplier<Optional<SaveData>> loadAction;

    /**
     * @param saveManager handles the actual file I/O
     */
    public SaveController(final SaveManager saveManager) {
        // Extract only behaviors from saveManager — avoids EI2.
        this.persistAction = () -> {
            try {
                final SaveData data = snapshotSupplier.get();
                if (data != null) {
                    saveManager.save(data);
                }
            } catch (final IOException e) {
                LOGGER.log(Level.WARNING, "Could not save match", e);
            }
        };
        this.saveExistsSupplier = saveManager::hasSave;
        this.deleteAction = saveManager::deleteSave;
        this.loadAction = () -> {
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
        return saveExistsSupplier.get();
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
        persistAction.run();
    }

    /**
     * Loads the saved match from disk and hands it to the restore callback.
     */
    public void load() {
        if (!hasSave()) {
            return;
        }
        loadAction.get().ifPresent(data -> {
            restoreCallback.accept(data);
            deleteAction.run();
        });
    }
}
