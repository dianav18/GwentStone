package game;

import lombok.Getter;
import cards.minion.Minion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    @Getter
    private List<Minion> minions;
    private final List<Minion> originalCards;

    public Deck(List<Minion> minions) {
        this.minions = new ArrayList<>(minions);
        this.originalCards = new ArrayList<>(minions);
    }

    public void shuffle() {
        Collections.shuffle(minions);
    }

    public void resetDeck() {
        this.minions = new ArrayList<>(originalCards);
    }


}
