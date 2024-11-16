package cards.hero;

import cards.Card;
import cards.minion.Minion;
import fileio.CardInput;
import game.Game;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public abstract class Hero extends Card {
    private int mana;
    private int health ;
    private String description;
    private ArrayList<String> colors;
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

    public static Hero create(CardInput input){
        switch (input.getName()){
            case "Empress Thorina":
                return new EmpressThorina(input);
            case "General Kocioraw":
                return new GeneralKocioraw(input);
            case "King Mudface":
                return new KingMudface(input);
            case "Lord Royce":
                return new LordRoyce(input);
        }
        return null; // This should never happen
    }

    public boolean canUseAbility(int abilityHandCost) {
        return this.mana >= abilityHandCost;
    }

    public void useAbility(int abilityHandCost) {
        this.mana -= abilityHandCost;
    }

    public boolean hasAttacked(){
        return this.hasAttacked;
    }
}
