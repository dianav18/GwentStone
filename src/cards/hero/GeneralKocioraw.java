package cards.hero;

import fileio.CardInput;
import cards.minion.Minion;

import java.util.List;

public class GeneralKocioraw extends Hero{

    public GeneralKocioraw(CardInput cardInput) {
        super(cardInput);
    }

    public void BloodThirst(List<Minion> row) {
        for (Minion myMinion : row) {
            myMinion.setAttackDamage(myMinion.getAttackDamage() + 1);
        }
    }
}
