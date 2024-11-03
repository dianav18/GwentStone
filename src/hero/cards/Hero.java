package hero.cards;

import fileio.CardInput;

import java.util.ArrayList;

public class Hero {
    private int hand;
    private final int health = 30;
    private String description;
    private ArrayList<String> colors;;
    private String name;

    public Hero(CardInput cardInput) {
        this.hand = cardInput.getMana();
        this.description = cardInput.getDescription();
        this.colors = cardInput.getColors();
        this.name = cardInput.getName();
    }
}
