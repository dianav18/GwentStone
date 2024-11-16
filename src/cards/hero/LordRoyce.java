package cards.hero;

import fileio.CardInput;
import cards.minion.Minion;
import lombok.NoArgsConstructor;

public class LordRoyce extends Hero {

    public LordRoyce(CardInput cardInput) {
        super(cardInput);
    }

    public void subZero(Minion[][] board, int rowIndex) {
        Minion[] row = board[rowIndex];

        for (Minion minion : row) {
            if (minion != null) {
                minion.setFrozen(true);
            }
        }
    }
}
