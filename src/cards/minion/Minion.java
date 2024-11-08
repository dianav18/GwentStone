package cards.minion;

import cards.Card;
import delete_me.MessageBuilder;
import fileio.CardInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
public abstract class Minion {
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

    public Minion(CardInput cardInput, boolean isTank, Row rowPosition) {
        this.mana = cardInput.getMana();
        this.health = cardInput.getHealth();
        this.attackDamage = cardInput.getAttackDamage();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.hasAttacked = false;
        this.frozen = false;
        this.isTank = isTank;
        this.rowPosition = rowPosition;
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

    public boolean canUseAbility(int abilityHandCost) {
        return this.mana >= abilityHandCost;
    }

    public Minion copy(){
        Minion copy = constructNew();

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

    protected abstract Minion constructNew();

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

    public boolean hasAttacked() {
        return this.hasAttacked;
    }

    @Override
    public String toString() {
        return new MessageBuilder("{name}(H{health} A{attack} M{mana} H_A={has_attacked} F={frozen})")
                .parse("name", this.name)
                .parse("attack", this.attackDamage)
                .parse("health", this.health)
                .parse("mana", this.mana)
                .parse("has_attacked", this.hasAttacked)
                .parse("frozen", this.frozen)
                .parse();
    }
}