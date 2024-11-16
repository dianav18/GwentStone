package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;
import cards.hero.Hero;
import game.Player;

public class UseHeroAbility {
    private final Game game;
    private final int affectedRow;
    private final ObjectMapper objectMapper;

    public UseHeroAbility(Game game, int affectedRow, ObjectMapper objectMapper) {
        this.game = game;
        this.affectedRow = affectedRow;
        this.objectMapper = objectMapper;
    }

    public ObjectNode executeAbility() {
        ArrayNode output = objectMapper.createArrayNode();

        game.useHeroAbility(affectedRow, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
