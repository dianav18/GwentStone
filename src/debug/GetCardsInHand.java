package debug;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Player;

import java.util.List;

/**
 * Handles the retrieval of cards in a player's hand.
 */
public final class GetCardsInHand {
    private final Player player;

    /**
     * Constructs a new GetCardsInHand instance.
     *
     * @param player the player whose hand is to be queried.
     */
    public GetCardsInHand(final Player player) {
        this.player = player;
    }

    /**
     * Retrieves the details of all cards in the player's hand.
     *
     * @param objectMapper the ObjectMapper used for creating JSON nodes.
     * @return an ObjectNode containing details of all cards in the player's hand.
     * The structure includes the card's mana, attack damage, health, description,
     * colors, and name.
     */
    public ObjectNode getCardsInHand(final ObjectMapper objectMapper) {
        final ObjectNode output = objectMapper.createObjectNode();
        output.put("command", "getCardsInHand");
        output.put("playerIdx", player.getPlayerIdx());

        final ArrayNode cardsArray = objectMapper.createArrayNode();
        final List<Minion> minions = player.getHand().getMinions();

        for (final Minion minion : minions) {
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
            cardsArray.add(cardNode);
        }

        output.set("output", cardsArray);
        output.put("playerIdx", player.getPlayerIdx());
        return output;
    }
}
