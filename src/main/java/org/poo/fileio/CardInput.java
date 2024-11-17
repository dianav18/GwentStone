package org.poo.fileio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * The type Card input.
 */
@AllArgsConstructor
@Getter
@Setter
public final class CardInput {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    /**
     * Instantiates a new Card input.
     */
    public CardInput() {
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }
}
