package cards.hero;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import game.Game;
import game.Player;

/**
 * The type Lord royce.
 */
public class LordRoyce extends Hero {

    /**
     * Instantiates a new Lord royce.
     *
     * @param cardInput the card input
     */
    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
    }

    public void useAbility(final Game game, final Player player,
                           final int affectedRow, final ObjectNode resultNode,
                           final ArrayNode output) {
        if (!game.isEnemyRow(player, affectedRow)) {
            resultNode.put("command", "useHeroAbility");
            resultNode.put("affectedRow", affectedRow);
            resultNode.put("error", "Selected row does not belong to the enemy.");
            output.add(resultNode);
            return;
        }

        final Minion[] row = game.getBoard()[affectedRow];

        for (final Minion minion : row) {
            if (minion != null) {
                minion.setFrozen(true);
            }
        }
        player.setMana(player.getMana() - this.getMana());
        this.setHasAttacked(true);
    }


}
