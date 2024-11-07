package debug;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import game.Game;

import java.util.List;

public class GetCardsOnTable {
    private Game game;

    public GetCardsOnTable(Game game) {
        this.game = game;
    }

    public ObjectNode getCardsOnTable() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode output = objectMapper.createObjectNode();
        ArrayNode cardsArray = objectMapper.createArrayNode();

        output.put("command", "getCardsOnTable");

        for (List<CardInput> cardInputs : this.game.getCardsOnTable()) {
            ArrayNode tmp = objectMapper.createArrayNode();

            for (CardInput minion : cardInputs) {
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
                tmp.add(cardNode);
            }
            cardsArray.add(tmp);
        }

        output.set("output", cardsArray);
        return output;
    }
}
