package com.cavanaugh.day_06;

import java.util.AbstractMap.SimpleEntry;

public class GuardCharacter {
    public final String CHARACTER;
    public final Direction NEXT_DIRECTION;
    public final SimpleEntry<Integer, Integer> OFFSETS;

    public GuardCharacter(String character, Direction nextDirection, SimpleEntry<Integer, Integer> offsets) {
        CHARACTER = character;
        NEXT_DIRECTION = nextDirection;
        OFFSETS = offsets;
    }
}