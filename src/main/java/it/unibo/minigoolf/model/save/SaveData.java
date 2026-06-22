package it.unibo.minigoolf.model.save;

import java.util.List;

/**
 * Immutable snapshot of a minigolf match that can be serialised to JSON and
 * restored later.
 * The map is saved only as a string id, not as a complete object.
 * When loading, the program uses this id to find the correct
 * GameMapFactory and rebuild the map from the beginning.
 * This makes the save file smaller and avoids saving
 * complicated geometry objects.
 *
 * @author fedesparvo1-a11y
 * @param currentPlayerIndex index into {@code players} of whose turn it is
 * @param mapId              identifier used by GameMapFactory to rebuild the map
 * @param players            ordered list of player snapshots
 * @param ballX              ball X position in logical (1920x1080) coordinates
 * @param ballY              ball Y position in logical (1920x1080) coordinates
 */
public record SaveData(
    int currentPlayerIndex,
    String mapId,
    List<PlayerSaveData> players,
    double ballX,
    double ballY
) {

    /**
     * Compact canonical constructor that defensively copies the players list.
     *
     * @param currentPlayerIndex index into {@code players} of whose turn it is
     * @param mapId              identifier used by GameMapFactory to rebuild the map
     * @param players            ordered list of player snapshots
     * @param ballX              ball X position in logical (1920x1080) coordinates
     * @param ballY              ball Y position in logical (1920x1080) coordinates
     */
    public SaveData {
        players = List.copyOf(players);
    }
}
