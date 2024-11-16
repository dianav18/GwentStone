package cards.hero;

import fileio.CardInput;
import cards.minion.Minion;

public class EmpressThorina extends Hero {
    public EmpressThorina(CardInput cardInput) {
        super(cardInput);
    }

    public void LowBlow(Minion[][] board, int rowIndex) {
        Minion[] row = board[rowIndex];
        Minion targetMinion = null;

        for (Minion minion : row) {
            if (minion != null) {
                if (targetMinion == null || minion.getHealth() > targetMinion.getHealth()) {
                    targetMinion = minion;
                }
            }
        }

        if (targetMinion != null) {
            targetMinion.setHealth(0);
            for (int i = 0; i < row.length; i++) {
                if (row[i] == targetMinion) {
                    row[i] = null;
                    break;
                }
            }
        }
    }
}
