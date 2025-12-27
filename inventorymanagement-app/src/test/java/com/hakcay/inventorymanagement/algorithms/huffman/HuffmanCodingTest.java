package com.hakcay.inventorymanagement.algorithms.huffman;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class HuffmanCodingTest {
    
    @Test
    public void testEncodeSimpleString() {
        String data = "AAAAABBBCC";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        assertNotNull(result);
        assertNotNull(result.getEncoded());
        assertNotNull(result.getEncodingTable());
        assertFalse(result.getEncoded().isEmpty());
    }
    
    @Test
    public void testEncodeDecodeSimpleString() {
        String data = "AAAAABBBCC";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeSingleCharacter() {
        String data = "AAAAA";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeTwoCharacters() {
        String data = "ABABAB";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeComplexString() {
        String data = "Hello World!";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeWithSpaces() {
        String data = "A B C D E";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeWithSpecialCharacters() {
        String data = "Hello! @#$%^&*()";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeNullString() {
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(null);
        assertNotNull(result);
        assertEquals("", result.getEncoded());
        assertTrue(result.getEncodingTable().isEmpty());
    }
    
    @Test
    public void testEncodeEmptyString() {
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode("");
        assertNotNull(result);
        assertEquals("", result.getEncoded());
        assertTrue(result.getEncodingTable().isEmpty());
    }
    
    @Test
    public void testDecodeNullEncoded() {
        Map<Character, String> table = new java.util.HashMap<>();
        String decoded = HuffmanCoding.decode(null, table);
        assertEquals("", decoded);
    }
    
    @Test
    public void testDecodeEmptyEncoded() {
        Map<Character, String> table = new java.util.HashMap<>();
        table.put('A', "0");
        String decoded = HuffmanCoding.decode("", table);
        assertEquals("", decoded);
    }
    
    @Test
    public void testDecodeNullTable() {
        String decoded = HuffmanCoding.decode("0101", null);
        assertEquals("", decoded);
    }
    
    @Test
    public void testDecodeEmptyTable() {
        Map<Character, String> table = new java.util.HashMap<>();
        String decoded = HuffmanCoding.decode("0101", table);
        assertEquals("", decoded);
    }
    
    @Test
    public void testEncodingTableCorrectness() {
        String data = "AAAAABBBCC";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        Map<Character, String> table = result.getEncodingTable();
        
        // All characters should be in the table
        assertTrue(table.containsKey('A'));
        assertTrue(table.containsKey('B'));
        assertTrue(table.containsKey('C'));
        
        // More frequent characters should have shorter codes
        String codeA = table.get('A');
        String codeB = table.get('B');
        String codeC = table.get('C');
        
        // A appears 5 times, B appears 3 times, C appears 2 times
        // So A should have the shortest code
        assertTrue(codeA.length() <= codeB.length());
        assertTrue(codeA.length() <= codeC.length());
    }
    
    @Test
    public void testEncodeDecodeLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        }
        String data = sb.toString();
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeUnicode() {
        String data = "Hello 世界 🌍";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeNumbers() {
        String data = "1234567890";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
    
    @Test
    public void testEncodeDecodeMixedCase() {
        String data = "HelloWorld";
        HuffmanCoding.EncodedResult result = HuffmanCoding.encode(data);
        String decoded = HuffmanCoding.decode(result.getEncoded(), result.getEncodingTable());
        assertEquals(data, decoded);
    }
}

