package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type Goliath.
 */
@NoArgsConstructor
public final class Goliath extends Minion {
    /**
     * Instantiates a new Goliath.
     *
     * @param cardInput the card input
     */
    public Goliath(final CardInput cardInput) {
        super(cardInput, true, Row.FRONT);
    }

    protected Minion constructNew() {
        return new Goliath();
    }
}
