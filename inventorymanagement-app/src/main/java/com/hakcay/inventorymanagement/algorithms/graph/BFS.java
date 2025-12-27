/**
 * @file BFS.java
 * @brief This file contains the BFS (Breadth-First Search) algorithm implementation.
 * @details This class provides BFS traversal for graphs.
 * @package com.hakcay.inventorymanagement.algorithms.graph
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @class BFS
 * @brief Breadth-First Search algorithm implementation.
 * @details Provides BFS traversal and path finding for graphs.
 */
public class BFS {
    
    /**
     * @brief Performs BFS traversal starting from a given vertex.
     * @param <T> The type of vertices
     * @param graph The graph to traverse
     * @param start The starting vertex
     * @return List of vertices in BFS order
     */
    public static <T> List<T> traverse(Graph<T> graph, T start) {
        List<T> result = new ArrayList<>();
        if (graph == null || start == null || graph.isEmpty()) {
            return result;
        }
        
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        
        queue.offer(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            T current = queue.poll();
            result.add(current);
            
            List<T> neighbors = graph.getNeighbors(current);
            for (T neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        return result;
    }
    
    /**
     * @brief Finds the shortest path between two vertices using BFS.
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
        Queue<T> queue = new LinkedList<>();
        java.util.Map<T, T> parent = new java.util.HashMap<>();
        
        queue.offer(start);
        visited.add(start);
        parent.put(start, null);
        
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            T current = queue.poll();
            
            List<T> neighbors = graph.getNeighbors(current);
            for (T neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                    
                    if (neighbor.equals(target)) {
                        found = true;
                        break;
                    }
                }
            }
        }
        
        if (found) {
            // Reconstruct path
            T current = target;
            while (current != null) {
                path.add(0, current);
                current = parent.get(current);
            }
        }
        
        return path;
    }
    
    /**
     * @brief Checks if a path exists between two vertices.
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
        Queue<T> queue = new LinkedList<>();
        
        queue.offer(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            T current = queue.poll();
            
            if (current.equals(target)) {
                return true;
            }
            
            List<T> neighbors = graph.getNeighbors(current);
            for (T neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        return false;
    }
}

