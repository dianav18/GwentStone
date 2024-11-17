package org.poo.cards.hero;

import org.poo.cards.Card;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.game.Game;
import org.poo.game.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * The type Hero.
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class Hero extends Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean hasAttacked;
    private static final int DEFAULT_HEALTH = 30;
    private int health;

    /**
     * Instantiates a new Hero.
     *
     * @param cardInput the card input
     */
    public Hero(final CardInput cardInput) {
        super(cardInput);
        this.mana = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
        this.hasAttacked = false;
        this.health = DEFAULT_HEALTH;
    }

    /**
     * Constructor for initializing a Hero object based on the provided card input.
     *
     * @param input the input containing the hero's attributes such as mana, description, colors, and name
     * @return the hero
     */
    public static Hero create(final CardInput input) {
        switch (input.getName()) {
            case "Empress Thorina":
                return new EmpressThorina(input);
            case "General Kocioraw":
                return new GeneralKocioraw(input);
            case "King Mudface":
                return new KingMudface(input);
            case "Lord Royce":
                return new LordRoyce(input);
            default:
                break;
        }
        return null;
    }

    /**
     * Checks if the hero has already attacked during the current turn.
     *
     * @return true if the hero has attacked, false otherwise
     */
    public boolean hasAttacked() {
        return this.hasAttacked;
    }

    /**
     * Use ability.
     *
     * @param game        the game
     * @param player      the player
     * @param affectedRow the affected row
     * @param resultNode  the result node
     * @param output      the output
     */
    public abstract void useAbility(Game game, Player player, final int affectedRow, ObjectNode resultNode, final ArrayNode output);

}
