package game;

import cards.hero.Hero;
import cards.minion.Minion;
import fileio.CardInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player {

    private List<Deck> decks;
    private Deck currentDeck;
    private Hand hand;

    public Player(ArrayList<ArrayList<CardInput>> decks) {
        this.decks = new ArrayList<>();
        for (ArrayList<CardInput> deck : decks) {
            this.decks.add(new Deck(deck));
        }
    }

    public void setup(){
        this.hand = new Hand();
        // TODO and mana etc
    }

    public void selectDeck(int index, long seed) {
        this.currentDeck = decks.get(index).copy();
        this.currentDeck.shuffle(seed);
    }

    public void nextRound(int round){
        this.hand.getMinions().add(this.currentDeck.getMinions().remove(0));
        // TODO - add mana according to round and hw requirements
    }
}