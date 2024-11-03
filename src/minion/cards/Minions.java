package minion.cards;

import fileio.CardInput;
import lombok.Getter;

import java.util.ArrayList;

public class Minions {
    private int hand;
    private int health;
    private int attackDamage;
    private String description;
    private ArrayList<String> colors;
    private String name;

    private boolean hasAttacked;
    private boolean frozen;

    private MinionType type;

    public Minions(CardInput cardInput, MinionType type) {
        this.hand = cardInput.getMana();
        this.health = cardInput.getHealth();
        this.attackDamage = cardInput.getAttackDamage();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.hasAttacked = false;
        this.frozen = false;
        this.type = type;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isTank() {
        return this.type == MinionType.TANK;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void freeze() {
        this.frozen = true;
    }

    public void unfreeze() {
        this.frozen = false;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public boolean hasAttacked() {
        return hasAttacked;
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

}
