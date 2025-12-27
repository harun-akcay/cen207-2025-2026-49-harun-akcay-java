package com.hakcay.inventorymanagement.algorithms.graph;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {
    private Graph<Integer> graph;
    
    @Before
    public void setUp() {
        graph = new Graph<>();
    }
    
    @Test
    public void testEmptyGraph() {
        assertTrue(graph.isEmpty());
        assertEquals(0, graph.getVertexCount());
    }
    
    @Test
    public void testAddVertex() {
        graph.addVertex(1);
        assertFalse(graph.isEmpty());
        assertEquals(1, graph.getVertexCount());
        assertTrue(graph.getVertices().contains(1));
    }
    
    @Test
    public void testAddVertexNull() {
        graph.addVertex(null);
        assertTrue(graph.isEmpty());
    }
    
    @Test
    public void testAddVertexDuplicate() {
        graph.addVertex(1);
        graph.addVertex(1);
        assertEquals(1, graph.getVertexCount());
    }
    
    @Test
    public void testAddEdge() {
        graph.addEdge(1, 2);
        assertEquals(2, graph.getVertexCount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 1)); // Undirected
    }
    
    @Test
    public void testAddEdgeNull() {
        graph.addEdge(null, 2);
        graph.addEdge(1, null);
        assertTrue(graph.isEmpty());
    }
    
    @Test
    public void testAddEdgeCreatesVertices() {
        graph.addEdge(1, 2);
        assertTrue(graph.getVertices().contains(1));
        assertTrue(graph.getVertices().contains(2));
    }
    
    @Test
    public void testGetNeighbors() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        List<Integer> neighbors = graph.getNeighbors(1);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(3));
    }
    
    @Test
    public void testGetNeighborsNull() {
        List<Integer> neighbors = graph.getNeighbors(null);
        assertTrue(neighbors.isEmpty());
    }
    
    @Test
    public void testGetNeighborsNonExistent() {
        graph.addVertex(1);
        List<Integer> neighbors = graph.getNeighbors(2);
        assertTrue(neighbors.isEmpty());
    }
    
    @Test
    public void testRemoveEdge() {
        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(1, 2));
        graph.removeEdge(1, 2);
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 1)); // Undirected
    }
    
    @Test
    public void testRemoveEdgeNull() {
        graph.addEdge(1, 2);
        graph.removeEdge(null, 2);
        graph.removeEdge(1, null);
        assertTrue(graph.hasEdge(1, 2));
    }
    
    @Test
    public void testHasEdge() {
        graph.addEdge(1, 2);
        assertTrue(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 3));
    }
    
    @Test
    public void testHasEdgeNull() {
        assertFalse(graph.hasEdge(null, 2));
        assertFalse(graph.hasEdge(1, null));
    }
    
    @Test
    public void testDirectedGraph() {
        Graph<Integer> directedGraph = new Graph<>(true);
        directedGraph.addEdge(1, 2);
        assertTrue(directedGraph.hasEdge(1, 2));
        assertFalse(directedGraph.hasEdge(2, 1)); // Directed
        assertTrue(directedGraph.isDirected());
    }
    
    @Test
    public void testClear() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.clear();
        assertTrue(graph.isEmpty());
        assertEquals(0, graph.getVertexCount());
    }
    
    @Test
    public void testMultipleEdges() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        assertEquals(3, graph.getVertexCount());
        assertEquals(2, graph.getNeighbors(1).size());
        assertEquals(2, graph.getNeighbors(2).size());
        assertEquals(2, graph.getNeighbors(3).size());
    }
    
    @Test
    public void testGetVertices() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        List<Integer> vertices = graph.getVertices();
        assertEquals(3, vertices.size());
        assertTrue(vertices.contains(1));
        assertTrue(vertices.contains(2));
        assertTrue(vertices.contains(3));
    }
}

