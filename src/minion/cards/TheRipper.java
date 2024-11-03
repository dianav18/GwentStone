package minion.cards;

import fileio.CardInput;

public class TheRipper extends FrontRowMinion {
    public TheRipper(CardInput cardInput) {
        super(cardInput);
    }

    public void weakKnees(Minions enemyMinion) {
        int newAttackDamage = enemyMinion.getAttackDamage() - 2;

        if (newAttackDamage < 0) {
            newAttackDamage = 0;
        }

        enemyMinion.setAttackDamage(newAttackDamage);
    }
}
