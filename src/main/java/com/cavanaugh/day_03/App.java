package com.cavanaugh.day_03;

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

            MullParser mullParser = new MullParser(lines);
            mullParser.setPreprocessInput(false);
            System.out.println("The total multiplied value found after parsing is: [" + mullParser.calculateProduct() + "];");

            mullParser.setPreprocessInput(true);
            System.out.println("If we enable the preprocesser then the total multiplied value is instead: [" + mullParser.calculateProduct() + "];");
        } catch(IOException ioException) {
            // TODO: Slap the user's wrist for this one.
            System.err.println("Failed to open and parse the file:" + fileName);
            ioException.printStackTrace();
        }
    }
}
