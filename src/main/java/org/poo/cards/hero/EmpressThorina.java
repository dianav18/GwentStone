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
public class EmpressThorina extends Hero {
    /**
     * Instantiates a new Empress thorina.
     *
     * @param cardInput the card input
     */
    public EmpressThorina(final CardInput cardInput) {
        super(cardInput);
    }

    public void useAbility(final Game game, final Player player, final int affectedRow, final ObjectNode resultNode, final ArrayNode output){
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
