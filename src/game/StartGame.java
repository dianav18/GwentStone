package game;

import fileio.StartGameInput;
import fileio.CardInput;
import cards.hero.Hero;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StartGame {
    @Getter
    private Player playerOne;
    @Getter
    private Player playerTwo;
    @Getter
    private Hero playerOneHero;
    @Getter
    private Hero playerTwoHero;
    @Getter
    private int startingPlayer;
    private int playerOneDeckIdx;
    private int playerTwoDeckIdx;
    private int shuffleSeed;

    public StartGame(StartGameInput startGameInput, Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.startingPlayer = startGameInput.getStartingPlayer();
        this.playerOneDeckIdx = startGameInput.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = startGameInput.getPlayerTwoDeckIdx();
        this.shuffleSeed = startGameInput.getShuffleSeed();

        this.playerOneHero = new Hero(startGameInput.getPlayerOneHero());
        this.playerTwoHero = new Hero(startGameInput.getPlayerTwoHero());

        setDeckForPlayer(playerOne, playerOneDeckIdx, shuffleSeed);
        setDeckForPlayer(playerTwo, playerTwoDeckIdx, shuffleSeed);
    }

    private void setDeckForPlayer(Player player, int deckIndex, int shuffleSeed) {
//        ArrayList<CardInput> selectedDeck = new ArrayList<>(player.getDecks().get(deckIndex));
//
//        Random random = new Random(shuffleSeed);
//        Collections.shuffle(selectedDeck, random);
//
//        player.setCurrentDeck(selectedDeck);
    }

}
