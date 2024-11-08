package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Miraj extends Minion {
    public Miraj(CardInput cardInput) {
        super(cardInput, false, Row.FRONT);
    }

    public void skyjack(Minion enemyMinion) {
        int originalHealth = this.getHealth();
        this.setHealth(enemyMinion.getHealth());
        enemyMinion.setHealth(originalHealth);
    }

    protected Minion constructNew(){
        return new Miraj();
    }
}