package debug;

import cards.hero.Hero;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class GetPlayerHero {
    private final Hero hero;
    private final int playerIdx;

    public GetPlayerHero(Hero hero, int playerIdx) {
        this.hero = hero;
        this.playerIdx = playerIdx;
    }

    public ObjectNode getHeroData(ObjectMapper objectMapper) {
        ObjectNode heroOutput = objectMapper.createObjectNode();
        heroOutput.put("command", "getPlayerHero");
        heroOutput.put("playerIdx", playerIdx);

        ObjectNode heroDetails = objectMapper.createObjectNode();
        heroDetails.put("mana", hero.getMana());
        heroDetails.put("description", hero.getDescription());
        heroDetails.put("name", hero.getName());
        heroDetails.put("health", hero.getHealth());

        ArrayNode colorsArray = heroDetails.putArray("colors");
        hero.getColors().forEach(colorsArray::add);

        heroOutput.set("output", heroDetails);
        return heroOutput;
    }
}
