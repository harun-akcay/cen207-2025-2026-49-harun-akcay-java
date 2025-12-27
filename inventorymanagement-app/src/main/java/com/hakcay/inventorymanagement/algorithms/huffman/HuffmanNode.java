/**
 * @file HuffmanNode.java
 * @brief This file contains the HuffmanNode class for Huffman Coding.
 * @details Represents a node in the Huffman tree.
 * @package com.hakcay.inventorymanagement.algorithms.huffman
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.huffman;

/**
 * @class HuffmanNode
 * @brief Node class for Huffman tree.
 * @details Represents a node in the Huffman binary tree used for encoding/decoding.
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    
    /** @brief Character stored in this node (null for internal nodes) */
    private Character character;
    
    /** @brief Frequency of the character */
    private int frequency;
    
    /** @brief Left child node */
    private HuffmanNode left;
    
    /** @brief Right child node */
    private HuffmanNode right;
    
    /**
     * @brief Constructor for leaf node (with character).
     * @param character The character
     * @param frequency The frequency of the character
     */
    public HuffmanNode(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
    
    /**
     * @brief Constructor for internal node (without character).
     * @param frequency The combined frequency
     * @param left Left child node
     * @param right Right child node
     */
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = null;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
    
    /**
     * @brief Checks if this is a leaf node.
     * @return true if leaf node, false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
    
    /**
     * @brief Gets the character.
     * @return The character, or null if internal node
     */
    public Character getCharacter() {
        return character;
    }
    
    /**
     * @brief Gets the frequency.
     * @return The frequency
     */
    public int getFrequency() {
        return frequency;
    }
    
    /**
     * @brief Gets the left child.
     * @return The left child node
     */
    public HuffmanNode getLeft() {
        return left;
    }
    
    /**
     * @brief Gets the right child.
     * @return The right child node
     */
    public HuffmanNode getRight() {
        return right;
    }
    
    /**
     * @brief Compares nodes by frequency (for priority queue).
     * @param other The other node to compare
     * @return Negative if this frequency < other, positive if greater, 0 if equal
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return Integer.compare(this.frequency, other.frequency);
    }
}

