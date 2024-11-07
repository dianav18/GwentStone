package game;

import cards.Card;
import cards.hero.Hero;
import cards.minion.Minion;
import fileio.ActionsInput;
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
    private BoardSide boardSide = new BoardSide();

    private ActionsInput actionsInput;
    private int playerIdx;
    @Setter
    private int mana = 1;
    private int round = 0;
    @Setter
    private int player1Idx;
    @Setter
    private int player2Idx;

    public Player(int playerIdx, ArrayList<ArrayList<CardInput>> decks) {
        this.playerIdx = playerIdx;
        this.decks = new ArrayList<>();
        for (ArrayList<CardInput> deck : decks) {
            this.decks.add(new Deck(deck));
        }
        this.mana = 0;
    }

    public void setup(){
        this.hand = new Hand();
        this.mana = 0;
        // TODO and mana etc
    }

    public void selectDeck(int index, long seed) {
        this.currentDeck = decks.get(index).copy();
        this.currentDeck.shuffle(seed);
    }

    public void nextRound(int round) {
        if (!currentDeck.getMinions().isEmpty()) {
            this.hand.getMinions().add(this.currentDeck.getMinions().remove(0));
        }
        this.mana += Math.min(round, 10);
    }

    @Getter
    public static class BoardSide{
        private Row frontRow;
        private Row backRow;

        public BoardSide(){
            this.frontRow = new Row();
            this.backRow = new Row();
        }

        @Getter
        public static class Row{
            private List<Minion> minions = new ArrayList<>();
        }
    }

}