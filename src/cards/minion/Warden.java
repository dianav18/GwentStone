package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Warden extends Minion {
    public Warden(CardInput cardInput) {
        super(cardInput, true, Row.FRONT);
    }

    protected Minion constructNew(){
        return new Warden();
    }
}
