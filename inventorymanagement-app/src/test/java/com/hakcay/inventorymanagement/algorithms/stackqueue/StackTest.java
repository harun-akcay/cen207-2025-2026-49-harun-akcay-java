/**
 * @file StackTest.java
 * @brief Test class for Stack implementation.
 * @package com.hakcay.inventorymanagement.algorithms.stackqueue
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.stackqueue;

import static org.junit.Assert.*;

import java.util.EmptyStackException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @class StackTest
 * @brief Comprehensive unit tests for Stack class.
 */
public class StackTest {
    
    private Stack<Integer> stack;
    
    @Before
    public void setUp() {
        stack = new Stack<>();
    }
    
    @Test
    public void testDefaultConstructor() {
        Stack<Integer> newStack = new Stack<>();
        assertNotNull(newStack);
        assertTrue(newStack.isEmpty());
        assertEquals(0, newStack.size());
    }
    
    @Test
    public void testPushAndPop() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        assertEquals(3, stack.size());
        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
        assertTrue(stack.isEmpty());
    }
    
    @Test
    public void testPeek() {
        stack.push(10);
        stack.push(20);
        
        assertEquals(Integer.valueOf(20), stack.peek());
        assertEquals(2, stack.size()); // Size should not change
        assertEquals(Integer.valueOf(20), stack.peek());
    }
    
    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }
    
    @Test
    public void testSize() {
        assertEquals(0, stack.size());
        stack.push(1);
        assertEquals(1, stack.size());
        stack.push(2);
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
    }
    
    @Test(expected = EmptyStackException.class)
    public void testPopOnEmptyStack() {
        stack.pop();
    }
    
    @Test(expected = EmptyStackException.class)
    public void testPeekOnEmptyStack() {
        stack.peek();
    }
    
    @Test
    public void testClear() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        stack.clear();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }
    
    @Test
    public void testPushNull() {
        stack.push(null);
        assertTrue(stack.isEmpty()); // Null elements should not be added
        assertEquals(0, stack.size());
    }
    
    @Test
    public void testMultiplePushPop() {
        for (int i = 0; i < 100; i++) {
            stack.push(i);
        }
        
        assertEquals(100, stack.size());
        
        for (int i = 99; i >= 0; i--) {
            assertEquals(Integer.valueOf(i), stack.pop());
        }
        
        assertTrue(stack.isEmpty());
    }
    
    @Test
    public void testLIFOBehavior() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        // Last In First Out
        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
    }
    
    @Test
    public void testToList() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        
        List<Integer> list = stack.toList();
        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(1), list.get(0));
        assertEquals(Integer.valueOf(2), list.get(1));
        assertEquals(Integer.valueOf(3), list.get(2));
        
        // Verify it's a copy
        list.add(4);
        assertEquals(3, stack.size());
    }
    
    @Test
    public void testStringStack() {
        Stack<String> stringStack = new Stack<>();
        stringStack.push("first");
        stringStack.push("second");
        stringStack.push("third");
        
        assertEquals("third", stringStack.pop());
        assertEquals("second", stringStack.pop());
        assertEquals("first", stringStack.pop());
    }
    
    @Test
    public void testMixedOperations() {
        stack.push(1);
        stack.push(2);
        assertEquals(Integer.valueOf(2), stack.peek());
        stack.push(3);
        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.peek());
        stack.push(4);
        assertEquals(Integer.valueOf(4), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
    }
    
    @Test
    public void testClearThenPush() {
        stack.push(1);
        stack.push(2);
        stack.clear();
        
        stack.push(3);
        assertEquals(1, stack.size());
        assertEquals(Integer.valueOf(3), stack.pop());
    }
}

