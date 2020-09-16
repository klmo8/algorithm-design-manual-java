package com.klm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    ArrayList<Integer> actual;
    ArrayList<Integer> expected;

    @BeforeEach
    void setUp() {
        actual = new ArrayList<>();
        expected = new ArrayList<>();
    }

    @Test
    void mergeSortsOddLength() {
        actual.add(1000); expected.add(-500);
        actual.add(-500); expected.add(0);
        actual.add(0); expected.add(10);
        actual.add(10); expected.add(50);
        actual.add(50); expected.add(1000);

        int size = actual.size();
        MergeSort.mergeSort(0, size-1, actual);
        assertEquals(actual, expected);
    }

    @Test
    void mergeSortsEvenLength() {
        actual.add(1000); expected.add(-500);
        actual.add(-500); expected.add(0);
        actual.add(0); expected.add(10);
        actual.add(10); expected.add(1000);

        int size = actual.size();
        MergeSort.mergeSort(0, size-1, actual);
        assertEquals(actual, expected);
    }

    @Test
    void mergeSortsSizeOne() {
        actual.add(1000); expected.add(1000);

        int size = actual.size();
        MergeSort.mergeSort(0, size-1, actual);
        assertEquals(actual, expected);
    }

    @Test
    void mergeSortsAlreadySorted() {
        actual.add(1); expected.add(1);
        actual.add(2); expected.add(2);
        actual.add(3); expected.add(3);
        actual.add(4); expected.add(4);

        int size = actual.size();
        MergeSort.mergeSort(0, size-1, actual);
        assertEquals(actual, expected);
    }

    @Test
    void mergeSortsWithDuplicates() {
        actual.add(5); expected.add(1);
        actual.add(1); expected.add(1);
        actual.add(1); expected.add(2);
        actual.add(2); expected.add(2);
        actual.add(2); expected.add(5);

        int size = actual.size();
        MergeSort.mergeSort(0, size-1, actual);
        assertEquals(actual, expected);
    }
}