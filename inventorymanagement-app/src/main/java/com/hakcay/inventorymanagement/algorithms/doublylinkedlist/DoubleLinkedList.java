/**
 * @file DoubleLinkedList.java
 * @brief This file contains the DoubleLinkedList class implementation.
 * @details This class provides a generic doubly linked list data structure with bidirectional traversal.
 * @package com.hakcay.inventorymanagement.algorithms.doublylinkedlist
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.doublylinkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @class DoubleLinkedList
 * @brief Generic doubly linked list implementation.
 * @details This list provides O(1) insertion and deletion at both ends, and O(n) for arbitrary positions.
 * @param <T> The type of elements in the list
 */
public class DoubleLinkedList<T> {
    
    /**
     * @brief Internal node class for doubly linked list.
     */
    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }
    
    /** @brief Head (first) node of the list */
    private Node<T> head;
    
    /** @brief Tail (last) node of the list */
    private Node<T> tail;
    
    /** @brief Current number of elements in the list */
    private int size;
    
    /**
     * @brief Default constructor.
     * @details Creates an empty doubly linked list.
     */
    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * @brief Adds an element to the end of the list.
     * @param data The element to add
     */
    public void add(T data) {
        if (data == null) {
            return;
        }
        
        Node<T> newNode = new Node<>(data);
        
        if (head == null) {
            // First element
            head = newNode;
            tail = newNode;
        } else {
            // Add to end
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }
    
    /**
     * @brief Adds an element at the beginning of the list.
     * @param data The element to add
     */
    public void addFirst(T data) {
        if (data == null) {
            return;
        }
        
        Node<T> newNode = new Node<>(data);
        
        if (head == null) {
            // First element
            head = newNode;
            tail = newNode;
        } else {
            // Add to beginning
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }
    
    /**
     * @brief Removes and returns the first element from the list.
     * @return The first element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        
        T data = head.data;
        
        if (head == tail) {
            // Only one element
            head = null;
            tail = null;
        } else {
            // Multiple elements
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }
    
    /**
     * @brief Removes and returns the last element from the list.
     * @return The last element
     * @throws NoSuchElementException if the list is empty
     */
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("List is empty");
        }
        
        T data = tail.data;
        
        if (head == tail) {
            // Only one element
            head = null;
            tail = null;
        } else {
            // Multiple elements
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }
    
    /**
     * @brief Removes the first occurrence of the specified element.
     * @param data The element to remove
     * @return true if the element was found and removed, false otherwise
     */
    public boolean remove(T data) {
        if (data == null || head == null) {
            return false;
        }
        
        Node<T> current = head;
        
        while (current != null) {
            if (current.data.equals(data)) {
                // Found the node to remove
                if (current == head && current == tail) {
                    // Only one element
                    head = null;
                    tail = null;
                } else if (current == head) {
                    // Remove from beginning
                    head = head.next;
                    head.prev = null;
                } else if (current == tail) {
                    // Remove from end
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    // Remove from middle
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * @brief Gets the first element without removing it.
     * @return The first element
     * @throws NoSuchElementException if the list is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }
    
    /**
     * @brief Gets the last element without removing it.
     * @return The last element
     * @throws NoSuchElementException if the list is empty
     */
    public T getLast() {
        if (tail == null) {
            throw new NoSuchElementException("List is empty");
        }
        return tail.data;
    }
    
    /**
     * @brief Gets the element at the specified index.
     * @param index The index of the element
     * @return The element at the specified index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        
        Node<T> current;
        if (index < size / 2) {
            // Start from head
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            // Start from tail
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        
        return current.data;
    }
    
    /**
     * @brief Checks if the list contains the specified element.
     * @param data The element to check
     * @return true if the element is found, false otherwise
     */
    public boolean contains(T data) {
        if (data == null) {
            return false;
        }
        
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    
    /**
     * @brief Checks if the list is empty.
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * @brief Gets the number of elements in the list.
     * @return The size of the list
     */
    public int size() {
        return size;
    }
    
    /**
     * @brief Clears all elements from the list.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * @brief Gets all elements as a list (from head to tail).
     * @return A list containing all elements
     */
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }
    
    /**
     * @brief Gets all elements as a list in reverse order (from tail to head).
     * @return A list containing all elements in reverse order
     */
    public List<T> toReverseList() {
        List<T> result = new ArrayList<>();
        Node<T> current = tail;
        while (current != null) {
            result.add(current.data);
            current = current.prev;
        }
        return result;
    }
}

