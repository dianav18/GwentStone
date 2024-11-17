package org.poo.cards.hero;

import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import org.poo.game.Player;

/**
 * The type Empress thorina.
 */
public final class EmpressThorina extends Hero {
    /**
     * Instantiates a new Empress thorina.
     *
     * @param cardInput the card input
     */
    public EmpressThorina(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Executes the hero's ability on a specific row of the enemy board. The ability targets the
     * minion with the highest health in the specified row, setting its health to 0 and removing
     * it from the board by shifting subsequent minions to the left.
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
        if (!game.isEnemyRow(player, affectedRow)) {
            resultNode.put("command", "useHeroAbility");
            resultNode.put("affectedRow", affectedRow);
            resultNode.put("error", "Selected row does not belong to the enemy.");
            output.add(resultNode);
            return;
        }

        final Minion[] row = game.getBoard()[affectedRow];

        Minion targetMinion = null;
        int targetIndex = -1;

        for (int i = 0; i < row.length; i++) {
            final Minion minion = row[i];
            if (minion != null) {
                if (targetMinion == null || minion.getHealth() > targetMinion.getHealth()) {
                    targetMinion = minion;
                    targetIndex = i;
                }
            }
        }

        if (targetMinion != null && targetIndex != -1) {
            targetMinion.setHealth(0);
            for (int i = targetIndex; i < row.length - 1; i++) {
                row[i] = row[i + 1];
            }
            row[row.length - 1] = null;
        }

        player.setMana(player.getMana() - this.getMana());
        this.setHasAttacked(true);
    }
}
