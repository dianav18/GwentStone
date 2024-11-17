package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type The cursed one.
 */
@NoArgsConstructor
public final class TheCursedOne extends Minion {
    /**
     * Instantiates a new The cursed one.
     *
     * @param cardInput the card input
     */
    public TheCursedOne(final CardInput cardInput) {
        super(cardInput, false, Row.BACK);
        this.setAttackDamage(0);
    }

    /**
     * Executes the ShapeShift ability which switches the attack and the life of
     * an enemy minion
     * if the health of the attack minion becomes zero it gets eliminated
     *
     * @param enemyMinion the enemy minion
     */
    public void shapeShift(final Minion enemyMinion) {
        if (enemyMinion.getAttackDamage() == 0) {
            enemyMinion.setHealth(0);
        } else {
            final int originalHealth = enemyMinion.getHealth();
            enemyMinion.setHealth(enemyMinion.getAttackDamage());
            enemyMinion.setAttackDamage(originalHealth);
        }
    }

    protected Minion constructNew() {
        return new TheCursedOne();
    }
}
