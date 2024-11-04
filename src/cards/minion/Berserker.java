package cards.minion;

import fileio.CardInput;

public class Berserker extends Minion {
    public Berserker(CardInput cardInput) {
        super(cardInput);
        this.setIsTank(false);
    }
}
