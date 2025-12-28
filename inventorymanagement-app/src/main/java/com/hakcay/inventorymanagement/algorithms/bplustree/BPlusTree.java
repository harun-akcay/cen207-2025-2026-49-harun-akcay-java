/**
 * @file BPlusTree.java
 * @brief This file contains the B+ Tree implementation.
 * @details Provides efficient indexing with O(log n) search, insert, and delete operations.
 * @package com.hakcay.inventorymanagement.algorithms.bplustree
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.bplustree;

import java.util.ArrayList;
import java.util.List;

/**
 * @class BPlusTree
 * @brief B+ Tree implementation for indexing.
 * @details Provides efficient key-value storage with ordered traversal.
 * @param <K> The type of keys (must be Comparable)
 * @param <V> The type of values
 */
public class BPlusTree<K extends Comparable<K>, V> {
    
    /** @brief Default maximum keys per node */
    private static final int DEFAULT_MAX_KEYS = 3;
    
    /** @brief Root node of the tree */
    private BPlusTreeNode<K, V> root;
    
    /** @brief Maximum number of keys per node */
    private int maxKeys;
    
    /** @brief First leaf node (for ordered traversal) */
    private BPlusTreeNode<K, V> firstLeaf;
    
    /**
     * @brief Default constructor with default max keys.
     */
    public BPlusTree() {
        this(DEFAULT_MAX_KEYS);
    }
    
    /**
     * @brief Constructor with custom max keys.
     * @param maxKeys Maximum number of keys per node
     */
    public BPlusTree(int maxKeys) {
        this.maxKeys = maxKeys;
        this.root = new BPlusTreeNode<>(true, maxKeys);
        this.firstLeaf = root;
    }
    
    /**
     * @brief Inserts a key-value pair into the tree.
     * @param key The key
     * @param value The value
     */
    public void insert(K key, V value) {
        if (key == null) {
            return;
        }
        
        BPlusTreeNode<K, V> leaf = findLeaf(key);
        leaf.addKeyValue(key, value);
        
        if (leaf.isFull()) {
            splitLeaf(leaf);
        }
    }
    
    /**
     * @brief Searches for a value by key.
     * @param key The key to search for
     * @return The value associated with the key, or null if not found
     */
    public V search(K key) {
        if (key == null) {
            return null;
        }
        
        BPlusTreeNode<K, V> leaf = findLeaf(key);
        return leaf.getValue(key);
    }
    
    /**
     * @brief Deletes a key-value pair from the tree.
     * @param key The key to delete
     * @return The deleted value, or null if not found
     */
    public V delete(K key) {
        if (key == null) {
            return null;
        }
        
        BPlusTreeNode<K, V> leaf = findLeaf(key);
        V value = leaf.removeKeyValue(key);
        
        if (value != null && !leaf.hasMinimumKeys() && leaf != root) {
            // Handle underflow (simplified - would need merge/borrow in full implementation)
            // For now, we just remove the key
        }
        
        return value;
    }
    
    /**
     * @brief Gets all values in key order.
     * @return List of values in ascending key order
     */
    public List<V> getAllValues() {
        List<V> result = new ArrayList<>();
        BPlusTreeNode<K, V> current = firstLeaf;
        
        while (current != null) {
            result.addAll(current.getValues());
            current = current.getNext();
        }
        
        return result;
    }
    
    /**
     * @brief Gets all key-value pairs in key order.
     * @return List of entries in ascending key order
     */
    public List<Entry<K, V>> getAllEntries() {
        List<Entry<K, V>> result = new ArrayList<>();
        BPlusTreeNode<K, V> current = firstLeaf;
        
        while (current != null) {
            List<K> keys = current.getKeys();
            List<V> values = current.getValues();
            for (int i = 0; i < keys.size(); i++) {
                result.add(new Entry<>(keys.get(i), values.get(i)));
            }
            current = current.getNext();
        }
        
        return result;
    }
    
    /**
     * @brief Checks if the tree is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return root.getKeyCount() == 0;
    }
    
    /**
     * @brief Gets the number of entries in the tree.
     * @return Number of entries
     */
    public int size() {
        int count = 0;
        BPlusTreeNode<K, V> current = firstLeaf;
        
        while (current != null) {
            count += current.getKeyCount();
            current = current.getNext();
        }
        
        return count;
    }
    
    /**
     * @brief Clears all entries from the tree.
     */
    public void clear() {
        this.root = new BPlusTreeNode<>(true, maxKeys);
        this.firstLeaf = root;
    }
    
    /**
     * @brief Finds the leaf node that should contain the key.
     * @param key The key to find
     * @return The leaf node
     */
    private BPlusTreeNode<K, V> findLeaf(K key) {
        BPlusTreeNode<K, V> current = root;
        
        while (!current.isLeaf()) {
            List<K> keys = current.getKeys();
            List<BPlusTreeNode<K, V>> children = current.getChildren();
            
            if (children.isEmpty()) {
                // Should not happen, but handle gracefully
                break;
            }
            
            // In B+ tree internal nodes: children[0] -> keys[0] -> children[1] -> keys[1] -> ...
            // If key <= keys[i], go to children[i]
            // If key > keys[i], go to children[i+1]
            int index = 0;
            while (index < keys.size() && key.compareTo(keys.get(index)) > 0) {
                index++;
            }
            
            if (index < children.size()) {
                current = children.get(index);
            } else {
                // Should not happen, but handle gracefully
                break;
            }
        }
        
        return current;
    }
    
    /**
     * @brief Splits a full leaf node.
     * @param leaf The leaf node to split
     */
    private void splitLeaf(BPlusTreeNode<K, V> leaf) {
        int mid = leaf.getKeyCount() / 2;
        K splitKey = leaf.getKey(mid);
        
        // Create new leaf node
        BPlusTreeNode<K, V> newLeaf = new BPlusTreeNode<>(true, maxKeys);
        
        // Move half the keys and values to new leaf
        List<K> keys = leaf.getKeys();
        List<V> values = leaf.getValues();
        
        for (int i = mid; i < keys.size(); i++) {
            newLeaf.addKeyValue(keys.get(i), values.get(i));
        }
        
        // Remove moved keys from original leaf
        for (int i = keys.size() - 1; i >= mid; i--) {
            leaf.removeKeyValue(keys.get(i));
        }
        
        // Update leaf links
        newLeaf.setNext(leaf.getNext());
        newLeaf.setPrevious(leaf);
        if (leaf.getNext() != null) {
            leaf.getNext().setPrevious(newLeaf);
        }
        leaf.setNext(newLeaf);
        
        // Insert split key into parent
        if (leaf.getParent() == null) {
            // Create new root
            BPlusTreeNode<K, V> newRoot = new BPlusTreeNode<>(false, maxKeys);
            newRoot.addFirstChild(leaf);
            newRoot.addKeyChild(splitKey, newLeaf);
            root = newRoot;
            leaf.setParent(root);
            newLeaf.setParent(root);
            // firstLeaf remains the same (leftmost leaf) - leaf is still the first leaf
        } else {
            BPlusTreeNode<K, V> parent = leaf.getParent();
            parent.addKeyChild(splitKey, newLeaf);
            newLeaf.setParent(parent);
            
            if (parent.isFull()) {
                splitInternal(parent);
            }
        }
    }
    
    /**
     * @brief Splits a full internal node.
     * @param node The internal node to split
     */
    private void splitInternal(BPlusTreeNode<K, V> node) {
        int mid = node.getKeyCount() / 2;
        K splitKey = node.getKey(mid);
        
        // Create new internal node
        BPlusTreeNode<K, V> newNode = new BPlusTreeNode<>(false, maxKeys);
        
        // Move keys and children
        List<K> keys = node.getKeys();
        List<BPlusTreeNode<K, V>> children = node.getChildren();
        
        for (int i = mid + 1; i < keys.size(); i++) {
            newNode.addKeyChild(keys.get(i), children.get(i + 1));
        }
        newNode.addKeyChild(splitKey, children.get(mid + 1));
        
        // Remove moved keys from original node
        for (int i = keys.size() - 1; i >= mid; i--) {
            keys.remove(i);
            children.remove(i + 1);
        }
        
        // Insert split key into parent
        if (node.getParent() == null) {
            // Create new root
            BPlusTreeNode<K, V> newRoot = new BPlusTreeNode<>(false, maxKeys);
            newRoot.addFirstChild(node);
            newRoot.addKeyChild(splitKey, newNode);
            root = newRoot;
            node.setParent(root);
            newNode.setParent(root);
        } else {
            BPlusTreeNode<K, V> parent = node.getParent();
            parent.addKeyChild(splitKey, newNode);
            newNode.setParent(parent);
            
            if (parent.isFull()) {
                splitInternal(parent);
            }
        }
    }
    
    /**
     * @brief Entry class for key-value pairs.
     */
    public static class Entry<K, V> {
        private K key;
        private V value;
        
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return key;
        }
        
        public V getValue() {
            return value;
        }
    }
}

