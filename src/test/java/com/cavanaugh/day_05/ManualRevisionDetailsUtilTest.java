package com.cavanaugh.day_05;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManualRevisionDetailsUtilTest {
    public static final String rawOrderingRules =
        "47|53" + System.lineSeparator() +
        "97|13" + System.lineSeparator() +
        "97|61" + System.lineSeparator() +
        "97|47" + System.lineSeparator() +
        "75|29" + System.lineSeparator() +
        "61|13" + System.lineSeparator() +
        "75|53" + System.lineSeparator() +
        "29|13" + System.lineSeparator() +
        "97|29" + System.lineSeparator() +
        "53|29" + System.lineSeparator() +
        "61|53" + System.lineSeparator() +
        "97|53" + System.lineSeparator() +
        "61|29" + System.lineSeparator() +
        "47|13" + System.lineSeparator() +
        "75|47" + System.lineSeparator() +
        "97|75" + System.lineSeparator() +
        "47|61" + System.lineSeparator() +
        "75|61" + System.lineSeparator() +
        "47|29" + System.lineSeparator() +
        "75|13" + System.lineSeparator() +
        "53|13";

    public static final String rawRevisionData =
        "75,47,61,53,29" + System.lineSeparator() + //
        "97,61,53,29,13" + System.lineSeparator() +
        "75,29,13" + System.lineSeparator() +
        "75,97,47,61,53" + System.lineSeparator() +
        "61,13,29" + System.lineSeparator() +
        "97,13,75,29,47";

    private static Map<Integer, List<Integer>> expectedOrderingRules;

    @BeforeEach
    public void setup() {
        expectedOrderingRules = new HashMap<>();

        List<Integer> nintySeven = new ArrayList<>();
        nintySeven.add(13);
        nintySeven.add(61);
        nintySeven.add(29);
        nintySeven.add(47);
        nintySeven.add(53);
        nintySeven.add(75);
        expectedOrderingRules.put(97, nintySeven);

        List<Integer> seventyFive = new ArrayList<>();
        seventyFive.add(29);
        seventyFive.add(53);
        seventyFive.add(13);
        seventyFive.add(61);
        seventyFive.add(47);
        expectedOrderingRules.put(75, seventyFive);

        List<Integer> fourtySeven = new ArrayList<>();
        fourtySeven.add(13);
        fourtySeven.add(61);
        fourtySeven.add(29);
        fourtySeven.add(53);
        expectedOrderingRules.put(47, fourtySeven);

        List<Integer> sixtyOne = new ArrayList<>();
        sixtyOne.add(53);
        sixtyOne.add(29);
        sixtyOne.add(13);
        expectedOrderingRules.put(61, sixtyOne);

        List<Integer> fifthThree = new ArrayList<>();
        fifthThree.add(29);
        fifthThree.add(13);
        expectedOrderingRules.put(53, fifthThree);

        List<Integer> twentyNine = new ArrayList<>();
        twentyNine.add(13);
        expectedOrderingRules.put(29, twentyNine);
    }

    @Test
    public void convertRawOrderingRulesIntoMapTest() {
        Map<Integer, List<Integer>> actualOrderingRules = ManualRevisionDetailsUtil.convertRawOrderingRulesIntoMap(rawOrderingRules.split(System.lineSeparator()));

        assertEquals(expectedOrderingRules.size(), actualOrderingRules.size());

        for(Integer currentKey: expectedOrderingRules.keySet()) {
            assertTrue(actualOrderingRules.containsKey(currentKey), "The expected key [" + currentKey + "] was not in the provided output map.");

            List<Integer> expectedRulesForCurrent = expectedOrderingRules.get(currentKey);
            List<Integer> actualRulesForCurrent = actualOrderingRules.get(currentKey);
            assertEquals(expectedRulesForCurrent.size(), actualRulesForCurrent.size());

            for(Integer currentValue: expectedRulesForCurrent) {
                assertTrue(actualRulesForCurrent.contains(currentValue), "The expected value [" + currentValue + "], for the key [" + currentKey +"], was not in the provided output map.");
            }
        }
    }

    @Test
    public void instantiateTest() {
        String fullInput = rawOrderingRules + System.lineSeparator() + System.lineSeparator() + rawRevisionData;
        List<String> listedInput = new ArrayList<String>(Arrays.asList(fullInput.split(System.lineSeparator())));
        ManualRevisionDetailsUtil manualRevisionDetailsUtil = new ManualRevisionDetailsUtil(listedInput);
        Map<Integer, List<Integer>> actualOrderingRules = manualRevisionDetailsUtil.getOrderingRules();

        assertEquals(6, actualOrderingRules.size());
        assertEquals(expectedOrderingRules.size(), actualOrderingRules.size());

        for(Integer currentKey: expectedOrderingRules.keySet()) {
            assertTrue(actualOrderingRules.containsKey(currentKey), "The expected key [" + currentKey + "] was not in the provided output map.");

            List<Integer> expectedRulesForCurrent = expectedOrderingRules.get(currentKey);
            List<Integer> actualRulesForCurrent = actualOrderingRules.get(currentKey);
            assertEquals(expectedRulesForCurrent.size(), actualRulesForCurrent.size());

            for(Integer currentValue: expectedRulesForCurrent) {
                assertTrue(actualRulesForCurrent.contains(currentValue), "The expected value [" + currentValue + "], for the key [" + currentKey +"], was not in the provided output map.");
            }
        }

        assertEquals(6, manualRevisionDetailsUtil.getPagesToRevise().size());
        // TODO: Add assertions about the revision pages here.
    }

    @Test
    public void doPagesToReviseFollowOrderingRulesTest() {
        ManualRevisionDetailsUtil manualRevisionDetailsUtil = new ManualRevisionDetailsUtil();
        manualRevisionDetailsUtil.setOrderingRules(ManualRevisionDetailsUtil.convertRawOrderingRulesIntoMap(rawOrderingRules.split(System.lineSeparator())));

        assertTrue(manualRevisionDetailsUtil.doPagesToReviseFollowOrderingRules(
            ManualRevisionDetailsUtil.convertRawPagesIntoList("75,47,61,53,29")),
            "The provided pages to revise should match the ordering rules, but were rejected!");

        assertFalse(manualRevisionDetailsUtil.doPagesToReviseFollowOrderingRules(
            ManualRevisionDetailsUtil.convertRawPagesIntoList(("75,97,47,61,53"))),
            "The provided pages to revise do not match the ordering rules, but weren't rejected!");
    }

    @Test
    public void correctOrderOfPagesTest() {
        List<Integer> input = new ArrayList<>();
        input.add(75);
        input.add(97);
        input.add(47);
        input.add(61);
        input.add(53);

        List<Integer> expectedOutput = new ArrayList<>();
        expectedOutput.add(97);
        expectedOutput.add(75);
        expectedOutput.add(47);
        expectedOutput.add(61);
        expectedOutput.add(53);

        ManualRevisionDetailsUtil manualRevisionDetailsUtil = new ManualRevisionDetailsUtil();
        manualRevisionDetailsUtil.setOrderingRules(ManualRevisionDetailsUtil.convertRawOrderingRulesIntoMap(rawOrderingRules.split(System.lineSeparator())));

        manualRevisionDetailsUtil.setShouldTotalCorrectRevisions(true);
        List<Integer> actualOutput = manualRevisionDetailsUtil.reorderPagesToFollowOrderingRules(input);

        assertEquals(expectedOutput.size(), actualOutput.size());

        for(int index = 0; index < expectedOutput.size(); index++) {
            assertEquals(expectedOutput.get(index), actualOutput.get(index));
        }
    }

    @Test
    public void endToEndWithoutOrderCorrectionTest() {
        String fullInput = rawOrderingRules + System.lineSeparator() + System.lineSeparator() + rawRevisionData;
        List<String> listedInput = new ArrayList<String>(Arrays.asList(fullInput.split(System.lineSeparator())));
        ManualRevisionDetailsUtil manualRevisionDetailsUtil = new ManualRevisionDetailsUtil(listedInput);
        manualRevisionDetailsUtil.setShouldTotalCorrectRevisions(true);
        manualRevisionDetailsUtil.setShouldTotalWrongRevisions(false);
        assertEquals(143, manualRevisionDetailsUtil.totalUpCenterElementsOfPageists());
    }

    @Test
    public void endToEndWithOrderCorrectionTest() {
        String fullInput = rawOrderingRules + System.lineSeparator() + System.lineSeparator() + rawRevisionData;
        List<String> listedInput = new ArrayList<String>(Arrays.asList(fullInput.split(System.lineSeparator())));
        ManualRevisionDetailsUtil manualRevisionDetailsUtil = new ManualRevisionDetailsUtil(listedInput);
        manualRevisionDetailsUtil.setShouldTotalCorrectRevisions(false);
        manualRevisionDetailsUtil.setShouldTotalWrongRevisions(true);
        assertEquals(123, manualRevisionDetailsUtil.totalUpCenterElementsOfPageists());
    }
}
