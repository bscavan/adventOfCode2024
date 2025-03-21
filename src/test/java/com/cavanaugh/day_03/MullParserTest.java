package com.cavanaugh.day_03;

import java.util.AbstractMap.SimpleEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MullParserTest {
    
    @BeforeEach
    public void setup() {

    }

    @Test
    public void parseMullTextTest() {
        String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";

        List<Entry<Integer, Integer>> expectedOutput = new ArrayList<>();
        expectedOutput.add(new SimpleEntry<>(2, 4));
        expectedOutput.add(new SimpleEntry<>(5, 5));
        expectedOutput.add(new SimpleEntry<>(11, 8));
        expectedOutput.add(new SimpleEntry<>(8, 5));

        MullParser mullParser = new MullParser();
        mullParser.setPreprocessInput(false);
        List<Entry<Integer, Integer>> actualOutput = mullParser.parseMullText(input);

        assertEquals(expectedOutput.size(), actualOutput.size());

        assertEquals(expectedOutput.get(0).getKey(), actualOutput.get(0).getKey());
        assertEquals(expectedOutput.get(0).getValue(), actualOutput.get(0).getValue());
        

        assertEquals(expectedOutput.get(1).getKey(), actualOutput.get(1).getKey());
        assertEquals(expectedOutput.get(1).getValue(), actualOutput.get(1).getValue());
        

        assertEquals(expectedOutput.get(2).getKey(), actualOutput.get(2).getKey());
        assertEquals(expectedOutput.get(2).getValue(), actualOutput.get(2).getValue());
        

        assertEquals(expectedOutput.get(3).getKey(), actualOutput.get(3).getKey());
        assertEquals(expectedOutput.get(3).getValue(), actualOutput.get(3).getValue());
    }

    @Test
    public void calculateProductTest() {
        String input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";

        MullParser mullParser = new MullParser(input);
        int output = mullParser.calculateProduct();

        assertEquals(161, output);
    }

    @Test
    public void preprocessInputTest() {
        String input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
        String expectedOutput = "xmul(2,4)&mul[3,7]!^do()?mul(8,5))";

        MullParser mullParser = new MullParser();
        String actualOuptut = mullParser.preprocessText(input);

        assertEquals(expectedOutput, actualOuptut);
    }

    @Test
    public void parseMullTextWithDisableCommandsTest() {
        String input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

        List<Entry<Integer, Integer>> expectedOutput = new ArrayList<>();
        expectedOutput.add(new SimpleEntry<>(2, 4));
        expectedOutput.add(new SimpleEntry<>(8, 5));

        MullParser mullParser = new MullParser();
        mullParser.setPreprocessInput(true);
        List<Entry<Integer, Integer>> actualOutput = mullParser.parseMullText(input);

        assertEquals(expectedOutput.size(), actualOutput.size());

        assertEquals(expectedOutput.get(0).getKey(), actualOutput.get(0).getKey());
        assertEquals(expectedOutput.get(0).getValue(), actualOutput.get(0).getValue());
        

        assertEquals(expectedOutput.get(1).getKey(), actualOutput.get(1).getKey());
        assertEquals(expectedOutput.get(1).getValue(), actualOutput.get(1).getValue());
    }

    @Test
    public void calculateProductWithDisableCommandsTest() {
        String input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

        MullParser mullParser = new MullParser(input);
        mullParser.setPreprocessInput(true);
        int output = mullParser.calculateProduct();

        assertEquals(48, output);
    }
}
