package com.hakcay.inventorymanagement.algorithms.bplustree;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BPlusTreeTest {
    private BPlusTree<Integer, String> tree;
    
    @Before
    public void setUp() {
        tree = new BPlusTree<>();
    }
    
    @Test
    public void testInsertAndSearch() {
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.insert(3, "Three");
        
        assertEquals("One", tree.search(1));
        assertEquals("Two", tree.search(2));
        assertEquals("Three", tree.search(3));
    }
    
    @Test
    public void testInsertNullKey() {
        tree.insert(null, "Value");
        assertTrue(tree.isEmpty());
    }
    
    @Test
    public void testSearchNullKey() {
        tree.insert(1, "One");
        assertNull(tree.search(null));
    }
    
    @Test
    public void testSearchNonExistent() {
        tree.insert(1, "One");
        assertNull(tree.search(999));
    }
    
    @Test
    public void testDelete() {
        tree.insert(1, "One");
        tree.insert(2, "Two");
        
        String deleted = tree.delete(1);
        assertEquals("One", deleted);
        assertNull(tree.search(1));
        assertEquals("Two", tree.search(2));
    }
    
    @Test
    public void testDeleteNonExistent() {
        tree.insert(1, "One");
        assertNull(tree.delete(999));
        assertEquals("One", tree.search(1));
    }
    
    @Test
    public void testDeleteNullKey() {
        tree.insert(1, "One");
        assertNull(tree.delete(null));
    }
    
    @Test
    public void testIsEmpty() {
        assertTrue(tree.isEmpty());
        tree.insert(1, "One");
        assertFalse(tree.isEmpty());
    }
    
    @Test
    public void testSize() {
        assertEquals(0, tree.size());
        tree.insert(1, "One");
        assertEquals(1, tree.size());
        tree.insert(2, "Two");
        assertEquals(2, tree.size());
    }
    
    @Test
    public void testClear() {
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.clear();
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }
    
    @Test
    public void testGetAllValues() {
        tree.insert(3, "Three");
        tree.insert(1, "One");
        tree.insert(2, "Two");
        
        List<String> values = tree.getAllValues();
        assertEquals(3, values.size());
        assertEquals("One", values.get(0));
        assertEquals("Two", values.get(1));
        assertEquals("Three", values.get(2));
    }
    
    @Test
    public void testGetAllValuesEmpty() {
        List<String> values = tree.getAllValues();
        assertTrue(values.isEmpty());
    }
    
    @Test
    public void testGetAllEntries() {
        tree.insert(3, "Three");
        tree.insert(1, "One");
        tree.insert(2, "Two");
        
        List<BPlusTree.Entry<Integer, String>> entries = tree.getAllEntries();
        assertEquals(3, entries.size());
        assertEquals(Integer.valueOf(1), entries.get(0).getKey());
        assertEquals("One", entries.get(0).getValue());
        assertEquals(Integer.valueOf(2), entries.get(1).getKey());
        assertEquals("Two", entries.get(1).getValue());
        assertEquals(Integer.valueOf(3), entries.get(2).getKey());
        assertEquals("Three", entries.get(2).getValue());
    }
    
    @Test
    public void testGetAllEntriesEmpty() {
        List<BPlusTree.Entry<Integer, String>> entries = tree.getAllEntries();
        assertTrue(entries.isEmpty());
    }
    
    @Test
    public void testInsertManyValues() {
        for (int i = 1; i <= 20; i++) {
            tree.insert(i, "Value" + i);
        }
        
        assertEquals(20, tree.size());
        assertEquals("Value1", tree.search(1));
        assertEquals("Value20", tree.search(20));
    }
    
    @Test
    public void testOrderedTraversal() {
        tree.insert(5, "Five");
        tree.insert(2, "Two");
        tree.insert(8, "Eight");
        tree.insert(1, "One");
        tree.insert(9, "Nine");
        
        List<String> values = tree.getAllValues();
        assertEquals(5, values.size());
        assertEquals("One", values.get(0));
        assertEquals("Two", values.get(1));
        assertEquals("Five", values.get(2));
        assertEquals("Eight", values.get(3));
        assertEquals("Nine", values.get(4));
    }
    
    @Test
    public void testCustomMaxKeys() {
        BPlusTree<Integer, String> customTree = new BPlusTree<>(5);
        for (int i = 1; i <= 10; i++) {
            customTree.insert(i, "Value" + i);
        }
        
        assertEquals(10, customTree.size());
        assertEquals("Value5", customTree.search(5));
    }
    
    @Test
    public void testInsertDuplicateKey() {
        tree.insert(1, "One");
        tree.insert(1, "OneUpdated");
        
        // B+ Tree typically updates value for duplicate key
        assertEquals("OneUpdated", tree.search(1));
    }
    
    @Test
    public void testDeleteAll() {
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.insert(3, "Three");
        
        tree.delete(1);
        tree.delete(2);
        tree.delete(3);
        
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }
    
    @Test
    public void testLeafSplit() {
        // Default maxKeys is 3, so inserting 4 items should trigger split
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.insert(3, "Three");
        tree.insert(4, "Four"); // This should trigger split
        
        assertEquals(4, tree.size());
        assertEquals("One", tree.search(1));
        assertEquals("Two", tree.search(2));
        assertEquals("Three", tree.search(3));
        assertEquals("Four", tree.search(4));
        
        // Verify ordered traversal still works after split
        List<String> values = tree.getAllValues();
        assertEquals(4, values.size());
        assertEquals("One", values.get(0));
        assertEquals("Four", values.get(3));
    }
    
    @Test
    public void testMultipleSplits() {
        // Insert enough items to trigger multiple splits
        for (int i = 1; i <= 10; i++) {
            tree.insert(i, "Value" + i);
        }
        
        assertEquals(10, tree.size());
        for (int i = 1; i <= 10; i++) {
            assertEquals("Value" + i, tree.search(i));
        }
        
        // Verify ordered traversal
        List<String> values = tree.getAllValues();
        assertEquals(10, values.size());
        for (int i = 0; i < 10; i++) {
            assertEquals("Value" + (i + 1), values.get(i));
        }
    }
    
    @Test
    public void testInternalNodeSplit() {
        // Create a tree with maxKeys=2 to trigger splits more easily
        BPlusTree<Integer, String> smallTree = new BPlusTree<>(2);
        
        // Insert items to trigger internal node split
        for (int i = 1; i <= 7; i++) {
            smallTree.insert(i, "Value" + i);
        }
        
        assertEquals(7, smallTree.size());
        assertEquals("Value1", smallTree.search(1));
        assertEquals("Value7", smallTree.search(7));
        
        // Verify ordered traversal
        List<String> values = smallTree.getAllValues();
        assertEquals(7, values.size());
    }
    
    @Test
    public void testInsertAfterSplit() {
        tree.insert(1, "One");
        tree.insert(2, "Two");
        tree.insert(3, "Three");
        tree.insert(4, "Four"); // Split occurs
        
        // Insert more after split
        tree.insert(5, "Five");
        tree.insert(6, "Six");
        
        assertEquals(6, tree.size());
        assertEquals("Five", tree.search(5));
        assertEquals("Six", tree.search(6));
    }
    
    @Test
    public void testDeleteAfterSplit() {
        // Insert to trigger split
        for (int i = 1; i <= 5; i++) {
            tree.insert(i, "Value" + i);
        }
        
        // Delete items after split
        tree.delete(2);
        tree.delete(4);
        
        assertEquals(3, tree.size());
        assertNull(tree.search(2));
        assertNull(tree.search(4));
        assertEquals("Value1", tree.search(1));
        assertEquals("Value3", tree.search(3));
        assertEquals("Value5", tree.search(5));
    }
    
    @Test
    public void testDeleteRootLeaf() {
        // Delete from root leaf (single node tree)
        tree.insert(1, "One");
        tree.delete(1);
        
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
    }
    
    @Test
    public void testMultipleInternalSplits() {
        // Create tree with maxKeys=2 to trigger multiple internal splits
        BPlusTree<Integer, String> smallTree = new BPlusTree<>(2);
        
        // Insert enough items to trigger multiple internal node splits
        for (int i = 1; i <= 15; i++) {
            smallTree.insert(i, "Value" + i);
        }
        
        assertEquals(15, smallTree.size());
        for (int i = 1; i <= 15; i++) {
            assertEquals("Value" + i, smallTree.search(i));
        }
    }
    
    @Test
    public void testFindLeafWithEmptyChildren() {
        // This tests the edge case in findLeaf where children.isEmpty()
        // This is hard to trigger directly, but we can test with a complex tree structure
        BPlusTree<Integer, String> testTree = new BPlusTree<>(2);
        
        // Insert items to create a complex tree structure
        for (int i = 1; i <= 10; i++) {
            testTree.insert(i, "Value" + i);
        }
        
        // All searches should still work
        for (int i = 1; i <= 10; i++) {
            assertNotNull("Should find value for key " + i, testTree.search(i));
        }
    }
    
    @Test
    public void testSplitInternalCreatesNewRoot() {
        // Create tree with maxKeys=2 to easily trigger internal splits
        BPlusTree<Integer, String> smallTree = new BPlusTree<>(2);
        
        // Insert enough to trigger internal node split that creates new root
        for (int i = 1; i <= 8; i++) {
            smallTree.insert(i, "Value" + i);
        }
        
        assertEquals(8, smallTree.size());
        // Verify all values are still accessible
        for (int i = 1; i <= 8; i++) {
            assertEquals("Value" + i, smallTree.search(i));
        }
    }
    
    @Test
    public void testRecursiveInternalSplit() {
        // Create tree with maxKeys=2 to trigger recursive internal splits
        BPlusTree<Integer, String> smallTree = new BPlusTree<>(2);
        
        // Insert enough items to trigger recursive internal node splits
        for (int i = 1; i <= 20; i++) {
            smallTree.insert(i, "Value" + i);
        }
        
        assertEquals(20, smallTree.size());
        // Verify ordered traversal still works
        List<String> values = smallTree.getAllValues();
        assertEquals(20, values.size());
        for (int i = 0; i < 20; i++) {
            assertEquals("Value" + (i + 1), values.get(i));
        }
    }
}

