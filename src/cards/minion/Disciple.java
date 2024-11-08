package cards.minion;

import fileio.CardInput;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Disciple extends Minion {
    public Disciple(CardInput cardInput) {
        super(cardInput, false, Row.BACK);
        this.setAttackDamage(0);
    }

    public void godsPlan(Minion allyMinion) {
        allyMinion.setHealth(allyMinion.getHealth() + 2);
    }

    protected Minion constructNew(){
        return new Disciple();
    }
}
