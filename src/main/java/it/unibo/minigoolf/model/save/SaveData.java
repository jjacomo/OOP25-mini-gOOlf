package it.unibo.minigoolf.model.save;

import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.map.GameMap;

import java.util.List;

/**
 * Immutable snapshot of a minigolf match that can be serialised to JSON and
 * restored later.
 *
 * <p>The map is stored as a string identifier rather than a full object graph:
 * on load the factory looks up the right {@code GameMapFactory} implementation
 * by this id and rebuilds the map from scratch. This keeps the save file small
 * and avoids serialising complex geometry.</p>
 *
 * @author fede
 * @param currentPlayerIndex index into {@code players} of whose turn it is
 * @param mapId              identifier used by {@link GameMapFactory} to rebuild the map
 * @param players            ordered list of player snapshots
 * @param ballX              ball X position in logical (1920×1080) coordinates
 * @param ballY              ball Y position in logical (1920×1080) coordinates
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
     * @param mapId              identifier used by {@link GameMapFactory} to rebuild the map
     * @param players            ordered list of player snapshots
     * @param ballX              ball X position in logical (1920×1080) coordinates
     * @param ballY              ball Y position in logical (1920×1080) coordinates
     */
    public SaveData {
        players = List.copyOf(players);
    }

    /**
     * Builds a {@code SaveData} snapshot from the current game state and map.
     *
     * @param gameState the current game state
     * @param map       the current game map (used to read ball position)
     * @param mapId     the string identifier of the map, used to rebuild it on load
     *
     * @return a new SaveData ready to be persisted
     */
    public static SaveData from(final GameState gameState, final GameMap map, final String mapId) {
        final List<PlayerSaveData> playerSnapshots = gameState.getPlayers().stream()
            .map(p -> new PlayerSaveData(p.getName(), p.getShots()))
            .toList();

        final double ballX = map.getBall().getPosition().getX();
        final double ballY = map.getBall().getPosition().getY();

        return new SaveData(
            gameState.getCurrentPlayerIndex(),
            mapId,
            playerSnapshots,
            ballX,
            ballY
        );
    }
}
