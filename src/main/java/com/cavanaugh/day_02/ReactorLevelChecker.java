package com.cavanaugh.day_02;

import java.util.ArrayList;
import java.util.List;

public class ReactorLevelChecker {
    public static final String DELIMITER = " ";
    public static final int MAX_STEP_SIZE = 3;
    private boolean dampenerEngaged = true;

    public ReactorLevelChecker() {
    }

    public ReactorLevelChecker(boolean dampenerEngaged) {
        this();
        this.dampenerEngaged = dampenerEngaged;
    }

    public void setDampenerEngaged(boolean dampenerEngaged) {
        this.dampenerEngaged = dampenerEngaged;
    }

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

    public boolean isLevelSafe(List<Integer> inputNumbers) {
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

    public int countSafeLevels(List<List<Integer>> input) {
        int safeLevelsCount = 0;

        for(List<Integer> currentLevels: input) {
            if(isLevelSafe(currentLevels)) {
                safeLevelsCount++;
                continue;
            }

            if(dampenerEngaged) {
                for(List<Integer> currentLevelsDampened: generateDampenedLevelsLists(currentLevels)) {
                    if(isLevelSafe(currentLevelsDampened)) {
                        safeLevelsCount++;
                        break;
                    }
                }
            }
        }

        return safeLevelsCount;
    }

    public static List<List<Integer>> generateDampenedLevelsLists(List<Integer> inputLevels) {
        if(inputLevels.size() < 2) {
            return new ArrayList<>();
        }

        List<List<Integer>> dampenedLists = new ArrayList<>();

        /*
         * Iterate over every index in the array to be "dampened." For every
         * element a new List will be constructed without that element. So for
         * an array of length 'n,' another 'n' arrays will be generated.
         */
        for(int currentDampenedIndex = 0; currentDampenedIndex < inputLevels.size(); currentDampenedIndex++) {
            List<Integer> currentDampenedLevel = new ArrayList<>();

            /*
             * For each element that doesn't match the currentDampenedIndex
             * add it to currentDampenedLevel.
             */
            for(int index = 0; index < inputLevels.size(); index++) {
                if(index != currentDampenedIndex) {
                    currentDampenedLevel.add(inputLevels.get(index));
                }
            }

            dampenedLists.add(currentDampenedLevel);
        }

        return dampenedLists;
    }
}
