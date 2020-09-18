package com.klm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnionFindTest {
    UnionFind unionFind;

    @BeforeEach
    void setUp() {
        unionFind = new UnionFind(5);
    }

    @Test
    void testGetSizeOfComponent() {
        unionFind.union(0, 4);
        int expectedComponentSizeOfU = 2;
        int actualComponentSizeOfU = unionFind.getSizeOfComponent(0);
        int expectedComponentSizeOfV = 2;
        int actualComponentSizeOfV = unionFind.getSizeOfComponent(4);
        assertEquals(expectedComponentSizeOfU, actualComponentSizeOfU);
        assertEquals(expectedComponentSizeOfV, actualComponentSizeOfV);
    }

    @Test
    void getParentReturnsCorrectlyWhenParentIsChild() {
        assertEquals(unionFind.getFirstParent(0), unionFind.getFirstParent(0));
    }

    @Test
    void getParentReturnsCorrectlyWhenParentIsNotChild() {
        assertNotEquals(unionFind.getFirstParent(0), unionFind.getFirstParent(1));
    }

    @Test
    void sameComponentReturnsTrueForSameComponent() {
        assertTrue(unionFind.isSameComponent(0, 0));
    }

    @Test
    void sameComponentReturnsFalseForDifferentComponent() {
        assertFalse(unionFind.isSameComponent(0, 1));
    }

    @Test
    void findsParentWhenParentIsItself() {
        int parent = unionFind.find(0);
        assertEquals(0, parent);
    }

    @Test
    void findsParentWhenParentIsAnotherComponent() {
        int u = 0;
        int v = 4;
        unionFind.union(u, v);
        int newParentOfV = unionFind.find(v);
        assertEquals(u, newParentOfV);
    }

    @Test
    void findsParentWhenParentIsNestedInAnotherComponent() {
        int u = 0;
        int v = 4;
        int z = 3;
        unionFind.union(u, v);
        unionFind.union(v, z);
        int newParentOfZ = unionFind.find(z);
        assertEquals(u, newParentOfZ);
    }

    @Test
    void unionOfSameComponentHasNoEffect() {
        int initalComponentSizeOf0 = unionFind.getSizeOfComponent(0);
        int initalComponentSizeOf1 = unionFind.getSizeOfComponent(1);
        int initialParentOf0 = unionFind.find(0);
        int initialParentOf1 = unionFind.find(1);
        unionFind.union(0, 0);
        unionFind.union(1, 1);
        int afterUnionComponentSizeOf0 = unionFind.getSizeOfComponent(0);
        int afterUnionComponentSizeOf1 = unionFind.getSizeOfComponent(1);
        int afterUnionParentOf0 = unionFind.find(0);
        int afterUnionParentOf1 = unionFind.find(1);
        assertEquals(initalComponentSizeOf0, afterUnionComponentSizeOf0);
        assertEquals(initalComponentSizeOf1, afterUnionComponentSizeOf1);
        assertEquals(initialParentOf0, afterUnionParentOf0);
        assertEquals(initialParentOf1, afterUnionParentOf1);
    }

    @Test
    void unionOfDifferentComponentsHasCorrectEffect() {
        int initalComponentSizeOf0 = unionFind.getSizeOfComponent(0);
        int initalComponentSizeOf1 = unionFind.getSizeOfComponent(1);
        int initialParentOf0 = unionFind.find(0);
        int initialParentOf1 = unionFind.find(1);
        unionFind.union(0, 1);
        int afterUnionComponentSizeOf0 = unionFind.getSizeOfComponent(0);
        int afterUnionComponentSizeOf1 = unionFind.getSizeOfComponent(1);
        int afterUnionParentOf0 = unionFind.find(0);
        int afterUnionParentOf1 = unionFind.find(1);
        assertEquals(initalComponentSizeOf0 + 1, afterUnionComponentSizeOf0);
        assertEquals(initalComponentSizeOf1 + 1, afterUnionComponentSizeOf1);
        assertEquals(initialParentOf0, afterUnionParentOf0);
        assertEquals(0, afterUnionParentOf1);
    }

    @Test
    void getTotalElementsCountsComponentsCorrectly() {
        int initialComponentCount = unionFind.getTotalElements();
        unionFind.union(0, 4);
        unionFind.union(0, 3);
        unionFind.union(0, 2);
        unionFind.union(0, 1);
        int afterUnionComponentCount = unionFind.getTotalElements();
        assertEquals(5, initialComponentCount);
        assertEquals(1, afterUnionComponentCount);
    }
}