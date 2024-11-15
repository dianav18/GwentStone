package cards.hero;

import fileio.CardInput;
import cards.minion.Minion;

import java.util.List;


public class KingMudface extends Hero{
    public KingMudface(CardInput cardInput) {
        super(cardInput);
    }


    public void EarthBorn(List<Minion> row) {
        for (Minion myMinion : row) {
            myMinion.setHealth(myMinion.getHealth() + 1);
        }
    }
}
