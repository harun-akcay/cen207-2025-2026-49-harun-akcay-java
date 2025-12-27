package com.hakcay.inventorymanagement.algorithms.kmp;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class KMPAlgorithmTest {
    
    @Test
    public void testSearchSimplePattern() {
        String text = "ABABDABACDABABCABCAB";
        String pattern = "ABABCAB";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(1, occurrences.size());
        assertEquals(Integer.valueOf(10), occurrences.get(0));
    }
    
    @Test
    public void testSearchMultipleOccurrences() {
        String text = "ABABABAB";
        String pattern = "ABAB";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(3, occurrences.size());
        assertEquals(Integer.valueOf(0), occurrences.get(0));
        assertEquals(Integer.valueOf(2), occurrences.get(1));
        assertEquals(Integer.valueOf(4), occurrences.get(2));
    }
    
    @Test
    public void testSearchNoMatch() {
        String text = "ABCDEFG";
        String pattern = "XYZ";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testSearchPatternAtStart() {
        String text = "HELLO WORLD";
        String pattern = "HELLO";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(1, occurrences.size());
        assertEquals(Integer.valueOf(0), occurrences.get(0));
    }
    
    @Test
    public void testSearchPatternAtEnd() {
        String text = "HELLO WORLD";
        String pattern = "WORLD";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(1, occurrences.size());
        assertEquals(Integer.valueOf(6), occurrences.get(0));
    }
    
    @Test
    public void testSearchEmptyText() {
        String text = "";
        String pattern = "ABC";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testSearchEmptyPattern() {
        String text = "ABC";
        String pattern = "";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testSearchNullText() {
        List<Integer> occurrences = KMPAlgorithm.search(null, "ABC");
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testSearchNullPattern() {
        List<Integer> occurrences = KMPAlgorithm.search("ABC", null);
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testSearchPatternEqualsText() {
        String text = "ABC";
        String pattern = "ABC";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(1, occurrences.size());
        assertEquals(Integer.valueOf(0), occurrences.get(0));
    }
    
    @Test
    public void testSearchPatternLongerThanText() {
        String text = "AB";
        String pattern = "ABC";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testContainsTrue() {
        String text = "HELLO WORLD";
        String pattern = "WORLD";
        assertTrue(KMPAlgorithm.contains(text, pattern));
    }
    
    @Test
    public void testContainsFalse() {
        String text = "HELLO WORLD";
        String pattern = "XYZ";
        assertFalse(KMPAlgorithm.contains(text, pattern));
    }
    
    @Test
    public void testContainsNullText() {
        assertFalse(KMPAlgorithm.contains(null, "ABC"));
    }
    
    @Test
    public void testContainsNullPattern() {
        assertFalse(KMPAlgorithm.contains("ABC", null));
    }
    
    @Test
    public void testContainsEmptyText() {
        assertFalse(KMPAlgorithm.contains("", "ABC"));
    }
    
    @Test
    public void testContainsEmptyPattern() {
        assertFalse(KMPAlgorithm.contains("ABC", ""));
    }
    
    @Test
    public void testFindFirst() {
        String text = "ABABABAB";
        String pattern = "ABAB";
        int index = KMPAlgorithm.findFirst(text, pattern);
        assertEquals(0, index);
    }
    
    @Test
    public void testFindFirstNotFound() {
        String text = "ABCDEFG";
        String pattern = "XYZ";
        int index = KMPAlgorithm.findFirst(text, pattern);
        assertEquals(-1, index);
    }
    
    @Test
    public void testFindFirstNullText() {
        int index = KMPAlgorithm.findFirst(null, "ABC");
        assertEquals(-1, index);
    }
    
    @Test
    public void testFindFirstNullPattern() {
        int index = KMPAlgorithm.findFirst("ABC", null);
        assertEquals(-1, index);
    }
    
    @Test
    public void testFindLast() {
        String text = "ABABABAB";
        String pattern = "ABAB";
        int index = KMPAlgorithm.findLast(text, pattern);
        assertEquals(4, index);
    }
    
    @Test
    public void testFindLastNotFound() {
        String text = "ABCDEFG";
        String pattern = "XYZ";
        int index = KMPAlgorithm.findLast(text, pattern);
        assertEquals(-1, index);
    }
    
    @Test
    public void testFindLastNullText() {
        int index = KMPAlgorithm.findLast(null, "ABC");
        assertEquals(-1, index);
    }
    
    @Test
    public void testFindLastNullPattern() {
        int index = KMPAlgorithm.findLast("ABC", null);
        assertEquals(-1, index);
    }
    
    @Test
    public void testCount() {
        String text = "ABABABAB";
        String pattern = "ABAB";
        int count = KMPAlgorithm.count(text, pattern);
        assertEquals(3, count);
    }
    
    @Test
    public void testCountZero() {
        String text = "ABCDEFG";
        String pattern = "XYZ";
        int count = KMPAlgorithm.count(text, pattern);
        assertEquals(0, count);
    }
    
    @Test
    public void testCountNullText() {
        int count = KMPAlgorithm.count(null, "ABC");
        assertEquals(0, count);
    }
    
    @Test
    public void testCountNullPattern() {
        int count = KMPAlgorithm.count("ABC", null);
        assertEquals(0, count);
    }
    
    @Test
    public void testSearchIgnoreCase() {
        String text = "Hello World";
        String pattern = "WORLD";
        List<Integer> occurrences = KMPAlgorithm.searchIgnoreCase(text, pattern);
        assertEquals(1, occurrences.size());
        assertEquals(Integer.valueOf(6), occurrences.get(0));
    }
    
    @Test
    public void testSearchIgnoreCaseNoMatch() {
        String text = "Hello World";
        String pattern = "XYZ";
        List<Integer> occurrences = KMPAlgorithm.searchIgnoreCase(text, pattern);
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testSearchIgnoreCaseNullText() {
        List<Integer> occurrences = KMPAlgorithm.searchIgnoreCase(null, "ABC");
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testSearchIgnoreCaseNullPattern() {
        List<Integer> occurrences = KMPAlgorithm.searchIgnoreCase("ABC", null);
        assertTrue(occurrences.isEmpty());
    }
    
    @Test
    public void testContainsIgnoreCase() {
        String text = "Hello World";
        String pattern = "WORLD";
        assertTrue(KMPAlgorithm.containsIgnoreCase(text, pattern));
    }
    
    @Test
    public void testContainsIgnoreCaseFalse() {
        String text = "Hello World";
        String pattern = "XYZ";
        assertFalse(KMPAlgorithm.containsIgnoreCase(text, pattern));
    }
    
    @Test
    public void testContainsIgnoreCaseNullText() {
        assertFalse(KMPAlgorithm.containsIgnoreCase(null, "ABC"));
    }
    
    @Test
    public void testContainsIgnoreCaseNullPattern() {
        assertFalse(KMPAlgorithm.containsIgnoreCase("ABC", null));
    }
    
    @Test
    public void testSearchOverlappingPatterns() {
        String text = "AAAA";
        String pattern = "AA";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(3, occurrences.size());
        assertEquals(Integer.valueOf(0), occurrences.get(0));
        assertEquals(Integer.valueOf(1), occurrences.get(1));
        assertEquals(Integer.valueOf(2), occurrences.get(2));
    }
    
    @Test
    public void testSearchSingleCharacterPattern() {
        String text = "ABCDEFG";
        String pattern = "C";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(1, occurrences.size());
        assertEquals(Integer.valueOf(2), occurrences.get(0));
    }
    
    @Test
    public void testSearchSingleCharacterText() {
        String text = "A";
        String pattern = "A";
        List<Integer> occurrences = KMPAlgorithm.search(text, pattern);
        assertEquals(1, occurrences.size());
        assertEquals(Integer.valueOf(0), occurrences.get(0));
    }
}

