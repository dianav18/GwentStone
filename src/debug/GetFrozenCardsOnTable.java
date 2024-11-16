package debug;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

public class GetFrozenCardsOnTable {
    private final Game game;

    public GetFrozenCardsOnTable(Game game) {
        this.game = game;
    }

    public ObjectNode getFrozenCards(ObjectMapper objectMapper) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        ArrayNode frozenCardsArray = objectMapper.createArrayNode();

        Minion[][] board = game.getBoard();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                Minion minion = board[row][col];
                if (minion != null && minion.isFrozen()) {
                    ObjectNode cardNode = objectMapper.createObjectNode();
                    cardNode.put("mana", minion.getMana());
                    cardNode.put("attackDamage", minion.getAttackDamage());
                    cardNode.put("health", minion.getHealth());
                    cardNode.put("description", minion.getDescription());
                    ArrayNode colorsArray = objectMapper.createArrayNode();
                    for (String color : minion.getColors()) {
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
