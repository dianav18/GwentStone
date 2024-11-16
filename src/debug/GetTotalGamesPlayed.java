package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

public class GetTotalGamesPlayed {
    private final Game game;

    public GetTotalGamesPlayed(Game game) {
        this.game = game;
    }

    public ObjectNode getTotalGamesPlayed(ObjectMapper objectMapper) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getTotalGamesPlayed");
        resultNode.put("output", game.getEndedGames());
        return resultNode;
    }
}
