package cards.hero;

import cards.minion.Berserker;
import fileio.CardInput;
import cards.minion.Minion;
import game.Game;
import lombok.NoArgsConstructor;

public class EmpressThorina extends Hero {
    public EmpressThorina(CardInput cardInput) {
        super(cardInput);
    }

    public void LowBlow(Minion[][] board, int rowIndex) {
        Minion[] row = board[rowIndex];

        Minion targetMinion = null;
        int targetIndex = -1;

        for (int i = 0; i < row.length; i++) {
            Minion minion = row[i];
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
