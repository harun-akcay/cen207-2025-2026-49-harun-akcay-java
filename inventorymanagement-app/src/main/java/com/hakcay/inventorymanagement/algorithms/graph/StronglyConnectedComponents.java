/**
 * @file StronglyConnectedComponents.java
 * @brief This file contains the Strongly Connected Components (SCC) algorithm implementation.
 * @details This class provides Kosaraju's algorithm for finding strongly connected components in a directed graph.
 * @package com.hakcay.inventorymanagement.algorithms.graph
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @class StronglyConnectedComponents
 * @brief Strongly Connected Components algorithm implementation using Kosaraju's algorithm.
 * @details Finds all strongly connected components in a directed graph.
 */
public class StronglyConnectedComponents {
    
    /**
     * @brief Finds all strongly connected components in a directed graph.
     * @param <T> The type of vertices
     * @param graph The directed graph
     * @return List of lists, where each inner list represents a strongly connected component
     */
    public static <T> List<List<T>> findSCC(Graph<T> graph) {
        List<List<T>> components = new ArrayList<>();
        if (graph == null || graph.isEmpty()) {
            return components;
        }
        
        // Step 1: Perform DFS to get finishing times (order)
        Stack<T> finishOrder = new Stack<>();
        Set<T> visited = new HashSet<>();
        
        for (T vertex : graph.getVertices()) {
            if (!visited.contains(vertex)) {
                dfsForFinishTime(graph, vertex, visited, finishOrder);
            }
        }
        
        // Step 2: Create transpose graph
        Graph<T> transposeGraph = createTranspose(graph);
        
        // Step 3: Process vertices in reverse finish order
        visited.clear();
        while (!finishOrder.isEmpty()) {
            T vertex = finishOrder.pop();
            if (!visited.contains(vertex)) {
                List<T> component = new ArrayList<>();
                dfsForComponent(transposeGraph, vertex, visited, component);
                components.add(component);
            }
        }
        
        return components;
    }
    
    /**
     * @brief Checks if the graph has any cycles (SCCs with more than one vertex).
     * @param <T> The type of vertices
     * @param graph The directed graph
     * @return true if cycles exist, false otherwise
     */
    public static <T> boolean hasCycle(Graph<T> graph) {
        if (graph == null || graph.isEmpty()) {
            return false;
        }
        
        List<List<T>> components = findSCC(graph);
        for (List<T> component : components) {
            if (component.size() > 1) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @brief Finds all cycles in the graph (SCCs with more than one vertex).
     * @param <T> The type of vertices
     * @param graph The directed graph
     * @return List of cycles, where each cycle is a list of vertices
     */
    public static <T> List<List<T>> findCycles(Graph<T> graph) {
        List<List<T>> cycles = new ArrayList<>();
        if (graph == null || graph.isEmpty()) {
            return cycles;
        }
        
        List<List<T>> components = findSCC(graph);
        for (List<T> component : components) {
            if (component.size() > 1) {
                cycles.add(new ArrayList<>(component));
            }
        }
        
        return cycles;
    }
    
    /**
     * @brief Checks if there is a cycle involving a specific vertex.
     * @param <T> The type of vertices
     * @param graph The directed graph
     * @param vertex The vertex to check
     * @return true if vertex is part of a cycle, false otherwise
     */
    public static <T> boolean isInCycle(Graph<T> graph, T vertex) {
        if (graph == null || vertex == null || graph.isEmpty()) {
            return false;
        }
        
        List<List<T>> components = findSCC(graph);
        for (List<T> component : components) {
            if (component.size() > 1 && component.contains(vertex)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @brief Helper method: DFS to get finishing times.
     * @param <T> The type of vertices
     * @param graph The graph
     * @param vertex Current vertex
     * @param visited Set of visited vertices
     * @param finishOrder Stack to store finish order
     */
    private static <T> void dfsForFinishTime(Graph<T> graph, T vertex, Set<T> visited, Stack<T> finishOrder) {
        visited.add(vertex);
        
        List<T> neighbors = graph.getNeighbors(vertex);
        for (T neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                dfsForFinishTime(graph, neighbor, visited, finishOrder);
            }
        }
        
        finishOrder.push(vertex);
    }
    
    /**
     * @brief Helper method: DFS to collect vertices in a component.
     * @param <T> The type of vertices
     * @param graph The graph
     * @param vertex Current vertex
     * @param visited Set of visited vertices
     * @param component List to store component vertices
     */
    private static <T> void dfsForComponent(Graph<T> graph, T vertex, Set<T> visited, List<T> component) {
        visited.add(vertex);
        component.add(vertex);
        
        List<T> neighbors = graph.getNeighbors(vertex);
        for (T neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                dfsForComponent(graph, neighbor, visited, component);
            }
        }
    }
    
    /**
     * @brief Creates the transpose (reverse) of a directed graph.
     * @param <T> The type of vertices
     * @param graph The original graph
     * @return The transpose graph
     */
    private static <T> Graph<T> createTranspose(Graph<T> graph) {
        Graph<T> transpose = new Graph<>(true); // Directed
        
        // Add all vertices
        for (T vertex : graph.getVertices()) {
            transpose.addVertex(vertex);
        }
        
        // Reverse all edges
        for (T from : graph.getVertices()) {
            for (T to : graph.getNeighbors(from)) {
                transpose.addEdge(to, from); // Reverse edge direction
            }
        }
        
        return transpose;
    }
}

