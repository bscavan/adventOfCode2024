package com.cavanaugh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListComparisonTest {
    private List<Integer> leftList;
    private List<Integer> rightList;

    @BeforeEach
    public void setup() {
        leftList = new ArrayList<>();
        leftList.add(3);
        leftList.add(4);
        leftList.add(2);
        leftList.add(1);
        leftList.add(3);
        leftList.add(3);
        
        rightList = new ArrayList<>();
        rightList.add(4);
        rightList.add(3);
        rightList.add(5);
        rightList.add(3);
        rightList.add(9);
        rightList.add(3);
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        ListComparison comparator = new ListComparison(leftList, rightList);
        comparator.compareLists();
        assertEquals(11, comparator.getDifferenceCount());
    }
}
