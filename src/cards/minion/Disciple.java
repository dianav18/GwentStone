package cards.minion;

import fileio.CardInput;

public class Disciple extends Minion {
    public Disciple(CardInput cardInput) {
        super(cardInput);
        this.setAttackDamage(0);
        this.setRowPosition(Row.BACK);
        this.setIsTank(false);
    }

    public void godsPlan(Minion allyMinion) {
        allyMinion.setHealth(allyMinion.getHealth() + 2);
    }
}
