package com.cavanaugh.day_04;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        if(args.length <= 0) {
            System.err.println("A filename for the input file is required. Exiting now.");
            System.exit(1);
        }

        String fileName = args[0];
        System.out.println("The input filename was: " + fileName);

        try (Scanner myReader = new Scanner(new File(fileName))) {
            List<String> lines = new ArrayList<>();

            while (myReader.hasNextLine()) {
                lines.add(myReader.nextLine());
            }

            WordSearchUtils crosswordUtils = new WordSearchUtils(false);
            List<AnswerItem> output = crosswordUtils.parseFullInput(lines, new String[] {"XMAS"});

            for(AnswerItem current: output) {
                System.out.printf("The current word [%s] was found [%d] times.", current.getText(), current.getTimesFound());
            }
        } catch(IOException ioException) {
            // TODO: Slap the user's wrist for this one.
            System.err.println("Failed to open and parse the file:" + fileName);
            ioException.printStackTrace();
        }
    }
}
