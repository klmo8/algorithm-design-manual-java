package com.klm;

public class UnionFind {
    private int[] parents;
    private int[] elementsPerComponent;
    private int totalElements;

    public UnionFind(int totalElements) {
        this.totalElements = totalElements;
        this.parents = new int[totalElements];
        this.elementsPerComponent = new int[totalElements];
        for (int i = 0; i < totalElements; i++) {
            elementsPerComponent[i] = 1;
            parents[i] = i;
        }
    }

    // finds the topmost parent of given element
    public int find(int i) {
        if (parents[i] == i) {
            return i;
        } else {
            int currentParent = parents[i];
            return find(currentParent);
        }
    }

    public void union(int u, int v) {
        assert(u >= 0 && v >= 0);

        int parentOfU = find(u);
        int parentOfV = find(v);

        if (parentOfU == parentOfV) {
            return;
        }

        if (getSizeOfComponent(u) >= getSizeOfComponent(v)) {
            parents[v] = parentOfU;
            updateComponentCount(parentOfU, parentOfV);
        } else {
            parents[u] = parentOfV;
            updateComponentCount(v, u);
        }
        totalElements -= 1;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public boolean isSameComponent(int u, int v) {
        return (find(u) == find(v));
    }

    public int getFirstParent(int u) {
        return parents[u];
    }

    // gets the size of the component this element _belongs_ to (so not just its immediate children)
    public int getSizeOfComponent(int u) {
        return elementsPerComponent[find(u)];
    }

    public void updateComponentCount(int u, int v) {
        elementsPerComponent[u] += getSizeOfComponent(v);
    }
}
