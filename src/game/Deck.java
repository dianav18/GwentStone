package game;

import cards.Card;
import cards.minion.*;
import fileio.CardInput;
import lombok.Getter;

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
            switch (minion.getName()){
                case "Berserker":
                    this.minions.add(new Berserker(minion));
                    break;
                case "Disciple":
                    this.minions.add(new Disciple(minion));
                    break;
                case "Goliath":
                    this.minions.add(new Goliath(minion));
                    break;
                case "Miraj":
                    this.minions.add(new Miraj(minion));
                    break;
                case "Sentinel":
                    this.minions.add(new Sentinel(minion));
                    break;
                case "The Cursed One":
                    this.minions.add(new TheCursedOne(minion));
                    break;
                case "The Ripper":
                    this.minions.add(new TheRipper(minion));
                    break;
                case "Warden":
                    this.minions.add(new Warden(minion));
                    break;
                default:
                    System.out.println(minion.getName() + " is not a valid minion");
                    break;

            }
        }
    }

    public void shuffle(long seed) {
        Collections.shuffle(minions, new Random(seed));
    }

    public Deck copy(){
        return new Deck(this);
    }
}