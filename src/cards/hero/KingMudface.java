package cards.hero;

import fileio.CardInput;
import cards.minion.Minion;
import lombok.NoArgsConstructor;

import java.util.List;

public class KingMudface extends Hero{
    public KingMudface(CardInput cardInput) {
        super(cardInput);
    }

    public void EarthBorn(Minion[][] board, int rowIndex) {
        Minion[] row = board[rowIndex];

        for (Minion minion : row) {
            if (minion != null) {
                minion.setHealth(minion.getHealth() + 1);
            }
        }
    }
}
