package cards.minion;

import fileio.CardInput;

public class Miraj extends Minion {
    public Miraj(CardInput cardInput) {
        super(cardInput);
        this.setRowPosition(Row.FRONT);
        this.setIsTank(false);
    }

    public void skyjack(Minion enemyMinion) {
        int originalHealth = this.getHealth();
        this.setHealth(enemyMinion.getHealth());
        enemyMinion.setHealth(originalHealth);
    }
}