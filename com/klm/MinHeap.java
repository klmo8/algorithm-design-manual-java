package com.klm;

import java.util.ArrayList;
import java.util.List;

// We don't want it to _implement_ comparable
// This would imply that we want to compare two comparable objects
// But what we really want is to compare the elements of the heap
// So we extend Comparable, which allows us to use the parent classes compareTo method
public class MinHeap<E extends Comparable<E>> {
    private List<E> heap = new ArrayList<>();
    private int size = 0;

    public MinHeap(List<E> data) {
        heap.add(null);
        heapify(data);
    }

    public MinHeap() {
        heap.add(null);
    }

    private void heapify(List<E> data) {
        for (E element : data) {
            addToHeap(element);
            bubbleUp();
        }
    }

    public E extractMin() throws IllegalStateException {
        if (getSize() == 0) {
            throw new IllegalStateException("Error: cannot extract min from empty stack");
        }
        E lastElement = heap.get(size);
        E minElement = heap.get(1);
        heap.set(1, lastElement);

        bubbleDown(1);

        removeFromHeap();
        return minElement;
    }

    private int getParentIndex(int index) {
        return index / 2;
    }

    private int getLeftChildIndex(int index) {
        if (2 * index <= size) {
            return 2 * index;
        } else {
            return -1;
        }
    }

    private int getRightChildIndex(int index) {
        if ((2 * index + 1) <= size) {
            return 2 * index + 1;
        } else {
            return -1;
        }
    }

    public void addToHeap(E element) {
        heap.add(element);
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
        E parentValue = heap.get(currentIndex);
        // get children
        int rightChildIndex = getRightChildIndex(currentIndex);
        int leftChildIndex = getLeftChildIndex(currentIndex);
        // determine which child is smaller
        int smallerChildIndex;
        if (hasTwoChildren(currentIndex)) {
            if (heap.get(leftChildIndex).compareTo(heap.get(rightChildIndex)) < 0) {
                smallerChildIndex = leftChildIndex;
            } else {
                smallerChildIndex = rightChildIndex;
            }
        } else if (hasRightChild(currentIndex)) {
            smallerChildIndex = rightChildIndex;
        } else if (hasLeftChild(currentIndex)) {
            smallerChildIndex = leftChildIndex;
        } else {
            return;
        }
        E smallestChildValue = heap.get(smallerChildIndex);
        // determine if smallest child is smaller than current parent
        // if so, swap parent and child
        if (smallestChildValue.compareTo(parentValue) < 0) {
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
        return (parentIndex > 0 && heap.get(currIndex).compareTo(heap.get(parentIndex)) < 0);
    }

    public void printHeap() {
        for (int i = 1; i <= size; i++) {
            System.out.println(heap.get(i));
        }
    }

    private void swapValues(int index1, int index2) {
        E currValue = heap.get(index1);
        E tempValue = heap.get(index2);
        heap.set(index2, currValue);
        heap.set(index1, tempValue);
    }

    public int getSize() {
        return size;
    }

    public E peakMin() {
        return heap.get(1);
    }
}
