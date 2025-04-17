package com.cavanaugh.day_06;

import java.util.AbstractMap.SimpleEntry;

public class TurnData {
    private SimpleEntry<Integer, Integer> coordinates;
    private Direction origDirection;
    
    TurnData(SimpleEntry<Integer, Integer> coordinates, Direction origDirection) {
        this.coordinates = coordinates;
        this.origDirection = origDirection;
    }

    public SimpleEntry<Integer, Integer> getCoordinates() {
        return this.coordinates;
    }

    public Direction getOrigDirection() {
        return origDirection;
    }
}
