package game;

import cards.hero.Hero;
import fileio.StartGameInput;
import lombok.Getter;

/**
 * The type Start game.
 */
public class StartGame {
    @Getter
    private final Player playerOne;
    @Getter
    private final Player playerTwo;
    @Getter
    private final Hero playerOneHero;
    @Getter
    private final Hero playerTwoHero;
    @Getter
    private final int startingPlayer;
    private final int playerOneDeckIdx;
    private final int playerTwoDeckIdx;
    private final int shuffleSeed;

    /**
     * Instantiates a new Start game.
     *
     * @param startGameInput the start game input
     * @param playerOne      the player one
     * @param playerTwo      the player two
     */
    public StartGame(final StartGameInput startGameInput,
                     final Player playerOne, final Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.startingPlayer = startGameInput.getStartingPlayer();
        this.playerOneDeckIdx = startGameInput.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = startGameInput.getPlayerTwoDeckIdx();
        this.shuffleSeed = startGameInput.getShuffleSeed();

        this.playerOneHero = Hero.create(startGameInput.getPlayerOneHero());
        this.playerTwoHero = Hero.create(startGameInput.getPlayerTwoHero());
    }
}
