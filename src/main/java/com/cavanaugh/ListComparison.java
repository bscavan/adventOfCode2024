package com.cavanaugh;

import java.util.Collections;
import java.util.List;

public class ListComparison {
    private List<Integer> leftList;
    private List<Integer> rightList;
    private int differenceCount = 0;

    public ListComparison(List<Integer> leftList, List<Integer> rightList) {
        this.leftList = leftList;
        this.rightList = rightList;
    }

    public void compareLists() {
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
        return differenceCount;
    }
}