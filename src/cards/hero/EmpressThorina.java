package cards.hero;

import cards.minion.Berserker;
import fileio.CardInput;
import cards.minion.Minion;

import java.util.List;

public class EmpressThorina extends Hero{
    public EmpressThorina(CardInput cardInput) {
        super(cardInput);
    }
    public void LowBlow(List<Minion> row) {
        Minion enemyMinion = row.get(0);
        for (Minion minion : row) {
            if (minion.getHealth() > enemyMinion.getHealth()) {
                enemyMinion = minion;
            }
        }
        enemyMinion.setHealth(0);
        row.remove(enemyMinion); ////
    }
}
