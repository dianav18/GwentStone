package cards.minion;

import fileio.CardInput;

public class Miraj extends Minion {
    public Miraj(CardInput cardInput) {
        super(cardInput, false, Row.FRONT);
    }

    public void skyjack(Minion enemyMinion) {
        int originalHealth = this.getHealth();
        this.setHealth(enemyMinion.getHealth());
        enemyMinion.setHealth(originalHealth);
    }
}