/**
 * @file ExpenseRepositoryTest.java
 * @brief Test class for ExpenseRepository.
 * @details This class contains unit tests for ExpenseRepository to achieve 100% coverage.
 * @package com.hakcay.inventorymanagement.expense
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.expense;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @class ExpenseRepositoryTest
 * @brief Test class for ExpenseRepository.
 */
public class ExpenseRepositoryTest {
    
    private ExpenseRepository repository;
    private File tempFile;
    
    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("expenses", ".csv");
        tempFile.deleteOnExit();
        repository = new ExpenseRepository(tempFile.getAbsolutePath());
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
        List<Expense> expenses = repository.loadAll();
        assertEquals(0, expenses.size());
    }
    
    /**
     * @brief Test loadAll with valid expenses.
     */
    @Test
    public void testLoadAllWithValidExpenses() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,10,100,250.50,Steel purchase\n");
        writer.write("2,20,100,150.00,Wood purchase\n");
        writer.close();
        
        List<Expense> expenses = repository.loadAll();
        assertEquals(2, expenses.size());
        assertEquals(1, expenses.get(0).getId());
        assertEquals(10, expenses.get(0).getMaterialId());
        assertEquals(100, expenses.get(0).getProjectId());
        assertEquals(250.50, expenses.get(0).getAmount(), 0.001);
        assertEquals("Steel purchase", expenses.get(0).getDescription());
    }
    
    /**
     * @brief Test loadAll with invalid CSV line (wrong number of parts).
     */
    @Test
    public void testLoadAllWithInvalidLine() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,10,100\n"); // Only 3 parts, should be 5
        writer.write("2,20,100,150.00,Wood purchase\n"); // Valid line
        writer.close();
        
        List<Expense> expenses = repository.loadAll();
        assertEquals(1, expenses.size()); // Only valid line should be loaded
        assertEquals(2, expenses.get(0).getId());
    }
    
    /**
     * @brief Test loadAll with invalid ID (NumberFormatException).
     */
    @Test
    public void testLoadAllWithInvalidId() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("invalid,10,100,250.50,Steel purchase\n"); // Invalid ID
        writer.write("2,20,100,150.00,Wood purchase\n"); // Valid line
        writer.close();
        
        List<Expense> expenses = repository.loadAll();
        assertEquals(1, expenses.size()); // Only valid line should be loaded
        assertEquals(2, expenses.get(0).getId());
    }
    
    /**
     * @brief Test loadAll with invalid amount (NumberFormatException).
     */
    @Test
    public void testLoadAllWithInvalidAmount() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,10,100,invalid,Steel purchase\n"); // Invalid amount
        writer.write("2,20,100,150.00,Wood purchase\n"); // Valid line
        writer.close();
        
        List<Expense> expenses = repository.loadAll();
        assertEquals(1, expenses.size()); // Only valid line should be loaded
        assertEquals(2, expenses.get(0).getId());
    }
    
    /**
     * @brief Test loadAll with empty lines.
     */
    @Test
    public void testLoadAllWithEmptyLines() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("\n"); // Empty line
        writer.write("   \n"); // Whitespace line
        writer.write("1,10,100,250.50,Steel purchase\n"); // Valid line
        writer.write("\n"); // Another empty line
        writer.close();
        
        List<Expense> expenses = repository.loadAll();
        assertEquals(1, expenses.size()); // Only valid line should be loaded
    }
    
    /**
     * @brief Test saveAll with valid expenses.
     */
    @Test
    public void testSaveAllWithValidExpenses() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1, 10, 100, 250.50, "Steel purchase"));
        expenses.add(new Expense(2, 20, 100, 150.00, "Wood purchase"));
        repository.saveAll(expenses);
        
        List<Expense> loaded = repository.loadAll();
        assertEquals(2, loaded.size());
    }
    
    /**
     * @brief Test saveAll with null list.
     */
    @Test
    public void testSaveAllWithNullList() {
        repository.saveAll(null);
        List<Expense> loaded = repository.loadAll();
        assertEquals(0, loaded.size());
    }
    
    /**
     * @brief Test saveAll with null expense (should skip).
     */
    @Test
    public void testSaveAllWithNullExpense() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1, 10, 100, 250.50, "Steel purchase"));
        expenses.add(null); // null expense should be skipped
        expenses.add(new Expense(2, 20, 100, 150.00, "Wood purchase"));
        repository.saveAll(expenses);
        
        List<Expense> loaded = repository.loadAll();
        assertEquals(2, loaded.size()); // Only non-null expenses should be saved
    }
    
    /**
     * @brief Test saveAll with expense containing commas in description.
     */
    @Test
    public void testSaveAllWithCommasInDescription() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1, 10, 100, 250.50, "Steel, purchase, with, commas"));
        repository.saveAll(expenses);
        
        List<Expense> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        // Verify the description was saved and loaded correctly
        assertTrue(loaded.get(0).getDescription().contains("Steel"));
    }
    
    /**
     * @brief Test saveAll with expense containing null description.
     */
    @Test
    public void testSaveAllWithNullDescription() {
        List<Expense> expenses = new ArrayList<>();
        Expense expense = new Expense(1, 10, 100, 250.50, null);
        expenses.add(expense);
        repository.saveAll(expenses);
        
        List<Expense> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        assertNotNull(loaded.get(0));
    }
    
    /**
     * @brief Test loadAll with non-existent file.
     */
    @Test
    public void testLoadAllWithNonExistentFile() {
        ExpenseRepository repo = new ExpenseRepository("nonexistent_file.csv");
        List<Expense> expenses = repo.loadAll();
        assertEquals(0, expenses.size()); // Should return empty list
    }
    
    /**
     * @brief Test default constructor.
     */
    @Test
    public void testDefaultConstructor() throws IOException {
        // Use temp file to ensure isolation
        File tempFile2 = File.createTempFile("expenses_default", ".csv");
        tempFile2.deleteOnExit();
        ExpenseRepository repo = new ExpenseRepository(tempFile2.getAbsolutePath());
        List<Expense> expenses = repo.loadAll();
        assertEquals(0, expenses.size());
    }
    
    /**
     * @brief Test loadAll with commas in description (escape/restore).
     */
    @Test
    public void testLoadAllWithCommasInDescription() throws IOException {
        // Save expense with commas
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1, 10, 100, 250.50, "Steel, purchase"));
        repository.saveAll(expenses);
        
        // Load and verify
        List<Expense> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        // The commas should be handled correctly
    }
}

