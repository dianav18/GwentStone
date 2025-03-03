package org.poo.main;

import org.poo.checker.CheckerConstants;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Use this if you want to test on a specific input file
 */
public final class Test {
    /**
     * for coding style
     */
    private Test() {
    }

    /**
     * Main.
     *
     * @param args input files
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        final File directory = new File(CheckerConstants.TESTS_PATH);
        final File[] inputDir = directory.listFiles();

        if (inputDir != null) {
            Arrays.sort(inputDir);

            final Scanner scanner = new Scanner(System.in);
            final String fileName = scanner.next();
            for (final File file : inputDir) {
                if (file.getName().equalsIgnoreCase(fileName)) {
                    Main.action(file.getName(), CheckerConstants.OUT_PATH + file.getName());
                    break;
                }
                if (file.getName().contains(fileName)) {
                    Main.action(file.getName(), CheckerConstants.OUT_PATH + file.getName());
                    break;
                }
            }
        }
    }
}
