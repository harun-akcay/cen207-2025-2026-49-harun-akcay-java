package com.hakcay.inventorymanagement.algorithms.graph;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StronglyConnectedComponentsTest {
    private Graph<Integer> graph;
    
    @Before
    public void setUp() {
        graph = new Graph<>(true); // Directed graph
    }
    
    @Test
    public void testFindSCCEmptyGraph() {
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        assertTrue(components.isEmpty());
    }
    
    @Test
    public void testFindSCCNullGraph() {
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(null);
        assertTrue(components.isEmpty());
    }
    
    @Test
    public void testFindSCCSingleVertex() {
        graph.addVertex(1);
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        assertEquals(1, components.size());
        assertEquals(1, components.get(0).size());
        assertTrue(components.get(0).contains(1));
    }
    
    @Test
    public void testFindSCCNoEdges() {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        assertEquals(3, components.size());
    }
    
    @Test
    public void testFindSCCSimpleCycle() {
        // 1 -> 2 -> 3 -> 1 (cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        assertEquals(1, components.size());
        assertEquals(3, components.get(0).size());
        assertTrue(components.get(0).contains(1));
        assertTrue(components.get(0).contains(2));
        assertTrue(components.get(0).contains(3));
    }
    
    @Test
    public void testFindSCCMultipleComponents() {
        // Component 1: 1 -> 2 -> 1 (cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        
        // Component 2: 3 -> 4 -> 3 (cycle)
        graph.addEdge(3, 4);
        graph.addEdge(4, 3);
        
        // Component 3: 5 (single vertex)
        graph.addVertex(5);
        
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        assertEquals(3, components.size());
        
        // Find components with size > 1 (cycles)
        int cycleCount = 0;
        for (List<Integer> component : components) {
            if (component.size() > 1) {
                cycleCount++;
            }
        }
        assertEquals(2, cycleCount);
    }
    
    @Test
    public void testFindSCCNoCycle() {
        // 1 -> 2 -> 3 (no cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        assertEquals(3, components.size()); // Each vertex is its own component
    }
    
    @Test
    public void testHasCycleTrue() {
        // 1 -> 2 -> 3 -> 1 (cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        
        assertTrue(StronglyConnectedComponents.hasCycle(graph));
    }
    
    @Test
    public void testHasCycleFalse() {
        // 1 -> 2 -> 3 (no cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        
        assertFalse(StronglyConnectedComponents.hasCycle(graph));
    }
    
    @Test
    public void testHasCycleEmptyGraph() {
        assertFalse(StronglyConnectedComponents.hasCycle(graph));
    }
    
    @Test
    public void testHasCycleNullGraph() {
        assertFalse(StronglyConnectedComponents.hasCycle(null));
    }
    
    @Test
    public void testHasCycleSingleVertex() {
        graph.addVertex(1);
        assertFalse(StronglyConnectedComponents.hasCycle(graph));
    }
    
    @Test
    public void testFindCycles() {
        // Component 1: 1 -> 2 -> 1 (cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);
        
        // Component 2: 3 -> 4 -> 3 (cycle)
        graph.addEdge(3, 4);
        graph.addEdge(4, 3);
        
        // Component 3: 5 (single vertex, no cycle)
        graph.addVertex(5);
        
        List<List<Integer>> cycles = StronglyConnectedComponents.findCycles(graph);
        assertEquals(2, cycles.size());
    }
    
    @Test
    public void testFindCyclesNoCycles() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        
        List<List<Integer>> cycles = StronglyConnectedComponents.findCycles(graph);
        assertTrue(cycles.isEmpty());
    }
    
    @Test
    public void testFindCyclesEmptyGraph() {
        List<List<Integer>> cycles = StronglyConnectedComponents.findCycles(graph);
        assertTrue(cycles.isEmpty());
    }
    
    @Test
    public void testFindCyclesNullGraph() {
        List<List<Integer>> cycles = StronglyConnectedComponents.findCycles(null);
        assertTrue(cycles.isEmpty());
    }
    
    @Test
    public void testIsInCycleTrue() {
        // 1 -> 2 -> 3 -> 1 (cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        
        assertTrue(StronglyConnectedComponents.isInCycle(graph, 1));
        assertTrue(StronglyConnectedComponents.isInCycle(graph, 2));
        assertTrue(StronglyConnectedComponents.isInCycle(graph, 3));
    }
    
    @Test
    public void testIsInCycleFalse() {
        // 1 -> 2 -> 3 (no cycle)
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        
        assertFalse(StronglyConnectedComponents.isInCycle(graph, 1));
        assertFalse(StronglyConnectedComponents.isInCycle(graph, 2));
        assertFalse(StronglyConnectedComponents.isInCycle(graph, 3));
    }
    
    @Test
    public void testIsInCycleNullVertex() {
        graph.addVertex(1);
        assertFalse(StronglyConnectedComponents.isInCycle(graph, null));
    }
    
    @Test
    public void testIsInCycleNullGraph() {
        assertFalse(StronglyConnectedComponents.isInCycle(null, 1));
    }
    
    @Test
    public void testIsInCycleNonExistentVertex() {
        graph.addVertex(1);
        assertFalse(StronglyConnectedComponents.isInCycle(graph, 999));
    }
    
    @Test
    public void testComplexCycle() {
        // Complex cycle: 1 -> 2 -> 3 -> 4 -> 2 (cycle: 2,3,4)
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 2);
        
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        // Should have 2 components: {1} and {2,3,4}
        assertEquals(2, components.size());
        
        boolean foundCycle = false;
        for (List<Integer> component : components) {
            if (component.size() > 1) {
                foundCycle = true;
                assertTrue(component.contains(2));
                assertTrue(component.contains(3));
                assertTrue(component.contains(4));
            }
        }
        assertTrue(foundCycle);
    }
    
    @Test
    public void testSelfLoop() {
        // Self loop: 1 -> 1
        graph.addEdge(1, 1);
        
        List<List<Integer>> components = StronglyConnectedComponents.findSCC(graph);
        assertEquals(1, components.size());
        assertTrue(StronglyConnectedComponents.hasCycle(graph));
        assertTrue(StronglyConnectedComponents.isInCycle(graph, 1));
    }
}

