package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Player;

public class GetPlayerMana {

    private final Player player;

    public GetPlayerMana(Player player) {
        this.player = player;
    }

    public ObjectNode getPlayerMana() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        response.put("command", "getPlayerMana");
        response.put("playerIdx", player.getPlayerIdx());
        response.put("output", player.getMana());

        return response;
    }
}
