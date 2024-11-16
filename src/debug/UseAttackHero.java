package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;
import cards.hero.Hero;

public class UseAttackHero {
    private final Game game;
    private final int xAttacker;
    private final int yAttacker;
    private final Hero attackedHero;
    private final ObjectMapper objectMapper;

    public UseAttackHero(Game game, int xAttacker, int yAttacker, Hero attackedHero, ObjectMapper objectMapper) {
        this.game = game;
        this.xAttacker = xAttacker;
        this.yAttacker = yAttacker;
        this.attackedHero = attackedHero;
        this.objectMapper = objectMapper;
    }

    public ObjectNode executeAttack() {
        ArrayNode output = objectMapper.createArrayNode();

        game.useAttackHero(xAttacker, yAttacker, attackedHero, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
