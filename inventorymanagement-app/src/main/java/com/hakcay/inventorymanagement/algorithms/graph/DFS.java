/**
 * @file DFS.java
 * @brief This file contains the DFS (Depth-First Search) algorithm implementation.
 * @details This class provides DFS traversal for graphs.
 * @package com.hakcay.inventorymanagement.algorithms.graph
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @class DFS
 * @brief Depth-First Search algorithm implementation.
 * @details Provides DFS traversal and path finding for graphs.
 */
public class DFS {
    
    /**
     * @brief Performs DFS traversal starting from a given vertex (iterative).
     * @param <T> The type of vertices
     * @param graph The graph to traverse
     * @param start The starting vertex
     * @return List of vertices in DFS order
     */
    public static <T> List<T> traverse(Graph<T> graph, T start) {
        List<T> result = new ArrayList<>();
        if (graph == null || start == null || graph.isEmpty()) {
            return result;
        }
        
        Set<T> visited = new HashSet<>();
        java.util.Stack<T> stack = new java.util.Stack<>();
        
        stack.push(start);
        
        while (!stack.isEmpty()) {
            T current = stack.pop();
            
            if (!visited.contains(current)) {
                visited.add(current);
                result.add(current);
                
                List<T> neighbors = graph.getNeighbors(current);
                // Push neighbors in reverse order to maintain natural traversal order
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    T neighbor = neighbors.get(i);
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * @brief Performs DFS traversal recursively.
     * @param <T> The type of vertices
     * @param graph The graph to traverse
     * @param start The starting vertex
     * @return List of vertices in DFS order
     */
    public static <T> List<T> traverseRecursive(Graph<T> graph, T start) {
        List<T> result = new ArrayList<>();
        if (graph == null || start == null || graph.isEmpty()) {
            return result;
        }
        
        Set<T> visited = new HashSet<>();
        dfsRecursive(graph, start, visited, result);
        return result;
    }
    
    /**
     * @brief Helper method for recursive DFS.
     * @param <T> The type of vertices
     * @param graph The graph to traverse
     * @param vertex The current vertex
     * @param visited Set of visited vertices
     * @param result List to store traversal result
     */
    private static <T> void dfsRecursive(Graph<T> graph, T vertex, Set<T> visited, List<T> result) {
        visited.add(vertex);
        result.add(vertex);
        
        List<T> neighbors = graph.getNeighbors(vertex);
        for (T neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(graph, neighbor, visited, result);
            }
        }
    }
    
    /**
     * @brief Finds a path between two vertices using DFS.
     * @param <T> The type of vertices
     * @param graph The graph to search
     * @param start The starting vertex
     * @param target The target vertex
     * @return List of vertices representing the path, or empty list if no path exists
     */
    public static <T> List<T> findPath(Graph<T> graph, T start, T target) {
        List<T> path = new ArrayList<>();
        if (graph == null || start == null || target == null || graph.isEmpty()) {
            return path;
        }
        
        if (start.equals(target)) {
            path.add(start);
            return path;
        }
        
        Set<T> visited = new HashSet<>();
        List<T> currentPath = new ArrayList<>();
        boolean found = dfsPath(graph, start, target, visited, currentPath);
        
        if (found) {
            return new ArrayList<>(currentPath);
        }
        
        return path;
    }
    
    /**
     * @brief Helper method for DFS path finding.
     * @param <T> The type of vertices
     * @param graph The graph to search
     * @param current The current vertex
     * @param target The target vertex
     * @param visited Set of visited vertices
     * @param path Current path being explored
     * @return true if path found, false otherwise
     */
    private static <T> boolean dfsPath(Graph<T> graph, T current, T target, Set<T> visited, List<T> path) {
        visited.add(current);
        path.add(current);
        
        if (current.equals(target)) {
            return true;
        }
        
        List<T> neighbors = graph.getNeighbors(current);
        for (T neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                if (dfsPath(graph, neighbor, target, visited, path)) {
                    return true;
                }
            }
        }
        
        // Backtrack
        path.remove(path.size() - 1);
        return false;
    }
    
    /**
     * @brief Checks if a path exists between two vertices using DFS.
     * @param <T> The type of vertices
     * @param graph The graph to search
     * @param start The starting vertex
     * @param target The target vertex
     * @return true if path exists, false otherwise
     */
    public static <T> boolean hasPath(Graph<T> graph, T start, T target) {
        if (graph == null || start == null || target == null || graph.isEmpty()) {
            return false;
        }
        
        if (start.equals(target)) {
            return true;
        }
        
        Set<T> visited = new HashSet<>();
        return dfsHasPath(graph, start, target, visited);
    }
    
    /**
     * @brief Helper method for DFS path existence check.
     * @param <T> The type of vertices
     * @param graph The graph to search
     * @param current The current vertex
     * @param target The target vertex
     * @param visited Set of visited vertices
     * @return true if path exists, false otherwise
     */
    private static <T> boolean dfsHasPath(Graph<T> graph, T current, T target, Set<T> visited) {
        if (current.equals(target)) {
            return true;
        }
        
        visited.add(current);
        
        List<T> neighbors = graph.getNeighbors(current);
        for (T neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                if (dfsHasPath(graph, neighbor, target, visited)) {
                    return true;
                }
            }
        }
        
        return false;
    }
}

