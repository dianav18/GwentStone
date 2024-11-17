package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type Disciple.
 */
@NoArgsConstructor
    public final class Disciple extends Minion {
    /**
     * Instantiates a new Disciple.
     *
     * @param cardInput the card input
     */
    public Disciple(final CardInput cardInput) {
            super(cardInput, false, Row.BACK);
            this.setAttackDamage(0);
        }

    /**
     * Increases the allyMinion's health by 2
     *
     * @param allyMinion the minion on which the ability will be applied
     */
    public void godsPlan(final Minion allyMinion) {
        allyMinion.setHealth(allyMinion.getHealth() + 2);
    }

    protected Minion constructNew() {
        return new Disciple();
    }
}
