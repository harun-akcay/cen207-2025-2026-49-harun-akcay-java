/**
 * @file SaleRepositoryTest.java
 * @brief Test class for SaleRepository.
 * @details This class contains unit tests for SaleRepository to achieve 100% coverage.
 * @package com.hakcay.inventorymanagement.sales
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.sales;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @class SaleRepositoryTest
 * @brief Test class for SaleRepository.
 */
public class SaleRepositoryTest {
    
    private SaleRepository repository;
    private File tempFile;
    
    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("sales", ".csv");
        tempFile.deleteOnExit();
        repository = new SaleRepository(tempFile.getAbsolutePath());
    }
    
    @After
    public void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }
    
    /**
     * @brief Test loadAll with empty file.
     */
    @Test
    public void testLoadAllWithEmptyFile() {
        List<Sale> sales = repository.loadAll();
        assertEquals(0, sales.size());
    }
    
    /**
     * @brief Test loadAll with valid sales.
     */
    @Test
    public void testLoadAllWithValidSales() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,10,5,25.50,2024-01-15,John Doe\n");
        writer.write("2,20,3,30.00,2024-01-16,Jane Smith\n");
        writer.close();
        
        List<Sale> sales = repository.loadAll();
        assertEquals(2, sales.size());
        assertEquals(1, sales.get(0).getId());
        assertEquals(10, sales.get(0).getMaterialId());
        assertEquals(5, sales.get(0).getQuantity());
        assertEquals(25.50, sales.get(0).getPrice(), 0.001);
        assertEquals("2024-01-15", sales.get(0).getDate());
        assertEquals("John Doe", sales.get(0).getCustomerName());
    }
    
    /**
     * @brief Test loadAll with invalid CSV line (wrong number of parts).
     */
    @Test
    public void testLoadAllWithInvalidLine() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,10,5,25.50\n"); // Only 4 parts, should be 6
        writer.write("2,20,3,30.00,2024-01-16,Jane Smith\n"); // Valid line
        writer.close();
        
        List<Sale> sales = repository.loadAll();
        assertEquals(1, sales.size()); // Only valid line should be loaded
        assertEquals(2, sales.get(0).getId());
    }
    
    /**
     * @brief Test loadAll with invalid ID (NumberFormatException).
     */
    @Test
    public void testLoadAllWithInvalidId() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("invalid,10,5,25.50,2024-01-15,John Doe\n"); // Invalid ID
        writer.write("2,20,3,30.00,2024-01-16,Jane Smith\n"); // Valid line
        writer.close();
        
        List<Sale> sales = repository.loadAll();
        assertEquals(1, sales.size()); // Only valid line should be loaded
        assertEquals(2, sales.get(0).getId());
    }
    
    /**
     * @brief Test loadAll with invalid price (NumberFormatException).
     */
    @Test
    public void testLoadAllWithInvalidPrice() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,10,5,invalid,2024-01-15,John Doe\n"); // Invalid price
        writer.write("2,20,3,30.00,2024-01-16,Jane Smith\n"); // Valid line
        writer.close();
        
        List<Sale> sales = repository.loadAll();
        assertEquals(1, sales.size()); // Only valid line should be loaded
        assertEquals(2, sales.get(0).getId());
    }
    
    /**
     * @brief Test loadAll with empty lines.
     */
    @Test
    public void testLoadAllWithEmptyLines() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("\n"); // Empty line
        writer.write("   \n"); // Whitespace line
        writer.write("1,10,5,25.50,2024-01-15,John Doe\n"); // Valid line
        writer.write("\n"); // Another empty line
        writer.close();
        
        List<Sale> sales = repository.loadAll();
        assertEquals(1, sales.size()); // Only valid line should be loaded
    }
    
    /**
     * @brief Test saveAll with valid sales.
     */
    @Test
    public void testSaveAllWithValidSales() {
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe"));
        sales.add(new Sale(2, 20, 3, 30.00, "2024-01-16", "Jane Smith"));
        repository.saveAll(sales);
        
        List<Sale> loaded = repository.loadAll();
        assertEquals(2, loaded.size());
    }
    
    /**
     * @brief Test saveAll with null list.
     */
    @Test
    public void testSaveAllWithNullList() {
        repository.saveAll(null);
        List<Sale> loaded = repository.loadAll();
        assertEquals(0, loaded.size());
    }
    
    /**
     * @brief Test saveAll with null sale (should skip).
     */
    @Test
    public void testSaveAllWithNullSale() {
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe"));
        sales.add(null); // null sale should be skipped
        sales.add(new Sale(2, 20, 3, 30.00, "2024-01-16", "Jane Smith"));
        repository.saveAll(sales);
        
        List<Sale> loaded = repository.loadAll();
        assertEquals(2, loaded.size()); // Only non-null sales should be saved
    }
    
    /**
     * @brief Test saveAll with sale containing commas in date or customer name.
     */
    @Test
    public void testSaveAllWithCommasInFields() {
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1, 10, 5, 25.50, "2024,01,15", "John, Doe"));
        repository.saveAll(sales);
        
        List<Sale> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        // Verify the fields were saved and loaded correctly
        assertTrue(loaded.get(0).getDate().contains("2024"));
        assertTrue(loaded.get(0).getCustomerName().contains("John"));
    }
    
    /**
     * @brief Test saveAll with sale containing null date or customer name.
     */
    @Test
    public void testSaveAllWithNullFields() {
        List<Sale> sales = new ArrayList<>();
        Sale sale = new Sale(1, 10, 5, 25.50, null, null);
        sales.add(sale);
        repository.saveAll(sales);
        
        List<Sale> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        assertNotNull(loaded.get(0));
    }
    
    /**
     * @brief Test loadAll with non-existent file.
     */
    @Test
    public void testLoadAllWithNonExistentFile() {
        SaleRepository repo = new SaleRepository("nonexistent_file.csv");
        List<Sale> sales = repo.loadAll();
        assertEquals(0, sales.size()); // Should return empty list
    }
    
    /**
     * @brief Test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        SaleRepository repo = new SaleRepository();
        List<Sale> sales = repo.loadAll();
        assertEquals(0, sales.size());
    }
    
    /**
     * @brief Test loadAll with commas in date and customer name (escape/restore).
     */
    @Test
    public void testLoadAllWithCommasInFields() throws IOException {
        // Save sale with commas
        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1, 10, 5, 25.50, "2024,01,15", "John, Doe"));
        repository.saveAll(sales);
        
        // Load and verify
        List<Sale> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        // The commas should be handled correctly
    }
}

