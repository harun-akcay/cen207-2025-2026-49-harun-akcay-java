/**
 * @file DoubleLinkedListTest.java
 * @brief Test class for DoubleLinkedList implementation.
 * @package com.hakcay.inventorymanagement.algorithms.doublylinkedlist
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.doublylinkedlist;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * @class DoubleLinkedListTest
 * @brief Comprehensive unit tests for DoubleLinkedList class.
 */
public class DoubleLinkedListTest {
    
    private DoubleLinkedList<Integer> list;
    
    @Before
    public void setUp() {
        list = new DoubleLinkedList<>();
    }
    
    @Test
    public void testDefaultConstructor() {
        DoubleLinkedList<Integer> newList = new DoubleLinkedList<>();
        assertNotNull(newList);
        assertTrue(newList.isEmpty());
        assertEquals(0, newList.size());
    }
    
    @Test
    public void testAdd() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
    }
    
    @Test
    public void testAddFirst() {
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
        
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(3), list.getFirst());
        assertEquals(Integer.valueOf(1), list.getLast());
    }
    
    @Test
    public void testAddNull() {
        list.add(null);
        assertEquals(0, list.size());
        
        list.addFirst(null);
        assertEquals(0, list.size());
    }
    
    @Test
    public void testRemoveFirst() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertEquals(Integer.valueOf(1), list.removeFirst());
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(2), list.getFirst());
    }
    
    @Test
    public void testRemoveLast() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertEquals(Integer.valueOf(3), list.removeLast());
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(2), list.getLast());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstOnEmptyList() {
        list.removeFirst();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastOnEmptyList() {
        list.removeLast();
    }
    
    @Test
    public void testRemoveFirstSingleElement() {
        list.add(1);
        assertEquals(Integer.valueOf(1), list.removeFirst());
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testRemoveLastSingleElement() {
        list.add(1);
        assertEquals(Integer.valueOf(1), list.removeLast());
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testRemove() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertTrue(list.remove(2));
        assertEquals(2, list.size());
        assertFalse(list.contains(2));
        
        assertTrue(list.remove(1));
        assertEquals(1, list.size());
        
        assertTrue(list.remove(3));
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testRemoveNonExistent() {
        list.add(1);
        list.add(2);
        
        assertFalse(list.remove(999));
        assertEquals(2, list.size());
    }
    
    @Test
    public void testRemoveNull() {
        list.add(1);
        assertFalse(list.remove(null));
        assertEquals(1, list.size());
    }
    
    @Test
    public void testRemoveFromBeginning() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertTrue(list.remove(1));
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(2), list.getFirst());
    }
    
    @Test
    public void testRemoveFromEnd() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertTrue(list.remove(3));
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(2), list.getLast());
    }
    
    @Test
    public void testGetFirst() {
        list.add(1);
        list.add(2);
        
        assertEquals(Integer.valueOf(1), list.getFirst());
        assertEquals(2, list.size()); // Size should not change
    }
    
    @Test
    public void testGetLast() {
        list.add(1);
        list.add(2);
        
        assertEquals(Integer.valueOf(2), list.getLast());
        assertEquals(2, list.size()); // Size should not change
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testGetFirstOnEmptyList() {
        list.getFirst();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testGetLastOnEmptyList() {
        list.getLast();
    }
    
    @Test
    public void testGet() {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        
        assertEquals(Integer.valueOf(10), list.get(0));
        assertEquals(Integer.valueOf(30), list.get(2));
        assertEquals(Integer.valueOf(50), list.get(4));
    }
    
    @Test
    public void testGetFromTail() {
        // Test that get() uses tail optimization for indices > size/2
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        
        assertEquals(Integer.valueOf(7), list.get(7));
        assertEquals(Integer.valueOf(9), list.get(9));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {
        list.add(1);
        list.get(-1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() {
        list.add(1);
        list.get(10);
    }
    
    @Test
    public void testContains() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertTrue(list.contains(2));
        assertFalse(list.contains(999));
    }
    
    @Test
    public void testContainsNull() {
        list.add(1);
        assertFalse(list.contains(null));
    }
    
    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add(1);
        assertFalse(list.isEmpty());
        list.removeFirst();
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add(1);
        assertEquals(1, list.size());
        list.add(2);
        assertEquals(2, list.size());
        list.removeFirst();
        assertEquals(1, list.size());
    }
    
    @Test
    public void testClear() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }
    
    @Test
    public void testClearEmptyList() {
        list.clear();
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testToList() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        List<Integer> result = list.toList();
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(1), result.get(0));
        assertEquals(Integer.valueOf(2), result.get(1));
        assertEquals(Integer.valueOf(3), result.get(2));
        
        // Verify it's a copy
        result.add(4);
        assertEquals(3, list.size());
    }
    
    @Test
    public void testToReverseList() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        List<Integer> result = list.toReverseList();
        assertEquals(3, result.size());
        assertEquals(Integer.valueOf(3), result.get(0));
        assertEquals(Integer.valueOf(2), result.get(1));
        assertEquals(Integer.valueOf(1), result.get(2));
    }
    
    @Test
    public void testBidirectionalTraversal() {
        list.add(1);
        list.add(2);
        list.add(3);
        
        // Forward traversal
        List<Integer> forward = list.toList();
        assertEquals(Integer.valueOf(1), forward.get(0));
        assertEquals(Integer.valueOf(2), forward.get(1));
        assertEquals(Integer.valueOf(3), forward.get(2));
        
        // Reverse traversal
        List<Integer> reverse = list.toReverseList();
        assertEquals(Integer.valueOf(3), reverse.get(0));
        assertEquals(Integer.valueOf(2), reverse.get(1));
        assertEquals(Integer.valueOf(1), reverse.get(2));
    }
    
    @Test
    public void testMultipleOperations() {
        list.add(1);
        list.addFirst(0);
        list.add(2);
        
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(0), list.getFirst());
        assertEquals(Integer.valueOf(2), list.getLast());
        
        list.remove(1);
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(0), list.getFirst());
        assertEquals(Integer.valueOf(2), list.getLast());
    }
    
    @Test
    public void testStringList() {
        DoubleLinkedList<String> stringList = new DoubleLinkedList<>();
        stringList.add("first");
        stringList.add("second");
        stringList.add("third");
        
        assertEquals("first", stringList.getFirst());
        assertEquals("third", stringList.getLast());
        assertTrue(stringList.contains("second"));
    }
    
    @Test
    public void testLargeList() {
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        
        assertEquals(1000, list.size());
        assertEquals(Integer.valueOf(0), list.getFirst());
        assertEquals(Integer.valueOf(999), list.getLast());
        assertEquals(Integer.valueOf(500), list.get(500));
    }
    
    @Test
    public void testAddRemoveSequence() {
        list.add(1);
        list.add(2);
        list.removeFirst();
        list.add(3);
        list.removeLast();
        list.add(4);
        
        assertEquals(2, list.size());
        assertEquals(Integer.valueOf(2), list.getFirst());
        assertEquals(Integer.valueOf(4), list.getLast());
    }
}

