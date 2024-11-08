package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TheCursedOne extends Minion {
    public TheCursedOne(CardInput cardInput) {
        super(cardInput, false, Row.BACK);
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

    protected Minion constructNew(){
        return new TheCursedOne();
    }
}
