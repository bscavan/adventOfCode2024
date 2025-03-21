package com.cavanaugh.day_02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReactorLevelCheckerTest {
    /*
     * 
    private String allText = "7 6 4 2 1" + System.lineSeparator()
        + "1 2 7 8 9" + System.lineSeparator()
        + "9 7 6 2 1" + System.lineSeparator()
        + "1 3 2 4 5" + System.lineSeparator()
        + "8 6 4 4 1" + System.lineSeparator()
        + "1 3 6 7 9";
     */
    private String[] allText = {"7 6 4 2 1",
        "1 2 7 8 9",
        "9 7 6 2 1",
        "1 3 2 4 5",
        "8 6 4 4 1",
        "1 3 6 7 9"};
    private List<List<Integer>> levelList;

    @BeforeEach
    public void setup() {
        levelList = new ArrayList<>();

        List<Integer> firstLevel = new ArrayList<>();
        firstLevel.add(7);
        firstLevel.add(6);
        firstLevel.add(4);
        firstLevel.add(2);
        firstLevel.add(1);
        levelList.add(firstLevel);

        List<Integer> secondLevel = new ArrayList<>();
        secondLevel.add(1);
        secondLevel.add(2);
        secondLevel.add(7);
        secondLevel.add(8);
        secondLevel.add(9);
        levelList.add(secondLevel);

        List<Integer> thirdLevel = new ArrayList<>();
        thirdLevel.add(9);
        thirdLevel.add(7);
        thirdLevel.add(6);
        thirdLevel.add(2);
        thirdLevel.add(1);
        levelList.add(thirdLevel);

        List<Integer> fourthLevel = new ArrayList<>();
        fourthLevel.add(1);
        fourthLevel.add(3);
        fourthLevel.add(2);
        fourthLevel.add(4);
        fourthLevel.add(5);
        levelList.add(fourthLevel);
        
        List<Integer> fifthLevel = new ArrayList<>();
        fifthLevel.add(8);
        fifthLevel.add(6);
        fifthLevel.add(4);
        fifthLevel.add(4);
        fifthLevel.add(1);
        levelList.add(fifthLevel);
        
        List<Integer> sixthLevel = new ArrayList<>();
        sixthLevel.add(1);
        sixthLevel.add(3);
        sixthLevel.add(6);
        sixthLevel.add(7);
        sixthLevel.add(9);
        levelList.add(sixthLevel);
    }

    @Test
    public void shouldParseTextCorrectly() {
        List<List<Integer>> output = ReactorLevelChecker.convertTextToReactorLevels(allText);
        assertEquals(levelList.size(), output.size(), "The parsed text did not result in the expected number of levels.");

        for(int index = 0; index < levelList.size(); index++) {
            List<Integer> currentLevelExpected = levelList.get(index);
            List<Integer> currentLevelActual = output.get(index);
            assertEquals(currentLevelExpected.size(), currentLevelActual.size(),
                "Level ["+ index + "] did not match between the expected and actual output.");

            for(int innerIndex = 0; innerIndex < currentLevelExpected.size(); innerIndex++ ) {
                assertEquals(currentLevelExpected.get(innerIndex), currentLevelActual.get(innerIndex),
                    "The values for level [" + index+ "] differed between the expected and actual values.");
            }
        }
    }

    @Test
    public void isLevelSafeTest() {
        ReactorLevelChecker reactorLevelChecker = new ReactorLevelChecker();
        assertTrue(reactorLevelChecker.isLevelSafe(levelList.get(0)));
        assertFalse(reactorLevelChecker.isLevelSafe(levelList.get(1)));
        assertFalse(reactorLevelChecker.isLevelSafe(levelList.get(2)));
        assertFalse(reactorLevelChecker.isLevelSafe(levelList.get(3)));
        assertFalse(reactorLevelChecker.isLevelSafe(levelList.get(4)));
        assertTrue(reactorLevelChecker.isLevelSafe(levelList.get(5)));
    }

    @Test
    public void countSafeLevelsUndampenedTest() {
        ReactorLevelChecker reactorLevelChecker = new ReactorLevelChecker();
        reactorLevelChecker.setDampenerEngaged(false);
        assertEquals(2, reactorLevelChecker.countSafeLevels(levelList));
    }

    @Test
    public void helper() {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(7);
        input.add(8);
        input.add(9);

        ReactorLevelChecker reactorLevelChecker = new ReactorLevelChecker();
        reactorLevelChecker.setDampenerEngaged(true);
        assertFalse(reactorLevelChecker.isLevelSafe(input));
    }

    @Test
    public void countSafeLevelsDampenedTest() {
        ReactorLevelChecker reactorLevelChecker = new ReactorLevelChecker();
        reactorLevelChecker.setDampenerEngaged(true);
        assertEquals(4, reactorLevelChecker.countSafeLevels(levelList));
    }

    @Test
    public void simpleGenerateDampenedLevelsListsTest() {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        List<List<Integer>> output = ReactorLevelChecker.generateDampenedLevelsLists(input);

        // Assert exactly two lists were generated.
        assertEquals(2, output.size());

        // Assert the first list has exactly one element.
        assertEquals(1, output.get(0).size());
        // Assert the first element in the first list equals 2.
        assertEquals(2, output.get(0).get(0));

        // Assert the second list has exactly one element.
        assertEquals(1, output.get(1).size());
        // Assert the first element in the second list equals 1.
        assertEquals(1, output.get(1).get(0));
    }

    @Test
    public void complexGenerateDampenedLevelsListsTest() {
        List<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        List<List<Integer>> output = ReactorLevelChecker.generateDampenedLevelsLists(input);

        // Assert exactly four lists were generated.
        assertEquals(4, output.size());

        // Assert the first list has exactly three elements.
        assertEquals(3, output.get(0).size());
        // Assert the first element in the first list equals 2.
        assertEquals(2, output.get(0).get(0));
        // Assert the second element in the first list equals 3.
        assertEquals(3, output.get(0).get(1));
        // Assert the third element in the first list equals 4.
        assertEquals(4, output.get(0).get(2));

        // Assert the second list has exactly three elements.
        assertEquals(3, output.get(1).size());
        // Assert the first element in the second list equals 1.
        assertEquals(1, output.get(1).get(0));
        // Assert the second element in the second list equals 3.
        assertEquals(3, output.get(1).get(1));
        // Assert the third element in the second list equals 4.
        assertEquals(4, output.get(1).get(2));

        // Assert the third list has exactly three elemenst.
        assertEquals(3, output.get(2).size());
        // Assert the first element in the third list equals 1.
        assertEquals(1, output.get(2).get(0));
        // Assert the second element in the third list equals 2.
        assertEquals(2, output.get(2).get(1));
        // Assert the third element in the third list equals 4.
        assertEquals(4, output.get(2).get(2));

        // Assert the fourth list has exactly three elements.
        assertEquals(3, output.get(3).size());
        // Assert the first element in the fourth list equals 1.
        assertEquals(1, output.get(3).get(0));
        // Assert the second element in the fourth list equals 2.
        assertEquals(2, output.get(3).get(1));
        // Assert the third element in the fourth list equals 3.
        assertEquals(3, output.get(3).get(2));
    }
}
