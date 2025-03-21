package com.cavanaugh.day_03;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.AbstractMap.SimpleEntry;

public class MullParser {
    public static final String MULL_PATTERN = "mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)";
    private List<String> inputValues;
    
    public MullParser() {
        inputValues = new ArrayList<>();
    }

    public MullParser(String input) {
        this();
        inputValues.add(input);
    }

    public MullParser(List<String> input) {
        inputValues = input;
    }

    public static List<Entry<Integer, Integer>> parseMullText(String inputText) {
        List<Entry<Integer, Integer>> valuesToMultiply = new ArrayList<>();

        Pattern pattern = Pattern.compile(MULL_PATTERN);
        Matcher matcher = pattern.matcher(inputText);

        while(matcher.find()) {
            try {
                valuesToMultiply.add(new SimpleEntry<>(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            } catch(IndexOutOfBoundsException iOfBoundsException) {
                // TODO: Slap the user's hand for this one.
                iOfBoundsException.printStackTrace();
            }
        }

        return valuesToMultiply;
    }

    public int calculateProduct() {
        int runningTotal = 0;

        for(String currentInput: inputValues) {
            for(Entry<Integer, Integer> current: parseMullText(currentInput)) {
                runningTotal = runningTotal + current.getKey() * current.getValue();
            }
        }

        return runningTotal;
    }
}
