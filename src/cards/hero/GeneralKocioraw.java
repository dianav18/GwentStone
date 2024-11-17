package cards.hero;

import cards.minion.Minion;
import fileio.CardInput;

/**
 * The type General kocioraw.
 */
public class GeneralKocioraw extends Hero {

    /**
     * Instantiates a new General kocioraw.
     *
     * @param cardInput the card input
     */
    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Executes the BloodThirst ability, which increments the attack by one for all cards
     * on the specified row.
     *
     * @param board    the game board
     * @param rowIndex the index of the targeted row
     */
    public static void bloodThirst(final Minion[][] board, final int rowIndex) {
        final Minion[] row = board[rowIndex];

        for (final Minion minion : row) {
            if (minion != null) {
                minion.setAttackDamage(minion.getAttackDamage() + 1);
            }
        }
    }
}
