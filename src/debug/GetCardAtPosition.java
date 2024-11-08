package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Game;

public class GetCardAtPosition {
    private final int x;
    private final int y;
    private final Game game;
    private final ObjectMapper objectMapper;

    public GetCardAtPosition(Game game, int x, int y, ObjectMapper objectMapper) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.objectMapper = objectMapper;
    }

    public ObjectNode getCardAtPosition() {
        ArrayNode output = objectMapper.createArrayNode();

        game.getCardAtPosition(x, y, objectMapper, output);

        if (!output.isEmpty()) {
            return (ObjectNode) output.get(0);
        } else {
            return objectMapper.createObjectNode();
        }
    }
}
