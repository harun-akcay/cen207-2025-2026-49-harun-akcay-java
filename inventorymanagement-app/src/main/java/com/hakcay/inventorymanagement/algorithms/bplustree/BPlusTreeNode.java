/**
 * @file BPlusTreeNode.java
 * @brief This file contains the BPlusTreeNode class for B+ Tree.
 * @details Represents a node in the B+ tree structure.
 * @package com.hakcay.inventorymanagement.algorithms.bplustree
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.bplustree;

import java.util.ArrayList;
import java.util.List;

/**
 * @class BPlusTreeNode
 * @brief Node class for B+ tree.
 * @details Represents a node in the B+ tree used for indexing.
 * @param <K> The type of keys
 * @param <V> The type of values
 */
public class BPlusTreeNode<K extends Comparable<K>, V> {
    
    /** @brief Whether this is a leaf node */
    private boolean isLeaf;
    
    /** @brief List of keys */
    private List<K> keys;
    
    /** @brief List of values (only for leaf nodes) */
    private List<V> values;
    
    /** @brief List of child nodes (only for internal nodes) */
    private List<BPlusTreeNode<K, V>> children;
    
    /** @brief Parent node */
    private BPlusTreeNode<K, V> parent;
    
    /** @brief Next leaf node (for leaf traversal) */
    private BPlusTreeNode<K, V> next;
    
    /** @brief Previous leaf node (for leaf traversal) */
    private BPlusTreeNode<K, V> previous;
    
    /** @brief Maximum number of keys in a node */
    private int maxKeys;
    
    /**
     * @brief Constructor.
     * @param isLeaf Whether this is a leaf node
     * @param maxKeys Maximum number of keys
     */
    public BPlusTreeNode(boolean isLeaf, int maxKeys) {
        this.isLeaf = isLeaf;
        this.maxKeys = maxKeys;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.values = isLeaf ? new ArrayList<>() : null;
        this.parent = null;
        this.next = null;
        this.previous = null;
    }
    
    /**
     * @brief Checks if this is a leaf node.
     * @return true if leaf, false otherwise
     */
    public boolean isLeaf() {
        return isLeaf;
    }
    
    /**
     * @brief Gets the keys.
     * @return List of keys
     */
    public List<K> getKeys() {
        return new ArrayList<>(keys);
    }
    
    /**
     * @brief Gets the values (only for leaf nodes).
     * @return List of values
     */
    public List<V> getValues() {
        if (isLeaf && values != null) {
            return new ArrayList<>(values);
        }
        return new ArrayList<>();
    }
    
    /**
     * @brief Gets the children (only for internal nodes).
     * @return List of child nodes
     */
    public List<BPlusTreeNode<K, V>> getChildren() {
        if (!isLeaf) {
            return new ArrayList<>(children);
        }
        return new ArrayList<>();
    }
    
    /**
     * @brief Gets the parent node.
     * @return Parent node, or null if root
     */
    public BPlusTreeNode<K, V> getParent() {
        return parent;
    }
    
    /**
     * @brief Sets the parent node.
     * @param parent The parent node
     */
    public void setParent(BPlusTreeNode<K, V> parent) {
        this.parent = parent;
    }
    
    /**
     * @brief Gets the next leaf node.
     * @return Next leaf node, or null if none
     */
    public BPlusTreeNode<K, V> getNext() {
        return next;
    }
    
    /**
     * @brief Sets the next leaf node.
     * @param next The next leaf node
     */
    public void setNext(BPlusTreeNode<K, V> next) {
        this.next = next;
    }
    
    /**
     * @brief Gets the previous leaf node.
     * @return Previous leaf node, or null if none
     */
    public BPlusTreeNode<K, V> getPrevious() {
        return previous;
    }
    
    /**
     * @brief Sets the previous leaf node.
     * @param previous The previous leaf node
     */
    public void setPrevious(BPlusTreeNode<K, V> previous) {
        this.previous = previous;
    }
    
    /**
     * @brief Checks if the node is full.
     * @return true if full, false otherwise
     */
    public boolean isFull() {
        return keys.size() >= maxKeys;
    }
    
    /**
     * @brief Checks if the node has minimum keys.
     * @return true if has minimum keys, false otherwise
     */
    public boolean hasMinimumKeys() {
        int minKeys = (maxKeys + 1) / 2;
        return keys.size() >= minKeys;
    }
    
    /**
     * @brief Gets the number of keys.
     * @return Number of keys
     */
    public int getKeyCount() {
        return keys.size();
    }
    
    /**
     * @brief Adds a key-value pair (for leaf nodes).
     * @param key The key
     * @param value The value
     */
    public void addKeyValue(K key, V value) {
        if (!isLeaf) {
            return;
        }
        
        int index = 0;
        while (index < keys.size() && keys.get(index).compareTo(key) < 0) {
            index++;
        }
        
        keys.add(index, key);
        values.add(index, value);
    }
    
    /**
     * @brief Adds a key with child node (for internal nodes).
     * @param key The key
     * @param child The child node
     */
    public void addKeyChild(K key, BPlusTreeNode<K, V> child) {
        if (isLeaf) {
            return;
        }
        
        int index = 0;
        while (index < keys.size() && keys.get(index).compareTo(key) < 0) {
            index++;
        }
        
        keys.add(index, key);
        children.add(index + 1, child);
        child.setParent(this);
    }
    
    /**
     * @brief Removes a key-value pair (for leaf nodes).
     * @param key The key to remove
     * @return The removed value, or null if not found
     */
    public V removeKeyValue(K key) {
        if (!isLeaf) {
            return null;
        }
        
        int index = keys.indexOf(key);
        if (index != -1) {
            keys.remove(index);
            return values.remove(index);
        }
        return null;
    }
    
    /**
     * @brief Gets the value for a key (for leaf nodes).
     * @param key The key
     * @return The value, or null if not found
     */
    public V getValue(K key) {
        if (!isLeaf) {
            return null;
        }
        
        int index = keys.indexOf(key);
        if (index != -1) {
            return values.get(index);
        }
        return null;
    }
    
    /**
     * @brief Gets the key at a specific index.
     * @param index The index
     * @return The key
     */
    public K getKey(int index) {
        if (index >= 0 && index < keys.size()) {
            return keys.get(index);
        }
        return null;
    }
    
    /**
     * @brief Gets the value at a specific index (for leaf nodes).
     * @param index The index
     * @return The value
     */
    public V getValue(int index) {
        if (isLeaf && values != null && index >= 0 && index < values.size()) {
            return values.get(index);
        }
        return null;
    }
    
    /**
     * @brief Gets the child at a specific index (for internal nodes).
     * @param index The index
     * @return The child node
     */
    public BPlusTreeNode<K, V> getChild(int index) {
        if (!isLeaf && index >= 0 && index < children.size()) {
            return children.get(index);
        }
        return null;
    }
}

