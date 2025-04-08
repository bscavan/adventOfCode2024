package com.cavanaugh.day_06;

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

            String[] allLines = lines.toArray(new String[lines.size()]);
            MapWalker mapWalker = new MapWalker(allLines);
            mapWalker.advanceUntilDone();

            System.out.printf("The total number of ticks passed was: %d", mapWalker.getTickCount());
            System.out.printf("The total number of marked spaces iss: %d", mapWalker.countMarkedSpaces());

            // System.out.println();
            // manualRevisionDetailsUtil.setShouldTotalCorrectRevisions(false);
            // manualRevisionDetailsUtil.setShouldTotalWrongRevisions(true);
            // System.out.printf("The total value of the center page numbers of the invalid printing instructions is: %d", manualRevisionDetailsUtil.totalUpCenterElementsOfPageists());
        } catch(IOException ioException) {
            // TODO: Slap the user's wrist for this one.
            System.err.println("Failed to open and parse the file:" + fileName);
            ioException.printStackTrace();
        }
    }
}
