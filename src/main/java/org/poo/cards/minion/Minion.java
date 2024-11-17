package org.poo.cards.minion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
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
     * Use ability.
     *
     * @param xAttacked    the x attacked
     * @param yAttacked    the y attacked
     * @param xAttacker    the x attacker
     * @param yAttacker    the y attacker
     * @param objectMapper the object mapper
     * @param output       the output
     * @param game         the game
     * @param resultNode   the result node
     */
    public void useAbility(final int xAttacked, final int yAttacked,
                                    final int xAttacker, final int yAttacker,
                                    final ObjectMapper objectMapper,
                                    final ArrayNode output, final Game game, final ObjectNode resultNode){
        final Minion attackerCard = game.getBoard()[xAttacker][yAttacker];
        final Minion attackedCard = game.getBoard()[xAttacked][yAttacked];

        int isAttackedCardEnemy = 0;

        if (game.getPlayerTurn() == game.getPlayer1() && (xAttacked == 0 || xAttacked == 1)) {
            isAttackedCardEnemy = 1;
        } else if (game.getPlayerTurn() == game.getPlayer2() && (xAttacked == 2 || xAttacked == 3)) {
            isAttackedCardEnemy = 1;
        }
        if (isAttackedCardEnemy == 0) {
            cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker,
                    yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacked card does not belong to the enemy.");
            output.add(resultNode);
            return;
        }

        boolean enemyHasTank = false;
        final int enemyFrontRow;

        if (game.getPlayerTurn() == game.getPlayer1()) {
            enemyFrontRow = 1;
        } else {
            enemyFrontRow = 2;
        }

        for (final Minion minion : game.getBoard()[enemyFrontRow]) {
            if (minion != null && minion.isTank()) {
                enemyHasTank = true;
                break;
            }
        }

        if (enemyHasTank && !attackedCard.isTank()) {
            cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker,
                    yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacked card is not of type 'Tank'.");
            output.add(resultNode);
            return;
        }

        internalUseAbility(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, output, game, resultNode, attackedCard);

        attackerCard.setHasAttacked(true);
    }

    /**
     * Internal use ability.
     *
     * @param xAttacked    the x attacked
     * @param yAttacked    the y attacked
     * @param xAttacker    the x attacker
     * @param yAttacker    the y attacker
     * @param objectMapper the object mapper
     * @param output       the output
     * @param game         the game
     * @param resultNode   the result node
     * @param attackedCard the attacked card
     */
    protected abstract void internalUseAbility(final int xAttacked, final int yAttacked,
                                    final int xAttacker, final int yAttacker,
                                    final ObjectMapper objectMapper,
                                               final ArrayNode output, final Game game, final ObjectNode resultNode,
                                               Minion attackedCard
                                               );

    /**
     * Card uses ability output.
     *
     * @param xAttacked    the x attacked
     * @param yAttacked    the y attacked
     * @param xAttacker    the x attacker
     * @param yAttacker    the y attacker
     * @param objectMapper the object mapper
     * @param resultNode   the result node
     */
    protected void cardUsesAbilityOutput(final int xAttacked, final int yAttacked,
                                       final int xAttacker, final int yAttacker,
                                       final ObjectMapper objectMapper,
                                       final ObjectNode resultNode) {
        final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
        attackerCardDetails.put("x", xAttacker);
        attackerCardDetails.put("y", yAttacker);
        final ObjectNode attackedCardDetails = objectMapper.createObjectNode();
        attackedCardDetails.put("x", xAttacked);
        attackedCardDetails.put("y", yAttacked);

        resultNode.set("cardAttacked", attackedCardDetails);
        resultNode.set("cardAttacker", attackerCardDetails);

        resultNode.put("command", "cardUsesAbility");
    }
}
