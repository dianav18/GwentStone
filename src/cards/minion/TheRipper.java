package cards.minion;

import fileio.CardInput;

public class TheRipper extends Minion {
    public TheRipper(CardInput cardInput) {
        super(cardInput, false, Row.FRONT);
    }

    public void weakKnees(Minion enemyMinion) {
        int newAttackDamage = enemyMinion.getAttackDamage() - 2;

        if (newAttackDamage < 0) {
            newAttackDamage = 0;
        }

        enemyMinion.setAttackDamage(newAttackDamage);

    }
}
