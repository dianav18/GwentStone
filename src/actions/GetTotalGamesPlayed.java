package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

/**
 * The type Get total games played.
 */
public class GetTotalGamesPlayed {
    private final Game game;

    /**
     * Instantiates a new Get total games played.
     *
     * @param game the game
     */
    public GetTotalGamesPlayed(final Game game) {
        this.game = game;
    }

    /**
     * Gets total games played.
     *
     * @param objectMapper the object mapper
     * @return the total games played
     */
    public ObjectNode getTotalGamesPlayed(final ObjectMapper objectMapper) {
        final ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getTotalGamesPlayed");
        resultNode.put("output", Game.endedGames);
        return resultNode;
    }
}
