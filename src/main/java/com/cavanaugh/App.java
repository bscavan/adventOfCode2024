package com.cavanaugh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 */
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

            List<Integer> leftList = new ArrayList<>();
            List<Integer> rightList = new ArrayList<>();

            for(String currentLine: lines) {
                String[] leftAndRight = currentLine.split("   ");

                if(leftAndRight.length != 2) {
                    System.err.println("The line [" + currentLine + "] line was parsed without two values.");
                    continue;
                }

                try {
                    int left = Integer.parseInt(leftAndRight[0]);
                    int right = Integer.parseInt(leftAndRight[1]);
                    leftList.add(left);
                    rightList.add(right);
                } catch (NumberFormatException nfex) {
                    System.err.println("Failed to convert both numbers to integers in the line [" + currentLine + "].");
                }
            }

            ListComparison comparator = new ListComparison(leftList, rightList);
            comparator.compareLists();
            System.out.println("The difference between the lists is: " + comparator.getDifferenceCount());
        } catch(IOException ioException) {
            // TODO: Slap the user's wrist for this one.
            System.err.println("Failed to open and parse the file:" + fileName);
            ioException.printStackTrace();
        }
    }
}
