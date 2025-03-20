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
        assertTrue(ReactorLevelChecker.isLevelSafe(levelList.get(0)));
        assertFalse(ReactorLevelChecker.isLevelSafe(levelList.get(1)));
        assertFalse(ReactorLevelChecker.isLevelSafe(levelList.get(2)));
        assertFalse(ReactorLevelChecker.isLevelSafe(levelList.get(3)));
        assertFalse(ReactorLevelChecker.isLevelSafe(levelList.get(4)));
        assertTrue(ReactorLevelChecker.isLevelSafe(levelList.get(5)));
    }

    @Test
    public void countSafeLevelsTest() {
        assertEquals(2, ReactorLevelChecker.countSafeLevels(levelList));
    }
}
