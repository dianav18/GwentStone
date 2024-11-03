package cards.hero;

import cards.Card;
import fileio.CardInput;

import java.util.ArrayList;

public class Hero extends Card {
    private int mana;
    private final int health = 30;
    private String description;
    private ArrayList<String> colors;;
    private String name;

    //private boolean hasAttacked;

    public Hero(CardInput cardInput) {
        super(cardInput);
        this.mana = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
       // this.hasAttacked = false;
    }

    public boolean canUseAbility(int abilityHandCost) {
        return this.mana >= abilityHandCost;
    }

   public void useAbility(int abilityHandCost) {
        this.mana -= abilityHandCost;
   }

   public int getMana() {
        return mana;
   }

   public void setMana(int mana) {
        this.mana = mana;
   }

   public int getHealth() {
        return health;
   }

   //public void setHasAttacked(boolean hasAttacked) {
    //    this.hasAttacked = hasAttacked;
 //  }

  // public boolean hasAttacked() {
  //      return hasAttacked;
  // }
}
