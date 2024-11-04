package cards.minion;

import fileio.CardInput;

public class Warden extends Minion {
    public Warden(CardInput cardInput) {
        super(cardInput);
        this.setIsTank(true);
    }
}
