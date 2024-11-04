package cards.minion;

import fileio.CardInput;

public class TheCursedOne extends Minion {
    public TheCursedOne(CardInput cardInput) {
        super(cardInput);
        this.setAttackDamage(0);
        this.setRowPosition(Row.BACK);
        this.setIsTank(false);
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
