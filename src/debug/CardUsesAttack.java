package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

public class CardUsesAttack {
    private final Game game;
    private final int xAttacker;
    private final int yAttacker;
    private final int xAttacked;
    private final int yAttacked;
    private final ObjectMapper objectMapper;

    public CardUsesAttack(Game game, int xAttacker, int yAttacker, int xAttacked, int yAttacked, ObjectMapper objectMapper) {
        this.game = game;
        this.xAttacker = xAttacker;
        this.yAttacker = yAttacker;
        this.xAttacked = xAttacked;
        this.yAttacked = yAttacked;
        this.objectMapper = objectMapper;
    }

    public ObjectNode executeAttack() {
        ArrayNode output = objectMapper.createArrayNode();

        game.cardUsesAttack(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
