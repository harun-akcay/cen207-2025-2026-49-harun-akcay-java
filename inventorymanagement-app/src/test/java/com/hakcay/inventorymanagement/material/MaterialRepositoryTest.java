/**
 * @file MaterialRepositoryTest.java
 * @brief Test class for MaterialRepository.
 * @details This class contains unit tests for MaterialRepository to achieve 100% coverage.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

import static org.junit.Assert.*;

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
}

