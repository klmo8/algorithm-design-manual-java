package com.klm;

import java.util.*;

public class AdjacencyGraph {
    private List<LinkedList<Edge>> adjList = new ArrayList<>();
    private int vertexCount = 0;
    private int edgeCount = 0;
    private boolean isDirected;
    private HashSet<Integer> visited;
    private String[] vertexColours;
    private boolean isBipartite = true;

    public AdjacencyGraph(int numberOfVertices, boolean isDirected) {
        this.isDirected = isDirected;
        this.vertexCount = numberOfVertices;
        visited = new HashSet<>();
        for (int v = 0; v < numberOfVertices; v++) {
            LinkedList<Edge> adjToVertex = new LinkedList<>();
            adjList.add(adjToVertex);
        }
    }

    // For constructing weighted graphs
    public void addEdge(int u, int v, int weight) {
        Edge uEdge = new Edge(u, v, weight);
        if (isDirected) {
            adjList.get(u).add(uEdge);
        } else {
            Edge vEdge = new Edge(v, u, weight);
            adjList.get(u).add(uEdge);
            adjList.get(v).add(vEdge);
            edgeCount++;
        }
        edgeCount++;
    }

    // For constructing unweighted graphs
    public void addEdge(int u, int v) {
        Edge uEdge = new Edge(u, v, 1);
        if (isDirected) {
            adjList.get(u).add(uEdge);
        } else {
            Edge vEdge = new Edge(v, u, 1);
            adjList.get(u).add(uEdge);
            adjList.get(v).add(vEdge);
            edgeCount++;
        }
        edgeCount++;
    }

    public void printGraph() {
        for (int i = 0; i < vertexCount; i++) {
            int linkedListSize = adjList.get(i).size();
            System.out.println("Printing nodes adjacent to vertex with value " + i + ":");
            for (int j = 0; j < linkedListSize; j++) {
                Edge edge = adjList.get(i).get(j);
                int value = edge.getV();
                int weight = edge.getWeight();
                System.out.println("Value: " + value + " | Weight: " + weight);
            }
        }
    }


    public void breadthFirstSearch(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int currVertex = queue.poll();
            if (visited.contains(currVertex)) {
                continue;
            }
            visited.add(currVertex);
            processVertexEarly(currVertex);
            LinkedList<Edge> adjacentToCurr = adjList.get(currVertex);
            Iterator<Edge> adjListIterator = adjacentToCurr.listIterator();
            while (adjListIterator.hasNext()) {
                Edge currentEdge = adjListIterator.next();
                int neighbourVertex = currentEdge.getV();
                processEdge(currentEdge);
                if (visited.contains(neighbourVertex)) {
                    continue;
                } else {
                    queue.add(neighbourVertex);
                }
            }
            processVertexLate(currVertex);
        }
    }

    private void processVertexEarly(int currVertex) {
        System.out.println("Currently visiting: " + currVertex);
    }

    private void processEdge(Edge currentEdge) {
        System.out.println(" ---contains edge to " + currentEdge.getV() + " with weight " + currentEdge.getWeight());
    }

    private void processVertexLate(int currVertex) {
        return;
    }

    public void depthFirstSearch(int currVertex) {
        if (visited.contains(currVertex)) {
            return;
        }

        processVertexEarly(currVertex);
        visited.add(currVertex);

        LinkedList<Edge> adjacentToCurr = adjList.get(currVertex);
        Iterator<Edge> adjListIterator = adjacentToCurr.listIterator();
        while (adjListIterator.hasNext()) {
            Edge currentEdge = adjListIterator.next();
            int neighbourVertex = currentEdge.getV();
            processEdge(currentEdge);
            if (visited.contains(neighbourVertex)) {
                continue;
            } else {
                depthFirstSearch(neighbourVertex);
            }
        }
        processVertexLate(currVertex);
    }

    public void depthFirstSearchForDisconnectedGraphs() {
        for (int i = 0; i < vertexCount; i++) {
            depthFirstSearch(i);
        }
    }

    public void breadthFirstSearchForDisconnectedGraphs() {
        for (int i = 0; i < vertexCount; i++) {
            breadthFirstSearch(i);
        }
    }

    public boolean hasTwoColouring() {
        vertexColours = new String[vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            if (i == 0) {
                vertexColours[i] = "BLACK";
            } else {
                vertexColours[i] = "UNCOLOURED";
            }
        }

        // Check all other vertices to handle for case of disconnected components
        for (int i = 0; i < vertexCount; i++) {
            int currVertex = i;
            if (!visited.contains(currVertex)) {
                breadthFirstSearchForTwoColouring(currVertex);
            }
        }
        return isBipartite;
    }

    private void breadthFirstSearchForTwoColouring(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            int currVertex = queue.poll();
            if (visited.contains(currVertex)) {
                continue;
            }
            visited.add(currVertex);
            processVertexEarly(currVertex);
            LinkedList<Edge> adjacentToCurr = adjList.get(currVertex);
            Iterator<Edge> adjListIterator = adjacentToCurr.listIterator();
            while (adjListIterator.hasNext()) {
                Edge currentEdge = adjListIterator.next();
                int neighbourVertex = currentEdge.getV();
                processEdgeTwoColouring(currentEdge);
                if (visited.contains(neighbourVertex)) {
                    continue;
                } else {
                    queue.add(neighbourVertex);
                }
            }
            processVertexLate(currVertex);
        }
    }

    private void processEdgeTwoColouring(Edge currentEdge) {
        int currentVertex = currentEdge.getU();
        int neighbourVertex = currentEdge.getV();
        if (vertexColours[currentVertex].equals(vertexColours[neighbourVertex])) {
            isBipartite = false;
        } else if (vertexColours[neighbourVertex].equals("UNCOLOURED")) {
            String complement = getComplementColour(currentVertex);
            vertexColours[neighbourVertex] = complement;
        };
    }

    private String getComplementColour(int currVertex) {
        if (vertexColours[currVertex].equals("WHITE")) {
            return "BLACK";
        } else {
            return "WHITE";
        }
    }

    public void clearVisited() {
        visited.clear();
    }

    // Can be improved by converting the distances array to a priority queue (of Edges, ordered by weight)
    public int findMSTPrim() {
        assert(!isDirected);
        boolean[] vertexIsInTree = new boolean[vertexCount];
        int[] distances = new int[vertexCount];
        int[] parents = new int[vertexCount];
        int minimumWeight = 0;
        int vertexCountInMST = 0;

        for (int i = 0; i < vertexCount; i++) {
            distances[i] = Integer.MAX_VALUE;
        }

        int currentVertex = 0;
        distances[currentVertex] = 0;
        while (true) {
            vertexIsInTree[currentVertex] = true;
            vertexCountInMST++;
            if (vertexCountInMST == vertexCount) {
                break;
            }
            LinkedList<Edge> edges = adjList.get(currentVertex);
            ListIterator<Edge> edgeIterator = edges.listIterator();
            while (edgeIterator.hasNext()) {
                Edge currentEdge = edgeIterator.next();
                int edgeWeight = currentEdge.getWeight();
                int u = currentEdge.getU();
                int v = currentEdge.getV();
                if (!vertexIsInTree[v]) {
                    if (edgeWeight < distances[v]) {
                        distances[v] = currentEdge.getWeight();
                        parents[v] = u;
                    }
                }
            }

            int bestDistance = Integer.MAX_VALUE;
            for (int i = 0; i < vertexCount; i++) {
                if (!vertexIsInTree[i] && distances[i] < bestDistance) {
                    currentVertex = i;
                    bestDistance = distances[i];
                }
            }
            minimumWeight += bestDistance;
        }
        reconstructMST(parents);
        return minimumWeight;
    }

    public int findMSTKruskal() {
        assert(!isDirected);

        int minimumWeight = 0;
        ArrayList<Edge> allEdges = getAllEdges();
        Collections.sort(allEdges);

        UnionFind unionFind = new UnionFind(vertexCount);

        for (int i = 0; i < allEdges.size(); i++) {
            Edge currentEdge = allEdges.get(i);
            int u = currentEdge.getU();
            int v = currentEdge.getV();
            int edgeWeight = currentEdge.getWeight();
            if (!unionFind.isSameComponent(u, v)) {
                unionFind.union(u, v);
                minimumWeight += edgeWeight;
            }
        }
        return minimumWeight;
    }

    private ArrayList<Edge> getAllEdges() {
        ArrayList<Edge> allEdges = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            LinkedList<Edge> currentEdgeList = adjList.get(i);
            Iterator<Edge> currentEdgeListIterator = currentEdgeList.listIterator();
            while (currentEdgeListIterator.hasNext()) {
                Edge currentEdge = currentEdgeListIterator.next();
                allEdges.add(currentEdge);
            }
        }
        return allEdges;
    }

    private void reconstructMST(int[] parents) {
        for (int i = vertexCount-1; i >= 0; i--) {
            System.out.println("Parent of " + i + " is: " + parents[i]);
        }
    }

    public int djikstraShortestPaths(int startVertex) {
        // initialize verticesInTree array, containing best distances for vertices we've already chosen
        boolean[] vertexIsInTree = new boolean[vertexCount];
        // initialize distances array, containing best estimates for current round
        // initalize parent array to help with path reconstruction
        int[] distances = new int[vertexCount];
        int[] parents = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            distances[i] = Integer.MAX_VALUE;
            parents[i] = -1;
        }
        distances[startVertex] = 0;

        int currentVertex = startVertex;

        while (!vertexIsInTree[currentVertex]) {
            vertexIsInTree[currentVertex] = true;
            // loop over all edges adjacent to currentVertex
            LinkedList<Edge> currentEdgeList = adjList.get(currentVertex);
            ListIterator<Edge> currentEdgeListIterator = currentEdgeList.listIterator();
            while (currentEdgeListIterator.hasNext()) {
                Edge currentEdge = currentEdgeListIterator.next();
                int endpoint = currentEdge.getV();
                int weight = currentEdge.getWeight();
                if ((distances[currentVertex] + weight) < distances[endpoint]) {
                    distances[endpoint] = distances[currentVertex] + weight;
                    parents[endpoint] = currentVertex;
                }
            }

            int bestDistance = Integer.MAX_VALUE;
            for (int i = 0; i < vertexCount; i++) {
                if (!vertexIsInTree[i] && (distances[i] < bestDistance)) {
                    bestDistance = distances[i];
                    currentVertex = i;
                }
            }
        }
        return distances[vertexCount-1];
    }
}

