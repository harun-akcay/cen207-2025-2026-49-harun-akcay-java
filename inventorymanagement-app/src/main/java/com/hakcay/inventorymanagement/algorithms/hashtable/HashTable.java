/**
 * @file HashTable.java
 * @brief This file contains the HashTable class implementation.
 * @details This class provides a generic hash table data structure with O(1) average time complexity for put, get, and remove operations.
 * @package com.hakcay.inventorymanagement.algorithms.hashtable
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.hashtable;

/**
 * @class HashTable
 * @brief Generic hash table implementation using chaining for collision resolution.
 * @details This hash table uses an array of linked lists to handle collisions.
 *          It provides O(1) average time complexity for basic operations.
 * @param <K> The type of keys
 * @param <V> The type of values
 */
public class HashTable<K, V> {
    
    /**
     * @brief Internal node class for chaining.
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;
        
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    
    /** @brief Default initial capacity */
    private static final int DEFAULT_CAPACITY = 16;
    
    /** @brief Default load factor threshold for resizing */
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    
    /** @brief Array of buckets (linked lists) */
    private Node<K, V>[] buckets;
    
    /** @brief Current number of key-value pairs */
    private int size;
    
    /** @brief Load factor threshold */
    private final double loadFactor;
    
    /**
     * @brief Default constructor.
     * @details Creates a hash table with default capacity and load factor.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.buckets = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }
    
    /**
     * @brief Constructor with initial capacity.
     * @param initialCapacity The initial capacity of the hash table
     */
    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        this.buckets = (Node<K, V>[]) new Node[initialCapacity];
        this.size = 0;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }
    
    /**
     * @brief Constructor with initial capacity and load factor.
     * @param initialCapacity The initial capacity of the hash table
     * @param loadFactor The load factor threshold for resizing
     */
    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity, double loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        if (loadFactor <= 0 || loadFactor > 1) {
            throw new IllegalArgumentException("Load factor must be between 0 and 1");
        }
        this.buckets = (Node<K, V>[]) new Node[initialCapacity];
        this.size = 0;
        this.loadFactor = loadFactor;
    }
    
    /**
     * @brief Computes the hash code for a key.
     * @param key The key to hash
     * @return The hash code
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int hashCode = key.hashCode();
        // Ensure non-negative index
        return (hashCode & Integer.MAX_VALUE) % buckets.length;
    }
    
    /**
     * @brief Inserts or updates a key-value pair in the hash table.
     * @param key The key
     * @param value The value
     * @return The previous value associated with the key, or null if the key was not present
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        // Check if resize is needed
        if ((double) size / buckets.length >= loadFactor) {
            resize();
        }
        
        int index = hash(key);
        Node<K, V> current = buckets[index];
        
        // Check if key already exists
        while (current != null) {
            if (current.key.equals(key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }
        
        // Insert new node at the beginning of the chain
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;
        
        return null;
    }
    
    /**
     * @brief Retrieves the value associated with a key.
     * @param key The key to look up
     * @return The value associated with the key, or null if not found
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }
        
        int index = hash(key);
        Node<K, V> current = buckets[index];
        
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        
        return null;
    }
    
    /**
     * @brief Removes a key-value pair from the hash table.
     * @param key The key to remove
     * @return The value associated with the key, or null if not found
     */
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        
        int index = hash(key);
        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;
        
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    // First node in chain
                    buckets[index] = current.next;
                } else {
                    // Middle or last node
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        
        return null;
    }
    
    /**
     * @brief Checks if the hash table contains a key.
     * @param key The key to check
     * @return true if the key exists, false otherwise
     */
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        
        int index = hash(key);
        Node<K, V> current = buckets[index];
        
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * @brief Gets the number of key-value pairs in the hash table.
     * @return The size of the hash table
     */
    public int size() {
        return size;
    }
    
    /**
     * @brief Checks if the hash table is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * @brief Clears all key-value pairs from the hash table.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        this.buckets = (Node<K, V>[]) new Node[buckets.length];
        this.size = 0;
    }
    
    /**
     * @brief Resizes the hash table when load factor is exceeded.
     * @details Doubles the capacity and rehashes all entries.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        int newCapacity = buckets.length * 2;
        buckets = (Node<K, V>[]) new Node[newCapacity];
        int oldSize = size;
        size = 0;
        
        // Rehash all entries
        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                int newIndex = (node.key.hashCode() & Integer.MAX_VALUE) % newCapacity;
                Node<K, V> newNode = new Node<>(node.key, node.value);
                newNode.next = buckets[newIndex];
                buckets[newIndex] = newNode;
                node = node.next;
            }
        }
        
        // Restore size (we're just moving nodes, not adding new ones)
        size = oldSize;
    }
}

