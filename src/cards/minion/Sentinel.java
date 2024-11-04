package cards.minion;

import fileio.CardInput;

public class Sentinel extends Minion {
    public Sentinel(CardInput cardInput) {
        super(cardInput);
        this.setIsTank(false);
    }
}
