package org.poo.cards.hero;

import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import org.poo.game.Player;

/**
 * The type Lord royce.
 */
public final class LordRoyce extends Hero {

    /**
     * Instantiates a new Lord Royce.
     *
     * @param cardInput the card input
     */
    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
    }
    /**
     * Uses the hero's ability to freeze all minions on a specified row belonging to the enemy.
     *
     * @param game        the current game state, containing the board and player details.
     * @param player      the player using the hero ability.
     * @param affectedRow the index of the row to apply the ability to.
     * @param resultNode  the JSON node to store the result of the action.
     * @param output      the JSON array to store any error or success messages.
     */
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
