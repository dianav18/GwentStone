package debug;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Player;

import java.util.List;

public class GetCardsInHand {
    private final Player player;

    public GetCardsInHand(Player player) {
        this.player = player;
    }

    public ObjectNode getCardsInHand(ObjectMapper objectMapper) {
        ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "getCardsInHand");
        output.put("playerIdx", player.getPlayerIdx());

        ArrayNode cardsArray = objectMapper.createArrayNode();
        List<Minion> minions = player.getHand().getMinions();

        for (Minion minion : minions) {
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
            cardsArray.add(cardNode);
        }

        output.set("output", cardsArray);
        output.put("playerIdx", player.getPlayerIdx());
        return output;
    }
}
