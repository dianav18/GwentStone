package cards.hero;

import fileio.CardInput;
import cards.minion.Minion;

import java.util.List;

public class GeneralKocioraw extends Hero{

    public GeneralKocioraw(CardInput cardInput) {
        super(cardInput);
    }

    public void BloodThirst(Minion[][] board, int rowIndex) {
        Minion[] row = board[rowIndex];

        for (Minion minion : row) {
            if (minion != null) {
                minion.setAttackDamage(minion.getAttackDamage() + 1);
            }
        }
    }
}
