package game;

import cards.hero.*;
import cards.minion.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import debug.PlaceCard;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Game.
 */
@Getter
public class Game {

    private int startingTurn = 1;
    private int turn = 1;
    private final Player player1;
    private final Player player2;

    private int round = 1;

    private final Minion[][] board = new Minion[4][5];

    private final Hero player1Hero;
    private final Hero player2Hero;

    public static int endedGames = 0;
    public static int playerOneWins = 0;
    public static int playerTwoWins = 0;

    private boolean gameEnded = false;

    /**
     * Instantiates a new Game.
     *
     * @param player1          the player 1
     * @param player2          the player 2
     * @param player1DeckIndex the player 1 deck index
     * @param player2DeckIndex the player 2 deck index
     * @param seed             the seed
     * @param startingTurn     the starting turn
     * @param player1Hero      the player 1 hero
     * @param player2Hero      the player 2 hero
     */
    public Game(final Player player1, final Player player2, final int player1DeckIndex, final int player2DeckIndex, final long seed, final int startingTurn, final Hero player1Hero, final Hero player2Hero) {
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

    private static void cardUsesAttackOutput(final int xAttacked, final int yAttacked, final int xAttacker, final int yAttacker, final ObjectMapper objectMapper, final ObjectNode attackerCardDetails, final ObjectNode resultNode) {
        attackerCardDetails.put("x", xAttacker);
        attackerCardDetails.put("y", yAttacker);
        final ObjectNode attackedCardDetails = objectMapper.createObjectNode();
        attackedCardDetails.put("x", xAttacked);
        attackedCardDetails.put("y", yAttacked);

        resultNode.set("cardAttacked", attackedCardDetails);
        resultNode.set("cardAttacker", attackerCardDetails);

        resultNode.put("command", "cardUsesAttack");
    }

    /**
     * Next round.
     */
    public void nextRound() {
        if (gameEnded) {
            return;
        }
        player1.nextRound(this.round);
        player2.nextRound(this.round);

        for (final Minion[] row : board) {
            for (final Minion minion : row) {
                if (minion == null) {
                    continue;
                }
                minion.setHasAttacked(false);
            }
        }

        this.round += 1;
        player1Hero.setHasAttacked(false);
        player2Hero.setHasAttacked(false);
    }

    /**
     * End turn.
     */
    public void endTurn() {
        if (gameEnded) {
            return;
        }

        int index = 0;

        switch (this.turn) {
            case 2:
                index = 0;
                break;
            case 1:
                index = 2;
                break;
        }

        for (final Minion minion : board[index]) {
            if (minion == null) {
                continue;
            }
            minion.setFrozen(false);
        }
        for (final Minion minion : board[index+1]) {
            if (minion == null) {
                continue;
            }
            minion.setFrozen(false);
        }

        this.turn = getNextTurn();

        if (turn == startingTurn) {
            nextRound();
        }
    }

    /**
     * Gets player turn.
     *
     * @return the player turn
     */
    public Player getPlayerTurn() {
        return getPlayerTurn(this.turn);
    }

    /**
     * Gets player turn.
     *
     * @param playerIndex the player index
     * @return the player turn
     */
    public Player getPlayerTurn(final int playerIndex) {
        return (playerIndex == 1) ? player1 : player2;
    }

    private int getNextTurn() {
        return (turn == 1) ? 2 : 1;
    }

    /**
     * Place card.
     *
     * @param cardIndex    the card index
     * @param objectMapper the object mapper
     * @param output       the output
     */
    public void placeCard(final int cardIndex, final ObjectMapper objectMapper, final ArrayNode output) {
        final Player player = getPlayerTurn();
        final List<Minion> handMinions = player.getHand().getMinions();

        if (cardIndex >= 0 && cardIndex < handMinions.size()) {
            final Minion minion = handMinions.get(cardIndex);

            if (minion.getMana() > player.getMana()) {
                output.add(PlaceCard.placeCardError(objectMapper, "Not enough mana to place card on table.", cardIndex));
                return;
            }

            final int row;

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

    /**
     * Gets cards on table.
     *
     * @return the cards on table
     */
    public List<List<Minion>> getCardsOnTable() {
        final List<List<Minion>> cardsOnTable = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            final List<Minion> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (board[i][j] != null) {
                    row.add(board[i][j]);
                }
            }
            cardsOnTable.add(row);
        }
        return cardsOnTable;
    }

    /**
     * Gets card at position.
     *
     * @param x            the x
     * @param y            the y
     * @param objectMapper the object mapper
     * @param output       the output
     */
    public void getCardAtPosition(final int x, final int y, final ObjectMapper objectMapper, final ArrayNode output) {
        final ObjectNode resultNode = objectMapper.createObjectNode();
        resultNode.put("command", "getCardAtPosition");
        resultNode.put("x", x);
        resultNode.put("y", y);

        if (x < 4 && y < 5 && board[x][y] != null) {
            final Minion minion = board[x][y];

            final ObjectNode cardDetails = objectMapper.createObjectNode();
            cardDetails.put("mana", minion.getMana());
            cardDetails.put("attackDamage", minion.getAttackDamage());
            cardDetails.put("health", minion.getHealth());
            cardDetails.put("description", minion.getDescription());
            cardDetails.put("name", minion.getName());

            final ArrayNode colorsArray = objectMapper.createArrayNode();
            for (final String color : minion.getColors()) {
                colorsArray.add(color);
            }
            cardDetails.set("colors", colorsArray);

            resultNode.set("output", cardDetails);
        } else {
            resultNode.put("output", "No card available at that position.");
        }

        output.add(resultNode);
    }

    /**
     * Card uses attack.
     *
     * @param xAttacked    the x attacked
     * @param yAttacked    the y attacked
     * @param xAttacker    the x attacker
     * @param yAttacker    the y attacker
     * @param objectMapper the object mapper
     * @param output       the output
     */
    public void cardUsesAttack(final int xAttacked, final int yAttacked, final int xAttacker, final int yAttacker, final ObjectMapper objectMapper, final ArrayNode output) {
        final ObjectNode resultNode = objectMapper.createObjectNode();

        final Minion attackedCard = board[xAttacked][yAttacked];
        final Minion attackerCard = board[xAttacker][yAttacker];

        if (attackerCard == null || attackedCard == null)
            return;

        int isAttackedCardEnemy = 0;

        if (getPlayerTurn() == player1 && (xAttacked == 0 || xAttacked == 1)) {
            isAttackedCardEnemy = 1;
        } else if (getPlayerTurn() == player2 && (xAttacked == 2 || xAttacked == 3)) {
            isAttackedCardEnemy = 1;
        }

        if (isAttackedCardEnemy == 0) {
            final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, attackerCardDetails, resultNode);
            resultNode.put("error", "Attacked card does not belong to the enemy.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.hasAttacked()) {
            final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, attackerCardDetails, resultNode);

            resultNode.put("error", "Attacker card has already attacked this turn.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.isFrozen()) {
            final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, attackerCardDetails, resultNode);

            resultNode.put("error", "Attacker card is frozen.");
            output.add(resultNode);
            return;
        }

        boolean enemyHasTank = false;
        final int enemyFrontRow;

        if (getPlayerTurn() == player1) {
            enemyFrontRow = 1;
        } else {
            enemyFrontRow = 2;
        }

        for (final Minion minion : board[enemyFrontRow]) {
            if (minion != null && minion.isTank()) {
                enemyHasTank = true;
                break;
            }
        }

        if (enemyHasTank && !attackedCard.isTank()) {
            final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker, objectMapper, attackerCardDetails, resultNode);

            resultNode.put("error", "Attacked card is not of type 'Tank'.");
            output.add(resultNode);
            return;
        }

        attackedCard.setHealth(attackedCard.getHealth() - attackerCard.getAttackDamage());
        attackerCard.setHasAttacked(true);

        if (attackedCard.getHealth() <= 0) {
            board[xAttacked][yAttacked] = null;

            for (int i = yAttacked; i < board[xAttacked].length - 1; i++) {
                board[xAttacked][i] = board[xAttacked][i + 1];
            }

            board[xAttacked][board[xAttacked].length - 1] = null;
        }
        output.add(resultNode);
    }

    /**
     * Card uses ability.
     *
     * @param xAttacked    the x attacked
     * @param yAttacked    the y attacked
     * @param xAttacker    the x attacker
     * @param yAttacker    the y attacker
     * @param objectMapper the object mapper
     * @param output       the output
     */
    public void cardUsesAbility(final int xAttacked, final int yAttacked, final int xAttacker, final int yAttacker, final ObjectMapper objectMapper, final ArrayNode output) {
        final ObjectNode resultNode = objectMapper.createObjectNode();

        final Minion attackerCard = board[xAttacker][yAttacker];
        final Minion attackedCard = board[xAttacked][yAttacked];

        if (attackerCard == null || attackedCard == null) {
            return;
        }

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
            ((Disciple) attackerCard).godsPlan(attackedCard);
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
            final int enemyFrontRow;

            if (getPlayerTurn() == player1) {
                enemyFrontRow = 1;
            } else {
                enemyFrontRow = 2;
            }

            for (final Minion minion : board[enemyFrontRow]) {
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

    private void cardUsesAbilityOutput(final int xAttacked, final int yAttacked, final int xAttacker, final int yAttacker, final ObjectMapper objectMapper, final ObjectNode resultNode) {
        final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
        attackerCardDetails.put("x", xAttacker);
        attackerCardDetails.put("y", yAttacker);
        final ObjectNode attackedCardDetails = objectMapper.createObjectNode();
        attackedCardDetails.put("x", xAttacked);
        attackedCardDetails.put("y", yAttacked);

        resultNode.set("cardAttacked", attackedCardDetails);
        resultNode.set("cardAttacker", attackerCardDetails);

        resultNode.put("command", "cardUsesAbility");
    }

    /**
     * Use attack hero.
     *
     * @param xAttacker    the x attacker
     * @param yAttacker    the y attacker
     * @param attackedHero the attacked hero
     * @param objectMapper the object mapper
     * @param output       the output
     */
    public void useAttackHero(final int xAttacker, final int yAttacker, final Hero attackedHero, final ObjectMapper objectMapper, final ArrayNode output) {

        final ObjectNode resultNode = objectMapper.createObjectNode();

        final Minion attackerCard = board[xAttacker][yAttacker];

        if (attackerCard == null) {
            return;
        }
        final Player attackerPlayer;
        if (xAttacker == 0 || xAttacker == 1)
            attackerPlayer = player2;
        else
            attackerPlayer = player1;


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
        final int enemyFrontRow;

        if (getPlayerTurn() == player1) {
            enemyFrontRow = 1;
        } else {
            enemyFrontRow = 2;
        }
        for (final Minion minion : board[enemyFrontRow]) {
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
            final ObjectNode gameEndNode = objectMapper.createObjectNode();
            if (attackerPlayer == player2) {
                playerTwoWins += 1;
                gameEnded = true;
                gameEndNode.put("gameEnded", "Player two killed the enemy hero.");
                output.add(gameEndNode);

            } else if (attackerPlayer == player1) {
                playerOneWins += 1;
                gameEnded = true;
                gameEndNode.put("gameEnded", "Player one killed the enemy hero.");
                output.add(gameEndNode);
            }
            endedGames += 1;
        }
    }

    private void useAttackHeroOutput(final int xAttacker, final int yAttacker, final ObjectMapper objectMapper, final ObjectNode resultNode) {
        final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
        attackerCardDetails.put("x", xAttacker);
        attackerCardDetails.put("y", yAttacker);

        resultNode.set("cardAttacker", attackerCardDetails);

        resultNode.put("command", "useAttackHero");
    }

    /**
     * Use hero ability.
     *
     * @param affectedRow  the affected row
     * @param objectMapper the object mapper
     * @param output       the output
     */
    public void useHeroAbility(final int affectedRow, final ObjectMapper objectMapper, final ArrayNode output) {
        final Hero hero;
        final Player player;

        if (getPlayerTurn() == player1) {
            player = player1;
            hero = player1Hero;
        } else {
            player = player2;
            hero = player2Hero;
        }

        final ObjectNode resultNode = objectMapper.createObjectNode();
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
            return;
        }

        if (hero instanceof LordRoyce || hero instanceof EmpressThorina) {
            if (!isEnemyRow(player, affectedRow)) {
                resultNode.put("command", "useHeroAbility");
                resultNode.put("affectedRow", affectedRow);
                resultNode.put("error", "Selected row does not belong to the enemy.");
                output.add(resultNode);
                return;
            }

            switch (hero.getName()) {
                case "Lord Royce":
                    ((LordRoyce) hero).subZero(board, affectedRow);
                    break;
                case "Empress Thorina":
                    EmpressThorina.lowBlow(board, affectedRow);
                    break;
            }
            player.setMana(player.getMana() - hero.getMana());
            hero.setHasAttacked(true);
        }

        if (hero instanceof GeneralKocioraw || hero instanceof KingMudface) {
            if (!isPlayerRow(player, affectedRow)) {
                resultNode.put("command", "useHeroAbility");
                resultNode.put("affectedRow", affectedRow);
                resultNode.put("error", "Selected row does not belong to the current player.");
                output.add(resultNode);
                return;
            }
            switch (hero.getName()) {
                case "General Kocioraw":
                    GeneralKocioraw.bloodThirst(board, affectedRow);
                    break;
                case "King Mudface":
                    ((KingMudface) hero).earthBorn(board, affectedRow);
                    break;
            }
            player.setMana(player.getMana() - hero.getMana());
            hero.setHasAttacked(true);
        }
    }

    /**
     * Is enemy row boolean.
     *
     * @param player the player
     * @param row    the row
     * @return the boolean
     */
    public boolean isEnemyRow(final Player player, final int row) {
        if (player.getPlayerIdx() == 1) {
            return row == 0 || row == 1;
        } else if (player.getPlayerIdx() == 2) {
            return row == 2 || row == 3;
        }
        return false;
    }

    /**
     * Is player row boolean.
     *
     * @param player the player
     * @param row    the row
     * @return the boolean
     */
    public boolean isPlayerRow(final Player player, final int row) {
        if (player.getPlayerIdx() == 1) {
            return row == 2 || row == 3;
        } else if (player.getPlayerIdx() == 2) {
            return row == 0 || row == 1;
        }
        return false;
    }
}
