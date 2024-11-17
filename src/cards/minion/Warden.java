package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

/**
 * The type Warden.
 */
@NoArgsConstructor
public final class Warden extends Minion {
    /**
     * Instantiates a new Warden.
     *
     * @param cardInput the card input
     */
    public Warden(final CardInput cardInput) {
        super(cardInput, true, Row.FRONT);
    }

    protected Minion constructNew() {
        return new Warden();
    }
}
