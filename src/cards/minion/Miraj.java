package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type Miraj.
 */
@NoArgsConstructor
public final class Miraj extends Minion {
    /**
     * Instantiates a new Miraj.
     *
     * @param cardInput the card input
     */
    public Miraj(final CardInput cardInput) {
        super(cardInput, false, Row.FRONT);
    }

    /**
     * Applies the Skyjack ability, which switches the life of the current minion
     * with the life of an enemyMinion
     *
     * @param enemyMinion represents the enemy minion
     */
    public void skyjack(final Minion enemyMinion) {
        final int originalHealth = this.getHealth();
        this.setHealth(enemyMinion.getHealth());
        enemyMinion.setHealth(originalHealth);
    }

    protected Minion constructNew() {
        return new Miraj();
    }
}
