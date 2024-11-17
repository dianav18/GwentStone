package cards.hero;

import cards.minion.Minion;
import fileio.CardInput;

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
}
