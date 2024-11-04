package game;

import fileio.CardInput;
import lombok.Getter;
import cards.minion.Minion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
public class Deck {
    private List<Minion> minions;

    public Deck(Deck deck){
        // performs a copy
        this.minions = new ArrayList<>(deck.minions.stream().map(Minion::copy).collect(Collectors.toList()));
    }

    public Deck(List<CardInput> minions) {
        this.minions = new ArrayList<>();

        for (CardInput minion : minions) {
            this.minions.add(new Minion(minion));
        }
    }

    public void shuffle(long seed) {
        Collections.shuffle(minions, new Random(seed));
    }

    public Deck copy(){
        return new Deck(this);
    }
}