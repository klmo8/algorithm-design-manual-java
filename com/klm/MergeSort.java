package com.klm;

import java.util.ArrayList;

public class MergeSort {

    public static void mergeSort(int left, int right, ArrayList<Integer> arr) {

        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid, arr);
            mergeSort(mid + 1, right, arr);
            merge(left, mid, right, arr);
        }
    }

    public static void merge(int left, int mid, int right, ArrayList<Integer> arr) {
        ArrayList<Integer> leftArr = new ArrayList<>();
        ArrayList<Integer> rightArr = new ArrayList<>();
        int leftPtr = left;
        int rightPtr = mid + 1;

        while (leftPtr <= mid) {
            leftArr.add(arr.get(leftPtr));
            leftPtr++;
        }
        while (rightPtr <= right) {
            rightArr.add(arr.get(rightPtr));
            rightPtr++;
        }

        int i = 0;
        int j = 0;
        int k = left;
        while (i < leftArr.size() && j < rightArr.size()) {
            if (leftArr.get(i) < rightArr.get(j)) {
                arr.set(k, leftArr.get(i));
                i++;
            } else {
                arr.set(k, rightArr.get(j));
                j++;
            }
            k++;
        }

        while (i < leftArr.size()) {
            arr.set(k, leftArr.get(i));
            i++;
            k++;
        }
        while (j < rightArr.size()) {
            arr.set(k, rightArr.get(j));
            j++;
            k++;
        }
    }
}
