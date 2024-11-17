package org.poo.game;

import org.poo.cards.hero.Hero;
import org.poo.cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.actions.PlaceCard;
import lombok.Getter;
import org.poo.cards.minion.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game session between two players.
 * This class handles game initialization, game logic, and player interactions,
 * including managing the board, player actions, and game state.
 */
@Getter
public class Game {

    private int startingTurn = 1;
    private int turn = 1;
    private final Player player1;
    private final Player player2;

    private int round = 1;

    private final int numOfRows = 4;
    private final int numOfCols = 5;

    private final Minion[][] board = new Minion[numOfRows][numOfCols];

    private final Hero player1Hero;
    private final Hero player2Hero;

    /**
     * The constant endedGames.
     */
    public static int endedGames = 0;
    /**
     * The constant playerOneWins.
     */
    public static int playerOneWins = 0;
    /**
     * The constant playerTwoWins.
     */
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
    public Game(final Player player1, final Player player2, final int player1DeckIndex,
                final int player2DeckIndex, final long seed, final int startingTurn,
                final Hero player1Hero, final Hero player2Hero) {
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
    /**
     * Generates the output structure for a "cardUsesAttack" command.
     *
     * @param xAttacked            The row position of the attacked card.
     * @param yAttacked            The column position of the attacked card.
     * @param xAttacker            The row position of the attacker card.
     * @param yAttacker            The column position of the attacker card.
     * @param objectMapper         The ObjectMapper used for creating JSON objects.
     * @param attackerCardDetails  The ObjectNode to store details about the attacker card.
     * @param resultNode           The ObjectNode to store the complete result, including
     *                             attacker and attacked card details.
     */

    private static void cardUsesAttackOutput(final int xAttacked, final int yAttacked,
                                             final int xAttacker, final int yAttacker,
                                             final ObjectMapper objectMapper,
                                             final ObjectNode attackerCardDetails,
                                             final ObjectNode resultNode) {
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
     * Implements the logic for the next round.
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
     * Implements the logic for the end of the turn.
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
            default:
                break;
        }

        for (final Minion minion : board[index]) {
            if (minion == null) {
                continue;
            }
            minion.setFrozen(false);
        }
        for (final Minion minion : board[index + 1]) {
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
     * Places a card from the player's hand onto the table.
     * This method attempts to place a card from the player's hand onto the appropriate
     * row of the table, based on the card's row position and the game rules. It
     * validates conditions such as sufficient mana
     * and available space on the row. If any condition is not met, an error is added
     * to the output.
     *
     * @param cardIndex    The index of the card in the player's hand to be placed on the table.
     * @param objectMapper The ObjectMapper used for creating JSON error messages.
     * @param output       The ArrayNode where any error messages or results will be added.
     */
    public void placeCard(final int cardIndex, final ObjectMapper objectMapper,
                          final ArrayNode output) {
        final Player player = getPlayerTurn();
        final List<Minion> handMinions = player.getHand().getMinions();

        if (cardIndex >= 0 && cardIndex < handMinions.size()) {
            final Minion minion = handMinions.get(cardIndex);

            if (minion.getMana() > player.getMana()) {
                output.add(PlaceCard.placeCardError(objectMapper,
                        "Not enough mana to place card on table.",
                        cardIndex));
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

            output.add(PlaceCard.placeCardError(objectMapper,
                    "Cannot place card on table since row is full.", cardIndex));
        }
    }

    /**
     * Retrieves all the cards currently on the table.
     * This method iterates through the game board and collects all the non-null cards
     * from each row. The cards are grouped into lists corresponding to each row on the table.
     *
     * @return A list of lists, where each inner list represents a row of cards on the table.
     * Each inner list contains the Minion objects currently present on that row.
     * Empty rows will be represented by empty lists.
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
     * Retrieves the card located at a specific position on the game board.
     * This method checks the specified position on the game board to determine if a card exists
     * there. If a card is found, its details (mana, attack damage, health, description,
     * colors, and name)
     * are added to the output. If no card is found, an appropriate message is added instead.
     *
     * @param x            The row index of the card's position on the board (0-based).
     * @param y            The column index of the card's position on the board (0-based).
     * @param objectMapper The ObjectMapper used for creating JSON objects.
     * @param output       The ArrayNode where the result (card details or an error message)
     *                     will be appended.
     */
    public void getCardAtPosition(final int x, final int y,
                                  final ObjectMapper objectMapper,
                                  final ArrayNode output) {
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
     * Handles the logic for a card attacking another card on the game board.
     *
     * @param xAttacked    the row index of the card being attacked.
     * @param yAttacked    the column index of the card being attacked.
     * @param xAttacker    the row index of the attacking card.
     * @param yAttacker    the column index of the attacking card.
     * @param objectMapper the ObjectMapper used for creating JSON objects.
     * @param output       the ArrayNode to which the result of the operation is added.
     */
    public void cardUsesAttack(final int xAttacked, final int yAttacked,
                               final int xAttacker, final int yAttacker,
                               final ObjectMapper objectMapper,
                               final ArrayNode output) {
        final ObjectNode resultNode = objectMapper.createObjectNode();

        final Minion attackedCard = board[xAttacked][yAttacked];
        final Minion attackerCard = board[xAttacker][yAttacker];

        if (attackerCard == null || attackedCard == null) {
            return;
        }
        int isAttackedCardEnemy = 0;

        if (getPlayerTurn() == player1 && (xAttacked == 0 || xAttacked == 1)) {
            isAttackedCardEnemy = 1;
        } else if (getPlayerTurn() == player2 && (xAttacked == 2 || xAttacked == 3)) {
            isAttackedCardEnemy = 1;
        }

        if (isAttackedCardEnemy == 0) {
            final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker,
                    objectMapper, attackerCardDetails, resultNode);
            resultNode.put("error", "Attacked card does not belong to the enemy.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.hasAttacked()) {
            final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker,
                    objectMapper, attackerCardDetails, resultNode);

            resultNode.put("error", "Attacker card has already attacked this turn.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.isFrozen()) {
            final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker,
                    objectMapper, attackerCardDetails, resultNode);

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
            cardUsesAttackOutput(xAttacked, yAttacked, xAttacker, yAttacker,
                    objectMapper, attackerCardDetails, resultNode);

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
     * Executes the ability of a card on the game board.
     * Validates the action based on the game rules and performs the ability effect.
     * Handles both ally-targeting and enemy-targeting abilities, as well as tank prioritization.
     *
     * @param xAttacked    the row index of the card being targeted by the ability.
     * @param yAttacked    the column index of the card being targeted by the ability.
     * @param xAttacker    the row index of the card using the ability.
     * @param yAttacker    the column index of the card using the ability.
     * @param objectMapper the ObjectMapper used for creating JSON objects.
     * @param output       the ArrayNode to which the result of the operation is added.
     */
    public void cardUsesAbility(final int xAttacked, final int yAttacked,
                                final int xAttacker, final int yAttacker,
                                final ObjectMapper objectMapper,
                                final ArrayNode output) {
        final ObjectNode resultNode = objectMapper.createObjectNode();

        final Minion attackerCard = board[xAttacker][yAttacker];
        final Minion attackedCard = board[xAttacked][yAttacked];

        if (attackerCard == null || attackedCard == null) {
            return;
        }

        if (attackerCard.isFrozen()) {
            cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker, yAttacker,
                    objectMapper, resultNode);
            resultNode.put("error", "Attacker card is frozen.");
            output.add(resultNode);
            return;
        }

        if (attackerCard.hasAttacked()) {
            cardUsesAbilityOutput(xAttacked, yAttacked, xAttacker, yAttacker,
                    objectMapper, resultNode);
            resultNode.put("error", "Attacker card has already attacked this turn.");
            output.add(resultNode);
            return;
        }

        attackerCard.useAbility(xAttacked, yAttacked, xAttacker,
                yAttacker,objectMapper, output, this, resultNode);

    }

    /**
     * Handles the general error output format for cardUsesAbility.
     * @param xAttacked    the x-coordinate (row index) of the card being targeted by the ability.
     * @param yAttacked    the y-coordinate (column index) of the card being targeted by
     *                     the ability.
     * @param xAttacker    the x-coordinate (row index) of the card using the ability.
     * @param yAttacker    the y-coordinate (column index) of the card using the ability.
     * @param objectMapper the ObjectMapper used for creating JSON objects.
     * @param resultNode   the ArrayNode to which the result of the operation is added.
     */
    private void cardUsesAbilityOutput(final int xAttacked, final int yAttacked,
                                       final int xAttacker, final int yAttacker,
                                       final ObjectMapper objectMapper,
                                       final ObjectNode resultNode) {
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
     * Executes an attack from a minion on the game board against the opposing hero.
     * Validates the attack based on the game rules, including checking for frozen status,
     * prior attacks during the turn, and the presence of tank minions that must be attacked first.
     * Updates the hero's health and tracks game-end conditions if the hero's health drops to zero.
     *
     * @param xAttacker    the x-coordinate (row index) of the attacking minion.
     * @param yAttacker    the y-coordinate (column index) of the attacking minion.
     * @param attackedHero the hero being attacked.
     * @param objectMapper the ObjectMapper used for creating JSON objects.
     * @param output       the ArrayNode to which the result of the operation is added.
     */
    public void useAttackHero(final int xAttacker, final int yAttacker,
                              final Hero attackedHero,
                              final ObjectMapper objectMapper,
                              final ArrayNode output) {

        final ObjectNode resultNode = objectMapper.createObjectNode();

        final Minion attackerCard = board[xAttacker][yAttacker];

        if (attackerCard == null) {
            return;
        }
        final Player attackerPlayer;

        if (xAttacker == 0 || xAttacker == 1) {
            attackerPlayer = player2;
        } else {
            attackerPlayer = player1;
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

    /**
     * Handles the generic error output for useAttackHero.
     * @param xAttacker    the x-coordinate (row index) of the attacking minion.
     * @param yAttacker    the y-coordinate (column index) of the attacking minion.
     * @param objectMapper the ObjectMapper used for creating JSON objects.
     * @param resultNode   the ArrayNode to which the result of the operation is added.
     */
    private void useAttackHeroOutput(final int xAttacker, final int yAttacker,
                                     final ObjectMapper objectMapper,
                                     final ObjectNode resultNode) {
        final ObjectNode attackerCardDetails = objectMapper.createObjectNode();
        attackerCardDetails.put("x", xAttacker);
        attackerCardDetails.put("y", yAttacker);

        resultNode.set("cardAttacker", attackerCardDetails);

        resultNode.put("command", "useAttackHero");
    }

    /**
     * Executes the hero's special ability on a specified row of the game board.
     * Validates the ability's usage based on game rules, including mana availability,
     * prior usage in the turn, and the target row's validity for the hero's type.
     * Updates the game state, including hero's attack status and player's mana.
     *
     * @param affectedRow  the index of the row affected by the hero's ability.
     * @param objectMapper the ObjectMapper used for creating JSON objects.
     * @param output       the ArrayNode to which the result of the operation is added.
     */
    public void useHeroAbility(final int affectedRow,
                               final ObjectMapper objectMapper,
                               final ArrayNode output) {
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

        hero.useAbility(this, player, affectedRow, resultNode, output);
    }

    /**
     * Verifies if the given row belongs to the enemy player.
     *
     * @param player the player.
     * @param row    the row.
     * @return the boolean.
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
     * Verifies if the given row belongs to the given player.
     *
     * @param player the player.
     * @param row    the row.
     * @return the boolean.
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
