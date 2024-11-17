package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Player;

/**
 * The type Get player mana.
 */
public class GetPlayerMana {

    private final Player player;

    /**
     * Instantiates a new Get player mana.
     *
     * @param player the player
     */
    public GetPlayerMana(final Player player) {
        this.player = player;
    }

    /**
     * Gets player mana.
     *
     * @return the player mana
     */
    public ObjectNode getPlayerMana() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode response = objectMapper.createObjectNode();

        response.put("command", "getPlayerMana");
        response.put("playerIdx", player.getPlayerIdx());
        response.put("output", player.getMana());

        return response;
    }
}
