package com.klm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {
    MinHeap minHeap;
    MinHeap emptyMinHeap;

    @BeforeEach
    void setUp() {
        List<Integer> data = new ArrayList<>();
        data.add(5);
        data.add(1);
        data.add(9);
        data.add(4);
        data.add(8);
        data.add(7);
        data.add(0);
        minHeap = new MinHeap(data);
        emptyMinHeap = new MinHeap();
    }

    @Test
    void addToHeapMaintainsMinHeap() {
        int topOfHeap = minHeap.peakMin();
        assertEquals(0, topOfHeap);

        minHeap.addToHeap(100);
        minHeap.addToHeap(-100);
        minHeap.addToHeap(500);
        minHeap.addToHeap(222);
        minHeap.addToHeap(-5555);
        minHeap.addToHeap(333);
        minHeap.addToHeap(999);

        topOfHeap = minHeap.peakMin();
        assertEquals(-5555, topOfHeap);
    }

    @Test
    void extractMinRepeatedlyReturnsMinimum() throws IllegalStateException {
        int min = minHeap.extractMin();
        assertEquals(0, min);
        min = minHeap.extractMin();
        assertEquals(1, min);
        min = minHeap.extractMin();
        assertEquals(4, min);
        min = minHeap.extractMin();
        assertEquals(5, min);
        min = minHeap.extractMin();
        assertEquals(7, min);
        min = minHeap.extractMin();
        assertEquals(8, min);
        min = minHeap.extractMin();
        assertEquals(9, min);
    }

    @Test
    void extractMinThrowsErrorOnEmptyHeap() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            emptyMinHeap.extractMin();
        });
    }
}