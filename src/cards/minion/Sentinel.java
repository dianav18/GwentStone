package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Sentinel extends Minion {
    public Sentinel(CardInput cardInput) {
        super(cardInput, false, Row.BACK);
    }

    protected Minion constructNew(){
        return new Sentinel();
    }


}
