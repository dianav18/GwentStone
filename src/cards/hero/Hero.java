package cards.hero;

import cards.Card;
import fileio.CardInput;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.util.ArrayList;

@Getter
@Setter
public class Hero extends Card {
    private int mana;
    private int health ;
    private String description;
    private ArrayList<String> colors;;
    private String name;

    private boolean hasAttacked;

    public Hero(CardInput cardInput) {
        super(cardInput);
        this.mana = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.hasAttacked = false;
        this.health = 30;
    }

    public boolean canUseAbility(int abilityHandCost) {
        return this.mana >= abilityHandCost;
    }

    public void useAbility(int abilityHandCost) {
        this.mana -= abilityHandCost;
   }

}
