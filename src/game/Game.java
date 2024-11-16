package game;

import cards.hero.*;
import cards.minion.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import debug.PlaceCard;
import fileio.ActionsInput;
import fileio.CardInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
public class Game {

    private int startingTurn = 1;
    private int turn = 1;
    private Player player1;
    private Player player2;

    private int round = 1;

    private Minion[][] board = new Minion[4][5];

    private Hero player1Hero;
    private Hero player2Hero;

    public Game(Player player1, Player player2, int player1DeckIndex, int player2DeckIndex, long seed, int startingTurn, Hero player1Hero, Hero player2Hero) {
        this.player1 = player1;
        this.player2 = player2;

        this.player1.selectDeck(player1DeckIndex, seed);
        this.player2.selectDeck(player2DeckIndex, seed);

        this.turn = startingTurn;
        this.startingTurn = startingTurn;

        this.player1Hero = player1Hero;
        this.player2Hero = player2Hero;

        nextRound();
    }

    public void nextRound() {
        //System.out.println("Advancing a round");
        player1.nextRound(this.round);
        player2.nextRound(this.round);

        for (Minion[] row : board) {
            for (Minion minion : row) {
                if (minion == null) {
                    continue;
                }
                minion.setHasAttacked(false);
                minion.setFrozen(false);
            }
        }

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

            int row;

            if (player == player1) {
                if (minion.getRowPosition() == Row.BACK) {
                    row = 3;
                } else {
                    row = 2;
                }
            } else {
                if (minion.getRowPosition() == Row.BACK) {
                    row = 0;
                } else {
                    row = 1;
                }
            }

            for (int col = 0; col < 5; col++) {
                if (board[row][col] == null) {
                    board[row][col] = minion;
                    handMinions.remove(cardIndex);
                    player.setMana(player.getMana() - minion.getMana());
                    return;
                }
            }

            output.add(PlaceCard.placeCardError(objectMapper, "Cannot place card on table since row is full.", cardIndex));
        }
    }


    public List<List<Minion>> getCardsOnTable() {
        List<List<Minion>> cardsOnTable = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            List<Minion> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (board[i][j] != null) {
                    row.add(board[i][j]);
                }
            }
            cardsOnTable.add(row);
        }
        return cardsOnTable;
    }


    public void getCardAtPosition(int x, int y, ObjectMapper objectMapper, ArrayNode output) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getCardAtPosition");
        resultNode.put("x", x);
        resultNode.put("y", y);

        if (x < 4 && y < 5 && board[x][y] != null) {
            Minion minion = board[x][y];

            ObjectNode cardDetails = objectMapper.createObjectNode();
            cardDetails.put("mana", minion.getMana());
            cardDetails.put("attackDamage", minion.getAttackDamage());
            cardDetails.put("health", minion.getHealth());
            cardDetails.put("description", minion.getDescription());
            cardDetails.put("name", minion.getName());

            ArrayNode colorsArray = objectMapper.createArrayNode();
            for (String color : minion.getColors()) {
                colorsArray.add(color);
            }
            cardDetails.set("colors", colorsArray);

            resultNode.set("output", cardDetails);
        } else {
            resultNode.put("output", "No card available at that position.");
        }

        output.add(resultNode);
    }


    public void cardUsesAttack(int xAttacked, int yAttacked, int xAttacker, int yAttacker, ObjectMapper objectMapper, ArrayNode output) {
        ObjectNode resultNode = objectMapper.createObjectNode();

        Minion attackedCard = board[xAttacked][yAttacked];
        Minion attackerCard = board[xAttacker][yAttacker];

        if (attackerCard == null || attackedCard == null)
            return;

        int isAttackedCardEnemy = 0;

        if (getPlayerTurn() == player1 && (xAttacked == 0 || xAttacked == 1)) {
            isAttackedCardEnemy = 1;
        } else if (getPlayerTurn() == player2 && (xAttacked == 2 || xAttacked == 3)) {
            isAttackedCardEnemy = 1;
        }

        // TODO You can make these a function
        if (isAttackedCardEnemy == 0) {
            ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            attackerCardDetails.put("x", xAttacker);
            attackerCardDetails.put("y", yAttacker);
            ObjectNode attackedCardDetails = objectMapper.createObjectNode();
            attackedCardDetails.put("x", xAttacked);
            attackedCardDetails.put("y", yAttacked);

            resultNode.set("cardAttacked", attackedCardDetails);
            resultNode.set("cardAttacker", attackerCardDetails);

            resultNode.put("command", "cardUsesAttack");
            resultNode.put("error", "Attacked card does not belong to the enemy.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.hasAttacked()) {
            ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            attackerCardDetails.put("x", xAttacker);
            attackerCardDetails.put("y", yAttacker);
            ObjectNode attackedCardDetails = objectMapper.createObjectNode();
            attackedCardDetails.put("x", xAttacked);
            attackedCardDetails.put("y", yAttacked);

            resultNode.set("cardAttacked", attackedCardDetails);
            resultNode.set("cardAttacker", attackerCardDetails);

            resultNode.put("command", "cardUsesAttack");
            resultNode.put("error", "Attacker card has already attacked this turn.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.isFrozen()) {
            ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            attackerCardDetails.put("x", xAttacker);
            attackerCardDetails.put("y", yAttacker);
            ObjectNode attackedCardDetails = objectMapper.createObjectNode();
            attackedCardDetails.put("x", xAttacked);
            attackedCardDetails.put("y", yAttacked);

            resultNode.set("cardAttacked", attackedCardDetails);
            resultNode.set("cardAttacker", attackerCardDetails);

            resultNode.put("command", "cardUsesAttack");
            resultNode.put("error", "Attacker card is frozen.");
            output.add(resultNode);
            return;
        }

        boolean enemyHasTank = false;
        int enemyFrontRow;

        if (getPlayerTurn() == player1) {
            enemyFrontRow = 1;
        } else {
            enemyFrontRow = 2;
        }

        for (Minion minion : board[enemyFrontRow]) {
            if (minion != null && minion.isTank()) {
                enemyHasTank = true;
                break;
            }
        }

        if (enemyHasTank && !attackedCard.isTank()) {
            ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            attackerCardDetails.put("x", xAttacker);
            attackerCardDetails.put("y", yAttacker);
            ObjectNode attackedCardDetails = objectMapper.createObjectNode();
            attackedCardDetails.put("x", xAttacked);
            attackedCardDetails.put("y", yAttacked);

            resultNode.set("cardAttacked", attackedCardDetails);
            resultNode.set("cardAttacker", attackerCardDetails);

            resultNode.put("command", "cardUsesAttack");
            resultNode.put("error", "Attacked card is not of type 'Tank'.");
            output.add(resultNode);
            return;
        }

        attackedCard.setHealth(attackedCard.getHealth() - attackerCard.getAttackDamage());
        attackerCard.setHasAttacked(true);

        if (attackedCard.getHealth() <= 0) {
            board[yAttacked][xAttacked] = null;
        }
        output.add(resultNode);
    }

    public void cardUsesAbility(int xAttacked, int yAttacked, int xAttacker, int yAttacker, ObjectMapper objectMapper, ArrayNode output) {
        ObjectNode resultNode = objectMapper.createObjectNode();

        Minion attackerCard = board[xAttacker][yAttacker];
        Minion attackedCard = board[xAttacked][yAttacked];

        if (attackerCard == null || attackedCard == null) {
            return;
        }

        //System.out.println("attackerCard class: " + attackerCard.getClass());
        // System.out.println("attackedCard class: " + attackedCard.getClass());

        if (attackerCard.isFrozen()) {
            cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacker card is frozen.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.hasAttacked()) {
            cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacker card has already attacked this turn.");
            output.add(resultNode);
            return;
        }

        if (attackerCard instanceof Disciple) {
            //System.out.println("attackerCard instanceof Disciple");

            boolean isAttackedCardAlly = false;

            if (getPlayerTurn() == player1 && (xAttacked == 2 || xAttacked == 3)) {
                isAttackedCardAlly = true;
            } else if (getPlayerTurn() == player2 && (xAttacked == 0 || xAttacked == 1)) {
                isAttackedCardAlly = true;
            }

            if (!isAttackedCardAlly) {
                cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, resultNode);
                resultNode.put("error", "Attacked card does not belong to the current player.");
                output.add(resultNode);
                return;
            }
            ((Disciple) attackerCard).godsPlan(attackedCard); //de verificat
            attackerCard.setHasAttacked(true);
        }

        if (attackerCard instanceof TheRipper || attackerCard instanceof Miraj || attackerCard instanceof TheCursedOne) {
            int isAttackedCardEnemy = 0;

            if (getPlayerTurn() == player1 && (xAttacked == 0 || xAttacked == 1)) {
                isAttackedCardEnemy = 1;
            } else if (getPlayerTurn() == player2 && (xAttacked == 2 || xAttacked == 3)) {
                isAttackedCardEnemy = 1;
            }
            if (isAttackedCardEnemy == 0) {
                cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, resultNode);
                resultNode.put("error", "Attacked card does not belong to the enemy.");
                output.add(resultNode);
                return;
            }

            boolean enemyHasTank = false;
            int enemyFrontRow;

            if (getPlayerTurn() == player1) {
                enemyFrontRow = 1;
            } else {
                enemyFrontRow = 2;
            }

            for (Minion minion : board[enemyFrontRow]) {
                if (minion != null && minion.isTank()) {
                    enemyHasTank = true;
                    break;
                }
            }

            if (enemyHasTank && !attackedCard.isTank()) {
                cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, resultNode);
                resultNode.put("error", "Attacked card is not of type 'Tank'.");
                output.add(resultNode);
                return;
            }
            switch (attackerCard.getName()) {
                case "The Ripper":
                    ((TheRipper) attackerCard).weakKnees(attackedCard);
                    break;
                case "Miraj":
                    ((Miraj) attackerCard).skyjack(attackedCard);
                    break;
                case "The Cursed One":
                    ((TheCursedOne) attackerCard).shapeShift(attackedCard);
                    break;
            }
            attackerCard.setHasAttacked(true);
        }
    }

    private void cardUsesAbilityOutput(int xAttacked, int yAttacked, int xAttacker, int yAttacker, ObjectMapper objectMapper, ObjectNode resultNode) {
        ObjectNode attackerCardDetails = objectMapper.createObjectNode();
        attackerCardDetails.put("x", xAttacker);
        attackerCardDetails.put("y", yAttacker);
        ObjectNode attackedCardDetails = objectMapper.createObjectNode();
        attackedCardDetails.put("x", xAttacked);
        attackedCardDetails.put("y", yAttacked);

        resultNode.set("cardAttacked", attackedCardDetails);
        resultNode.set("cardAttacker", attackerCardDetails);

        resultNode.put("command", "cardUsesAbility");
    }

    public void useAttackHero(int xAttacker, int yAttacker, Hero attackedHero, ObjectMapper objectMapper, ArrayNode output) {
        ObjectNode resultNode = objectMapper.createObjectNode();

        Minion attackerCard = board[xAttacker][yAttacker];

        if (attackerCard == null) {
            return;
        }

        if (attackerCard.isFrozen()) {
            useAttackHeroOutput(xAttacker, yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacker card is frozen.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.hasAttacked()) {
            useAttackHeroOutput(xAttacker, yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacker card has already attacked this turn.");
            output.add(resultNode);
            return;
        }

        boolean enemyHasTank = false;
        int enemyFrontRow;

        if (getPlayerTurn() == player1) {
            enemyFrontRow = 1;
        } else {
            enemyFrontRow = 2;
        }

        for (Minion minion : board[enemyFrontRow]) {
            if (minion != null && minion.isTank()) {
                enemyHasTank = true;
                break;
            }
        }

        if (enemyHasTank) {
            useAttackHeroOutput(xAttacker, yAttacker, objectMapper, resultNode);
            resultNode.put("error", "Attacked card is not of type 'Tank'.");
            output.add(resultNode);
            return;
        }

        attackedHero.setHealth(attackedHero.getHealth() - attackerCard.getAttackDamage());
        attackerCard.setHasAttacked(true);

        if (attackedHero.getHealth() <= 0) {
            ObjectNode gameEndNode = objectMapper.createObjectNode();
            if (getPlayerTurn() == player1) {
                gameEndNode.put("gameEnded", "Player one killed the enemy hero.");
                output.add(gameEndNode);
                return;
            } else {
                gameEndNode.put("gameEnded", "Player two killed the enemy hero.");
                output.add(gameEndNode);
                return;
            }
        }
    }

    private void useAttackHeroOutput(int xAttacker, int yAttacker, ObjectMapper objectMapper, ObjectNode resultNode) {
        ObjectNode attackerCardDetails = objectMapper.createObjectNode();
        attackerCardDetails.put("x", xAttacker);
        attackerCardDetails.put("y", yAttacker);

        resultNode.set("cardAttacker", attackerCardDetails);

        resultNode.put("command", "useAttackHero");
    }

    public void useHeroAbility(int affectedRow, Hero hero, Player player, ObjectMapper objectMapper, ArrayNode output) {
        ObjectNode resultNode = objectMapper.createObjectNode();
        if (player.getMana() < hero.getMana()) {
            resultNode.put("command", "useHeroAbility");
            resultNode.put("affectedRow", affectedRow);
            resultNode.put("error", "Not enough mana to use hero's ability.");
            output.add(resultNode);
            return;
        }

        if (hero.hasAttacked()) {
            resultNode.put("command", "useHeroAbility");
            resultNode.put("affectedRow", affectedRow);
            resultNode.put("error", "Hero has already attacked this turn.");
            output.add(resultNode);
        }

        if (hero instanceof LordRoyce || hero instanceof EmpressThorina) {
            if (!isEnemyRow(player, affectedRow)) {
                resultNode.put("command", "useHeroAbility");
                resultNode.put("affectedRow", affectedRow);
                resultNode.put("error", "Selected row does not belong to the enemy.");
                output.add(resultNode);
                return;
            }

            if (hero instanceof LordRoyce) {
                ((LordRoyce) hero).subZero(board, affectedRow);
            } else {
                ((EmpressThorina) hero).LowBlow(board, affectedRow);
            }
        }
        if (hero instanceof GeneralKocioraw || hero instanceof KingMudface) {
            if (!isCurrentPlayerRow(player, affectedRow)) {
                resultNode.put("command", "useHeroAbility");
                resultNode.put("affectedRow", affectedRow);
                resultNode.put("error", "Selected row does not belong to the current player.");
                return;
            }
            if (hero instanceof GeneralKocioraw) {
                ((GeneralKocioraw) hero).BloodThirst(board, affectedRow);
            } else {
                ((KingMudface) hero).EarthBorn(board, affectedRow);
            }
        }
        player.setMana(player.getMana() - hero.getMana());
        hero.setHasAttacked(true);
    }
    private boolean isEnemyRow(Player currentPlayer, int row) {
        if (currentPlayer.getPlayerIdx() == 1) {
            return row == 0 || row == 1;
        } else {
            return row == 2 || row == 3;
        }
    }
    private boolean isCurrentPlayerRow(Player currentPlayer, int row) {
        if (currentPlayer.getPlayerIdx() == 1) {
            return row == 2 || row == 3;
        } else {
            return row == 0 || row == 1;
        }
    }
}
