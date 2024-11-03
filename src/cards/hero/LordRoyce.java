package cards.hero;

import fileio.CardInput;
import cards.minion.Minion;

import java.util.List;

public class LordRoyce extends Hero{
    public LordRoyce(CardInput cardInput) {
        super(cardInput);
    }

    public void subZero(List<Minion> row) {
        for (Minion enemyMinion : row) {
            enemyMinion.freeze();
        }
    }
}