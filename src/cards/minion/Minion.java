package cards.minion;

import cards.Card;
import fileio.CardInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
public class Minion {
    private int mana;
    private int health;
    private int attackDamage;
    private String description;
    private ArrayList<String> colors;
    private String name;

    private boolean hasAttacked;
    private boolean frozen;

    private Row rowPosition;

    private boolean isTank;

    public Minion(CardInput cardInput) {
        this.mana = cardInput.getMana();
        this.health = cardInput.getHealth();
        this.attackDamage = cardInput.getAttackDamage();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.hasAttacked = false;
        this.frozen = false;
    }

    public void freeze() {
        this.frozen = true;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean canAttack() {
        return !hasAttacked && !frozen;
    }

    protected void setIsTank(boolean b) {
        this.isTank = b;
    }

    public boolean canUseAbility(int abilityHandCost) {
        return this.mana >= abilityHandCost;
    }

    public Minion copy(){
        Minion copy = new Minion();

        copy.mana = this.mana;
        copy.health = this.health;
        copy.attackDamage = this.attackDamage;
        copy.description = this.description;
        copy.colors = this.colors;
        copy.name = this.name;
        copy.hasAttacked = this.hasAttacked;
        copy.frozen = this.frozen;
        copy.rowPosition = this.rowPosition;
        copy.isTank = this.isTank;

        return copy;
    }

    public CardInput toInferior(){

        return new CardInput(
                this.mana,
                this.attackDamage,
                this.health,
                this.description,
                this.colors,
                this.name
        );
    }
}