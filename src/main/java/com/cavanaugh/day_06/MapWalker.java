package com.cavanaugh.day_06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class MapWalker {
    private static final Map<String, GuardCharacter> GUARD_CHARACTERS;
    static {
        GUARD_CHARACTERS = new HashMap<>();
        GUARD_CHARACTERS.put("^", new GuardCharacter("^", ">", new SimpleEntry<>(-1, 0)));
        GUARD_CHARACTERS.put(">", new GuardCharacter(">", "v", new SimpleEntry<>(0, 1)));
        GUARD_CHARACTERS.put("v", new GuardCharacter("v", "<", new SimpleEntry<>(1, 0)));
        GUARD_CHARACTERS.put("<", new GuardCharacter("<", "^", new SimpleEntry<>(0, -1)));
    }

    private static final String WALL_CHARACTER = "#";
    private static final String MARKED_SPACE_CHARACTER = "X";
    private List<List<String>> map;
    private List<SimpleEntry<Integer, Integer>> walls;
    private List<SimpleEntry<Integer, Integer>> guards;
    private int tickCount = 0;

    public MapWalker() {
        map = new ArrayList<>();
        walls = new ArrayList<>();
        guards = new ArrayList<>();
    }

    public MapWalker(String input) {
        parseInput(input);
    }

    public MapWalker(String[] input) {
        parseInput(input);
    }

    public List<List<String>> getMap() {
        return map;
    }

    public List<SimpleEntry<Integer, Integer>> getWalls() {
        return walls;
    }

    public List<SimpleEntry<Integer, Integer>> getGuards() {
        return guards;
    }

    public int getTickCount() {
        return tickCount;
    }

    private void parseInput(String input) {
        String[] allLines = input.split(System.lineSeparator());
        parseInput(allLines);
    }

    private void parseInput(String[] allLines) {
        map = new ArrayList<>();
        walls = new ArrayList<>();
        guards = new ArrayList<>();

        for(int outerIndex = 0; outerIndex < allLines.length; outerIndex++) {
            String currentLine = allLines[outerIndex];
            List<String> currentRow = new ArrayList<>();

            String[] allCharactersInCurrentLine = currentLine.split("");
            for(int innerIndex = 0; innerIndex < allCharactersInCurrentLine.length; innerIndex++) {
                String currentCharacter = allCharactersInCurrentLine[innerIndex];
                currentRow.add(currentCharacter);

                // Add the current coordinates to the walls/guards lists here.
                if(WALL_CHARACTER.equals(currentCharacter)) {
                    walls.add(new SimpleEntry<>(outerIndex, innerIndex));
                } else if (GUARD_CHARACTERS.keySet().contains(currentCharacter)) {
                    guards.add(new SimpleEntry<>(outerIndex, innerIndex));
                }
            }

            map.add(currentRow);
        }
    }

    public void advanceUntilDone() {
        while(guards.isEmpty() == false) {
            updateTick();
        }
    }

    public void updateTick() {
        List<SimpleEntry<Integer, Integer>> updatedGuardsList = new ArrayList<>();

        for(int guardIndex = 0; guardIndex < guards.size(); guardIndex++) {
            SimpleEntry<Integer, Integer> currentGuard = guards.get(guardIndex);

            // Get the current character, using the coordinates of currentGuard.
            String currentGuardCharacter = map.get(currentGuard.getKey()).get(currentGuard.getValue());

            // adjust the current coordinates using the offsets in GUARD_CHARACTERS
            GuardCharacter objectNameGoesHere = GUARD_CHARACTERS.get(currentGuardCharacter);
            if(objectNameGoesHere == null) {
                // FIXME: This shouldn't happen!
                System.err.println("PROBLEM!");
                continue;
            }
            SimpleEntry<Integer, Integer> offsets = objectNameGoesHere.OFFSETS;
            SimpleEntry<Integer, Integer> newPosition = new SimpleEntry<>(currentGuard.getKey() + offsets.getKey(),
                                                                        currentGuard.getValue() + offsets.getValue());

            // If the new position is inside the bounds of the map...
            if(newPosition.getKey() >= 0 && newPosition.getKey() < map.size()
            && newPosition.getValue() >= 0 && newPosition.getValue() < map.get(0).size()) {
                String charAtNewPosition = map.get(newPosition.getKey()).get(newPosition.getValue());
                if(WALL_CHARACTER.equals(charAtNewPosition)) {
                    // Keep this guard with the old position.
                    updatedGuardsList.add(currentGuard);

                    // Update the map with the new character, rotated at a 90 degree angle!
                    map.get(currentGuard.getKey()).set(currentGuard.getValue(),
                        GUARD_CHARACTERS.get(currentGuardCharacter).NEXT_CHARACTER);
                } else {
                    // Keep this guard with the new positioning.
                    updatedGuardsList.add(newPosition);

                    // Update the old position on the map with the empty space character
                    map.get(currentGuard.getKey()).set(currentGuard.getValue(), MARKED_SPACE_CHARACTER);

                    // Update the new position on the map with the guard's character!
                    map.get(newPosition.getKey()).set(newPosition.getValue(), currentGuardCharacter);
                }
            } else {
                // Update the old position on the map with the empty space character
                map.get(currentGuard.getKey()).set(currentGuard.getValue(), MARKED_SPACE_CHARACTER);
            }
        }

        guards = updatedGuardsList;
        tickCount++;
    }

    public int countMarkedSpaces() {
        int count = 0;

        for(List<String> currentRow: map) {
            for(String currentCharacter: currentRow) {
                if(MARKED_SPACE_CHARACTER.equals(currentCharacter)) {
                    count++;
                }
            }
        }

        return count;
    }
}
