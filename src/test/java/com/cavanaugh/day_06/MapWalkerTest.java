package com.cavanaugh.day_06;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MapWalkerTest {
    public static final String rawMapData =
    "....#....." + System.lineSeparator() +
    ".........#" + System.lineSeparator() +
    ".........." + System.lineSeparator() +
    "..#......." + System.lineSeparator() +
    ".......#.." + System.lineSeparator() +
    ".........." + System.lineSeparator() +
    ".#..^....." + System.lineSeparator() +
    "........#." + System.lineSeparator() +
    "#........." + System.lineSeparator() +
    "......#...";

    /*
     * Tests to write:
     *  1, should parse the input.
     *  2, should identify where the guard is
     *  3, should identify where the walls are
     *  4, should move the guard forward until it hit a wall
     *      Each square stepped on should have a # mark on it.
     *  5, should turn right after hitting a wall,
     *  6, should continue processing after turning right.
     *  7, Should continue until it hits the edge of the map
     *      The tick count should match the expected amount after processing.
     *      The number of marked squares should match the expected amount.
     */

    @Test
    public void parseInputTest() {
        MapWalker mapWalker = new MapWalker(rawMapData);
        List<List<String>> parsedInput = mapWalker.getMap();

        assertEquals(10, parsedInput.size());

        for(int index = 0; index < parsedInput.size(); index++) {
            assertEquals(10, parsedInput.get(index).size());
        }

        assertEquals(8, mapWalker.getWalls().size());
        // Assert each wall is at the correct place
        assertEquals(0, mapWalker.getWalls().get(0).getKey());
        assertEquals(4, mapWalker.getWalls().get(0).getValue());
        assertEquals(1, mapWalker.getWalls().get(1).getKey());
        assertEquals(9, mapWalker.getWalls().get(1).getValue());
        assertEquals(3, mapWalker.getWalls().get(2).getKey());
        assertEquals(2, mapWalker.getWalls().get(2).getValue());
        assertEquals(4, mapWalker.getWalls().get(3).getKey());
        assertEquals(7, mapWalker.getWalls().get(3).getValue());
        assertEquals(6, mapWalker.getWalls().get(4).getKey());
        assertEquals(1, mapWalker.getWalls().get(4).getValue());
        assertEquals(7, mapWalker.getWalls().get(5).getKey());
        assertEquals(8, mapWalker.getWalls().get(5).getValue());
        assertEquals(8, mapWalker.getWalls().get(6).getKey());
        assertEquals(0, mapWalker.getWalls().get(6).getValue());
        assertEquals(9, mapWalker.getWalls().get(7).getKey());
        assertEquals(6, mapWalker.getWalls().get(7).getValue());

        assertEquals(1, mapWalker.getGuards().size());
        // Assert the Guard is in the correct place.
        assertEquals(6, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());
    }

    @Test
    public void updateTickTest() {
        MapWalker mapWalker = new MapWalker(rawMapData);

        assertEquals(1, mapWalker.getGuards().size());
        // Assert the Guard is in the correct place.
        assertEquals(6, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());

        mapWalker.updateTick();

        // Assert the Guard has moved forward one space.
        assertEquals(5, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());

        mapWalker.updateTick();

        // Assert the Guard has moved forward one space.
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());

        mapWalker.updateTick();

        // Assert the Guard has moved forward one space.
        assertEquals(3, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());

        mapWalker.updateTick();

        // Assert the Guard has moved forward one space.
        assertEquals(2, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());

        mapWalker.updateTick();

        // Assert the Guard has moved forward one space.
        assertEquals(1, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());

        mapWalker.updateTick();

        // Assert the Guard has NOT moved forward.
        assertEquals(1, mapWalker.getGuards().get(0).getCoordinates().getKey());
        assertEquals(4, mapWalker.getGuards().get(0).getCoordinates().getValue());

        // Assert that the guard has turned 90 degrees
        List<List<String>> parsedInput = mapWalker.getMap();
        SimpleEntry<Integer, Integer> guard = mapWalker.getGuards().get(0).getCoordinates();
        String guardCharacter = parsedInput.get(guard.getKey()).get(guard.getValue());
        assertEquals(">", guardCharacter);
    }

    @Test
    public void advanceUntilDoneTest() {
        MapWalker mapWalker = new MapWalker(rawMapData);
        mapWalker.setHardTickLimit(55);
        mapWalker.advanceUntilDone();
        assertEquals(41, mapWalker.countMarkedSpaces());
    }

    @Test
    public void isInLoopTest() {
        String boxMap =
            "###" + System.lineSeparator()
            + "#^#" + System.lineSeparator()
            + "###";
        MapWalker mapWalker = new MapWalker(boxMap);
        assertEquals(1, mapWalker.getGuards().size());

        mapWalker.updateTick();
        mapWalker.updateTick();
        mapWalker.updateTick();
        mapWalker.updateTick();
        mapWalker.updateTick();

        // TODO: Assert that the first guard is caught in a loop!
        assertTrue(mapWalker.getGuards().get(0).isCaughtInLoop());
    }
}
