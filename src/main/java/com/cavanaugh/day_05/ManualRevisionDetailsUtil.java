package com.cavanaugh.day_05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManualRevisionDetailsUtil {
    public static final String LINE_DELIMITER = "\\|";
    public static final String PAGES_DELIMITER = ",";
    private boolean shouldTotalCorrectRevisions = true;
    private boolean shouldTotalWrongRevisions = false;
    private Map<Integer, List<Integer>> orderingRules;
    private List<List<Integer>> pagesToRevise;

    public ManualRevisionDetailsUtil() {
        orderingRules = new HashMap<>();
        pagesToRevise = new ArrayList<>();
    }

    public ManualRevisionDetailsUtil(Map<Integer, List<Integer>> orderingRules, List<List<Integer>> pagesToRevise) {
        this.orderingRules = orderingRules;
        this.pagesToRevise = pagesToRevise;
    }

    public ManualRevisionDetailsUtil(List<String> inputData) {
        pagesToRevise = new ArrayList<>();
        StringBuilder orderingRulesData = new StringBuilder();
        StringBuilder pageRevisionData = new StringBuilder();
        StringBuilder currentTarget = orderingRulesData;

        /*
         * Iterates over inputData and appends it to orderintRulesData until an
         * empty line is encountered. Afterwards it switches to pageRevisionData.
         */
        for(String currentLine: inputData) {
            if(currentLine.trim().isEmpty()) {
                currentTarget = pageRevisionData;
            } else {
                // FIXME: Instead of re-adding line-separators here, just refactor the later methods to accept Lists of Strings.
                currentTarget.append(currentLine + System.lineSeparator());
            }
        }

        orderingRules = ManualRevisionDetailsUtil.convertRawOrderingRulesIntoMap(orderingRulesData.toString().split(System.lineSeparator()));

        for(String currentRevisionPageData: pageRevisionData.toString().split(System.lineSeparator())) {
            pagesToRevise.add(convertRawPagesIntoList(currentRevisionPageData));
        }
    }

    public boolean getShouldTotalCorrectRevisions() {
        return shouldTotalCorrectRevisions;
    }

    public void setShouldTotalCorrectRevisions(boolean shouldCorrectRevisionOrder) {
        this.shouldTotalCorrectRevisions = shouldCorrectRevisionOrder;
    }

    public boolean getShouldTotalWrongRevisions() {
        return shouldTotalWrongRevisions;
    }

    public void setShouldTotalWrongRevisions(boolean shouldTotalWrongRevisions) {
        this.shouldTotalWrongRevisions = shouldTotalWrongRevisions;
    }

    // Accepts a CSV
    public static List<Integer> convertRawPagesIntoList(String rawPagesData) {
        List<Integer> convertedPageData = new ArrayList<>();

        for(String str: rawPagesData.split(PAGES_DELIMITER)) {
            try {
                convertedPageData.add(Integer.parseInt(str));
            } catch (NumberFormatException nFormatException) {
                System.err.println("Failed to convert one of the page numbers in this list to an integer: " + rawPagesData);
                nFormatException.printStackTrace();
            }
        }

        return convertedPageData;
    }

    public Map<Integer, List<Integer>> getOrderingRules() {
        return orderingRules;
    }

    public void setOrderingRules(Map<Integer, List<Integer>> orderingRules) {
        this.orderingRules = orderingRules;
    }

    public static Map<Integer, List<Integer>> convertRawOrderingRulesIntoMap(String[] rawRules) {
        return convertRawOrderingRulesIntoMap(new ArrayList<String>(Arrays.asList(rawRules)));
    }

    public static Map<Integer, List<Integer>> convertRawOrderingRulesIntoMap(List<String> rawRules) {
        Map<Integer, List<Integer>> orderingRules = new HashMap<>();

        for(String currentLine:rawRules) {
            String[] splitRule = currentLine.split(LINE_DELIMITER);
            Integer newKey = Integer.parseInt(splitRule[0]);
            List<Integer> rulesForCurrentKey = orderingRules.get(newKey);

            if(rulesForCurrentKey == null) {
                rulesForCurrentKey = new ArrayList<>();
            }

            Integer newValue = Integer.parseInt(splitRule[1]);
            if(rulesForCurrentKey.contains(newValue) == false) {
                rulesForCurrentKey.add(newValue);
            }

            orderingRules.put(newKey, rulesForCurrentKey);
        }

        return orderingRules;
    }

    public List<List<Integer>> getPagesToRevise() {
        return pagesToRevise;
    }

    public boolean doPagesToReviseFollowOrderingRules(List<Integer> pagesToRevise) {
        // Iterate over every element in pagesToRevise
        for(int pageIndex = 0; pageIndex < pagesToRevise.size(); pageIndex++) {
            Integer currentPageNumber = pagesToRevise.get(pageIndex);
            // Find every entry in orderingRules that has a key matching the current page.
            List<Integer> pagesThatCannotExistBeforeCurrent = orderingRules.get(currentPageNumber);

            if(pagesThatCannotExistBeforeCurrent == null || pagesThatCannotExistBeforeCurrent.size() == 0) {
                continue;
            }

            // Check the preceeding pages and ensure none of them exist in the ordering rules for the current page.
            for(int previousPagesIndex = 0; previousPagesIndex < pageIndex; previousPagesIndex++) {
                Integer previousPageNumber = pagesToRevise.get(previousPagesIndex);
                if(pagesThatCannotExistBeforeCurrent.contains(previousPageNumber)) {
                    return false;
                }
            }
        }

        return true;
    }

    public List<Integer> reorderPagesToFollowOrderingRules(List<Integer> pagesToReorder) {
        List<Integer> reorderedPages = new ArrayList<>();
        reorderedPages.addAll(pagesToReorder);

        // Iterate over every element in reorderedPages
        for(int pageIndex = 0; pageIndex < reorderedPages.size(); pageIndex++) {
            Integer currentPageNumber = reorderedPages.get(pageIndex);
            // Find every entry in orderingRules that has a key matching the current page.
            List<Integer> pagesThatCannotExistBeforeCurrent = orderingRules.get(currentPageNumber);

            if(pagesThatCannotExistBeforeCurrent == null || pagesThatCannotExistBeforeCurrent.size() == 0) {
                continue;
            }

            // Check the preceeding pages and ensure none of them exist in the ordering rules for the current page.
            for(int previousPagesIndex = 0; previousPagesIndex < pageIndex; previousPagesIndex++) {
                Integer previousPageNumber = reorderedPages.get(previousPagesIndex);

                if(pagesThatCannotExistBeforeCurrent.contains(previousPageNumber)) {
                    // Remove previousPageNumber and add it after currentPageNumber
                    reorderedPages.add(pageIndex + 1, previousPageNumber);
                    reorderedPages.remove(previousPagesIndex);

                    // Reset pageIndex here to restart the loop.
                    pageIndex = 0;

                    // TODO: Add an escape case to ensure we never get caught in an infinite loop.
                }
            }
        }

        return reorderedPages;
    }

    public int totalUpCenterElementsOfPageists() {
        int runningTotal = 0;

        for(List<Integer> currentPages: this.pagesToRevise) {
            if(doPagesToReviseFollowOrderingRules(currentPages)) {
                if(shouldTotalCorrectRevisions) {
                    // Get the middle element of currentPages and add it to the running total here.
                    runningTotal = runningTotal + currentPages.get(currentPages.size() / 2);
                }
            } else if(shouldTotalWrongRevisions) {
                List<Integer> correctedOrder = this.reorderPagesToFollowOrderingRules(currentPages);
                runningTotal = runningTotal + correctedOrder.get(correctedOrder.size() / 2);
            }
        }

        return runningTotal;
    }
}
