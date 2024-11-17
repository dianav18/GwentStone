package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;
import cards.hero.Hero;

/**
 * The type Use attack hero.
 */
public class UseAttackHero {
    private final Game game;
    private final int xAttacker;
    private final int yAttacker;
    private final Hero attackedHero;
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new Use attack hero.
     *
     * @param game         the game
     * @param xAttacker    the x attacker
     * @param yAttacker    the y attacker
     * @param attackedHero the attacked hero
     * @param objectMapper the object mapper
     */
    public UseAttackHero(final Game game, final int xAttacker, final int yAttacker,
                         final Hero attackedHero, final ObjectMapper objectMapper) {
        this.game = game;
        this.xAttacker = xAttacker;
        this.yAttacker = yAttacker;
        this.attackedHero = attackedHero;
        this.objectMapper = objectMapper;
    }

    /**
     * Execute attack object node.
     *
     * @return the object node
     */
    public ObjectNode executeAttack() {
        final ArrayNode output = objectMapper.createArrayNode();

        game.useAttackHero(xAttacker, yAttacker, attackedHero, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
