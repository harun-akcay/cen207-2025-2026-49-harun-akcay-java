package com.hakcay.inventorymanagement.algorithms.huffman;

import static org.junit.Assert.*;

import org.junit.Test;

public class HuffmanNodeTest {
    
    @Test
    public void testLeafNodeConstructor() {
        HuffmanNode node = new HuffmanNode('A', 5);
        assertEquals(Character.valueOf('A'), node.getCharacter());
        assertEquals(5, node.getFrequency());
        assertTrue(node.isLeaf());
        assertNull(node.getLeft());
        assertNull(node.getRight());
    }
    
    @Test
    public void testInternalNodeConstructor() {
        HuffmanNode left = new HuffmanNode('A', 3);
        HuffmanNode right = new HuffmanNode('B', 2);
        HuffmanNode parent = new HuffmanNode(5, left, right);
        
        assertEquals(5, parent.getFrequency());
        assertNull(parent.getCharacter());
        assertFalse(parent.isLeaf());
        assertEquals(left, parent.getLeft());
        assertEquals(right, parent.getRight());
    }
    
    @Test
    public void testCompareTo() {
        HuffmanNode node1 = new HuffmanNode('A', 3);
        HuffmanNode node2 = new HuffmanNode('B', 5);
        HuffmanNode node3 = new HuffmanNode('C', 3);
        
        assertTrue(node1.compareTo(node2) < 0);
        assertTrue(node2.compareTo(node1) > 0);
        assertEquals(0, node1.compareTo(node3));
    }
    
    @Test
    public void testIsLeaf() {
        HuffmanNode leaf = new HuffmanNode('A', 5);
        assertTrue(leaf.isLeaf());
        
        HuffmanNode left = new HuffmanNode('A', 3);
        HuffmanNode right = new HuffmanNode('B', 2);
        HuffmanNode internal = new HuffmanNode(5, left, right);
        assertFalse(internal.isLeaf());
    }
    
    @Test
    public void testGetCharacter() {
        HuffmanNode leaf = new HuffmanNode('A', 5);
        assertEquals(Character.valueOf('A'), leaf.getCharacter());
        
        HuffmanNode left = new HuffmanNode('A', 3);
        HuffmanNode right = new HuffmanNode('B', 2);
        HuffmanNode internal = new HuffmanNode(5, left, right);
        assertNull(internal.getCharacter());
    }
    
    @Test
    public void testGetFrequency() {
        HuffmanNode node = new HuffmanNode('A', 10);
        assertEquals(10, node.getFrequency());
    }
    
    @Test
    public void testGetLeft() {
        HuffmanNode left = new HuffmanNode('A', 3);
        HuffmanNode right = new HuffmanNode('B', 2);
        HuffmanNode parent = new HuffmanNode(5, left, right);
        
        assertEquals(left, parent.getLeft());
    }
    
    @Test
    public void testGetRight() {
        HuffmanNode left = new HuffmanNode('A', 3);
        HuffmanNode right = new HuffmanNode('B', 2);
        HuffmanNode parent = new HuffmanNode(5, left, right);
        
        assertEquals(right, parent.getRight());
    }
}

