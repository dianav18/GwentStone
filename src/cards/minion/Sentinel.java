package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type Sentinel.
 */
@NoArgsConstructor
public final class Sentinel extends Minion {
    /**
     * Instantiates a new Sentinel.
     *
     * @param cardInput the card input
     */
    public Sentinel(final CardInput cardInput) {
        super(cardInput, false, Row.BACK);
    }

    protected Minion constructNew() {
        return new Sentinel();
    }


}
