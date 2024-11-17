package org.poo.cards.hero;

import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import org.poo.game.Player;

/**
 * The type General kocioraw.
 */
public final class GeneralKocioraw extends Hero {

    /**
     * Instantiates a new General kocioraw.
     *
     * @param cardInput the card input
     */
    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Executes the hero's ability on a specific row of the player's board.
     * The ability increases the attack damage of all minions in the specified row by 1.
     *
     * @param game       the current game instance, providing access to the game state.
     * @param player     the player using the hero ability.
     * @param affectedRow the index of the row affected by the hero's ability.
     * @param resultNode  the result node for storing output details about the operation.
     * @param output      the output array for storing command results.
     */


    public void useAbility(final Game game, final Player player,
                           final int affectedRow, final ObjectNode resultNode,
                           final ArrayNode output) {
        if (!game.isPlayerRow(player, affectedRow)) {
            resultNode.put("command", "useHeroAbility");
            resultNode.put("affectedRow", affectedRow);
            resultNode.put("error", "Selected row does not belong to the current player.");
            output.add(resultNode);
            return;
        }
        final Minion[] row = game.getBoard()[affectedRow];

        for (final Minion minion : row) {
            if (minion != null) {
                minion.setAttackDamage(minion.getAttackDamage() + 1);
            }
        }

        player.setMana(player.getMana() - this.getMana());
        this.setHasAttacked(true);
    }
}
