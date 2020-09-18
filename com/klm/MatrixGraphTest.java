package com.klm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatrixGraphTest {
    MatrixGraph shortestPathsGraphUndirected;

    @BeforeEach
    void setUp() {
        shortestPathsGraphUndirected = new MatrixGraph(5, true, true);
        shortestPathsGraphUndirected.addEdge(0, 1, 1);
        shortestPathsGraphUndirected.addEdge(0, 2, 5);
        shortestPathsGraphUndirected.addEdge(1, 3, 1);
        shortestPathsGraphUndirected.addEdge(1, 4, 4);
        shortestPathsGraphUndirected.addEdge(2, 1, 8);
        shortestPathsGraphUndirected.addEdge(3, 2, 2);
        shortestPathsGraphUndirected.addEdge(3, 4, 10);
    }

    @Test
    public void allPairsReturnsShortestPathsUndirected() {
        int vertexCount = shortestPathsGraphUndirected.getVertexCount();
        int[][] expected = new int[vertexCount][vertexCount];
        expected[0][0] = 0;
        expected[0][1] = 1;
        expected[0][2] = 4;
        expected[0][3] = 2;
        expected[0][4] = 5;
        expected[1][0] = shortestPathsGraphUndirected.MAXINT;
        expected[1][1] = 0;
        expected[1][2] = 3;
        expected[1][3] = 1;
        expected[1][4] = 4;
        expected[2][0] = shortestPathsGraphUndirected.MAXINT;
        expected[2][1] = 8;
        expected[2][2] = 0;
        expected[2][3] = 9;
        expected[2][4] = 12;
        expected[3][0] = shortestPathsGraphUndirected.MAXINT;
        expected[3][1] = 10;
        expected[3][2] = 2;
        expected[3][3] = 0;
        expected[3][4] = 10;
        expected[4][0] = shortestPathsGraphUndirected.MAXINT;
        expected[4][1] = shortestPathsGraphUndirected.MAXINT;
        expected[4][2] = shortestPathsGraphUndirected.MAXINT;
        expected[4][3] = shortestPathsGraphUndirected.MAXINT;
        expected[4][4] = 0;

        int[][] actual = shortestPathsGraphUndirected.allPairsShortestPaths();
        assertTrue(Arrays.deepEquals(expected, actual));
    }
}