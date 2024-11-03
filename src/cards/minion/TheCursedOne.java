package cards.minion;

import fileio.CardInput;

public class TheCursedOne extends Minion {
    public TheCursedOne(CardInput cardInput) {
        super(cardInput, Row.BACK, MinionType.REGULAR);
        this.setAttackDamage(0);
    }

    public void shapeShift(Minion enemyMinion) {
        if(enemyMinion.getAttackDamage() == 0) {
            enemyMinion.setHealth(0);
        } else {
            int originalHealth = enemyMinion.getHealth();
            enemyMinion.setHealth(enemyMinion.getAttackDamage());
            enemyMinion.setAttackDamage(originalHealth);
        }
    }
}
