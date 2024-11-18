package org.poo.game;

import org.poo.cards.hero.Hero;
import org.poo.fileio.ActionsInput;
import org.poo.fileio.CardInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Player.
 */
@Getter
public class Player {

    /**
     * The Hero input.
     */
    @Getter
    @Setter
    private CardInput heroInput;
    private final List<Deck> decks;
    private Deck currentDeck;
    private Hand hand;

    @Setter
    @Getter
    private Hero hero;
    private ActionsInput actionsInput;
    private final int playerIdx;
    @Setter
    private int mana = 1;
    private final int round = 0;
    @Setter
    private int player1Idx;
    @Setter
    private int player2Idx;
    private final int maxRounds = 10;

    /**
     * Instantiates a new Player.
     *
     * @param playerIdx the player idx
     * @param decks     the decks
     */
    public Player(final int playerIdx, final ArrayList<ArrayList<CardInput>> decks) {
        this.playerIdx = playerIdx;
        this.decks = new ArrayList<>();
        for (final ArrayList<CardInput> deck : decks) {
            this.decks.add(new Deck(deck));
        }
        this.mana = 0;
    }

    /**
     * Sets .
     */
    public void setup() {
        this.hand = new Hand();
        this.mana = 0;
    }

    /**
     * Select deck.
     *
     * @param index the index
     * @param seed  the seed
     */
    public void selectDeck(final int index, final long seed) {
        this.currentDeck = decks.get(index).copy();
        this.currentDeck.shuffle(seed);
    }

    /**
     * Next round.
     *
     * @param currentRound the round
     */
    public void nextRound(final int currentRound) {
        if (!currentDeck.getMinions().isEmpty()) {
            this.hand.getMinions().add(this.currentDeck.getMinions().removeFirst());
        }

        this.mana += Math.min(currentRound, maxRounds);
    }
}
