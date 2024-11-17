package org.poo.actions;

import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Game;

/**
 * Handles the frozen cards on the table
 */
public final class GetFrozenCardsOnTable {
    private final Game game;
    private final int numOfRows = 4;
    private final int numOfColumns = 5;

    /**
     * Constructs a new GetFrozenCardsOnTable instance.
     *
     * @param game the game object representing the current state of the game.
     */
    public GetFrozenCardsOnTable(final Game game) {
        this.game = game;
    }

    /**
     * Retrieves all frozen cards from the game table.
     *
     * @param objectMapper the ObjectMapper used for JSON serialization.
     * @return an ObjectNode containing the details of all frozen cards.
     * The structure includes: -
     * "command": the name of the command executed ("getFrozenCardsOnTable").
     * - "output": an ArrayNode of frozen cards, where each card includes its  mana,
     * attack damage, health, description, colors, and name.
     */
    public ObjectNode getFrozenCards(final ObjectMapper objectMapper) {
        final ObjectNode resultNode = objectMapper.createObjectNode();
        final ArrayNode frozenCardsArray = objectMapper.createArrayNode();

        final Minion[][] board = game.getBoard();

        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfColumns; col++) {
                final Minion minion = board[row][col];
                if (minion != null && minion.isFrozen()) {
                    final ObjectNode cardNode = objectMapper.createObjectNode();
                    cardNode.put("mana", minion.getMana());
                    cardNode.put("attackDamage", minion.getAttackDamage());
                    cardNode.put("health", minion.getHealth());
                    cardNode.put("description", minion.getDescription());
                    final ArrayNode colorsArray = objectMapper.createArrayNode();
                    for (final String color : minion.getColors()) {
                        colorsArray.add(color);
                    }
                    cardNode.set("colors", colorsArray);
                    cardNode.put("name", minion.getName());

                    frozenCardsArray.add(cardNode);
                }
            }
        }

        resultNode.put("command", "getFrozenCardsOnTable");
        resultNode.set("output", frozenCardsArray);

        return resultNode;
    }
}
