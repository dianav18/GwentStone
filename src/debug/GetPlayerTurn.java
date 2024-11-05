package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class GetPlayerTurn {
    private final int playerTurn;

    public GetPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public ObjectNode getPlayerTurn(ObjectMapper objectMapper) {
        ObjectNode playerTurnNode = objectMapper.createObjectNode();
        playerTurnNode.put("command", "getPlayerTurn");
        playerTurnNode.put("output", playerTurn);
        return playerTurnNode;
    }
}