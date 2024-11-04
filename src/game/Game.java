package game;

import cards.minion.Minion;
import lombok.Getter;

@Getter
public class Game {

    private int turn = 1;

    private Player player1;
    private Player player2;

    private Minion[][] board = new Minion[4][5]; // [x][y]

    private int round= 1;

    public Game(Player player1, Player player2,int player1DeckIndex,int player2DeckIndex , long seed){
        this.player1 = player1;
        this.player2 = player2;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 5; y++) {
                board[x][y] = null;
            }
        }

        this.player1.selectDeck(player1DeckIndex, seed);
        this.player2.selectDeck(player2DeckIndex, seed);

        nextRound();
    }

    public void nextRound(){
        player1.nextRound(this.round);
        player2.nextRound(this.round);

        this.round+=1;
    }

    public Player getPlayerTurn(){
        if(this.round==1){
            return player1;
        }
        else if(this.round==2){
            return player2;
        }
        return null;
    }
}