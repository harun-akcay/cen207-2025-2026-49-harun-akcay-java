/**
 * @file MaterialRepositoryTest.java
 * @brief Test class for MaterialRepository.
 * @details This class contains unit tests for MaterialRepository to achieve 100% coverage.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @class MaterialRepositoryTest
 * @brief Test class for MaterialRepository.
 */
public class MaterialRepositoryTest {
    
    private MaterialRepository repository;
    
    @Before
    public void setUp() {
        repository = new MaterialRepository();
    }
    
    /**
     * @brief Test add method with null material.
     * @details This test verifies that adding null material doesn't crash.
     */
    @Test
    public void testAddNullMaterial() {
        repository.add(null);
        assertEquals(0, repository.getAll().size());
    }
    
    /**
     * @brief Test update method with null material.
     * @details This test verifies that updating with null material returns false.
     */
    @Test
    public void testUpdateNullMaterial() {
        boolean result = repository.update(null);
        assertFalse(result);
    }
    
    /**
     * @brief Test material history tracking on add.
     */
    @Test
    public void testHistoryTrackingOnAdd() {
        Material material1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material material2 = new Material(2, "Wood", "Material", 50, 5.00);
        
        repository.add(material1);
        repository.add(material2);
        
        List<Material> history = repository.getHistory();
        assertEquals(2, history.size());
        assertEquals(1, history.get(0).getId());
        assertEquals(2, history.get(1).getId());
    }
    
    /**
     * @brief Test material history tracking on remove.
     */
    @Test
    public void testHistoryTrackingOnRemove() {
        Material material1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material material2 = new Material(2, "Wood", "Material", 50, 5.00);
        
        repository.add(material1);
        repository.add(material2);
        repository.remove(1);
        
        List<Material> history = repository.getHistory();
        assertEquals(3, history.size()); // 2 adds + 1 remove
        assertEquals(1, history.get(0).getId()); // First add
        assertEquals(2, history.get(1).getId()); // Second add
        assertEquals(1, history.get(2).getId()); // Remove
    }
    
    /**
     * @brief Test getHistoryReverse method.
     */
    @Test
    public void testGetHistoryReverse() {
        Material material1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material material2 = new Material(2, "Wood", "Material", 50, 5.00);
        
        repository.add(material1);
        repository.add(material2);
        
        List<Material> reverseHistory = repository.getHistoryReverse();
        assertEquals(2, reverseHistory.size());
        assertEquals(2, reverseHistory.get(0).getId()); // Most recent first
        assertEquals(1, reverseHistory.get(1).getId());
    }
    
    /**
     * @brief Test clearHistory method.
     */
    @Test
    public void testClearHistory() {
        Material material1 = new Material(1, "Steel", "Metal", 100, 10.50);
        repository.add(material1);
        
        assertEquals(1, repository.getHistorySize());
        repository.clearHistory();
        assertEquals(0, repository.getHistorySize());
        assertTrue(repository.getHistory().isEmpty());
    }
    
    /**
     * @brief Test getHistorySize method.
     */
    @Test
    public void testGetHistorySize() {
        assertEquals(0, repository.getHistorySize());
        
        Material material1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material material2 = new Material(2, "Wood", "Material", 50, 5.00);
        
        repository.add(material1);
        assertEquals(1, repository.getHistorySize());
        
        repository.add(material2);
        assertEquals(2, repository.getHistorySize());
        
        repository.remove(1);
        assertEquals(3, repository.getHistorySize());
    }
    
    /**
     * @brief Test history with null material (should not be added).
     */
    @Test
    public void testHistoryWithNullMaterial() {
        repository.add(null);
        assertEquals(0, repository.getHistorySize());
    }
    
    /**
     * @brief Test history independence from actual materials.
     */
    @Test
    public void testHistoryIndependence() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        repository.add(material);
        
        // Modify original material
        material.setQuantity(200);
        
        // History should have original value
        List<Material> history = repository.getHistory();
        assertEquals(100, history.get(0).getQuantity());
    }
}

