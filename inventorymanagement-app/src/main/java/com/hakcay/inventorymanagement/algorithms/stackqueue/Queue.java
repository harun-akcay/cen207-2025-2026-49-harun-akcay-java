/**
 * @file Queue.java
 * @brief This file contains the Queue class implementation.
 * @details This class provides a generic queue data structure with FIFO (First In First Out) behavior.
 * @package com.hakcay.inventorymanagement.algorithms.stackqueue
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.stackqueue;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;

/**
 * @class Queue
 * @brief Generic queue implementation using ArrayList.
 * @details This queue provides O(1) average time complexity for enqueue and dequeue operations.
 * @param <T> The type of elements in the queue
 */
public class Queue<T> {
    
    /** @brief Internal list to store queue elements */
    private List<T> elements;
    
    /**
     * @brief Default constructor.
     * @details Creates an empty queue.
     */
    public Queue() {
        this.elements = new ArrayList<>();
    }
    
    /**
     * @brief Adds an element to the rear of the queue.
     * @param element The element to enqueue
     */
    public void enqueue(T element) {
        if (element != null) {
            elements.add(element);
        }
    }
    
    /**
     * @brief Removes and returns the element at the front of the queue.
     * @return The element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elements.remove(0);
    }
    
    /**
     * @brief Returns the element at the front of the queue without removing it.
     * @return The element at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elements.get(0);
    }
    
    /**
     * @brief Checks if the queue is empty.
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    
    /**
     * @brief Gets the number of elements in the queue.
     * @return The size of the queue
     */
    public int size() {
        return elements.size();
    }
    
    /**
     * @brief Clears all elements from the queue.
     */
    public void clear() {
        elements.clear();
    }
    
    /**
     * @brief Gets all elements in the queue as a list (from front to rear).
     * @return A list containing all elements in the queue
     */
    public List<T> toList() {
        return new ArrayList<>(elements);
    }
}

