package main;

import cards.hero.Hero;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import actions.*;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;
import game.Game;
import game.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        final File directory = new File(CheckerConstants.TESTS_PATH);
        final Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            final File resultFile = new File(String.valueOf(path));
            for (final File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        try {
            for (final File file : Objects.requireNonNull(directory.listFiles())) {
                final String filepath = CheckerConstants.OUT_PATH + file.getName();
                final File out = new File(filepath);
                final boolean isCreated = out.createNewFile();
                if (isCreated) {
                    action(file.getName(), filepath);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        Checker.calculateScore();
    }

    /**
     * Action.
     *
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2)
            throws IOException {
        Game.endedGames = 0;
        Game.playerOneWins = 0;
        Game.playerTwoWins = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        final Input inputData = objectMapper.readValue(
                new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);


        final ArrayNode output = objectMapper.createArrayNode();

        for (final GameInput inputDataGame : inputData.getGames()) {
            final StartGameInput startGameInput = inputDataGame.getStartGame();
            final Player player1 = new Player(1, inputData.getPlayerOneDecks().getDecks());
            final Player player2 = new Player(2, inputData.getPlayerTwoDecks().getDecks());

            final Hero playerOneHero = Hero.create(startGameInput.getPlayerOneHero());
            final Hero playerTwoHero = Hero.create(startGameInput.getPlayerTwoHero());

            player1.setup();
            player2.setup();
            final Game game = new Game(
                    player1, player2,
                    inputDataGame.getStartGame().getPlayerOneDeckIdx(),
                    inputDataGame.getStartGame().getPlayerTwoDeckIdx(),
                    inputDataGame.getStartGame().getShuffleSeed(),
                    inputDataGame.getStartGame().getStartingPlayer(),
                    playerOneHero,
                    playerTwoHero
            );

            for (final ActionsInput action : inputDataGame.getActions()) {

                final String command = action.getCommand();

                ObjectNode newNode = null;
                switch (command) {
                    case "getPlayerDeck":
                        final int playerIdx = action.getPlayerIdx();
                        newNode = (new GetPlayerDeck(game).
                                getPlayerDeck(playerIdx));
                        break;
                    case "getPlayerHero":
                        final Hero selectedHero = action.getPlayerIdx() == 1
                                ? playerOneHero : playerTwoHero;
                        final GetPlayerHero getPlayerHeroCommand = new
                                GetPlayerHero(selectedHero, action.getPlayerIdx());
                        newNode = (getPlayerHeroCommand.getHeroData(objectMapper));
                        break;
                    case "getPlayerTurn":
                        final int activePlayerIndex = game.getTurn();
                        final GetPlayerTurn playerTurn = new GetPlayerTurn(activePlayerIndex);
                        newNode = (playerTurn.getPlayerTurn(objectMapper));
                        break;
                    case "getCardsInHand":
                        final int playerIndex = action.getPlayerIdx();
                        final Player targetPlayer = (playerIndex == 1) ? player1 : player2;
                        final GetCardsInHand getCardsInHandCommand = new
                                GetCardsInHand(targetPlayer);
                        newNode = (getCardsInHandCommand.getCardsInHand(objectMapper));
                        break;
                    case "endPlayerTurn":
                        game.endTurn();
                        break;
                    case "placeCard":
                        final int handIndex = action.getHandIdx();
                        game.placeCard(handIndex, objectMapper, output);
                        break;
                    case "getPlayerMana":
                        final int manaPlayerIdx = action.getPlayerIdx();
                        final Player manaTargetPlayer = (manaPlayerIdx == 1) ? player1 : player2;
                        final GetPlayerMana getPlayerManaCommand = new
                                GetPlayerMana(manaTargetPlayer);
                        newNode = (getPlayerManaCommand.getPlayerMana());
                        break;
                    case "getCardsOnTable":
                        output.add(new GetCardsOnTable(game).getCardsOnTable());
                        break;
                    case "getCardAtPosition":
                        final int x = action.getX();
                        final int y = action.getY();
                        final GetCardAtPosition getCardAtPositionCommand = new
                                GetCardAtPosition(game, x, y, objectMapper);
                        newNode = (getCardAtPositionCommand.getCardAtPosition());
                        break;
                    case "cardUsesAttack":
                        int xAttacker = action.getCardAttacker().getX();
                        int yAttacker = action.getCardAttacker().getY();
                        int xAttacked = action.getCardAttacked().getX();
                        int yAttacked = action.getCardAttacked().getY();

                        final CardUsesAttack cardUsesAttackCommand = new CardUsesAttack(
                                game, xAttacker, yAttacker, xAttacked, yAttacked, objectMapper);
                        final ObjectNode cardUsesAttackNode = cardUsesAttackCommand.executeAttack();
                        if (!cardUsesAttackNode.toString().equals("{}")) {
                            newNode = cardUsesAttackNode;
                        }
                        break;
                    case "cardUsesAbility":
                        xAttacker = action.getCardAttacker().getX();
                        yAttacker = action.getCardAttacker().getY();
                        xAttacked = action.getCardAttacked().getX();
                        yAttacked = action.getCardAttacked().getY();

                        final CardUsesAbility cardUsesAbilityCommand = new CardUsesAbility(
                                game, xAttacker, yAttacker, xAttacked, yAttacked, objectMapper);
                        final ObjectNode cardUsesAbilityNode =
                                cardUsesAbilityCommand.executeAbility();
                        if (!cardUsesAbilityNode.toString().equals("{}")) {
                            newNode = cardUsesAbilityNode;
                        }
                        break;
                    case "useAttackHero":
                        xAttacker = action.getCardAttacker().getX();
                        yAttacker = action.getCardAttacker().getY();

                        final Hero attackedHero;

                        if (xAttacker == 0 || xAttacker == 1) {
                            attackedHero = playerOneHero;
                        } else {
                            attackedHero = playerTwoHero;
                        }

                        final UseAttackHero useAttackHeroCommand = new UseAttackHero(
                                game, xAttacker, yAttacker, attackedHero, objectMapper);
                        final ObjectNode attackHeroResult = useAttackHeroCommand.executeAttack();

                        if (!attackHeroResult.isEmpty()) {
                            newNode = attackHeroResult;
                        }
                        break;
                    case "useHeroAbility":
                        final int affectedRow = action.getAffectedRow();

                        final UseHeroAbility useHeroAbilityCommand = new UseHeroAbility(
                                game, affectedRow, objectMapper);
                        final ObjectNode heroAbilityResult = useHeroAbilityCommand.executeAbility();
                        if (!heroAbilityResult.isEmpty()) {
                            newNode = heroAbilityResult;
                        }
                        break;
                    case "getFrozenCardsOnTable":
                        final GetFrozenCardsOnTable frozenCardsCommand =
                                new GetFrozenCardsOnTable(game);
                        newNode = frozenCardsCommand.getFrozenCards(objectMapper);
                        break;
                    case "getTotalGamesPlayed":
                        final GetTotalGamesPlayed getTotalGamesPlayedCommand =
                                new GetTotalGamesPlayed(game);
                        newNode = getTotalGamesPlayedCommand.getTotalGamesPlayed(objectMapper);
                        break;
                    case "getPlayerOneWins":
                        final GetPlayerOneWins getPlayerOneWinsCommand = new GetPlayerOneWins(game);
                        newNode = getPlayerOneWinsCommand.getPlayerOneWins(objectMapper);
                        break;
                    case "getPlayerTwoWins":
                        final GetPlayerTwoWins getPlayerTwoWinsCommand = new GetPlayerTwoWins(game);
                        newNode = getPlayerTwoWinsCommand.getPlayerTwoWins(objectMapper);
                        break;
                    default:
                        break;
                }
                if (newNode != null) {
                    output.add(newNode);
                }
            }

            final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            objectWriter.writeValue(new File(filePath2), output);
        }
    }
}
