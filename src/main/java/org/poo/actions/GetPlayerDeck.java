package org.poo.actions;

import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Deck;
import org.poo.game.Game;

/**
 * Handles the retrieval of a player's current deck.
 */
public final class GetPlayerDeck {
    private final Game game;

    /**
     * Constructs a new GetPlayerDeck instance.
     *
     * @param game the game object representing the current state of the game.
     */
    public GetPlayerDeck(final Game game) {
        this.game = game;
    }

    /**
     * Retrieves the current deck of a specified player.
     *
     * @param playerIdx the index of the player (1 for Player One, 2 for Player Two).
     * @return an ObjectNode containing the player's deck information.The structure includes: - "command": the name of the command executed ("getPlayerDeck"). - "playerIdx": the index of the player whose deck is retrieved. - "output": an ArrayNode containing the cards in the player's deck,              where each card includes its attributes              (e.g., mana, attack damage, health, etc.).
     */
    public ObjectNode getPlayerDeck(final int playerIdx) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectNode response = objectMapper.createObjectNode();

        response.put("command", "getPlayerDeck");
        response.put("playerIdx", playerIdx);

        Deck deck = null;
        if (playerIdx == 1) {
            deck = game.getPlayer1().getCurrentDeck();
        } else if (playerIdx == 2) {
            deck = game.getPlayer2().getCurrentDeck();
        }

        final ArrayNode output = objectMapper.createArrayNode();
        assert deck != null;
        for (final Minion card : deck.getMinions()) {
            final ObjectNode cardNode = objectMapper.convertValue(card.toInferior(), ObjectNode.class);
            output.add(cardNode);
        }

        response.set("output", output);
        return response;
    }
}
