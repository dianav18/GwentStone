package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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

    protected Minion constructNew(){
        return new TheRipper();
    }
}
