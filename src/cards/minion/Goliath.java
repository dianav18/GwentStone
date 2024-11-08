package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Goliath extends Minion {
    public Goliath(CardInput cardInput) {
        super(cardInput, true, Row.FRONT);
    }

    protected Minion constructNew(){
        return new Goliath();
    }
}
