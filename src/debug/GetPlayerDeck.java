package debug;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Input;
import game.Deck;
import game.Game;

import java.util.List;

public class GetPlayerDeck {
    private Game game;

    public GetPlayerDeck(Game game) {
        this.game = game;
    }

    public ObjectNode getPlayerDeck(int playerIdx) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        response.put("command", "getPlayerDeck");
        response.put("playerIdx", playerIdx);

        // Obținem deck-ul jucătorului specificat
        Deck deck;
        if (playerIdx == 1) {
            deck = game.getPlayer1().getCurrentDeck(); // Presupunând că luăm primul deck
        } else if (playerIdx == 2) {
            deck = game.getPlayer2().getCurrentDeck(); // Presupunând că luăm primul deck
        } else {
            throw new IllegalArgumentException("Invalid player index: " + playerIdx);
        }

        ArrayNode output = objectMapper.createArrayNode();
        for (Minion card : deck.getMinions()) {
            ObjectNode cardNode = objectMapper.convertValue(card.toInferior(), ObjectNode.class);
            output.add(cardNode);
        }

        response.set("output", output);
        return response;
    }
}
