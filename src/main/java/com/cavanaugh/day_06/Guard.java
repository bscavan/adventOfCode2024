package com.cavanaugh.day_06;

import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

public class Guard {
    public static final Map<Direction, GuardCharacter> GUARD_CHARACTERS;
    static {
        GUARD_CHARACTERS = new HashMap<>();
        GUARD_CHARACTERS.put(Direction.NORTH, new GuardCharacter(Direction.NORTH.label, Direction.EAST, new SimpleEntry<>(-1, 0)));
        GUARD_CHARACTERS.put(Direction.EAST, new GuardCharacter(Direction.EAST.label, Direction.SOUTH, new SimpleEntry<>(0, 1)));
        GUARD_CHARACTERS.put(Direction.SOUTH, new GuardCharacter(Direction.SOUTH.label, Direction.WEST, new SimpleEntry<>(1, 0)));
        GUARD_CHARACTERS.put(Direction.WEST, new GuardCharacter(Direction.WEST.label, Direction.NORTH, new SimpleEntry<>(0, -1)));
    }

    private SimpleEntry<Integer, Integer> coordinates;
    private List<TurnData> turnsTaken;
    private boolean caughtInLoop = false;
    private Direction currentDirection;

    public Guard(SimpleEntry<Integer, Integer> coordinates, Direction currentDirection, List<TurnData> turnsTaken) {
        this.coordinates = coordinates;
        this.currentDirection = currentDirection;
        this.turnsTaken = turnsTaken;
    }

    public SimpleEntry<Integer, Integer> getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(SimpleEntry<Integer, Integer> newCoordinates) {
        coordinates = newCoordinates;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void addNewTurn(TurnData newTurnData) {
        boolean alreadyRecorded = false;

        for(TurnData currentData: turnsTaken) {
            if(currentData.getCoordinates().getKey() == newTurnData.getCoordinates().getKey()
            && currentData.getCoordinates().getValue() == newTurnData.getCoordinates().getValue()
            && currentData.getOrigDirection().equals(newTurnData.getOrigDirection())) {
                caughtInLoop = true;
                alreadyRecorded = true;
            }
        }

        if(alreadyRecorded == false) {
            turnsTaken.add(newTurnData);
            this.currentDirection = GUARD_CHARACTERS.get(currentDirection).NEXT_DIRECTION;
        }
    }

    public boolean isCaughtInLoop() {
        return caughtInLoop;
    }
}
