package com.cavanaugh.day_04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

/**
 * Unit test for crossword utils.
 */
public class WordSearchUtilsTest {

    @Test
    public void testParseFirstLineBasic() {
        String input = "3x3";
        int expectedLength = 3;
        int expectedWdith = 3;
        int[] output = WordSearchUtils.parseFirstLine(input);
        assertEquals(expectedLength, output[0], "CrosswordUtils.parseFirstLine failed to correctly parse the length from the input.");
        assertEquals(expectedWdith, output[1], "CrosswordUtils.parseFirstLine failed to correctly parse the width from the input.");
    }

    @Test
    public void testParseFirstSecondary() {
        String input = "3x7";
        int expectedLength = 3;
        int expectedWdith = 7;
        int[] output = WordSearchUtils.parseFirstLine(input);
        assertEquals(expectedLength, output[0], "CrosswordUtils.parseFirstLine failed to correctly parse the length from the input.");
        assertEquals(expectedWdith, output[1], "CrosswordUtils.parseFirstLine failed to correctly parse the width from the input.");
    }

    @Test
    public void testParseFirstComplex() {
        String input = "56x34";
        int expectedLength = 56;
        int expectedWdith = 34;
        int[] output = WordSearchUtils.parseFirstLine(input);
        assertEquals(expectedLength, output[0], "CrosswordUtils.parseFirstLine failed to correctly parse the length from the input.");
        assertEquals(expectedWdith, output[1], "CrosswordUtils.parseFirstLine failed to correctly parse the width from the input.");
    }

    @Test
    public void testParseCrosswordBodyBasic() {
        String[] input = {"ABC"};
        int expectedLength = 1;
        int expectedWdith = 3;
        String[][] output = WordSearchUtils.parseCrosswordBody(input, expectedLength, expectedWdith);
        assertEquals(expectedLength, output.length);
        assertEquals(expectedWdith, output[0].length);
        assertEquals("A", output[0][0]);
        assertEquals("B", output[0][1]);
        assertEquals("C", output[0][2]);
    }

    @Test
    public void testParseCrosswordBodySecondary() {
        String[] input = {"ABC", "DEF", "GHI"};
        int expectedLength = 3;
        int expectedWdith = 3;
        String[][] output = WordSearchUtils.parseCrosswordBody(input, expectedLength, expectedWdith);
        assertEquals(expectedLength, output.length);
        assertEquals(expectedWdith, output[0].length);
        assertEquals("A", output[0][0]);
        assertEquals("B", output[0][1]);
        assertEquals("C", output[0][2]);
        assertEquals("D", output[1][0]);
        assertEquals("E", output[1][1]);
        assertEquals("F", output[1][2]);
        assertEquals("G", output[2][0]);
        assertEquals("H", output[2][1]);
        assertEquals("I", output[2][2]);
    }

    @Test
    public void testParseCrosswordBodyComplex() {
        String[] input = {"ABCDE", "FGHIJ","KLMNO"};
        int expectedLength = 3;
        int expectedWdith = 5;
        String[][] output = WordSearchUtils.parseCrosswordBody(input, expectedLength, expectedWdith);
        assertEquals(expectedLength, output.length);
        assertEquals(expectedWdith, output[0].length);
        assertEquals("A", output[0][0]);
        assertEquals("B", output[0][1]);
        assertEquals("C", output[0][2]);
        assertEquals("D", output[0][3]);
        assertEquals("E", output[0][4]);
        assertEquals("F", output[1][0]);
        assertEquals("G", output[1][1]);
        assertEquals("H", output[1][2]);
        assertEquals("I", output[1][3]);
        assertEquals("J", output[1][4]);
        assertEquals("K", output[2][0]);
        assertEquals("L", output[2][1]);
        assertEquals("M", output[2][2]);
        assertEquals("N", output[2][3]);
        assertEquals("O", output[2][4]);
    }
    
    @Test
    public void testGenerateCoordinateRangeBasic() {
        int[] startingPosition = {0, 1};
        int lengthOffset = 0;
        int widthOffset = 3;
        int[][] expectedOutput = new int[4][2];

        expectedOutput[0][0] = 0;
        expectedOutput[0][1] = 1;

        expectedOutput[1][0] = 0;
        expectedOutput[1][1] = 2;

        expectedOutput[2][0] = 0;
        expectedOutput[2][1] = 3;

        expectedOutput[3][0] = 0;
        expectedOutput[3][1] = 4;
        int[][] output = WordSearchUtils.generateCoordinateRange(startingPosition, lengthOffset, widthOffset);
        assertEquals(expectedOutput.length, output.length);

        for(int lengthIndex = 0; lengthIndex < expectedOutput.length; lengthIndex++) {
            assertEquals(expectedOutput[lengthIndex].length, output[lengthIndex].length);

            for(int widthIndex = 0; widthIndex < expectedOutput[lengthIndex].length; widthIndex++) {
                assertEquals(expectedOutput[lengthIndex][widthIndex], output[lengthIndex][widthIndex]);
            }
        }
    }
    
    @Test
    public void testGenerateCoordinateRangeSecondary() {
        int[] startingPosition = {0, 1};
        int lengthOffset = 4;
        int widthOffset = 4;
        int[][] expectedOutput = new int[5][2];

        expectedOutput[0][0] = 0;
        expectedOutput[0][1] = 1;

        expectedOutput[1][0] = 1;
        expectedOutput[1][1] = 2;

        expectedOutput[2][0] = 2;
        expectedOutput[2][1] = 3;

        expectedOutput[3][0] = 3;
        expectedOutput[3][1] = 4;

        expectedOutput[4][0] = 4;
        expectedOutput[4][1] = 5;

        int[][] output = WordSearchUtils.generateCoordinateRange(startingPosition, lengthOffset, widthOffset);
        assertEquals(expectedOutput.length, output.length);

        for(int lengthIndex = 0; lengthIndex < expectedOutput.length; lengthIndex++) {
            assertEquals(expectedOutput[lengthIndex].length, output[lengthIndex].length);

            for(int widthIndex = 0; widthIndex < expectedOutput[lengthIndex].length; widthIndex++) {
                assertEquals(expectedOutput[lengthIndex][widthIndex], output[lengthIndex][widthIndex]);
            }
        }
    }
    
    @Test
    public void testGenerateCoordinateRangeComplex() {
        int[] startingPosition = {5, 1};
        int lengthOffset = -4;
        int widthOffset = 4;
        int[][] expectedOutput = new int[5][2];

        expectedOutput[0][0] = 5;
        expectedOutput[0][1] = 1;

        expectedOutput[1][0] = 4;
        expectedOutput[1][1] = 2;

        expectedOutput[2][0] = 3;
        expectedOutput[2][1] = 3;

        expectedOutput[3][0] = 2;
        expectedOutput[3][1] = 4;

        expectedOutput[4][0] = 1;
        expectedOutput[4][1] = 5;

        int[][] output = WordSearchUtils.generateCoordinateRange(startingPosition, lengthOffset, widthOffset);
        assertEquals(expectedOutput.length, output.length);

        for(int lengthIndex = 0; lengthIndex < expectedOutput.length; lengthIndex++) {
            assertEquals(expectedOutput[lengthIndex].length, output[lengthIndex].length);

            for(int widthIndex = 0; widthIndex < expectedOutput[lengthIndex].length; widthIndex++) {
                assertEquals(expectedOutput[lengthIndex][widthIndex], output[lengthIndex][widthIndex]);
            }
        }
    }

    public static void assertionHelper(String searchText, boolean wasFound, int[] expectedStartingPosition, int[] expectedEndingPosition, Entry<Integer, Integer> actualStartingPosition, Entry<Integer, Integer> actualEndingPosition) {
        assertTrue(wasFound, "The text \"" + searchText + "\" was not found in the crossword body.");
        assertEquals(expectedStartingPosition[0], actualStartingPosition.getKey(), "The length portion of the starting position for the text \"" + searchText + "\" was not correct.");
        assertEquals(expectedStartingPosition[1], actualStartingPosition.getValue(), "The width portion of the starting position for the text \"" + searchText + "\" was not correct.");
        assertEquals(expectedEndingPosition[0], actualEndingPosition.getKey(), "The length portion of the ending position for the text \"" + searchText + "\" was not correct.");
        assertEquals(expectedEndingPosition[1], actualEndingPosition.getValue(), "The width portion of the ending position for the text \"" + searchText + "\" was not correct.");
    }

    @Test
    public void testSearchForMatchesBasic() {
        String[][] crosswordBody = new String[1][3];
        crosswordBody[0][0] = "A";
        crosswordBody[0][1] = "B";
        crosswordBody[0][2] = "C";

        int length = 1;
        int width = 3;
        List<AnswerItem> input = new ArrayList<>();
        input.add(new AnswerItem("ABC"));
        int[] expectedStartingPosition = {0, 0};
        int[] expectedEndingPosition = {0, 2};

        WordSearchUtils crosswordUtils = new WordSearchUtils(true);
        List<AnswerItem> output = crosswordUtils.searchForMatches(length, width, crosswordBody, input);

        assertionHelper(input.get(0).getText(), input.get(0).getTimesFound() == 1,
            expectedStartingPosition, expectedEndingPosition,
            output.get(0).getCoordinates().get(0).getStartingPosition(), output.get(0).getCoordinates().get(0).getEndingPosition());
    }

    @Test
    public void testSearchForMatchesSecondary() {
        String[][] crosswordBody = new String[3][3];
        crosswordBody[0][0] = "A";
        crosswordBody[0][1] = "B";
        crosswordBody[0][2] = "C";
        crosswordBody[1][0] = "D";
        crosswordBody[1][1] = "E";
        crosswordBody[1][2] = "F";
        crosswordBody[2][0] = "G";
        crosswordBody[2][1] = "H";
        crosswordBody[2][2] = "I";

        int length = 3;
        int width = 3;
        List<AnswerItem> input = new ArrayList<>();
        input.add(new AnswerItem("AEI"));
        int[] expectedStartingPosition = {0, 0};
        int[] expectedEndingPosition = {2, 2};

        WordSearchUtils crosswordUtils = new WordSearchUtils(true);
        List<AnswerItem> output = crosswordUtils.searchForMatches(length, width, crosswordBody, input);

        assertionHelper(input.get(0).getText(), input.get(0).getTimesFound() == 1,
            expectedStartingPosition, expectedEndingPosition,
            output.get(0).getCoordinates().get(0).getStartingPosition(), output.get(0).getCoordinates().get(0).getEndingPosition());
    }

    @Test
    public void testSearchForMatchesComplex() {
        String[][] crosswordBody = new String[3][5];
        crosswordBody[0][0] = "A";
        crosswordBody[0][1] = "B";
        crosswordBody[0][2] = "C";
        crosswordBody[0][3] = "D";
        crosswordBody[0][4] = "E";
        crosswordBody[1][0] = "F";
        crosswordBody[1][1] = "G";
        crosswordBody[1][2] = "H";
        crosswordBody[1][3] = "I";
        crosswordBody[1][4] = "J";
        crosswordBody[2][0] = "K";
        crosswordBody[2][1] = "L";
        crosswordBody[2][2] = "M";
        crosswordBody[2][3] = "N";
        crosswordBody[2][4] = "O";

        int length = 3;
        int width = 5;
        List<AnswerItem> input = new ArrayList<>();
        input.add(new AnswerItem("ABCD"));
        int[] firstWordExpectedStartingPosition = {0, 0};
        int[] firstWordExpectedEndingPosition = {0, 3};

        input.add(new AnswerItem("CGK"));
        int[] secondWordExpectedStartingPosition = {0, 2};
        int[] secondWordExpectedEndingPosition = {2, 0};

        input.add(new AnswerItem("OIC"));
        int[] thirdWordExpectedStartingPosition = {2, 4};
        int[] thirdWordExpectedEndingPosition = {0, 2};

        WordSearchUtils crosswordUtils = new WordSearchUtils(true);
        List<AnswerItem> output = crosswordUtils.searchForMatches(length, width, crosswordBody, input);

        assertionHelper(input.get(0).getText(), input.get(0).getTimesFound() == 1,
            firstWordExpectedStartingPosition, firstWordExpectedEndingPosition,
            output.get(0).getCoordinates().get(0).getStartingPosition(), output.get(0).getCoordinates().get(0).getEndingPosition());

        assertionHelper(input.get(1).getText(), input.get(1).getTimesFound() == 1,
            secondWordExpectedStartingPosition, secondWordExpectedEndingPosition,
            output.get(1).getCoordinates().get(0).getStartingPosition(), output.get(1).getCoordinates().get(0).getEndingPosition());

        assertionHelper(input.get(2).getText(), input.get(2).getTimesFound() == 1,
            thirdWordExpectedStartingPosition, thirdWordExpectedEndingPosition,
            output.get(2).getCoordinates().get(0).getStartingPosition(), output.get(2).getCoordinates().get(0).getEndingPosition());
    }

    @Test
    public void testParseFullInputBasic() {
        List<String> fullInput = new ArrayList<>();
        fullInput.add("ABC");
        fullInput.add("DEF");
        fullInput.add("GHI");

        List<AnswerItem> input = new ArrayList<>();
        input.add(new AnswerItem("ABC"));
        int[] firstWordExpectedStartingPosition = {0, 0};
        int[] firstWordExpectedEndingPosition = {0, 2};

        input.add(new AnswerItem("AEI"));
        int[] secondWordExpectedStartingPosition = {0, 0};
        int[] secondWordExpectedEndingPosition = {2, 2};

        WordSearchUtils crosswordUtils = new WordSearchUtils(true);
        List<AnswerItem> output = crosswordUtils.parseFullInput(fullInput, new String[] {"ABC", "AEI"});

        assertionHelper(input.get(0).getText(), output.get(0).getTimesFound() == 1,
            firstWordExpectedStartingPosition, firstWordExpectedEndingPosition,
            output.get(0).getCoordinates().get(0).getStartingPosition(), output.get(0).getCoordinates().get(0).getEndingPosition());

        assertionHelper(input.get(1).getText(), output.get(1).getTimesFound() == 1,
            secondWordExpectedStartingPosition, secondWordExpectedEndingPosition,
            output.get(1).getCoordinates().get(0).getStartingPosition(), output.get(1).getCoordinates().get(0).getEndingPosition());
    }


@Test
public void testParseFullInputMedium() {
    List<String> fullInput = new ArrayList<>();
    fullInput.add("MMMSXXMASM");
    fullInput.add("MSAMXMSMSA");
    fullInput.add("AMXSXMAAMM");
    fullInput.add("MSAMASMSMX");
    fullInput.add("XMASAMXAMM");
    fullInput.add("XXAMMXXAMA");
    fullInput.add("SMSMSASXSS");
    fullInput.add("SAXAMASAAA");
    fullInput.add("MAMMMXMMMM");
    fullInput.add("MXMXAXMASX");

    List<AnswerItem> input = new ArrayList<>();
    input.add(new AnswerItem("XMAS"));

    WordSearchUtils crosswordUtils = new WordSearchUtils(false);
    List<AnswerItem> output = crosswordUtils.parseFullInput(fullInput, new String[] {"XMAS"});

    assertEquals(18, output.get(0).getTimesFound());
}
    /*
    @Test
    public void testParseFullInputSecondary() {
        List<String> fullInput = new ArrayList<>();
        fullInput.add("3x5");
        fullInput.add("A B C D E");
        fullInput.add("F G H I J");
        fullInput.add("K L M N O");
        fullInput.add("ABCD");
        fullInput.add("CGK");
        fullInput.add("OIC");

        List<AnswerItem> input = new ArrayList<>();
        input.add(new AnswerItem("ABCD"));
        int[] firstWordExpectedStartingPosition = {0, 0};
        int[] firstWordExpectedEndingPosition = {0, 3};

        input.add(new AnswerItem("CGK"));
        int[] secondWordExpectedStartingPosition = {0, 2};
        int[] secondWordExpectedEndingPosition = {2, 0};

        input.add(new AnswerItem("OIC"));
        int[] thirdWordExpectedStartingPosition = {2, 4};
        int[] thirdWordExpectedEndingPosition = {0, 2};

        CrosswordUtils crosswordUtils = new CrosswordUtils(true);
        List<AnswerItem> output = crosswordUtils.parseFullInput(fullInput);

        assertionHelper(input.get(0).getText(), output.get(0).getTimesFound() == 1,
            firstWordExpectedStartingPosition, firstWordExpectedEndingPosition,
            output.get(0).getStartingPosition(), output.get(0).getEndingPosition());

        assertionHelper(input.get(1).getText(), output.get(1).getTimesFound() == 1,
            secondWordExpectedStartingPosition, secondWordExpectedEndingPosition,
            output.get(1).getStartingPosition(), output.get(1).getEndingPosition());

        assertionHelper(input.get(2).getText(), output.get(2).getTimesFound() == 1,
            thirdWordExpectedStartingPosition, thirdWordExpectedEndingPosition,
            output.get(2).getStartingPosition(), output.get(2).getEndingPosition());
    }

    @Test
    public void testParseFullInputComplex() {
        List<String> fullInput = new ArrayList<>();
        fullInput.add("5x5");
        fullInput.add("H A S D F");
        fullInput.add("G E Y B H");
        fullInput.add("J K L Z X");
        fullInput.add("C V B L N");
        fullInput.add("G O O D O");
        fullInput.add("HELLO");
        fullInput.add("GOOD");
        fullInput.add("BYE");

        List<AnswerItem> input = new ArrayList<>();
        input.add(new AnswerItem("HELLO"));
        int[] firstWordExpectedStartingPosition = {0, 0};
        int[] firstWordExpectedEndingPosition = {4, 4};

        input.add(new AnswerItem("GOOD"));
        int[] secondWordExpectedStartingPosition = {4, 0};
        int[] secondWordExpectedEndingPosition = {4, 3};

        input.add(new AnswerItem("BYE"));
        int[] thirdWordExpectedStartingPosition = {1, 3};
        int[] thirdWordExpectedEndingPosition = {1, 1};

        CrosswordUtils crosswordUtils = new CrosswordUtils(true);
        List<AnswerItem> output = crosswordUtils.parseFullInput(fullInput);

        assertionHelper(input.get(0).getText(), output.get(0).getTimesFound() == 1,
            firstWordExpectedStartingPosition, firstWordExpectedEndingPosition,
            output.get(0).getStartingPosition(), output.get(0).getEndingPosition());

        assertionHelper(input.get(1).getText(), output.get(1).getTimesFound() == 1,
            secondWordExpectedStartingPosition, secondWordExpectedEndingPosition,
            output.get(1).getStartingPosition(), output.get(1).getEndingPosition());

        assertionHelper(input.get(2).getText(), output.get(2).getTimesFound() == 1,
            thirdWordExpectedStartingPosition, thirdWordExpectedEndingPosition,
            output.get(2).getStartingPosition(), output.get(2).getEndingPosition());
    }
    */
}
