package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type The ripper.
 */
@NoArgsConstructor
public final class TheRipper extends Minion {
    /**
     * Instantiates a new The ripper.
     *
     * @param cardInput the card input
     */
    public TheRipper(final CardInput cardInput) {
        super(cardInput, false, Row.FRONT);
    }

    /**
     * Executes the WeakKnees ability which decrements the attack of the
     * enemy minion by 2
     *
     * @param enemyMinion the enemy minion
     */
    public void weakKnees(final Minion enemyMinion) {
        int newAttackDamage = enemyMinion.getAttackDamage() - 2;

        if (newAttackDamage < 0) {
            newAttackDamage = 0;
        }

        enemyMinion.setAttackDamage(newAttackDamage);

    }

    protected Minion constructNew() {
        return new TheRipper();
    }
}
