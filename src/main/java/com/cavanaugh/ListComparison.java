package com.cavanaugh;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ListComparison {
    private List<Integer> leftList;
    private List<Integer> rightList;
    private int differenceCount = -1;
    private int similarityScore = -1;

    public ListComparison(List<Integer> leftList, List<Integer> rightList) {
        this.leftList = leftList;
        this.rightList = rightList;
    }

    public void compareLists() {
        differenceCount = 0;
        if(leftList.size() != rightList.size()) {
            throw new IllegalArgumentException("The two lists provided must be the same length!");
        }

        Collections.sort(this.leftList);
        Collections.sort(this.rightList);

        for(int index = 0; index < leftList.size(); index++) {
            differenceCount = differenceCount + Math.abs(this.leftList.get(index) - this.rightList.get(index));
        }
    }

    public int getDifferenceCount() {
        if(differenceCount == -1) {
            compareLists();
        }

        return differenceCount;
    }

    public void calculateSimilarityScore() {
        similarityScore = 0;
        HashMap<Integer, Integer> rightSide = new HashMap<>();

        for(int current: rightList) {
            int countInRightSide = rightSide.containsKey(current) ? rightSide.get(current) : 0;
            countInRightSide++;

            rightSide.put(current, countInRightSide);
        }

        for(int current: leftList) {
            int countInRightSide = rightSide.containsKey(current) ? rightSide.get(current) : 0;

            similarityScore = similarityScore + current * countInRightSide; 
        }
    }

    public int getSimilarityScore() {
        if(similarityScore == -1) {
            calculateSimilarityScore();
        }

        return similarityScore;
    }
}