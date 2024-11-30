package com.xml.editor;

import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    // Add a vertex (user) to the graph
    public void addVertex(int id) {
        adjacencyList.putIfAbsent(id, new ArrayList<>());
    }

    // Add a directed edge (follower relationship) to the graph
    public void addEdge(int from, int to) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    // Get the adjacency list for a specific user
    public List<Integer> getAdjacencyList(int id) {
        return adjacencyList.getOrDefault(id, Collections.emptyList());
    }

    // Get all vertices in the graph
    public Set<Integer> getVertices() {
        return adjacencyList.keySet();
    }

    // Print the graph
    public void printGraph() {
        System.out.println("Graph Representation (Adjacency List):");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.println("User " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
