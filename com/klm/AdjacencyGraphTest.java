package com.klm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyGraphTest {
    AdjacencyGraph connectedGraph;
    AdjacencyGraph disconnectedGraph;
    AdjacencyGraph disconnectedComplicatedGraph;
    AdjacencyGraph graphWithMST;
    AdjacencyGraph shortestPathsGraph;

    @BeforeEach
    void setUp() {
        // Toggle isDirected to apply tests for both directed and undirected graphs
        connectedGraph = new AdjacencyGraph(4,true);
        disconnectedGraph = new AdjacencyGraph(6,true);
        disconnectedComplicatedGraph = new AdjacencyGraph(6, true);
        graphWithMST = new AdjacencyGraph(5, false);
        shortestPathsGraph = new AdjacencyGraph(5, false);
    }

    @org.junit.jupiter.api.Test
    void hasTwoColouringIdenitifiesBipartiteConnectedComponents() {
        connectedGraph.addEdge(0, 1);
        connectedGraph.addEdge( 1, 2);
        connectedGraph.addEdge( 2, 3);
        connectedGraph.addEdge( 3, 0);

        assert(connectedGraph.hasTwoColouring());
    }

    @org.junit.jupiter.api.Test
    void hasTwoColouringIdenitifiesBipartiteDisconnectedComponents() {
        disconnectedGraph.addEdge(0, 1);
        disconnectedGraph.addEdge( 1, 2);
        disconnectedGraph.addEdge( 2, 3);
        disconnectedGraph.addEdge( 3, 0);

        assert(disconnectedGraph.hasTwoColouring());
    }

    @org.junit.jupiter.api.Test
    void hasTwoColouringIdenitifiesNonBipartiteConnectedComponents() {
        connectedGraph.addEdge(0, 1);
        connectedGraph.addEdge( 1, 2);
        connectedGraph.addEdge( 2, 0);
        connectedGraph.addEdge( 2, 1);

        assertFalse(connectedGraph.hasTwoColouring());
    }

    @org.junit.jupiter.api.Test
    void hasTwoColouringIdenitifiesNonBipartiteDisconnectedComponents() {
        disconnectedGraph.addEdge(0, 1);
        disconnectedGraph.addEdge( 1, 2);
        disconnectedGraph.addEdge( 2, 0);

        assertFalse(disconnectedGraph.hasTwoColouring());
    }

    @Test
    void bfsRunsWithoutErrorsOnDisconnectedGraph() {
        disconnectedGraph.addEdge(0, 1);
        disconnectedGraph.addEdge( 1, 2);
        disconnectedGraph.addEdge( 2, 3);
        disconnectedGraph.addEdge( 3, 0);

        disconnectedGraph.breadthFirstSearchForDisconnectedGraphs();
    }

    @Test
    void bfsRunsWithoutErrorsOnConnectedGraph() {
        disconnectedGraph.addEdge(0, 1);
        disconnectedGraph.addEdge( 1, 2);
        disconnectedGraph.addEdge( 2, 3);
        disconnectedGraph.addEdge( 3, 0);

        disconnectedGraph.breadthFirstSearch(0);
    }

    @Test
    void dfsRunsWithoutErrorsOnDisconnectedGraph() {
        disconnectedGraph.addEdge(0, 1);
        disconnectedGraph.addEdge( 1, 2);
        disconnectedGraph.addEdge( 2, 3);
        disconnectedGraph.addEdge( 3, 0);

        disconnectedGraph.depthFirstSearchForDisconnectedGraphs();
    }

    @Test
    void dfsRunsWithoutErrorsOnConnectedGraph() {
        disconnectedGraph.addEdge(0, 1);
        disconnectedGraph.addEdge( 1, 2);
        disconnectedGraph.addEdge( 2, 3);
        disconnectedGraph.addEdge( 3, 0);

        disconnectedGraph.depthFirstSearch(0);
    }

    @Test
    void dfsRunsWithoutErrorsOnComplicatedDisconnectedGraph() {
        disconnectedComplicatedGraph.addEdge(0, 1);
        disconnectedComplicatedGraph.addEdge( 0, 2);
        disconnectedComplicatedGraph.addEdge( 1, 2);
        disconnectedComplicatedGraph.addEdge( 2, 5);
        disconnectedComplicatedGraph.addEdge( 3, 2);
        disconnectedComplicatedGraph.addEdge( 4, 5);
        disconnectedComplicatedGraph.addEdge( 5, 4);

        disconnectedComplicatedGraph.depthFirstSearchForDisconnectedGraphs();
    }

    @Test
    void bfsRunsWithoutErrorsOnComplicatedDisconnectedGraph() {
        disconnectedComplicatedGraph.addEdge(0, 1);
        disconnectedComplicatedGraph.addEdge( 0, 2);
        disconnectedComplicatedGraph.addEdge( 1, 2);
        disconnectedComplicatedGraph.addEdge( 2, 5);
        disconnectedComplicatedGraph.addEdge( 3, 2);
        disconnectedComplicatedGraph.addEdge( 4, 5);
        disconnectedComplicatedGraph.addEdge( 5, 4);

        disconnectedComplicatedGraph.breadthFirstSearchForDisconnectedGraphs();
    }

    @Test
    void primFindsMST() {
        graphWithMST.addEdge(0, 1, 1);
        graphWithMST.addEdge(0, 2, 5);
        graphWithMST.addEdge(1, 3, 2);
        graphWithMST.addEdge(1, 4, 4);
        graphWithMST.addEdge(2, 1, 8);
        graphWithMST.addEdge(2, 3, 3);
        graphWithMST.addEdge(3, 4, 10);

        int MSTweight = graphWithMST.findMSTPrim();
        assertEquals(10, MSTweight);
    }

    @Test
    void kruskalFindMST() {
        graphWithMST.addEdge(0, 1, 1);
        graphWithMST.addEdge(0, 2, 5);
        graphWithMST.addEdge(1, 3, 2);
        graphWithMST.addEdge(1, 4, 4);
        graphWithMST.addEdge(2, 1, 8);
        graphWithMST.addEdge(2, 3, 3);
        graphWithMST.addEdge(3, 4, 10);

        int MSTweight = graphWithMST.findMSTKruskal();
        assertEquals(10, MSTweight);
    }

    @Test
    void djikstraFindsShortestPath() {
        shortestPathsGraph.addEdge(0, 1, 1);
        shortestPathsGraph.addEdge(0, 2, 5);
        shortestPathsGraph.addEdge(1, 3, 2);
        shortestPathsGraph.addEdge(1, 4, 4);
        shortestPathsGraph.addEdge(2, 1, 8);
        shortestPathsGraph.addEdge(2, 3, 3);
        shortestPathsGraph.addEdge(3, 4, 10);
        int shortestPath = shortestPathsGraph.djikstraShortestPaths(0);

        assertEquals(5, shortestPath);
    }

    @AfterEach
    void cleanupGraph() {
        connectedGraph.clearVisited();
        disconnectedGraph.clearVisited();
        disconnectedComplicatedGraph.clearVisited();
    }
}