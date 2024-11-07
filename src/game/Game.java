package game;

import cards.hero.Hero;
import cards.minion.Minion;
import cards.minion.Row;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import debug.PlaceCard;
import fileio.CardInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Game {

    private int startingTurn = 1;
    private int turn = 1;
    private Player player1;
    private Player player2;

    private int round = 1;

    public Game(Player player1, Player player2, int player1DeckIndex, int player2DeckIndex, long seed, int startingTurn) {
        this.player1 = player1;
        this.player2 = player2;

        this.player1.selectDeck(player1DeckIndex, seed);
        this.player2.selectDeck(player2DeckIndex, seed);

        this.turn = startingTurn;
        this.startingTurn = startingTurn;

        nextRound();
    }

    public void nextRound() {
        player1.nextRound(this.round);
        player2.nextRound(this.round);

        this.round += 1;
    }

    public void endTurn() {
        this.turn = getNextTurn();

        if (turn == startingTurn) {
            nextRound();
        }
    }

    public Player getPlayerTurn() {
        return (this.turn == 1) ? player1 : player2;
    }

    public Player getPlayerTurn(int playerIndex) {
        return (playerIndex == 1) ? player1 : player2;
    }

    public int getActivePlayerIndex() {
        return turn;
    }

    private int getNextTurn() {
        return (turn == 1) ? 2 : 1;
    }

    public void placeCard(int cardIndex, ObjectMapper objectMapper, ArrayNode output) {
            Player player = getPlayerTurn();
            List<Minion> handMinions = player.getHand().getMinions();

            if (cardIndex >= 0 && cardIndex < handMinions.size()) {
                Minion minion = handMinions.get(cardIndex);

                if (minion.getMana() > player.getMana()) {
                    output.add(PlaceCard.placeCardError(objectMapper, "Not enough mana to place card on table.", cardIndex));
                    return;
                }

                List<Minion> targetRowMinions;
                if (minion.getRowPosition() == Row.BACK) {
                    targetRowMinions = player.getBoardSide().getBackRow().getMinions();
                } else {
                    targetRowMinions = player.getBoardSide().getFrontRow().getMinions();
                }

                if (targetRowMinions.size() >= 5) {
                    output.add(PlaceCard.placeCardError(objectMapper, "Cannot place card on table since row is full.", cardIndex));
                    return;
                }
                handMinions.remove(cardIndex);
                targetRowMinions.add(minion);
                player.setMana(player.getMana() - minion.getMana());
            }
    }

    public List<List<CardInput>> getCardsOnTable() {
        List<List<CardInput>> cardsOnTable = new ArrayList<>();

        List<Player.BoardSide.Row> rows = List.of(
                player2.getBoardSide().getBackRow(),
                player2.getBoardSide().getFrontRow(),
                player1.getBoardSide().getFrontRow(),
                player1.getBoardSide().getBackRow()
        );

        for (Player.BoardSide.Row row : rows) {
            List<CardInput> rowList = new ArrayList<>();
            for (Minion minion : row.getMinions()) {
                rowList.add(minion.toInferior());
            }
            cardsOnTable.add(rowList);
        }
        return cardsOnTable;
    }

}
