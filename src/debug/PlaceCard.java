package debug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The type Place card.
 */
public class PlaceCard {

    /**
     * Place card error object node.
     *
     * @param objectMapper the object mapper
     * @param errorMessage the error message
     * @param handIdx      the hand idx
     * @return the object node
     */
    public static ObjectNode placeCardError(final ObjectMapper objectMapper, final String errorMessage, final int handIdx) {
        final ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("command", "placeCard");
        errorNode.put("handIdx", handIdx);
        errorNode.put("error", errorMessage);
        return errorNode;
    }
}
