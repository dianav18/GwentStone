package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type Berserker.
 */
@NoArgsConstructor
public final class Berserker extends Minion {
    /**
     * Instantiates a new Berserker.
     *
     * @param cardInput the card input
     */
    public Berserker(final CardInput cardInput) {
        super(cardInput, false, Row.BACK);
    }

    /**
     * Factory method to create a new instance of Berserker.
     *
     * @return a new Berserker minion
     */
    protected Minion constructNew() {
        return new Berserker();
    }

}
