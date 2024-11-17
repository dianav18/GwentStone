package org.poo.fileio;

import java.util.ArrayList;

/**
 * The type Decks input.
 */
public final class DecksInput {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<CardInput>> decks;

    /**
     * Instantiates a new Decks input.
     */
    public DecksInput() {
    }

    /**
     * Gets nr cards in deck.
     *
     * @return the nr cards in deck
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     * Sets nr cards in deck.
     *
     * @param nrCardsInDeck the nr cards in deck
     */
    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    /**
     * Gets nr decks.
     *
     * @return the nr decks
     */
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     * Sets nr decks.
     *
     * @param nrDecks the nr decks
     */
    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    /**
     * Gets decks.
     *
     * @return the decks
     */
    public ArrayList<ArrayList<CardInput>> getDecks() {
        return decks;
    }

    /**
     * Sets decks.
     *
     * @param decks the decks
     */
    public void setDecks(final ArrayList<ArrayList<CardInput>> decks) {
        this.decks = decks;
    }

    @Override
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                +  ", nr_decks="
                + nrDecks
                + ", decks="
                + decks
                + '}';
    }
}
