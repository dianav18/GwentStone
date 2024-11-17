package debug;

import cards.hero.Hero;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Handles the retrieval of a player's hero.
 */
public class GetPlayerHero {
    private final Hero hero;
    private final int playerIdx;

    /**
     * Constructs a new GetPlayerHero instance.
     *
     * @param hero      the player's hero(given in the input)
     * @param playerIdx the player's index ( 1 or 2)
     */
    public GetPlayerHero(final Hero hero, final int playerIdx) {
        this.hero = hero;
        this.playerIdx = playerIdx;
    }

    /**
     * Retrieves the hero's data and formats it as a JSON object.
     *
     * @param objectMapper the ObjectMapper used for JSON operations.
     * @return an ObjectNode containing the hero's data. The structure includes:
     * - "command": the name of the command executed ("getPlayerHero").
     * - "playerIdx": the index of the player whose hero is being retrieved.
     * - "output": a JSON object with the hero's details, including:
     *              - "mana": the hero's mana points.
     *              - "description": the hero's description.
     *              - "name": the hero's name.
     *              - "health": the hero's current health points.
     *              - "colors": an array of colors associated with the hero.
     */
    public ObjectNode getHeroData(final ObjectMapper objectMapper) {
        final ObjectNode heroOutput = objectMapper.createObjectNode();
        heroOutput.put("command", "getPlayerHero");
        heroOutput.put("playerIdx", playerIdx);

        final ObjectNode heroDetails = objectMapper.createObjectNode();
        heroDetails.put("mana", hero.getMana());
        heroDetails.put("description", hero.getDescription());
        heroDetails.put("name", hero.getName());
        heroDetails.put("health", hero.getHealth());

        final ArrayNode colorsArray = heroDetails.putArray("colors");
        hero.getColors().forEach(colorsArray::add);

        heroOutput.set("output", heroDetails);
        return heroOutput;
    }
}
