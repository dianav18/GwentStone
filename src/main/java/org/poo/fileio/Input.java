package org.poo.fileio;

import java.util.ArrayList;

/**
 * The type Input.
 */
public final class Input {
    private DecksInput playerOneDecks;
    private DecksInput playerTwoDecks;
    private ArrayList<GameInput> games;

    /**
     * Instantiates a new Input.
     */
    public Input() {
    }

    /**
     * Gets games.
     *
     * @return the games
     */
    public ArrayList<GameInput> getGames() {
        return games;
    }

    /**
     * Sets games.
     *
     * @param games the games
     */
    public void setGames(final ArrayList<GameInput> games) {
        this.games = games;
    }

    /**
     * Gets player one decks.
     *
     * @return the player one decks
     */
    public DecksInput getPlayerOneDecks() {
        return playerOneDecks;
    }

    /**
     * Sets player one decks.
     *
     * @param playerOneDecks the player one decks
     */
    public void setPlayerOneDecks(final DecksInput playerOneDecks) {
        this.playerOneDecks = playerOneDecks;
    }

    /**
     * Gets player two decks.
     *
     * @return the player two decks
     */
    public DecksInput getPlayerTwoDecks() {
        return playerTwoDecks;
    }

    /**
     * Sets player two decks.
     *
     * @param playerTwoDecks the player two decks
     */
    public void setPlayerTwoDecks(final DecksInput playerTwoDecks) {
        this.playerTwoDecks = playerTwoDecks;
    }

    @Override
    public String toString() {
        return "Input{"
                + "player_one_decks="
                + playerOneDecks
                + ", player_two_decks="
                + playerTwoDecks
                +  ", games="
                + games
                +  '}';
    }
}
