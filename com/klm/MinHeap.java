package com.klm;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    private List<Integer> heap = new ArrayList<>();
    private int size = 0;

    public MinHeap(List<Integer> data) {
        heap.add(-1);
        heapify(data);
    }

    public MinHeap() {
        heap.add(-1);
    }

    private void heapify(List<Integer> data) {
        for (Integer element : data) {
            addToHeap(element);
            bubbleUp();
        }
    }

    public int extractMin() throws IllegalStateException {
        if (getSize() == 0) {
            throw new IllegalStateException("Error: cannot extract min from empty stack");
        }
        int lastElement = heap.get(size);
        int minElement = heap.get(1);
        heap.set(1, lastElement);

        bubbleDown(1);

        removeFromHeap();
        return minElement;
    }

    private int getParentIndex(int index) {
        return index/2;
    }

    private int getLeftChildIndex(int index) {
        if (2*index <= size) {
            return 2*index;
        } else {
            return -1;
        }
    }

    private int getRightChildIndex(int index) {
        if ((2*index + 1) <= size) {
            return 2*index + 1;
        } else {
            return -1;
        }
    }

    public void addToHeap(int value) {
        heap.add(value);
        size++;
        bubbleUp();
    }

    private void removeFromHeap() throws IllegalStateException {
        if (getSize() == 0) {
            throw new IllegalStateException("Error: cannot extract min from empty stack");
        }
        heap.remove(size);
        size--;
    }

    private void bubbleUp() {
        int currentIndex = size;
        int parentIndex = getParentIndex(currentIndex);

        while (hasSwappableParent(currentIndex, parentIndex)) {
            swapValues(currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = getParentIndex(currentIndex);
        }
    }

    private void bubbleDown(int currentIndex) {
        if (currentIndex >= size) {
            return;
        }
        int parentValue = heap.get(currentIndex);
        // get children
        int rightChildIndex = getRightChildIndex(currentIndex);
        int leftChildIndex = getLeftChildIndex(currentIndex);
        // determine which child is smaller
        int smallerChildIndex;
        if (hasTwoChildren(currentIndex)) {
            if (heap.get(leftChildIndex) < heap.get(rightChildIndex)) {
                smallerChildIndex = leftChildIndex;
            } else {
                smallerChildIndex = rightChildIndex;
            }
        } else if (hasRightChild(currentIndex)) {
            smallerChildIndex = rightChildIndex;
        } else if (hasLeftChild(currentIndex)){
            smallerChildIndex = leftChildIndex;
        } else {
            return;
        }
        int smallestChildValue = heap.get(smallerChildIndex);
        // determine if smallest child is smaller than current parent
        // if so, swap parent and child
        if (smallestChildValue < parentValue) {
            swapValues(smallerChildIndex, currentIndex);
        }
        // repeat process from smallestChildIndex
        bubbleDown(smallerChildIndex);
    }

    private boolean hasTwoChildren(int currentIndex) {
        return (getLeftChildIndex(currentIndex) != -1 && getRightChildIndex(currentIndex) != -1);
    }

    private boolean hasRightChild(int currentIndex) {
        return (getRightChildIndex(currentIndex) != -1);
    }

    private boolean hasLeftChild(int currentIndex) {
        return (getLeftChildIndex(currentIndex) != -1);
    }

    private boolean hasSwappableParent(int currIndex, int parentIndex) {
        return (parentIndex > 0 && heap.get(currIndex) < heap.get(parentIndex));
    }

    public void printHeap() {
        for (int i = 1; i <= size; i++) {
            System.out.println(heap.get(i));
        }
    }

    private void swapValues(int index1, int index2) {
        int currValue = heap.get(index1);
        int tempValue = heap.get(index2);
        heap.set(index2, currValue);
        heap.set(index1, tempValue);
    }

    public int getSize() {
        return size;
    }

    public int peakMin() {
        return heap.get(1);
    }
}
