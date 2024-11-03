package cards.minion;

import fileio.CardInput;

public class Disciple extends Minion {
    public Disciple(CardInput cardInput) {
        super(cardInput, Row.BACK, MinionType.REGULAR);
        this.setAttackDamage(0);
    }

    public void godsPlan(Minion allyMinion) {
        allyMinion.setHealth(allyMinion.getHealth() + 2);
    }
}
