/**
 * @file QueueTest.java
 * @brief Test class for Queue implementation.
 * @package com.hakcay.inventorymanagement.algorithms.stackqueue
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.stackqueue;

import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * @class QueueTest
 * @brief Comprehensive unit tests for Queue class.
 */
public class QueueTest {
    
    private Queue<Integer> queue;
    
    @Before
    public void setUp() {
        queue = new Queue<>();
    }
    
    @Test
    public void testDefaultConstructor() {
        Queue<Integer> newQueue = new Queue<>();
        assertNotNull(newQueue);
        assertTrue(newQueue.isEmpty());
        assertEquals(0, newQueue.size());
    }
    
    @Test
    public void testEnqueueAndDequeue() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        assertEquals(3, queue.size());
        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertEquals(Integer.valueOf(3), queue.dequeue());
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void testPeek() {
        queue.enqueue(10);
        queue.enqueue(20);
        
        assertEquals(Integer.valueOf(10), queue.peek());
        assertEquals(2, queue.size()); // Size should not change
        assertEquals(Integer.valueOf(10), queue.peek());
    }
    
    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void testSize() {
        assertEquals(0, queue.size());
        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.enqueue(2);
        assertEquals(2, queue.size());
        queue.dequeue();
        assertEquals(1, queue.size());
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testDequeueOnEmptyQueue() {
        queue.dequeue();
    }
    
    @Test(expected = NoSuchElementException.class)
    public void testPeekOnEmptyQueue() {
        queue.peek();
    }
    
    @Test
    public void testClear() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        queue.clear();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }
    
    @Test
    public void testEnqueueNull() {
        queue.enqueue(null);
        assertTrue(queue.isEmpty()); // Null elements should not be added
        assertEquals(0, queue.size());
    }
    
    @Test
    public void testMultipleEnqueueDequeue() {
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
        }
        
        assertEquals(100, queue.size());
        
        for (int i = 0; i < 100; i++) {
            assertEquals(Integer.valueOf(i), queue.dequeue());
        }
        
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void testFIFOBehavior() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        // First In First Out
        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertEquals(Integer.valueOf(3), queue.dequeue());
    }
    
    @Test
    public void testToList() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        List<Integer> list = queue.toList();
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(1), list.get(0));
        assertEquals(Integer.valueOf(2), list.get(1));
        assertEquals(Integer.valueOf(3), list.get(2));
        
        // Verify it's a copy
        list.add(4);
        assertEquals(3, queue.size());
    }
    
    @Test
    public void testStringQueue() {
        Queue<String> stringQueue = new Queue<>();
        stringQueue.enqueue("first");
        stringQueue.enqueue("second");
        stringQueue.enqueue("third");
        
        assertEquals("first", stringQueue.dequeue());
        assertEquals("second", stringQueue.dequeue());
        assertEquals("third", stringQueue.dequeue());
    }
    
    @Test
    public void testMixedOperations() {
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(Integer.valueOf(1), queue.peek());
        queue.enqueue(3);
        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.peek());
        queue.enqueue(4);
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertEquals(Integer.valueOf(3), queue.dequeue());
        assertEquals(Integer.valueOf(4), queue.dequeue());
    }
    
    @Test
    public void testClearThenEnqueue() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.clear();
        
        queue.enqueue(3);
        assertEquals(1, queue.size());
        assertEquals(Integer.valueOf(3), queue.dequeue());
    }
    
    @Test
    public void testNoSuchElementExceptionMessage() {
        try {
            queue.dequeue();
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            assertTrue(e.getMessage().contains("Queue is empty"));
        }
    }
}

