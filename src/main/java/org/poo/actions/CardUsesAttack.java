package org.poo.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Game;

/**
 * Handles the execution of a card's attack in the game.
 */
public final class CardUsesAttack {
    private final Game game;
    private final int xAttacker;
    private final int yAttacker;
    private final int xAttacked;
    private final int yAttacked;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new CardUsesAbility instance.
     *
     * @param game         the game object representing the current state of the game.
     * @param xAttacker    the row position of the attacking card.
     * @param yAttacker    the column position of the attacking card.
     * @param xAttacked    the row position of the attacked card.
     * @param yAttacked    the column position of the attacked card.
     * @param objectMapper the ObjectMapper used for JSON operations.
     */
    public CardUsesAttack(final Game game, final int xAttacker, final int yAttacker,
                          final int xAttacked, final int yAttacked,
                          final ObjectMapper objectMapper) {
        this.game = game;
        this.xAttacker = xAttacker;
        this.yAttacker = yAttacker;
        this.xAttacked = xAttacked;
        this.yAttacked = yAttacked;
        this.objectMapper = objectMapper;
    }

    /**
     * Executes the attack of a card in the game.
     *
     * @return an ObjectNode representing the result of the ability execution,
     * including any error messages or outcomes.
     */
    public ObjectNode executeAttack() {
        final ArrayNode output = objectMapper.createArrayNode();

        game.cardUsesAttack(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
