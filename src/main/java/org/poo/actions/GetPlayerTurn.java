package org.poo.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Get player turn.
 */
public class GetPlayerTurn {
    private final int playerTurn;

    /**
     * Instantiates a new Get player turn.
     *
     * @param playerTurn the player turn
     */
    public GetPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * Gets player turn.
     *
     * @param objectMapper the object mapper
     * @return the player turn
     */
    public ObjectNode getPlayerTurn(final ObjectMapper objectMapper) {
        final ObjectNode playerTurnNode = objectMapper.createObjectNode();
        playerTurnNode.put("command", "getPlayerTurn");
        playerTurnNode.put("output", playerTurn);
        return playerTurnNode;
    }
}
