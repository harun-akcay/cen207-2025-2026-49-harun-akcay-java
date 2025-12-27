/**
 * @file Graph.java
 * @brief This file contains the Graph class implementation.
 * @details This class provides a generic graph data structure with adjacency list representation.
 * @package com.hakcay.inventorymanagement.algorithms.graph
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @class Graph
 * @brief Generic graph implementation using adjacency list.
 * @details This graph supports both directed and undirected edges.
 * @param <T> The type of vertices in the graph
 */
public class Graph<T> {
    
    /** @brief Map to store adjacency lists for each vertex */
    private Map<T, List<T>> adjacencyList;
    
    /** @brief Whether the graph is directed */
    private boolean directed;
    
    /**
     * @brief Constructor for undirected graph.
     * @details Creates an undirected graph.
     */
    public Graph() {
        this(false);
    }
    
    /**
     * @brief Constructor with direction parameter.
     * @param directed true if the graph is directed, false otherwise
     */
    public Graph(boolean directed) {
        this.adjacencyList = new HashMap<>();
        this.directed = directed;
    }
    
    /**
     * @brief Adds a vertex to the graph.
     * @param vertex The vertex to add
     */
    public void addVertex(T vertex) {
        if (vertex != null && !adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }
    
    /**
     * @brief Adds an edge between two vertices.
     * @param from The source vertex
     * @param to The destination vertex
     */
    public void addEdge(T from, T to) {
        if (from == null || to == null) {
            return;
        }
        
        addVertex(from);
        addVertex(to);
        
        adjacencyList.get(from).add(to);
        
        // If undirected, add reverse edge
        if (!directed) {
            adjacencyList.get(to).add(from);
        }
    }
    
    /**
     * @brief Removes an edge between two vertices.
     * @param from The source vertex
     * @param to The destination vertex
     */
    public void removeEdge(T from, T to) {
        if (from == null || to == null || !adjacencyList.containsKey(from)) {
            return;
        }
        
        adjacencyList.get(from).remove(to);
        
        // If undirected, remove reverse edge
        if (!directed && adjacencyList.containsKey(to)) {
            adjacencyList.get(to).remove(from);
        }
    }
    
    /**
     * @brief Gets the neighbors of a vertex.
     * @param vertex The vertex
     * @return List of neighbors
     */
    public List<T> getNeighbors(T vertex) {
        if (vertex == null || !adjacencyList.containsKey(vertex)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(adjacencyList.get(vertex));
    }
    
    /**
     * @brief Gets all vertices in the graph.
     * @return List of all vertices
     */
    public List<T> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }
    
    /**
     * @brief Checks if an edge exists between two vertices.
     * @param from The source vertex
     * @param to The destination vertex
     * @return true if edge exists, false otherwise
     */
    public boolean hasEdge(T from, T to) {
        if (from == null || to == null || !adjacencyList.containsKey(from)) {
            return false;
        }
        return adjacencyList.get(from).contains(to);
    }
    
    /**
     * @brief Gets the number of vertices in the graph.
     * @return Number of vertices
     */
    public int getVertexCount() {
        return adjacencyList.size();
    }
    
    /**
     * @brief Checks if the graph is empty.
     * @return true if graph is empty, false otherwise
     */
    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }
    
    /**
     * @brief Clears all vertices and edges from the graph.
     */
    public void clear() {
        adjacencyList.clear();
    }
    
    /**
     * @brief Checks if the graph is directed.
     * @return true if directed, false otherwise
     */
    public boolean isDirected() {
        return directed;
    }
}

