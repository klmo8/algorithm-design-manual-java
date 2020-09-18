package com.klm;

public class MatrixGraph {
    private int vertexCount;
    private int edgeCount;
    private int[][] adjMatrix;
    private boolean isWeighted;
    private boolean isDirected;
    public int MAXINT = Integer.MAX_VALUE;

    public MatrixGraph(int vertexCount, boolean isWeighted, boolean isDirected) {
        this.vertexCount = vertexCount;
        this.isWeighted = isWeighted;
        this.isDirected = isDirected;
        this.adjMatrix = new int[vertexCount][vertexCount];
        if (isWeighted) {
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < vertexCount; j++) {
                    if (i == j) {
                        adjMatrix[i][j] = 0;
                    } else {
                        adjMatrix[i][j] = MAXINT;
                    }
                }
            }
        } else {
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < vertexCount; j++) {
                    adjMatrix[i][j] = 0;
                }
            }
        }
    }

    // For constructing weighted graphs
    public void addEdge(int u, int v, int weight) {
        adjMatrix[u][v] = weight;
        if (!isDirected) {
            adjMatrix[v][u] = weight;
            edgeCount++;
        }
        edgeCount++;
    }

    // For constructing unweighted graphs
    public void addEdge(int u, int v) {
        adjMatrix[u][v] = 1;
        if (!isDirected) {
            adjMatrix[v][u] = 1;
            edgeCount++;
        }
        edgeCount++;
    }

    public int[][] allPairsShortestPaths() {
        int[][] distances = cloneMatrix();

        for (int k = 0; k < vertexCount; k++) {
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < vertexCount; j++) {
                    // ensure i-->k and k-->j are both reachable paths
                    if (distances[i][k] != MAXINT && distances[k][j] != MAXINT) {
                        if ((distances[i][k] + distances[k][j]) < distances[i][j]) {
                            distances[i][j] = distances[i][k] + distances[k][j];
                        }
                    }
                }
            }
        }
        return distances;
    }

    private int[][] cloneMatrix() {
        int[][] clonedMatrix = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                clonedMatrix[i][j] = adjMatrix[i][j];
            }
        }
        return clonedMatrix;
    }

    public void printGraph() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.println("Vertex " + i + " is adjacent to: ");
            for (int j = 0; j < vertexCount; j++) {
                int currentEdgeWeight = adjMatrix[i][j];
                if (isWeighted && currentEdgeWeight != Integer.MAX_VALUE) {
                    System.out.println("-- Vertex " + j + " with a weight of: " + currentEdgeWeight);
                } else if (!isWeighted && currentEdgeWeight != 0) {
                    System.out.println("Vertex " + j);
                }
            }
        }
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
