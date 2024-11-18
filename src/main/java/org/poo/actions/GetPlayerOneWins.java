package org.poo.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Game;

/**
 * The type Get player one wins.
 */
public class GetPlayerOneWins {
    private final Game game;

    /**
     * Instantiates a new Get player one wins.
     *
     * @param game the game
     */
    public GetPlayerOneWins(final Game game) {
        this.game = game;
    }

    /**
     * Gets player one wins.
     *
     * @param objectMapper the object mapper
     * @return the player one wins
     */
    public ObjectNode getPlayerOneWins(final ObjectMapper objectMapper) {
        final ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getPlayerOneWins");
        resultNode.put("output", Game.getPlayerOneWins());
        return resultNode;
    }
}
