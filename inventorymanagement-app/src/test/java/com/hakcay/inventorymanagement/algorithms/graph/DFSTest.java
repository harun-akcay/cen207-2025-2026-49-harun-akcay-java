package com.hakcay.inventorymanagement.algorithms.graph;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DFSTest {
    private Graph<Integer> graph;
    
    @Before
    public void setUp() {
        graph = new Graph<>();
    }
    
    @Test
    public void testTraverseEmptyGraph() {
        List<Integer> result = DFS.traverse(graph, 1);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseNullGraph() {
        List<Integer> result = DFS.traverse(null, 1);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseNullStart() {
        graph.addVertex(1);
        List<Integer> result = DFS.traverse(graph, null);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseSingleVertex() {
        graph.addVertex(1);
        List<Integer> result = DFS.traverse(graph, 1);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
    }
    
    @Test
    public void testTraverseLinearGraph() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        List<Integer> result = DFS.traverse(graph, 1);
        assertEquals(4, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
    }
    
    @Test
    public void testTraverseRecursive() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        List<Integer> result = DFS.traverseRecursive(graph, 1);
        assertEquals(4, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
        assertTrue(result.contains(4));
    }
    
    @Test
    public void testTraverseRecursiveEmptyGraph() {
        List<Integer> result = DFS.traverseRecursive(graph, 1);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseRecursiveNullGraph() {
        List<Integer> result = DFS.traverseRecursive(null, 1);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testTraverseRecursiveNullStart() {
        graph.addVertex(1);
        List<Integer> result = DFS.traverseRecursive(graph, null);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testFindPathSameVertex() {
        graph.addVertex(1);
        List<Integer> path = DFS.findPath(graph, 1, 1);
        assertEquals(1, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
    }
    
    @Test
    public void testFindPathDirectEdge() {
        graph.addEdge(1, 2);
        List<Integer> path = DFS.findPath(graph, 1, 2);
        assertEquals(2, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(2), path.get(1));
    }
    
    @Test
    public void testFindPathMultipleHops() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        List<Integer> path = DFS.findPath(graph, 1, 4);
        assertFalse(path.isEmpty());
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(4), path.get(path.size() - 1));
    }
    
    @Test
    public void testFindPathNoPath() {
        graph.addVertex(1);
        graph.addVertex(2);
        List<Integer> path = DFS.findPath(graph, 1, 2);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testFindPathNullGraph() {
        List<Integer> path = DFS.findPath(null, 1, 2);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testFindPathNullStart() {
        graph.addVertex(1);
        List<Integer> path = DFS.findPath(graph, null, 1);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testFindPathNullTarget() {
        graph.addVertex(1);
        List<Integer> path = DFS.findPath(graph, 1, null);
        assertTrue(path.isEmpty());
    }
    
    @Test
    public void testHasPathSameVertex() {
        graph.addVertex(1);
        assertTrue(DFS.hasPath(graph, 1, 1));
    }
    
    @Test
    public void testHasPathDirectEdge() {
        graph.addEdge(1, 2);
        assertTrue(DFS.hasPath(graph, 1, 2));
    }
    
    @Test
    public void testHasPathMultipleHops() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        assertTrue(DFS.hasPath(graph, 1, 3));
    }
    
    @Test
    public void testHasPathNoPath() {
        graph.addVertex(1);
        graph.addVertex(2);
        assertFalse(DFS.hasPath(graph, 1, 2));
    }
    
    @Test
    public void testHasPathNullGraph() {
        assertFalse(DFS.hasPath(null, 1, 2));
    }
    
    @Test
    public void testHasPathNullStart() {
        graph.addVertex(1);
        assertFalse(DFS.hasPath(graph, null, 1));
    }
    
    @Test
    public void testHasPathNullTarget() {
        graph.addVertex(1);
        assertFalse(DFS.hasPath(graph, 1, null));
    }
    
    @Test
    public void testTraverseComplexGraph() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        List<Integer> result = DFS.traverse(graph, 1);
        assertEquals(5, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
        assertTrue(result.contains(5));
    }
    
    @Test
    public void testTraverseDisconnectedGraph() {
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        List<Integer> result = DFS.traverse(graph, 1);
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertFalse(result.contains(3));
        assertFalse(result.contains(4));
    }
}

