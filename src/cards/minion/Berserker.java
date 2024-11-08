package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Berserker extends Minion {
    public Berserker(CardInput cardInput) {
        super(cardInput, false, Row.BACK);
    }

    protected Minion constructNew(){
        return new Berserker();
    }

}
