package main;

import cards.hero.Hero;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import debug.*;
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
                              final String filePath2) throws IOException {
        Game.endedGames = 0;
        Game.playerOneWins = 0;
        Game.playerTwoWins = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        final Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);


        final ArrayNode output = objectMapper.createArrayNode();

//        StartGameInput startGameInput = inputDataGame.getStartGame();

//        Player player1 = new Player(1, inputData.getPlayerOneDecks().getDecks(), startGameInput.getPlayerOneHero());
//        Player player2 = new Player(2, inputData.getPlayerTwoDecks().getDecks(), startGameInput.getPlayerTwoHero());

        for (final GameInput inputDataGame : inputData.getGames()) {
            final StartGameInput startGameInput = inputDataGame.getStartGame();
            final Player player1 = new Player(1, inputData.getPlayerOneDecks().getDecks());
            final Player player2 = new Player(2, inputData.getPlayerTwoDecks().getDecks());

            final Hero playerOneHero = Hero.create(startGameInput.getPlayerOneHero());
            final Hero playerTwoHero = Hero.create(startGameInput.getPlayerTwoHero());

//            System.out.println(playerOneHero);
//            System.out.println(playerTwoHero);

//            System.out.println(playerOneHero.getName());
//            System.out.println(playerTwoHero.getName());

//            Hero player1Hero = new Hero(inputDataGame.getStartGame().getPlayerOneHero());
//            Hero player2Hero = new Hero(inputDataGame.getStartGame().getPlayerTwoHero());

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

            //StartGameInput startGameInput = inputDataGame.getStartGame();

            for (final ActionsInput action : inputDataGame.getActions()) {

                // System.out.println("ACTION: " + action);
                final String command = action.getCommand();

                ObjectNode newNode = null;
                switch (command) {
                    case "getPlayerDeck":
                        final int playerIdx = action.getPlayerIdx();
                        final ObjectNode deckOutput = new GetPlayerDeck(game).getPlayerDeck(playerIdx);
                        newNode = (deckOutput);
                        break;
                    case "getPlayerHero":
                        final Hero selectedHero = action.getPlayerIdx() == 1 ? playerOneHero : playerTwoHero;
                        final GetPlayerHero getPlayerHeroCommand = new GetPlayerHero(selectedHero, action.getPlayerIdx());
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
                        final GetCardsInHand getCardsInHandCommand = new GetCardsInHand(targetPlayer);
                        newNode = (getCardsInHandCommand.getCardsInHand(objectMapper));
                        break;
                    case "endPlayerTurn":
                        game.endTurn(); // TODO Move tgo class and call it
                        break;
                    case "placeCard":
                        final int handIndex = action.getHandIdx();
                        game.placeCard(handIndex, objectMapper, output);
                        break;
                    case "getPlayerMana":
                        final int manaPlayerIdx = action.getPlayerIdx();
                        final Player manaTargetPlayer = (manaPlayerIdx == 1) ? player1 : player2;
                        final GetPlayerMana getPlayerManaCommand = new GetPlayerMana(manaTargetPlayer);
                        newNode = (getPlayerManaCommand.getPlayerMana());
                        break;
                    case "getCardsOnTable":
                        output.add(new GetCardsOnTable(game).getCardsOnTable());
                        break;
                    case "getCardAtPosition":
                        final int x = action.getX();
                        final int y = action.getY();
                        final GetCardAtPosition getCardAtPositionCommand = new GetCardAtPosition(game, x, y, objectMapper);
                        newNode = (getCardAtPositionCommand.getCardAtPosition());
                        break;
                    case "cardUsesAttack":
                        int xAttacker = action.getCardAttacker().getX();
                        int yAttacker = action.getCardAttacker().getY();
                        int xAttacked = action.getCardAttacked().getX();
                        int yAttacked = action.getCardAttacked().getY();

                        final CardUsesAttack cardUsesAttackCommand = new CardUsesAttack(game, xAttacker, yAttacker, xAttacked, yAttacked, objectMapper);
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

                        final CardUsesAbility cardUsesAbilityCommand = new CardUsesAbility(game, xAttacker, yAttacker, xAttacked, yAttacked, objectMapper);
                        final ObjectNode cardUsesAbilityNode = cardUsesAbilityCommand.executeAbility();
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

                        final UseAttackHero useAttackHeroCommand = new UseAttackHero(game, xAttacker, yAttacker, attackedHero, objectMapper);
                        final ObjectNode attackHeroResult = useAttackHeroCommand.executeAttack();

                        if (!attackHeroResult.isEmpty()) {
                            newNode = attackHeroResult;
                        }
                        break;
                    case "useHeroAbility":
                        final int affectedRow = action.getAffectedRow();

                        final UseHeroAbility useHeroAbilityCommand = new UseHeroAbility(game, affectedRow, objectMapper);
                        final ObjectNode heroAbilityResult = useHeroAbilityCommand.executeAbility();
                        if (!heroAbilityResult.isEmpty()) {
                            newNode = heroAbilityResult;
                        }
                        break;
                    case "getFrozenCardsOnTable":
                        final GetFrozenCardsOnTable frozenCardsCommand = new GetFrozenCardsOnTable(game);
                        newNode = frozenCardsCommand.getFrozenCards(objectMapper);
                        break;
                    case "getTotalGamesPlayed":
                        final GetTotalGamesPlayed getTotalGamesPlayedCommand = new GetTotalGamesPlayed(game);
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
                        //System.out.println(command + " action not found");
                        break;
                }
                if (newNode != null) {
                    output.add(newNode);
                }
                //System.out.println(newNode);
                //printGameState(game); // TODO Remove
            }

            final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            objectWriter.writeValue(new File(filePath2), output);
        }
    }
}

//    public static void printGameState(Game game) {
//        AtomicInteger maxCardSize = new AtomicInteger();
//        for (Player player : List.of(game.getPlayer1(), game.getPlayer2())) {
//            for (Minion card : player.getCurrentDeck().getMinions()) {
//                maxCardSize.set(Math.max(maxCardSize.get(), card.toString().length()));
//            }
//            for (Minion card : player.getHand().getMinions()) {
//                maxCardSize.set(Math.max(maxCardSize.get(), card.toString().length()));
//            }
//            for (Minion[] cards : game.getBoard()) {
//                for (Minion card : cards) {
//                    if(card==null){
//                        continue;
//                    }
//                    maxCardSize.set(Math.max(maxCardSize.get(), card.toString().length()));
//                }
//            }
//        }
//
//        Player player1 = game.getPlayer1();
//        Player player2 = game.getPlayer2();
//
//        List<Minion> player2BackRow = Arrays.stream(game.getBoard()[0]).collect(Collectors.toList());
//        List<Minion> player2FrontRow = Arrays.stream(game.getBoard()[1]).collect(Collectors.toList());
//        List<Minion> player1FrontRow = Arrays.stream(game.getBoard()[2]).collect(Collectors.toList());
//        List<Minion> player1BackRow = Arrays.stream(game.getBoard()[3]).collect(Collectors.toList());
//
//        List<String> state = new MessageBuilderList(List.of(
//                "Starting Turn: {starting_turn}",
//                "Turn: {turn}",
//                "P2: {player_2_mana} mana",
//                "P2 Deck({player_2_deck_size}): {player_2_deck}",
//                "P2 Hand({player_2_hand_size}): {player_2_hand}",
//                "P2 Hero: {player_2_hero}",
//                "{player_2_back_row}",
//                "{player_2_front_row}",
//                "{player_1_front_row}",
//                "{player_1_back_row}",
//                "P1 Hero: {player_1_hero}",
//                "P1 Deck({player_1_deck_size}): {player_1_deck}",
//                "P1 Hand({player_1_hand_size}): {player_1_hand}",
//                "P1: {player_1_mana} mana",
//                "P1 {p1_wins} vs P2 {p2_wins}",
//                ""
//        ))
//                .parse("turn", game.getTurn())
//                .parse("starting_turn", game.getStartingTurn())
//                .parse("player_2_deck_size", player2.getCurrentDeck().getMinions().size())
//                .parse("player_2_deck", ListUtils.toString(player2.getCurrentDeck().getMinions(), Minion::toString))
//                .parse("player_1_deck_size", game.getPlayer1().getCurrentDeck().getMinions().size())
//                .parse("player_1_deck", ListUtils.toString(player1.getCurrentDeck().getMinions(), Minion::toString))
//                .parse("player_2_hand_size", player2.getHand().getMinions().size())
//                .parse("player_2_hand", ListUtils.toString(player2.getHand().getMinions(), Minion::toString))
//                .parse("player_1_hand_size", player1.getHand().getMinions().size())
//                .parse("player_1_hand", ListUtils.toString(player1.getHand().getMinions(), Minion::toString))
//                .parse("player_2_back_row", ListUtils.toString(player2BackRow, Minion::toString, (str) -> StringUtils.padString(str, maxCardSize.get())))
//                .parse("player_2_front_row", ListUtils.toString(player2FrontRow, Minion::toString, (str) -> StringUtils.padString(str, maxCardSize.get())))
//                .parse("player_1_front_row", ListUtils.toString(player1FrontRow, Minion::toString, (str) -> StringUtils.padString(str, maxCardSize.get())))
//                .parse("player_1_back_row", ListUtils.toString(player1BackRow, Minion::toString, (str) -> StringUtils.padString(str, maxCardSize.get())))
//                .parse("player_2_mana", player2.getMana())
//                .parse("player_1_mana", player1.getMana())
//                //a fost pauza de batut cu madalin :)))
////                .parse("player_2_hero", player2.getHero().toString())
////                .parse("player_1_hero", player1.getHero().toString())
////                .parse("p1_wins", Games.getStatistics().getWins(PlayerEnum.PLAYER_1))
////                .parse("p2_wins", Games.getStatistics().getWins(PlayerEnum.PLAYER_2))
//                .parse();
//        System.out.println();
//        System.out.println();
//        for (String stateLine : state) {
//            System.out.println(stateLine);
//        }
//        System.out.println();
//        System.out.println();
//    }
//}

// https://jsondiff.com/

