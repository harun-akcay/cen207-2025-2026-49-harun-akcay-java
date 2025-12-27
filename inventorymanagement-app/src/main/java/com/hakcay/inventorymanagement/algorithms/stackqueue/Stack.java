/**
 * @file Stack.java
 * @brief This file contains the Stack class implementation.
 * @details This class provides a generic stack data structure with LIFO (Last In First Out) behavior.
 * @package com.hakcay.inventorymanagement.algorithms.stackqueue
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.stackqueue;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @class Stack
 * @brief Generic stack implementation using ArrayList.
 * @details This stack provides O(1) average time complexity for push, pop, and peek operations.
 * @param <T> The type of elements in the stack
 */
public class Stack<T> {
    
    /** @brief Internal list to store stack elements */
    private List<T> elements;
    
    /**
     * @brief Default constructor.
     * @details Creates an empty stack.
     */
    public Stack() {
        this.elements = new ArrayList<>();
    }
    
    /**
     * @brief Pushes an element onto the top of the stack.
     * @param element The element to push
     */
    public void push(T element) {
        if (element != null) {
            elements.add(element);
        }
    }
    
    /**
     * @brief Removes and returns the element at the top of the stack.
     * @return The element at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.remove(elements.size() - 1);
    }
    
    /**
     * @brief Returns the element at the top of the stack without removing it.
     * @return The element at the top of the stack
     * @throws EmptyStackException if the stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.get(elements.size() - 1);
    }
    
    /**
     * @brief Checks if the stack is empty.
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    
    /**
     * @brief Gets the number of elements in the stack.
     * @return The size of the stack
     */
    public int size() {
        return elements.size();
    }
    
    /**
     * @brief Clears all elements from the stack.
     */
    public void clear() {
        elements.clear();
    }
    
    /**
     * @brief Gets all elements in the stack as a list (from bottom to top).
     * @return A list containing all elements in the stack
     */
    public List<T> toList() {
        return new ArrayList<>(elements);
    }
}

