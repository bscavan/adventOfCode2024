package com.cavanaugh.day_03;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.AbstractMap.SimpleEntry;

public class MullParser {
    public static final String MULL_PATTERN = "mul\\((\\d\\d?\\d?),(\\d\\d?\\d?)\\)";
    public static final String DISABLE_FLAG = "don't()";
    public static final String ENABLE_FLAG = "do()";
    private List<String> inputValues;
    private boolean preprocessInput = false;
    
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

    public void setPreprocessInput(boolean preprocessInput) {
        this.preprocessInput = preprocessInput;
    }

    public String preprocessText(String inputText) {
        String[] disassembledInput = inputText.split(DISABLE_FLAG);
        String onlyEnabledInput = disassembledInput[0];

        // Skip the first element because it was already included.
        for(int index = 1; index < disassembledInput.length; index++) {
            String currentText = disassembledInput[index];
            int resumeIndex = currentText.indexOf(ENABLE_FLAG);

            if(resumeIndex > 0) {
                // Append everything after the enable command begins to onlyEnabledInput
                onlyEnabledInput = onlyEnabledInput + currentText.substring(resumeIndex);
            }
        }
        
        return onlyEnabledInput;
    }

    public List<Entry<Integer, Integer>> parseMullText(String inputText) {
        String inputToUse = inputText;

        if(preprocessInput) {
            inputToUse = preprocessText(inputText);
        }

        List<Entry<Integer, Integer>> valuesToMultiply = new ArrayList<>();

        Pattern pattern = Pattern.compile(MULL_PATTERN);
        Matcher matcher = pattern.matcher(inputToUse);

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
        StringBuilder builder = new StringBuilder();

        for(String currentInput: inputValues) {
            builder.append(currentInput);
        }

        for(Entry<Integer, Integer> current: parseMullText(builder.toString())) {
            runningTotal = runningTotal + current.getKey() * current.getValue();
        }

        return runningTotal;
    }
}
