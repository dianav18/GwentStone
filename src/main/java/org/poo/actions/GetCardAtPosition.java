package org.poo.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Game;

/**
 * Handles the position of a card in the game.
 */
public class GetCardAtPosition {
    private final int x;
    private final int y;
    private final Game game;
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new Get card at position.
     *
     * @param game         the game object representing the current state of the game.
     * @param x            the row position of the card
     * @param y            the column position of the card
     * @param objectMapper the ObjectMapper used for JSON operations.
     */
    public GetCardAtPosition(final Game game, final int x,
                             final int y, final ObjectMapper objectMapper) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves the card at the specified position on the game board.
     *
     * @return an ObjectNode containing the details of the card at the specified position or an empty ObjectNode if no card is present.
     */
    public ObjectNode getCardAtPosition() {
        final ArrayNode output = objectMapper.createArrayNode();

        game.getCardAtPosition(x, y, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
