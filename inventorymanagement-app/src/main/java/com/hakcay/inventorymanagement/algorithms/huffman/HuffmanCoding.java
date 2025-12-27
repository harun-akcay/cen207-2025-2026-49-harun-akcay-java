/**
 * @file HuffmanCoding.java
 * @brief This file contains the Huffman Coding algorithm implementation.
 * @details Provides data compression using Huffman coding algorithm.
 * @package com.hakcay.inventorymanagement.algorithms.huffman
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @class HuffmanCoding
 * @brief Huffman Coding algorithm for data compression.
 * @details Implements Huffman encoding and decoding for string compression.
 */
public class HuffmanCoding {
    
    /**
     * @brief Encodes a string using Huffman coding.
     * @param data The string to encode
     * @return EncodedResult containing the encoded string and encoding table
     */
    public static EncodedResult encode(String data) {
        if (data == null || data.isEmpty()) {
            return new EncodedResult("", new HashMap<>());
        }
        
        // Calculate character frequencies
        Map<Character, Integer> frequencies = calculateFrequencies(data);
        
        // Build Huffman tree
        HuffmanNode root = buildTree(frequencies);
        
        // Generate encoding table
        Map<Character, String> encodingTable = new HashMap<>();
        generateCodes(root, "", encodingTable);
        
        // Encode the data
        StringBuilder encoded = new StringBuilder();
        for (char c : data.toCharArray()) {
            encoded.append(encodingTable.get(c));
        }
        
        return new EncodedResult(encoded.toString(), encodingTable);
    }
    
    /**
     * @brief Decodes a Huffman-encoded string.
     * @param encoded The encoded string
     * @param encodingTable The encoding table used for encoding
     * @return The decoded string
     */
    public static String decode(String encoded, Map<Character, String> encodingTable) {
        if (encoded == null || encoded.isEmpty() || encodingTable == null || encodingTable.isEmpty()) {
            return "";
        }
        
        // Build reverse mapping (code -> character)
        Map<String, Character> reverseTable = new HashMap<>();
        for (Map.Entry<Character, String> entry : encodingTable.entrySet()) {
            reverseTable.put(entry.getValue(), entry.getKey());
        }
        
        // Decode
        StringBuilder decoded = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();
        
        for (char bit : encoded.toCharArray()) {
            currentCode.append(bit);
            if (reverseTable.containsKey(currentCode.toString())) {
                decoded.append(reverseTable.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }
        
        return decoded.toString();
    }
    
    /**
     * @brief Calculates character frequencies in a string.
     * @param data The input string
     * @return Map of character to frequency
     */
    private static Map<Character, Integer> calculateFrequencies(String data) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (char c : data.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }
    
    /**
     * @brief Builds the Huffman tree from frequency map.
     * @param frequencies Map of character to frequency
     * @return Root node of the Huffman tree
     */
    private static HuffmanNode buildTree(Map<Character, Integer> frequencies) {
        if (frequencies.isEmpty()) {
            return null;
        }
        
        // Create priority queue (min-heap)
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        
        // Add all characters as leaf nodes
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            queue.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        
        // Build tree by combining nodes
        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            
            int combinedFrequency = left.getFrequency() + right.getFrequency();
            HuffmanNode parent = new HuffmanNode(combinedFrequency, left, right);
            queue.offer(parent);
        }
        
        return queue.poll();
    }
    
    /**
     * @brief Generates encoding codes by traversing the tree.
     * @param node Current node
     * @param code Current code string
     * @param encodingTable Map to store character to code mapping
     */
    private static void generateCodes(HuffmanNode node, String code, Map<Character, String> encodingTable) {
        if (node == null) {
            return;
        }
        
        if (node.isLeaf()) {
            if (!code.isEmpty()) {
                encodingTable.put(node.getCharacter(), code);
            } else {
                // Special case: only one character in the data
                encodingTable.put(node.getCharacter(), "0");
            }
            return;
        }
        
        generateCodes(node.getLeft(), code + "0", encodingTable);
        generateCodes(node.getRight(), code + "1", encodingTable);
    }
    
    /**
     * @brief Result class for encoding operation.
     */
    public static class EncodedResult {
        private String encoded;
        private Map<Character, String> encodingTable;
        
        /**
         * @brief Constructor.
         * @param encoded The encoded string
         * @param encodingTable The encoding table
         */
        public EncodedResult(String encoded, Map<Character, String> encodingTable) {
            this.encoded = encoded;
            this.encodingTable = encodingTable;
        }
        
        /**
         * @brief Gets the encoded string.
         * @return The encoded string
         */
        public String getEncoded() {
            return encoded;
        }
        
        /**
         * @brief Gets the encoding table.
         * @return The encoding table
         */
        public Map<Character, String> getEncodingTable() {
            return encodingTable;
        }
    }
}

