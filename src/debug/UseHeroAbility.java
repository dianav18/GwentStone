package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;
import cards.hero.Hero;
import game.Player;

public class UseHeroAbility {
    private final Game game;
    private final Hero hero;
    private final int affectedRow;
    private final ObjectMapper objectMapper;
    private final Player currentPlayer;

    public UseHeroAbility(Game game, Hero hero, int affectedRow, Player currentPlayer, ObjectMapper objectMapper) {
        this.game = game;
        this.hero = hero;
        this.affectedRow = affectedRow;
        this.objectMapper = objectMapper;
        this.currentPlayer = currentPlayer;
    }

    public ObjectNode executeAbility() {
        ArrayNode output = objectMapper.createArrayNode();

        game.useHeroAbility(affectedRow, hero, currentPlayer, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
