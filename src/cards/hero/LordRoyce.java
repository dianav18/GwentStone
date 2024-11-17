package cards.hero;

import cards.minion.Minion;
import fileio.CardInput;

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

    /**
     * Executes the SubZero ability, which freezes all the cards
     * on the specified row.
     *
     * @param board    the game board
     * @param rowIndex the index of the targeted row
     */
    public static void subZero(final Minion[][] board, final int rowIndex) {
        final Minion[] row = board[rowIndex];

        for (final Minion minion : row) {
            if (minion != null) {
                minion.setFrozen(true);
            }
        }
    }
}
