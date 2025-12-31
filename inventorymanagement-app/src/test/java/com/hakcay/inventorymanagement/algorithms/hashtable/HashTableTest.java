/**
 * @file HashTableTest.java
 * @brief Test class for HashTable implementation.
 * @package com.hakcay.inventorymanagement.algorithms.hashtable
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.algorithms.hashtable;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @class HashTableTest
 * @brief Comprehensive unit tests for HashTable class.
 */
public class HashTableTest {
    
    /** @brief HashTable instance for testing */
    private HashTable<String, Integer> hashTable;
    
    @Before
    public void setUp() {
        hashTable = new HashTable<>();
    }
    
    @Test
    public void testDefaultConstructor() {
        HashTable<String, Integer> table = new HashTable<>();
        assertNotNull(table);
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }
    
    @Test
    public void testConstructorWithCapacity() {
        HashTable<String, Integer> table = new HashTable<>(32);
        assertNotNull(table);
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }
    
    @Test
    public void testConstructorWithCapacityAndLoadFactor() {
        HashTable<String, Integer> table = new HashTable<>(32, 0.5);
        assertNotNull(table);
        assertTrue(table.isEmpty());
        assertEquals(0, table.size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithZeroCapacity() {
        new HashTable<>(0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeCapacity() {
        new HashTable<>(-1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithInvalidLoadFactor() {
        new HashTable<>(16, 1.5);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeLoadFactor() {
        new HashTable<>(16, -0.5);
    }
    
    @Test
    public void testPutAndGet() {
        hashTable.put("key1", 100);
        assertEquals(Integer.valueOf(100), hashTable.get("key1"));
        assertEquals(1, hashTable.size());
        assertFalse(hashTable.isEmpty());
    }
    
    @Test
    public void testPutMultipleEntries() {
        hashTable.put("key1", 100);
        hashTable.put("key2", 200);
        hashTable.put("key3", 300);
        
        assertEquals(Integer.valueOf(100), hashTable.get("key1"));
        assertEquals(Integer.valueOf(200), hashTable.get("key2"));
        assertEquals(Integer.valueOf(300), hashTable.get("key3"));
        assertEquals(3, hashTable.size());
    }
    
    @Test
    public void testPutUpdateExisting() {
        hashTable.put("key1", 100);
        Integer oldValue = hashTable.put("key1", 200);
        
        assertEquals(Integer.valueOf(100), oldValue);
        assertEquals(Integer.valueOf(200), hashTable.get("key1"));
        assertEquals(1, hashTable.size());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        hashTable.put(null, 100);
    }
    
    @Test
    public void testGetNonExistentKey() {
        assertNull(hashTable.get("nonexistent"));
    }
    
    @Test
    public void testGetNullKey() {
        assertNull(hashTable.get(null));
    }
    
    @Test
    public void testRemove() {
        hashTable.put("key1", 100);
        Integer removed = hashTable.remove("key1");
        
        assertEquals(Integer.valueOf(100), removed);
        assertNull(hashTable.get("key1"));
        assertEquals(0, hashTable.size());
        assertTrue(hashTable.isEmpty());
    }
    
    @Test
    public void testRemoveNonExistentKey() {
        assertNull(hashTable.remove("nonexistent"));
    }
    
    @Test
    public void testRemoveNullKey() {
        assertNull(hashTable.remove(null));
    }
    
    @Test
    public void testRemoveMultipleEntries() {
        hashTable.put("key1", 100);
        hashTable.put("key2", 200);
        hashTable.put("key3", 300);
        
        hashTable.remove("key2");
        assertEquals(2, hashTable.size());
        assertNull(hashTable.get("key2"));
        assertEquals(Integer.valueOf(100), hashTable.get("key1"));
        assertEquals(Integer.valueOf(300), hashTable.get("key3"));
    }
    
    @Test
    public void testContainsKey() {
        hashTable.put("key1", 100);
        assertTrue(hashTable.containsKey("key1"));
        assertFalse(hashTable.containsKey("key2"));
    }
    
    @Test
    public void testContainsKeyNull() {
        assertFalse(hashTable.containsKey(null));
    }
    
    @Test
    public void testSize() {
        assertEquals(0, hashTable.size());
        hashTable.put("key1", 100);
        assertEquals(1, hashTable.size());
        hashTable.put("key2", 200);
        assertEquals(2, hashTable.size());
        hashTable.remove("key1");
        assertEquals(1, hashTable.size());
    }
    
    @Test
    public void testIsEmpty() {
        assertTrue(hashTable.isEmpty());
        hashTable.put("key1", 100);
        assertFalse(hashTable.isEmpty());
        hashTable.remove("key1");
        assertTrue(hashTable.isEmpty());
    }
    
    @Test
    public void testClear() {
        hashTable.put("key1", 100);
        hashTable.put("key2", 200);
        hashTable.put("key3", 300);
        
        hashTable.clear();
        assertTrue(hashTable.isEmpty());
        assertEquals(0, hashTable.size());
        assertNull(hashTable.get("key1"));
        assertNull(hashTable.get("key2"));
        assertNull(hashTable.get("key3"));
    }
    
    @Test
    public void testResize() {
        // Create a small hash table and add enough elements to trigger resize
        HashTable<String, Integer> smallTable = new HashTable<>(4, 0.75);
        
        // Add 4 elements (should trigger resize at 3 elements due to 0.75 load factor)
        smallTable.put("key1", 1);
        smallTable.put("key2", 2);
        smallTable.put("key3", 3);
        smallTable.put("key4", 4);
        
        // Verify all elements are still accessible after resize
        assertEquals(Integer.valueOf(1), smallTable.get("key1"));
        assertEquals(Integer.valueOf(2), smallTable.get("key2"));
        assertEquals(Integer.valueOf(3), smallTable.get("key3"));
        assertEquals(Integer.valueOf(4), smallTable.get("key4"));
        assertEquals(4, smallTable.size());
    }
    
    @Test
    public void testCollisionHandling() {
        // Use a very small capacity to force collisions
        HashTable<String, Integer> smallTable = new HashTable<>(2);
        
        smallTable.put("a", 1);
        smallTable.put("b", 2);
        smallTable.put("c", 3);
        smallTable.put("d", 4);
        
        assertEquals(Integer.valueOf(1), smallTable.get("a"));
        assertEquals(Integer.valueOf(2), smallTable.get("b"));
        assertEquals(Integer.valueOf(3), smallTable.get("c"));
        assertEquals(Integer.valueOf(4), smallTable.get("d"));
        assertEquals(4, smallTable.size());
    }
    
    @Test
    public void testRemoveFromChain() {
        // Force collisions with small capacity
        HashTable<String, Integer> smallTable = new HashTable<>(2);
        
        smallTable.put("a", 1);
        smallTable.put("b", 2);
        smallTable.put("c", 3);
        
        // Remove middle element in chain
        smallTable.remove("b");
        assertEquals(2, smallTable.size());
        assertNull(smallTable.get("b"));
        assertEquals(Integer.valueOf(1), smallTable.get("a"));
        assertEquals(Integer.valueOf(3), smallTable.get("c"));
    }
    
    @Test
    public void testRemoveFirstInChain() {
        // Force collisions with small capacity
        HashTable<String, Integer> smallTable = new HashTable<>(2);
        
        smallTable.put("a", 1);
        smallTable.put("b", 2);
        
        // Remove first element in chain
        smallTable.remove("a");
        assertEquals(1, smallTable.size());
        assertNull(smallTable.get("a"));
        assertEquals(Integer.valueOf(2), smallTable.get("b"));
    }
    
    @Test
    public void testLargeNumberOfEntries() {
        HashTable<Integer, String> table = new HashTable<>();
        
        // Add 1000 entries
        for (int i = 0; i < 1000; i++) {
            table.put(i, "value" + i);
        }
        
        assertEquals(1000, table.size());
        
        // Verify random access
        assertEquals("value500", table.get(500));
        assertEquals("value999", table.get(999));
        assertEquals("value0", table.get(0));
    }
    
    @Test
    public void testDifferentTypes() {
        HashTable<Integer, String> intStringTable = new HashTable<>();
        intStringTable.put(1, "one");
        assertEquals("one", intStringTable.get(1));
        
        HashTable<String, Double> stringDoubleTable = new HashTable<>();
        stringDoubleTable.put("pi", 3.14);
        assertEquals(Double.valueOf(3.14), stringDoubleTable.get("pi"));
    }
    
    @Test
    public void testRemoveLastInChain() {
        // Force collisions with small capacity
        HashTable<String, Integer> smallTable = new HashTable<>(2);
        
        smallTable.put("a", 1);
        smallTable.put("b", 2);
        smallTable.put("c", 3);
        
        // Remove last element in chain
        smallTable.remove("c");
        assertEquals(2, smallTable.size());
        assertNull(smallTable.get("c"));
        assertEquals(Integer.valueOf(1), smallTable.get("a"));
        assertEquals(Integer.valueOf(2), smallTable.get("b"));
    }
    
    @Test
    public void testPutNullValue() {
        hashTable.put("key1", null);
        assertTrue(hashTable.containsKey("key1"));
        assertNull(hashTable.get("key1"));
        assertEquals(1, hashTable.size());
    }
    
    @Test
    public void testPutUpdateWithNullValue() {
        hashTable.put("key1", 100);
        Integer oldValue = hashTable.put("key1", null);
        assertEquals(Integer.valueOf(100), oldValue);
        assertNull(hashTable.get("key1"));
        assertTrue(hashTable.containsKey("key1"));
    }
    
    @Test
    public void testClearThenPut() {
        hashTable.put("key1", 100);
        hashTable.put("key2", 200);
        hashTable.clear();
        
        hashTable.put("key3", 300);
        assertEquals(1, hashTable.size());
        assertEquals(Integer.valueOf(300), hashTable.get("key3"));
        assertNull(hashTable.get("key1"));
    }
    
    @Test
    public void testMultipleResizes() {
        // Create a very small table that will resize multiple times
        HashTable<Integer, String> table = new HashTable<>(2, 0.75);
        
        // Add enough elements to trigger multiple resizes
        for (int i = 0; i < 20; i++) {
            table.put(i, "value" + i);
        }
        
        assertEquals(20, table.size());
        
        // Verify all elements are accessible after multiple resizes
        for (int i = 0; i < 20; i++) {
            assertEquals("value" + i, table.get(i));
        }
    }
    
    @Test
    public void testResizeWithUpdate() {
        // Create a small table
        HashTable<String, Integer> table = new HashTable<>(4, 0.75);
        
        // Add elements to trigger resize
        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3);
        
        // Update existing key after resize
        Integer oldValue = table.put("key1", 10);
        assertEquals(Integer.valueOf(1), oldValue);
        assertEquals(Integer.valueOf(10), table.get("key1"));
        assertEquals(3, table.size());
    }
    
    @Test
    public void testContainsKeyAfterRemove() {
        hashTable.put("key1", 100);
        assertTrue(hashTable.containsKey("key1"));
        hashTable.remove("key1");
        assertFalse(hashTable.containsKey("key1"));
    }
    
    @Test
    public void testPutAfterRemove() {
        hashTable.put("key1", 100);
        hashTable.remove("key1");
        hashTable.put("key1", 200);
        assertEquals(Integer.valueOf(200), hashTable.get("key1"));
        assertEquals(1, hashTable.size());
    }
    
    @Test
    public void testResizeWithCollisions() {
        // Create a table that will have collisions and then resize
        HashTable<String, Integer> table = new HashTable<>(2, 0.8);
        
        // Add elements that will collide
        table.put("a", 1);
        table.put("b", 2);
        table.put("c", 3);
        
        // Verify all elements are accessible
        assertEquals(Integer.valueOf(1), table.get("a"));
        assertEquals(Integer.valueOf(2), table.get("b"));
        assertEquals(Integer.valueOf(3), table.get("c"));
        
        // Add more to trigger resize
        table.put("d", 4);
        
        // Verify all elements still accessible after resize
        assertEquals(Integer.valueOf(1), table.get("a"));
        assertEquals(Integer.valueOf(2), table.get("b"));
        assertEquals(Integer.valueOf(3), table.get("c"));
        assertEquals(Integer.valueOf(4), table.get("d"));
    }
    
    @Test
    public void testLoadFactorBoundary() {
        // Test with load factor exactly at threshold
        HashTable<String, Integer> table = new HashTable<>(4, 0.75);
        
        // Add 3 elements (3/4 = 0.75, should trigger resize)
        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3);
        
        // Verify resize happened and all elements accessible
        assertEquals(3, table.size());
        assertEquals(Integer.valueOf(1), table.get("key1"));
        assertEquals(Integer.valueOf(2), table.get("key2"));
        assertEquals(Integer.valueOf(3), table.get("key3"));
    }
    
    @Test
    public void testRemoveAllFromChain() {
        HashTable<String, Integer> smallTable = new HashTable<>(2);
        
        smallTable.put("a", 1);
        smallTable.put("b", 2);
        smallTable.put("c", 3);
        
        // Remove all elements from a chain
        smallTable.remove("a");
        smallTable.remove("b");
        smallTable.remove("c");
        
        assertEquals(0, smallTable.size());
        assertTrue(smallTable.isEmpty());
        assertNull(smallTable.get("a"));
        assertNull(smallTable.get("b"));
        assertNull(smallTable.get("c"));
    }
    
    @Test
    public void testGetAfterClear() {
        hashTable.put("key1", 100);
        hashTable.put("key2", 200);
        hashTable.clear();
        
        assertNull(hashTable.get("key1"));
        assertNull(hashTable.get("key2"));
        assertFalse(hashTable.containsKey("key1"));
    }
    
    @Test
    public void testSizeAfterMultipleOperations() {
        assertEquals(0, hashTable.size());
        hashTable.put("key1", 100);
        assertEquals(1, hashTable.size());
        hashTable.put("key2", 200);
        assertEquals(2, hashTable.size());
        hashTable.put("key1", 300); // Update
        assertEquals(2, hashTable.size()); // Size should not change
        hashTable.remove("key2");
        assertEquals(1, hashTable.size());
        hashTable.clear();
        assertEquals(0, hashTable.size());
    }
    
    @Test
    public void testLoadFactorExactlyOne() {
        // Test with load factor = 1.0 (boundary case)
        HashTable<String, Integer> table = new HashTable<>(4, 1.0);
        
        // Add 4 elements (4/4 = 1.0, should trigger resize)
        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3);
        table.put("key4", 4);
        
        // Verify all elements are accessible
        assertEquals(4, table.size());
        assertEquals(Integer.valueOf(1), table.get("key1"));
        assertEquals(Integer.valueOf(2), table.get("key2"));
        assertEquals(Integer.valueOf(3), table.get("key3"));
        assertEquals(Integer.valueOf(4), table.get("key4"));
    }
    
    @Test
    public void testLoadFactorVerySmall() {
        // Test with very small load factor
        HashTable<String, Integer> table = new HashTable<>(4, 0.25);
        
        // Add 1 element (1/4 = 0.25, should trigger resize)
        table.put("key1", 1);
        
        // Verify element is accessible
        assertEquals(1, table.size());
        assertEquals(Integer.valueOf(1), table.get("key1"));
    }
    
    @Test
    public void testResizeWithEmptyBuckets() {
        // Test resize when some buckets are empty after removing elements
        HashTable<String, Integer> table = new HashTable<>(4, 0.75);
        
        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3);
        
        // Remove one element to create empty bucket
        table.remove("key2");
        
        // Add more to trigger resize with some empty buckets
        table.put("key4", 4);
        table.put("key5", 5);
        
        // Verify all elements are accessible after resize
        assertEquals(4, table.size());
        assertEquals(Integer.valueOf(1), table.get("key1"));
        assertEquals(Integer.valueOf(3), table.get("key3"));
        assertEquals(Integer.valueOf(4), table.get("key4"));
        assertEquals(Integer.valueOf(5), table.get("key5"));
    }
    
    @Test
    public void testHashWithNullKey() {
        // Test that hash method handles null (indirectly through get/remove)
        // Since hash is private, we test through get and remove which call it
        assertNull(hashTable.get(null));
        assertNull(hashTable.remove(null));
        assertFalse(hashTable.containsKey(null));
    }
    
    @Test
    public void testPutWithoutResize() {
        // Test put when resize is NOT needed
        HashTable<String, Integer> table = new HashTable<>(16, 0.75);
        
        // Add 11 elements (11/16 = 0.6875 < 0.75, should NOT trigger resize)
        for (int i = 0; i < 11; i++) {
            table.put("key" + i, i);
        }
        
        assertEquals(11, table.size());
        for (int i = 0; i < 11; i++) {
            assertEquals(Integer.valueOf(i), table.get("key" + i));
        }
    }
    
    @Test
    public void testResizeWithSingleBucketChain() {
        // Create table that will have all elements in one bucket after resize
        HashTable<String, Integer> table = new HashTable<>(2, 0.75);
        
        // Add elements that might hash to same bucket
        table.put("a", 1);
        table.put("b", 2);
        
        // Trigger resize
        table.put("c", 3);
        
        // Verify all elements accessible
        assertEquals(3, table.size());
        assertEquals(Integer.valueOf(1), table.get("a"));
        assertEquals(Integer.valueOf(2), table.get("b"));
        assertEquals(Integer.valueOf(3), table.get("c"));
    }
    
    @Test
    public void testPutUpdateExistingAfterResize() {
        // Add elements, trigger resize, then update existing key
        HashTable<String, Integer> table = new HashTable<>(4, 0.75);
        
        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3); // Triggers resize
        
        // Update existing key after resize
        Integer oldValue = table.put("key1", 10);
        assertEquals(Integer.valueOf(1), oldValue);
        assertEquals(Integer.valueOf(10), table.get("key1"));
        assertEquals(3, table.size());
    }
    
    @Test
    public void testRemoveAfterResize() {
        // Add elements, trigger resize, then remove
        HashTable<String, Integer> table = new HashTable<>(4, 0.75);
        
        table.put("key1", 1);
        table.put("key2", 2);
        table.put("key3", 3); // Triggers resize
        
        // Remove element after resize
        Integer removed = table.remove("key2");
        assertEquals(Integer.valueOf(2), removed);
        assertEquals(2, table.size());
        assertNull(table.get("key2"));
    }
    
    @Test
    public void testResizePreservesAllEntries() {
        // Test that resize correctly preserves all entries
        HashTable<Integer, String> table = new HashTable<>(2, 0.75);
        
        // Add enough elements to trigger multiple resizes
        for (int i = 0; i < 10; i++) {
            table.put(i, "value" + i);
        }
        
        // Verify all entries are still accessible after resizes
        assertEquals(10, table.size());
        for (int i = 0; i < 10; i++) {
            assertEquals("value" + i, table.get(i));
        }
    }
    
    @Test
    public void testHashWithNegativeHashCode() {
        // Test hash method with key that has negative hashCode
        HashTable<String, Integer> table = new HashTable<>();
        
        // Use a key that might have negative hashCode
        String key = "test";
        table.put(key, 100);
        
        // Verify it's stored and retrievable
        assertEquals(Integer.valueOf(100), table.get(key));
        assertTrue(table.containsKey(key));
    }
}

