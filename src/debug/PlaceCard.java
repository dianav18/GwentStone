package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PlaceCard {

    public static ObjectNode placeCardError(ObjectMapper objectMapper, String errorMessage, int handIdx) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", "placeCard");
        errorNode.put("handIdx", handIdx);
        errorNode.put("error", errorMessage);
        return errorNode;
    }
}
