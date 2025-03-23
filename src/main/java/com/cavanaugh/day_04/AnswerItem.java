package com.cavanaugh.day_04;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

public class AnswerItem {
    public class Coordinates {
        SimpleEntry<Integer, Integer> startingPosition;
        SimpleEntry<Integer, Integer> endingPosition;

        public Coordinates(SimpleEntry<Integer, Integer> startingPosition, SimpleEntry<Integer, Integer> endingPosition) {
            this.startingPosition = startingPosition;
            this.endingPosition = endingPosition;
        }

        public SimpleEntry<Integer, Integer> getStartingPosition() {
            return startingPosition;
        }

        public SimpleEntry<Integer, Integer> getEndingPosition() {
            return endingPosition;
        }
    }

    private String text;
    private List<Coordinates> coordinates = new ArrayList<>();
    private int[] startingPosition = new int[2];
    private int[] endingPosition = new int[2];
    // private boolean hasBeenFound = false;
    private int timesFound = 0;

    public AnswerItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    // public int[] getStartingPosition() {
    //     return startingPosition;
    // }

    // public void setStartingPosition(int y, int x) {
    //     startingPosition[0] = y;
    //     startingPosition[1] = x;
    // }

    // public int[] getEndngPosition() {
    //     return endingPosition;
    // }

    // public void setEndngPosition(int y, int x) {
    //     endingPosition[0] = y;
    //     endingPosition[1] = x;
    // }

    public int getTimesFound() {
        return timesFound;
    }

    public void incrementTimesFound() {
        timesFound++;
    }

    // public boolean getHasBeenFound() {
    //     return hasBeenFound;
    // }

    // public void setHasBeenFound(boolean hasBeenFound) {
    //     this.hasBeenFound = hasBeenFound;
    // }

    public void inputFoundCoordinates(int[][] newCoordinates) {
        // setHasBeenFound(true);
        incrementTimesFound();
        coordinates.add(
            new Coordinates(
                new SimpleEntry<Integer, Integer>(newCoordinates[0][0], newCoordinates[0][1]),
                new SimpleEntry<Integer, Integer>(newCoordinates[newCoordinates.length - 1][0], newCoordinates[newCoordinates.length - 1][1])
            )
        );
        // setStartingPosition(newCoordinates[0][0], newCoordinates[0][1]);
        // setEndngPosition(newCoordinates[newCoordinates.length - 1][0], newCoordinates[newCoordinates.length - 1][1]);
    }
}