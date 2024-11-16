package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

public class GetPlayerOneWins {
    private final Game game;

    public GetPlayerOneWins(Game game) {
        this.game = game;
    }

    public ObjectNode getPlayerOneWins(ObjectMapper objectMapper) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getPlayerOneWins");
        resultNode.put("output", game.getPlayerOneWins());
        return resultNode;
    }
}
