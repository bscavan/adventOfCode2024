package com.cavanaugh.day_06;

public enum Direction {
    NORTH("^"),
    EAST(">"),
    SOUTH("v"),
    WEST("<");

    public final String label;

    private Direction(String label) {
        this.label = label;
    }

    public static Direction getDirection(String dirCharacter) {
        switch(dirCharacter) {
            case "^":
                return NORTH;
            case ">":
                return EAST;
            case "v":
                return SOUTH;
            default:
                return WEST;
        }
    }
}
