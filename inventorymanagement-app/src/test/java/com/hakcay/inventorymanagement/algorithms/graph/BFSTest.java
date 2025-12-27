package com.hakcay.inventorymanagement.algorithms.graph;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BFSTest {
    private Graph<Integer> graph;
    
    @Before
    public void setUp() {
        graph = new Graph<>();
    }
    
    @Test
    public void testTraverseEmptyGraph() {
        List<Integer> result = BFS.traverse(graph, 1);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseNullGraph() {
        List<Integer> result = BFS.traverse(null, 1);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseNullStart() {
        graph.addVertex(1);
        List<Integer> result = BFS.traverse(graph, null);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseSingleVertex() {
        graph.addVertex(1);
        List<Integer> result = BFS.traverse(graph, 1);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
    }
    
    @Test
    public void testTraverseLinearGraph() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        List<Integer> result = BFS.traverse(graph, 1);
        assertEquals(4, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
        assertEquals(Integer.valueOf(2), result.get(1));
        assertEquals(Integer.valueOf(3), result.get(2));
        assertEquals(Integer.valueOf(4), result.get(3));
    }
    
    @Test
    public void testTraverseStarGraph() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        List<Integer> result = BFS.traverse(graph, 1);
        assertEquals(4, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
    }
    
    @Test
    public void testTraverseDisconnectedGraph() {
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        List<Integer> result = BFS.traverse(graph, 1);
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertFalse(result.contains(3));
        assertFalse(result.contains(4));
    }
    
    @Test
    public void testFindPathSameVertex() {
        graph.addVertex(1);
        List<Integer> path = BFS.findPath(graph, 1, 1);
        assertEquals(1, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
    }
    
    @Test
    public void testFindPathDirectEdge() {
        graph.addEdge(1, 2);
        List<Integer> path = BFS.findPath(graph, 1, 2);
        assertEquals(2, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(2), path.get(1));
    }
    
    @Test
    public void testFindPathMultipleHops() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        List<Integer> path = BFS.findPath(graph, 1, 4);
        assertEquals(4, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(4), path.get(path.size() - 1));
    }
    
    @Test
    public void testFindPathNoPath() {
        graph.addVertex(1);
        graph.addVertex(2);
        List<Integer> path = BFS.findPath(graph, 1, 2);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testFindPathNullGraph() {
        List<Integer> path = BFS.findPath(null, 1, 2);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testFindPathNullStart() {
        graph.addVertex(1);
        List<Integer> path = BFS.findPath(graph, null, 1);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testFindPathNullTarget() {
        graph.addVertex(1);
        List<Integer> path = BFS.findPath(graph, 1, null);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testHasPathSameVertex() {
        graph.addVertex(1);
        assertTrue(BFS.hasPath(graph, 1, 1));
    }
    
    @Test
    public void testHasPathDirectEdge() {
        graph.addEdge(1, 2);
        assertTrue(BFS.hasPath(graph, 1, 2));
    }
    
    @Test
    public void testHasPathMultipleHops() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        assertTrue(BFS.hasPath(graph, 1, 3));
    }
    
    @Test
    public void testHasPathNoPath() {
        graph.addVertex(1);
        graph.addVertex(2);
        assertFalse(BFS.hasPath(graph, 1, 2));
    }
    
    @Test
    public void testHasPathNullGraph() {
        assertFalse(BFS.hasPath(null, 1, 2));
    }
    
    @Test
    public void testHasPathNullStart() {
        graph.addVertex(1);
        assertFalse(BFS.hasPath(graph, null, 1));
    }
    
    @Test
    public void testHasPathNullTarget() {
        graph.addVertex(1);
        assertFalse(BFS.hasPath(graph, 1, null));
    }
    
    @Test
    public void testTraverseComplexGraph() {
        // Create a more complex graph
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        List<Integer> result = BFS.traverse(graph, 1);
        assertEquals(5, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
    }
}

