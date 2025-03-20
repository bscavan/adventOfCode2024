package com.cavanaugh.day_02;

import java.util.ArrayList;
import java.util.List;

public class ReactorLevelChecker {
    public static final String DELIMITER = " ";
    public static final int MAX_STEP_SIZE = 3;

    public static List<List<Integer>> convertTextToReactorLevels(String[] inputText) {
        List<List<Integer>> returnValues = new ArrayList<>();

        for(String currentLine: inputText) {
            List<Integer> currentLevel = new ArrayList<>();

            for(String current: currentLine.split(DELIMITER)) {
                try {
                    currentLevel.add(Integer.parseInt(current));
                } catch (NumberFormatException nfex) {
                    System.err.println("Failed to convert the text [" + current + "] to an integer.");
                    nfex.printStackTrace();
                }
            }

            returnValues.add(currentLevel);
        }

        return returnValues;
    }

    public static boolean isLevelSafe(List<Integer> inputNumbers) {
        if(inputNumbers.size() < 2) {
            return true;
        }

        int first = inputNumbers.get(0);
        int second = inputNumbers.get(1);

        if(inputNumbers.size() == 2) {
            if(first == second || Math.abs(first - second) > MAX_STEP_SIZE) {
                return false;
            }

            return true;
        }

        boolean decreasing = first > second;
        int current = first;

        for(int index = 1; index < inputNumbers.size(); index++) {
            int next = inputNumbers.get(index);

            if(decreasing) {
                if(next >= current || next < current - MAX_STEP_SIZE) {
                    return false;
                }
            } else {
                if(next <= current || next > current + MAX_STEP_SIZE) {
                    return false;
                }
            }

            current = next;
        }

        return true;
    }

    public static int countSafeLevels(List<List<Integer>> input) {
        int safeLevelsCount = 0;

        for(List<Integer> currentLevels: input) {
            if(isLevelSafe(currentLevels)) {
                safeLevelsCount++;
            }
        }

        return safeLevelsCount;
    }
}
