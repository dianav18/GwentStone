package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import debug.GetPlayerDeck;
import fileio.*;
import game.Game;
import game.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        Player player1 = new Player(inputData.getPlayerOneDecks().getDecks());
        Player player2 = new Player(inputData.getPlayerTwoDecks().getDecks());

        for (GameInput inputDataGame : inputData.getGames()) {
            player1.setup();
            player2.setup();
            Game game = new Game(
                    player1, player2,
                    inputDataGame.getStartGame().getPlayerOneDeckIdx(),
                    inputDataGame.getStartGame().getPlayerTwoDeckIdx(),
                    inputDataGame.getStartGame().getShuffleSeed()
            );

            for (ActionsInput action : inputDataGame.getActions()) {
                String command = action.getCommand();
                switch (command) {
                    case "getPlayerDeck":
                        int playerIdx = action.getPlayerIdx();
                        ObjectNode deckOutput = new GetPlayerDeck(game).getPlayerDeck(playerIdx);
                        output.add(deckOutput);
                        break;

                    default:
                        break;
                }
            }

            /*
             * TODO Implement your function here
             *
             * How to add output to the output array?
             * There are multiple ways to do this, here is one example:
             *
             * ObjectMapper mapper = new ObjectMapper();
             *
             * ObjectNode objectNode = mapper.createObjectNode();
             * objectNode.put("field_name", "field_value");
             *
             * ArrayNode arrayNode = mapper.createArrayNode();
             * arrayNode.add(objectNode);
             *
             * output.add(arrayNode);
             * output.add(objectNode);
             *
             */

            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            objectWriter.writeValue(new File(filePath2), output);
        }
    }
}

// https://jsondiff.com/