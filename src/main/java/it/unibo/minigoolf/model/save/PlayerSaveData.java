package it.unibo.minigoolf.model.save;

/**
 * Snapshot of a single player's state at save time.
 *
 * @param name  the player's display name
 * @param shots the number of shots taken on the current hole
 *
 * @author fede
 */
public record PlayerSaveData(String name, int shots) {
}
