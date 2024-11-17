package org.poo.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.game.Game;

/**
 * The type Use hero ability.
 */
public class UseHeroAbility {
    private final Game game;
    private final int affectedRow;
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new Use hero ability.
     *
     * @param game         the game
     * @param affectedRow  the affected row
     * @param objectMapper the object mapper
     */
    public UseHeroAbility(final Game game, final int affectedRow, final ObjectMapper objectMapper) {
        this.game = game;
        this.affectedRow = affectedRow;
        this.objectMapper = objectMapper;
    }

    /**
     * Execute ability object node.
     *
     * @return the object node
     */
    public ObjectNode executeAbility() {
        final ArrayNode output = objectMapper.createArrayNode();

        game.useHeroAbility(affectedRow, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
