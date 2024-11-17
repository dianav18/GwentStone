package org.poo.actions;

import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Game;

import java.util.List;

/**
 * Handles the retrieval of all cards currently on the game table.
 */
public final class GetCardsOnTable {
    private Game game;

    /**
     * Constructs a new GetCardsOnTable instance.
     *
     * @param game the game object representing the current state of the game.
     */
    public GetCardsOnTable(final Game game) {
        this.game = game;
    }

    /**
     * Retrieves the details of all cards currently placed on the game table.
     *
     * @return an ObjectNode containing a structure of the cards on the table. The structure includes: - "command": the name of the command executed ("getCardsOnTable"). - "output": an ArrayNode where each row of the table is represented             as an array of cards, with each card including its mana, attack damage,             health, description, colors, and name.
     */
    public ObjectNode getCardsOnTable() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode output = objectMapper.createObjectNode();
        final ArrayNode cardsArray = objectMapper.createArrayNode();

        output.put("command", "getCardsOnTable");

        for (final List<Minion> cardInputs : this.game.getCardsOnTable()) {
            final ArrayNode tmp = objectMapper.createArrayNode();

            for (final Minion minion : cardInputs) {
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
                tmp.add(cardNode);
            }
            cardsArray.add(tmp);
        }

        output.set("output", cardsArray);
        return output;
    }
}
