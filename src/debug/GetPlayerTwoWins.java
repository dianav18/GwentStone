package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

/**
 * The type Get player two wins.
 */
public class GetPlayerTwoWins {
    private final Game game;

    /**
     * Instantiates a new Get player two wins.
     *
     * @param game the game
     */
    public GetPlayerTwoWins(final Game game) {
        this.game = game;
    }

    /**
     * Gets player two wins.
     *
     * @param objectMapper the object mapper
     * @return the player two wins
     */
    public ObjectNode getPlayerTwoWins(final ObjectMapper objectMapper) {
        final ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getPlayerTwoWins");
        resultNode.put("output", Game.playerTwoWins);
        return resultNode;
    }
}
