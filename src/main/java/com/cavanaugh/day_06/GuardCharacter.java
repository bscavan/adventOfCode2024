package com.cavanaugh.day_06;

import java.util.AbstractMap.SimpleEntry;

public class GuardCharacter {
    public final String CHARACTER;
    public final String NEXT_CHARACTER;
    public final SimpleEntry<Integer, Integer> OFFSETS;

    public GuardCharacter(String character, String nextCharacter, SimpleEntry<Integer, Integer> offsets) {
        CHARACTER = character;
        NEXT_CHARACTER = nextCharacter;
        OFFSETS = offsets;
    }
}