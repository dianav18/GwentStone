package cards.minion;

import delete_me.MessageBuilder;
import fileio.CardInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Represents a Minion card in the game with various attributes and behaviors.
 */
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

    /**
     * Constructs a Minion with the given parameters.
     *
     * @param cardInput   the card's input data.
     * @param isTank      whether the minion is a tank.
     * @param rowPosition the row position where the minion can be placed.
     */
    public Minion(final CardInput cardInput, final boolean isTank, final Row rowPosition) {
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

    /**
     * Freezes the minion, preventing it from attacking.
     */
    public void freeze() {
        this.frozen = true;
    }

    /**
     * Unfreezes the minion, allowing it to attack.
     */
    public void unfreeze() {
        this.frozen = false;
    }

    /**
     * Reduces the minion's health by the given damage amount.
     *
     * @param damage the damage to deal to the minion.
     */
    public void takeDamage(final int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Checks if the minion can attack.
     *
     * @return true if the minion can attack, false otherwise.
     */
    public boolean canAttack() {
        return !hasAttacked && !frozen;
    }

    /**
     * Checks if the minion can use its ability.
     *
     * @param abilityHandCost the mana cost of the ability.
     * @return true if the minion has enough mana to use its ability, false otherwise.
     */
    public boolean canUseAbility(final int abilityHandCost) {
        return this.mana >= abilityHandCost;
    }

    /**
     * Creates a copy of this minion.
     *
     * @return a new Minion object with the same attributes.
     */
    public Minion copy() {
        final Minion copy = constructNew();

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

    /**
     * Constructs a new instance of the specific Minion subclass.
     *
     * @return a new Minion object.
     */
    protected abstract Minion constructNew();

    /**
     * Converts this minion into a simpler {@link CardInput} object.
     *
     * @return the converted CardInput object.
     */
    public CardInput toInferior() {
        return new CardInput(
                this.mana,
                this.attackDamage,
                this.health,
                this.description,
                this.colors,
                this.name
        );
    }

    /**
     * Checks if the minion has attacked in the current turn.
     *
     * @return true if the minion has attacked, false otherwise.
     */
    public boolean hasAttacked() {
        return this.hasAttacked;
    }

    /**
     * Returns a string representation of the minion.
     *
     * @return a string containing the minion's details.
     */
    @Override
    public String toString() {
        return new MessageBuilder("{name}(H{health} A{attack} M{mana}"
                + "H_A={has_attacked} F={frozen})")
                .parse("name", this.name)
                .parse("attack", this.attackDamage)
                .parse("health", this.health)
                .parse("mana", this.mana)
                .parse("has_attacked", this.hasAttacked)
                .parse("frozen", this.frozen)
                .parse();
    }
}
