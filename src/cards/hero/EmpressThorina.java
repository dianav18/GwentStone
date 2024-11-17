package cards.hero;

import cards.minion.Minion;
import fileio.CardInput;

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

    /**
     * Executes the LowBlow ability, which destroys the minion with the highest health
     * on the specified row.
     *
     * @param board    the game board
     * @param rowIndex the index of the targeted row
     */
    public static void lowBlow(final Minion[][] board, final int rowIndex) {
        final Minion[] row = board[rowIndex];

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
    }
}
