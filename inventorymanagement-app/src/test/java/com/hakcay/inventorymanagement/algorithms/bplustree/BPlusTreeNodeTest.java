package com.hakcay.inventorymanagement.algorithms.bplustree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BPlusTreeNodeTest {
    private BPlusTreeNode<Integer, String> leafNode;
    private BPlusTreeNode<Integer, String> internalNode;
    
    @Before
    public void setUp() {
        leafNode = new BPlusTreeNode<>(true, 3);
        internalNode = new BPlusTreeNode<>(false, 3);
    }
    
    @Test
    public void testIsLeaf() {
        assertTrue(leafNode.isLeaf());
        assertFalse(internalNode.isLeaf());
    }
    
    @Test
    public void testAddKeyValue() {
        leafNode.addKeyValue(1, "One");
        leafNode.addKeyValue(2, "Two");
        
        assertEquals(2, leafNode.getKeyCount());
        assertEquals("One", leafNode.getValue(Integer.valueOf(1)));
        assertEquals("Two", leafNode.getValue(Integer.valueOf(2)));
    }
    
    @Test
    public void testAddKeyValueOrdered() {
        leafNode.addKeyValue(3, "Three");
        leafNode.addKeyValue(1, "One");
        leafNode.addKeyValue(2, "Two");
        
        assertEquals(Integer.valueOf(1), leafNode.getKey(0));
        assertEquals(Integer.valueOf(2), leafNode.getKey(1));
        assertEquals(Integer.valueOf(3), leafNode.getKey(2));
    }
    
    @Test
    public void testAddKeyValueToInternalNode() {
        internalNode.addKeyValue(1, "One");
        assertEquals(0, internalNode.getKeyCount());
    }
    
    @Test
    public void testRemoveKeyValue() {
        leafNode.addKeyValue(1, "One");
        leafNode.addKeyValue(2, "Two");
        
        String removed = leafNode.removeKeyValue(1);
        assertEquals("One", removed);
        assertEquals(1, leafNode.getKeyCount());
        assertNull(leafNode.getValue(Integer.valueOf(1)));
    }
    
    @Test
    public void testRemoveKeyValueNonExistent() {
        leafNode.addKeyValue(1, "One");
        assertNull(leafNode.removeKeyValue(999));
        assertEquals(1, leafNode.getKeyCount());
    }
    
    @Test
    public void testGetValue() {
        leafNode.addKeyValue(1, "One");
        assertEquals("One", leafNode.getValue(Integer.valueOf(1)));
        assertNull(leafNode.getValue(Integer.valueOf(999)));
    }
    
    @Test
    public void testIsFull() {
        BPlusTreeNode<Integer, String> node = new BPlusTreeNode<>(true, 3);
        assertFalse(node.isFull());
        node.addKeyValue(1, "One");
        node.addKeyValue(2, "Two");
        assertFalse(node.isFull());
        node.addKeyValue(3, "Three");
        assertTrue(node.isFull());
    }
    
    @Test
    public void testHasMinimumKeys() {
        BPlusTreeNode<Integer, String> node = new BPlusTreeNode<>(true, 3);
        assertFalse(node.hasMinimumKeys());
        node.addKeyValue(1, "One");
        assertTrue(node.hasMinimumKeys());
    }
    
    @Test
    public void testGetKeyCount() {
        assertEquals(0, leafNode.getKeyCount());
        leafNode.addKeyValue(1, "One");
        assertEquals(1, leafNode.getKeyCount());
        leafNode.addKeyValue(2, "Two");
        assertEquals(2, leafNode.getKeyCount());
    }
    
    @Test
    public void testParent() {
        BPlusTreeNode<Integer, String> parent = new BPlusTreeNode<>(false, 3);
        leafNode.setParent(parent);
        assertEquals(parent, leafNode.getParent());
    }
    
    @Test
    public void testNextAndPrevious() {
        BPlusTreeNode<Integer, String> next = new BPlusTreeNode<>(true, 3);
        leafNode.setNext(next);
        next.setPrevious(leafNode);
        
        assertEquals(next, leafNode.getNext());
        assertEquals(leafNode, next.getPrevious());
    }
    
    @Test
    public void testGetKeys() {
        leafNode.addKeyValue(1, "One");
        leafNode.addKeyValue(2, "Two");
        
        assertEquals(2, leafNode.getKeys().size());
        assertTrue(leafNode.getKeys().contains(1));
        assertTrue(leafNode.getKeys().contains(2));
    }
    
    @Test
    public void testGetValues() {
        leafNode.addKeyValue(1, "One");
        leafNode.addKeyValue(2, "Two");
        
        assertEquals(2, leafNode.getValues().size());
        assertTrue(leafNode.getValues().contains("One"));
        assertTrue(leafNode.getValues().contains("Two"));
    }
    
    @Test
    public void testGetChildren() {
        BPlusTreeNode<Integer, String> child1 = new BPlusTreeNode<>(true, 3);
        BPlusTreeNode<Integer, String> child2 = new BPlusTreeNode<>(true, 3);
        
        internalNode.addKeyChild(5, child1);
        internalNode.addKeyChild(10, child2);
        
        assertEquals(2, internalNode.getChildren().size());
    }
    
    @Test
    public void testGetKey() {
        leafNode.addKeyValue(1, "One");
        assertEquals(Integer.valueOf(1), leafNode.getKey(0));
        assertNull(leafNode.getKey(999));
    }
    
    @Test
    public void testGetValueByIndex() {
        leafNode.addKeyValue(1, "One");
        assertEquals("One", leafNode.getValue(0));
        assertNull(leafNode.getValue(999));
    }
    
    @Test
    public void testGetChild() {
        BPlusTreeNode<Integer, String> child = new BPlusTreeNode<>(true, 3);
        internalNode.addKeyChild(5, child);
        
        assertEquals(child, internalNode.getChild(0));
        assertNull(internalNode.getChild(999));
    }
    
    @Test
    public void testGetChildFromLeaf() {
        // getChild should return null for leaf nodes
        assertNull(leafNode.getChild(0));
    }
    
    @Test
    public void testGetChildNegativeIndex() {
        BPlusTreeNode<Integer, String> child = new BPlusTreeNode<>(true, 3);
        internalNode.addKeyChild(5, child);
        
        assertNull(internalNode.getChild(-1));
    }
    
    @Test
    public void testGetValueByIndexFromInternalNode() {
        // getValue(index) should return null for internal nodes
        assertNull(internalNode.getValue(0));
    }
    
    @Test
    public void testGetValueByIndexNegativeIndex() {
        leafNode.addKeyValue(1, "One");
        assertNull(leafNode.getValue(-1));
    }
    
    @Test
    public void testAddFirstChildWhenChildrenNotEmpty() {
        BPlusTreeNode<Integer, String> child1 = new BPlusTreeNode<>(true, 3);
        BPlusTreeNode<Integer, String> child2 = new BPlusTreeNode<>(true, 3);
        
        internalNode.addFirstChild(child1);
        internalNode.addFirstChild(child2); // Should not add since children is not empty
        
        assertEquals(1, internalNode.getChildren().size());
        assertEquals(child1, internalNode.getChild(0));
    }
    
    @Test
    public void testAddFirstChildToLeaf() {
        BPlusTreeNode<Integer, String> child = new BPlusTreeNode<>(true, 3);
        leafNode.addFirstChild(child); // Should not add to leaf
        
        assertEquals(0, leafNode.getChildren().size());
    }
    
    @Test
    public void testHasMinimumKeysWithMaxKeys1() {
        BPlusTreeNode<Integer, String> node = new BPlusTreeNode<>(true, 1);
        assertFalse(node.hasMinimumKeys());
        node.addKeyValue(1, "One");
        assertTrue(node.hasMinimumKeys());
    }
    
    @Test
    public void testHasMinimumKeysWithMaxKeys2() {
        BPlusTreeNode<Integer, String> node = new BPlusTreeNode<>(true, 2);
        assertFalse(node.hasMinimumKeys());
        node.addKeyValue(1, "One");
        assertTrue(node.hasMinimumKeys());
    }
    
    @Test
    public void testAddKeyChildWithEmptyChildren() {
        BPlusTreeNode<Integer, String> child = new BPlusTreeNode<>(true, 3);
        internalNode.addKeyChild(5, child);
        
        assertEquals(1, internalNode.getChildren().size());
        assertEquals(child, internalNode.getChild(0));
        assertEquals(0, internalNode.getKeyCount()); // No key added when children is empty
    }
    
    @Test
    public void testGetKeyNegativeIndex() {
        leafNode.addKeyValue(1, "One");
        assertNull(leafNode.getKey(-1));
    }
}

