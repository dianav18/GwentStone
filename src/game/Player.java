package game;

import cards.hero.Hero;
import cards.minion.Minion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final List<Deck> decks;
    //private int playerIndex;
    private ArrayList<Minion> currentDeck;
    private ArrayList<Minion> hand;
    private int mana;
    private Hero hero;

    public Player(final ArrayList<Deck> decks, final Hero hero) {
        //this.playerIndex = playerIndex;

        this.decks = new ArrayList<>();
        this.decks.addAll(decks);
    }

    public void nextRound(int round){
        this.mana+=(Math.max(round, 10));// TODO Add as constant

        hand.add(currentDeck.remove(0));
    }

    public void prepareDeck(int deckIndex){
        this.currentDeck = decks.get(deckIndex).getMinions()
                .stream()
                .map(Minion::copy)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
