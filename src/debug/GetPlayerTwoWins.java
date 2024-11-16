package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

public class GetPlayerTwoWins {
    private final Game game;

    public GetPlayerTwoWins(Game game) {
        this.game = game;
    }

    public ObjectNode getPlayerTwoWins(ObjectMapper objectMapper) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getPlayerTwoWins");
        resultNode.put("output", game.getPlayerTwoWins());
        return resultNode;
    }
}
