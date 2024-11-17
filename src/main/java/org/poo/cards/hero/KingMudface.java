package org.poo.cards.hero;

import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import org.poo.game.Player;

/**
 * The type King mudface.
 */
public class KingMudface extends Hero {
    /**
     * Instantiates a new King mudface.
     *
     * @param cardInput the card input
     */
    public KingMudface(final CardInput cardInput) {
        super(cardInput);
    }


    /**
     * Executes the EarthBorn ability, which increments the health by one for all cards
     * on the specified row.
     *
     * @param board    the game board
     * @param rowIndex the index of the targeted row
     */
    public static void earthBorn(final Minion[][] board, final int rowIndex) {
        final Minion[] row = board[rowIndex];

        for (final Minion minion : row) {
            if (minion != null) {
                minion.setHealth(minion.getHealth() + 1);
            }
        }
    }

    public void useAbility(final Game game, final Player player, final int affectedRow,
                           final ObjectNode resultNode, final ArrayNode output) {
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
                minion.setHealth(minion.getHealth() + 1);
            }
        }

        player.setMana(player.getMana() - this.getMana());
        this.setHasAttacked(true);
    }
}
