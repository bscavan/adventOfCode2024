package com.cavanaugh.day_04;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WordSearchUtils {
    public static final String FIRST_LINE_DELIMITER = "x";
    public static final String BODY_CHARACTER_DELIMITER = "(?!^)";
    private boolean matchOnlyOnce;

    public WordSearchUtils(boolean matchOnlyOnce) {
        this.matchOnlyOnce = matchOnlyOnce;
    }

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

    public List<AnswerItem> parseFullInput(List<String> lines, String[] answerKey) {
        // int[] lengthAndWidth = parseFirstLine(lines.get(0));
        String[] linesArray = lines.toArray(new String[lines.size()]);

        // int length = lengthAndWidth[0];
        // int width = lengthAndWidth[1];
        int length = lines.size();
        int width = lines.get(0).length();
        String[] bodyLines = Arrays.copyOfRange(linesArray, 0, length);

        String[][] crosswordBody = parseCrosswordBody(bodyLines, length, width);

        // String[] answerKey = Arrays.copyOfRange(linesArray, 1 + length, lines.size());
        List<AnswerItem> results =  Arrays.asList(answerKey).stream().map(value -> new AnswerItem(value)).collect(Collectors.toList());

        // TODO: Switch to a radix/"bucket" style approach of grouping answer words in the future.
        // That will let you cut down on the number of comparisons.
        // But don't forget the answers need to be printed out in the original order.

        return searchForMatches(length, width, crosswordBody, results);
    }

    // TODO: Note this doesn't handle malformatted lines.
    public static int[] parseFirstLine(String firstLine) {
        String[] lengthAndWidth = firstLine.split(FIRST_LINE_DELIMITER);
        int[] output = { Integer.parseInt(lengthAndWidth[0]), Integer.parseInt(lengthAndWidth[1]) };

        return output;
    }

    // TODO: Note this doesn't check for incorrect length/width values.
    public static String[][] parseCrosswordBody(String[] bodyLines, int length, int width) {
        String[][] crosswordBody = new String[length][width];

        for(int lengthIndex = 0; lengthIndex < length; lengthIndex++) {
            String[] currentLine = bodyLines[lengthIndex].split(BODY_CHARACTER_DELIMITER);

            for(int widthIndex = 0; widthIndex < width; widthIndex++) {
                crosswordBody[lengthIndex][widthIndex] = currentLine[widthIndex];
            }
        }

        return crosswordBody;
    }

    public static int[][] generateCoordinateRange(int[] startingPosition, int lengthOffset, int widthOffset) {
        int steps = Math.max(Math.abs(lengthOffset), Math.abs(widthOffset));
        int[][] output = new int[steps + 1][2];
        output[0][0] = startingPosition[0];
        output[0][1] = startingPosition[1];

        int lengthStep = lengthOffset / steps;
        int widthStep = widthOffset / steps;

        for(int stepIndex = 1; stepIndex < steps + 1; stepIndex++) {
            output[stepIndex][0] = startingPosition[0] + lengthStep * stepIndex;
            output[stepIndex][1] = startingPosition[1] + widthStep * stepIndex;
        }

        return output;
    }

    // TODO: NOTE: This skips comparing the first character!
    public static boolean doesWordMatch(String word, int[][] coordinates, String[][] crosswordBody) {
        for(int characterIndex = 1; characterIndex < word.length(); characterIndex++) {
            int[] currentPosition = coordinates[characterIndex];
            String currentCharacter = word.substring(characterIndex, characterIndex + 1);

            if(currentPosition[0] < 0 || currentPosition[1] < 0) {
                System.out.println("BEACON");
            }

            if(currentCharacter.equals(crosswordBody[currentPosition[0]][currentPosition[1]]) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * Iterate over every character in the crosswordBody and check if it
     * matches the beginning of one of the words in the answerKey. If it does
     * then begin searching the puzzle for that word.
     */
    // NOTE: Every AnswerItem in results must have at least one character of text!
    public List<AnswerItem> searchForMatches(int length, int width, String[][] crosswordBody, List<AnswerItem> results) {
        for(int lengthIndex = 0; lengthIndex < length; lengthIndex++) {
            for(int widthIndex = 0; widthIndex < width; widthIndex++) {
                String currentCharacter = crosswordBody[lengthIndex][widthIndex];

                // TODO: Add an early termination once all of the words have been found?
                for(int answerIndex = 0; answerIndex < results.size(); answerIndex++) {
                    AnswerItem currentWord = results.get(answerIndex);
                    String firstCharacter = currentWord.getText().substring(0, 1);
                    int currentWordLength = currentWord.getText().length();

                    // If we are still searching for the current word and it's first character matches currentCharacter
                    if((this.matchOnlyOnce == false || currentWord.getTimesFound() == 0)
                    && currentCharacter.equals(firstCharacter)) {
                        // TODO: Explain why the "+1" are here when looking left and up.
                        // P.S. It has to do with array counting starting at 0 and the
                        // current character's position throwing off the length of the width by 1.
                        boolean canSearchNorth = lengthIndex + 1 - currentWordLength >= 0;
                        boolean canSearchEast = widthIndex + currentWordLength <= width;
                        boolean canSearchSouth = lengthIndex + currentWordLength <= length;
                        boolean canSearchWest = widthIndex + 1 - currentWordLength >= 0;

                        // FIXME: CUrrently word length is correct, but the coordinates need the word length minus 1.
                        currentWordLength--;

                        if(canSearchNorth) {
                            int[][] coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, 0 - currentWordLength, 0);
                            if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                currentWord.inputFoundCoordinates(coordinates);

                                if(matchOnlyOnce) {
                                    continue;
                                }
                            }

                            if(canSearchEast) {
                                // Moving NE
                                coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, 0 - currentWordLength, currentWordLength);
                                if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                    currentWord.inputFoundCoordinates(coordinates);
                                    if(matchOnlyOnce) {
                                        continue;
                                    }
                                }
                            }

                            if(canSearchWest) {
                                // Moving NW
                                coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, 0 - currentWordLength, 0 - currentWordLength);
                                if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                    currentWord.inputFoundCoordinates(coordinates);
                                    if(matchOnlyOnce) {
                                        continue;
                                    }
                                }
                            }
                        }

                        if(canSearchEast) {
                            int[][] coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, 0, currentWordLength);
                            if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                currentWord.inputFoundCoordinates(coordinates);
                                if(matchOnlyOnce) {
                                    continue;
                                }
                            }
                        }

                        if(canSearchSouth) {
                            int[][] coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, currentWordLength, 0);
                            if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                currentWord.inputFoundCoordinates(coordinates);
                                if(matchOnlyOnce) {
                                    continue;
                                }
                            }

                            if(canSearchEast) {
                                // Moving SE
                                coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, currentWordLength, currentWordLength);
                                if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                    currentWord.inputFoundCoordinates(coordinates);
                                    if(matchOnlyOnce) {
                                        continue;
                                    }
                                }
                            }

                            if(canSearchWest) {
                                // Moving SW
                                coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, currentWordLength, 0 - currentWordLength);
                                if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                    currentWord.inputFoundCoordinates(coordinates);
                                    if(matchOnlyOnce) {
                                        continue;
                                    }
                                }
                            }
                        }

                        if(canSearchWest) {
                            int[][] coordinates = generateCoordinateRange(new int[] {lengthIndex, widthIndex}, 0, 0 - currentWordLength);
                            if(doesWordMatch(currentWord.getText(), coordinates, crosswordBody)) {
                                currentWord.inputFoundCoordinates(coordinates);
                                if(matchOnlyOnce) {
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }

        return results;
    }
}
