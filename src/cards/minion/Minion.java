package cards.minion;

import cards.Card;
import fileio.CardInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter

//@NoArgsConstructor
public class Minion extends Card {
    private int mana;
    @Setter
    private int health;
    @Setter
    private int attackDamage;
    private String description;
    private ArrayList<String> colors;
    private String name;

    @Setter
    //private boolean hasAttacked;
    //private boolean frozen;

    private MinionType type;

    private Row rowType;

    public Minion(CardInput cardInput, Row rowType, MinionType type) {
        super(cardInput);
        this.mana = cardInput.getMana();
        this.health = cardInput.getHealth();
        this.attackDamage = cardInput.getAttackDamage();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();

       // this.hasAttacked = false;
        //this.frozen = false;
        this.rowType = rowType;
        this.type = type;
    }

    public boolean isTank() {
        return this.type == MinionType.TANK;
    }

    //public void freeze() {
      //  this.frozen = true;
    //}

   // public void unfreeze() {
     //   this.frozen = false;
   // }

   // public boolean hasAttacked() {
     //   return hasAttacked;
   // }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

   // public boolean canAttack() {
   //     return !hasAttacked && !frozen;
   // }

    public Minion copy(){
        //Minion copy = new Minion();

        //copy.mana = this.mana;
        //TODO

        //return copy;
        return null;
    }

}
