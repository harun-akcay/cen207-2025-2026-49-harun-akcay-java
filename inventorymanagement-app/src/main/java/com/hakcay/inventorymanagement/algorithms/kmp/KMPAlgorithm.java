/**
 * @file KMPAlgorithm.java
 * @brief This file contains the Knuth-Morris-Pratt (KMP) string matching algorithm implementation.
 * @details This class provides efficient string searching with O(n+m) time complexity.
 * @package com.hakcay.inventorymanagement.algorithms.kmp
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.kmp;

import java.util.ArrayList;
import java.util.List;

/**
 * @class KMPAlgorithm
 * @brief Knuth-Morris-Pratt string matching algorithm implementation.
 * @details Provides efficient pattern matching in text with O(n+m) time complexity.
 */
public class KMPAlgorithm {
    
    /**
     * @brief Searches for all occurrences of a pattern in a text.
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @return List of starting indices where pattern is found (empty if not found)
     */
    public static List<Integer> search(String text, String pattern) {
        List<Integer> occurrences = new ArrayList<>();
        
        if (text == null || pattern == null || pattern.isEmpty()) {
            return occurrences;
        }
        
        if (text.isEmpty()) {
            return occurrences;
        }
        
        int[] lps = computeLPS(pattern);
        int textIndex = 0;
        int patternIndex = 0;
        
        while (textIndex < text.length()) {
            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                textIndex++;
                patternIndex++;
            }
            
            if (patternIndex == pattern.length()) {
                // Pattern found
                occurrences.add(textIndex - patternIndex);
                patternIndex = lps[patternIndex - 1];
            } else if (textIndex < text.length() && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }
        }
        
        return occurrences;
    }
    
    /**
     * @brief Checks if a pattern exists in a text.
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @return true if pattern is found, false otherwise
     */
    public static boolean contains(String text, String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return false;
        }
        
        if (text.isEmpty()) {
            return false;
        }
        
        return !search(text, pattern).isEmpty();
    }
    
    /**
     * @brief Finds the first occurrence of a pattern in a text.
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @return The index of the first occurrence, or -1 if not found
     */
    public static int findFirst(String text, String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return -1;
        }
        
        if (text.isEmpty()) {
            return -1;
        }
        
        List<Integer> occurrences = search(text, pattern);
        if (occurrences.isEmpty()) {
            return -1;
        }
        return occurrences.get(0);
    }
    
    /**
     * @brief Finds the last occurrence of a pattern in a text.
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @return The index of the last occurrence, or -1 if not found
     */
    public static int findLast(String text, String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return -1;
        }
        
        if (text.isEmpty()) {
            return -1;
        }
        
        List<Integer> occurrences = search(text, pattern);
        if (occurrences.isEmpty()) {
            return -1;
        }
        return occurrences.get(occurrences.size() - 1);
    }
    
    /**
     * @brief Counts the number of occurrences of a pattern in a text.
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @return The number of occurrences
     */
    public static int count(String text, String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return 0;
        }
        
        if (text.isEmpty()) {
            return 0;
        }
        
        return search(text, pattern).size();
    }
    
    /**
     * @brief Computes the Longest Proper Prefix which is also Suffix (LPS) array.
     * @param pattern The pattern string
     * @return The LPS array
     */
    private static int[] computeLPS(String pattern) {
        int length = pattern.length();
        int[] lps = new int[length];
        int len = 0; // Length of the previous longest prefix suffix
        int i = 1;
        
        lps[0] = 0; // lps[0] is always 0
        
        while (i < length) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        return lps;
    }
    
    /**
     * @brief Case-insensitive search for all occurrences of a pattern in a text.
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @return List of starting indices where pattern is found (empty if not found)
     */
    public static List<Integer> searchIgnoreCase(String text, String pattern) {
        if (text == null || pattern == null) {
            return new ArrayList<>();
        }
        return search(text.toLowerCase(), pattern.toLowerCase());
    }
    
    /**
     * @brief Case-insensitive check if a pattern exists in a text.
     * @param text The text to search in
     * @param pattern The pattern to search for
     * @return true if pattern is found, false otherwise
     */
    public static boolean containsIgnoreCase(String text, String pattern) {
        if (text == null || pattern == null) {
            return false;
        }
        return contains(text.toLowerCase(), pattern.toLowerCase());
    }
}

