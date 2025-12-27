package com.hakcay.inventorymanagement.expense;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ExpenseService.
 * Uses temporary files to avoid affecting real project files.
 */
public class ExpenseServiceTest {
    private ExpenseService service;
    private ExpenseRepository repository;
    private File tempFile;

    /**
     * Sets up test fixtures before each test.
     * Creates a temporary file for each test.
     *
     * @throws IOException if file creation fails
     */
    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("expenses", ".csv");
        tempFile.deleteOnExit();
        repository = new ExpenseRepository(tempFile.getAbsolutePath());
        service = new ExpenseService(repository);
    }

    /**
     * Cleans up after each test.
     * Deletes the temporary file if it still exists.
     */
    @After
    public void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * Test that addExpense works correctly.
     */
    @Test
    public void testAddExpenseWorks() {
        Expense expense = new Expense(1, 10, 100, 250.50, "Steel purchase");
        service.addExpense(expense);

        List<Expense> expenses = service.getAllExpenses();
        assertEquals(1, expenses.size());
        assertEquals(1, expenses.get(0).getId());
        assertEquals(10, expenses.get(0).getMaterialId());
        assertEquals(100, expenses.get(0).getProjectId());
        assertEquals(250.50, expenses.get(0).getAmount(), 0.001);
        assertEquals("Steel purchase", expenses.get(0).getDescription());
    }

    /**
     * Test that addExpense throws IllegalArgumentException when duplicate id is used.
     */
    @Test
    public void testAddExpenseDuplicateIdThrowsException() {
        Expense expense1 = new Expense(1, 10, 100, 250.50, "Steel purchase");
        Expense expense2 = new Expense(1, 20, 200, 150.00, "Wood purchase");

        service.addExpense(expense1);

        try {
            service.addExpense(expense2);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("already exists"));
        }
    }

    /**
     * Test that removeExpenseById works correctly.
     */
    @Test
    public void testRemoveExpenseByIdWorks() {
        Expense expense1 = new Expense(1, 10, 100, 250.50, "Steel purchase");
        Expense expense2 = new Expense(2, 20, 100, 150.00, "Wood purchase");
        Expense expense3 = new Expense(3, 30, 200, 75.25, "Plastic purchase");

        service.addExpense(expense1);
        service.addExpense(expense2);
        service.addExpense(expense3);

        boolean removed = service.removeExpenseById(2);
        assertTrue(removed);

        List<Expense> expenses = service.getAllExpenses();
        assertEquals(2, expenses.size());
        assertEquals(1, expenses.get(0).getId());
        assertEquals(3, expenses.get(1).getId());

        // Test removing non-existent id
        boolean notRemoved = service.removeExpenseById(999);
        assertFalse(notRemoved);
        assertEquals(2, expenses.size());
    }

    /**
     * Test that getTotalExpenseForProject calculates correctly.
     */
    @Test
    public void testGetTotalExpenseForProjectIsCalculatedCorrectly() {
        Expense expense1 = new Expense(1, 10, 100, 250.50, "Steel purchase");
        Expense expense2 = new Expense(2, 20, 100, 150.00, "Wood purchase");
        Expense expense3 = new Expense(3, 30, 100, 75.25, "Plastic purchase");
        Expense expense4 = new Expense(4, 40, 200, 300.00, "Metal purchase");
        Expense expense5 = new Expense(5, 50, 200, 125.75, "Glass purchase");

        service.addExpense(expense1);
        service.addExpense(expense2);
        service.addExpense(expense3);
        service.addExpense(expense4);
        service.addExpense(expense5);

        // Total for project 100: 250.50 + 150.00 + 75.25 = 475.75
        double totalProject100 = service.getTotalExpenseForProject(100);
        assertEquals(475.75, totalProject100, 0.001);

        // Total for project 200: 300.00 + 125.75 = 425.75
        double totalProject200 = service.getTotalExpenseForProject(200);
        assertEquals(425.75, totalProject200, 0.001);

        // Total for non-existent project: 0.0
        double totalProject999 = service.getTotalExpenseForProject(999);
        assertEquals(0.0, totalProject999, 0.001);
    }

    /**
     * Test that persistence works correctly.
     */
    @Test
    public void testPersistenceWorks() {
        Expense expense1 = new Expense(1, 10, 100, 250.50, "Steel purchase");
        Expense expense2 = new Expense(2, 20, 100, 150.00, "Wood purchase");
        Expense expense3 = new Expense(3, 30, 200, 75.25, "Plastic purchase");

        service.addExpense(expense1);
        service.addExpense(expense2);
        service.addExpense(expense3);

        // Create a new service with the same repository to test persistence
        ExpenseService newService = new ExpenseService(repository);
        List<Expense> loadedExpenses = newService.getAllExpenses();

        assertEquals(3, loadedExpenses.size());
        assertEquals(1, loadedExpenses.get(0).getId());
        assertEquals(10, loadedExpenses.get(0).getMaterialId());
        assertEquals(100, loadedExpenses.get(0).getProjectId());
        assertEquals(250.50, loadedExpenses.get(0).getAmount(), 0.001);
        assertEquals("Steel purchase", loadedExpenses.get(0).getDescription());

        assertEquals(2, loadedExpenses.get(1).getId());
        assertEquals(20, loadedExpenses.get(1).getMaterialId());
        assertEquals(100, loadedExpenses.get(1).getProjectId());
        assertEquals(150.00, loadedExpenses.get(1).getAmount(), 0.001);
        assertEquals("Wood purchase", loadedExpenses.get(1).getDescription());

        assertEquals(3, loadedExpenses.get(2).getId());
        assertEquals(30, loadedExpenses.get(2).getMaterialId());
        assertEquals(200, loadedExpenses.get(2).getProjectId());
        assertEquals(75.25, loadedExpenses.get(2).getAmount(), 0.001);
        assertEquals("Plastic purchase", loadedExpenses.get(2).getDescription());
    }

    /**
     * Test that addExpense throws IllegalArgumentException when expense is null.
     */
    @Test
    public void testAddExpenseNullThrows() {
        try {
            service.addExpense(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("cannot be null"));
        }
    }

    /**
     * Test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        ExpenseService defaultService = new ExpenseService();
        assertNotNull(defaultService);
        assertEquals(0, defaultService.getAllExpenses().size());
    }

    /**
     * Test getTotalExpenseForProject with empty expenses.
     */
    @Test
    public void testGetTotalExpenseForProjectWithEmptyExpenses() {
        double total = service.getTotalExpenseForProject(100);
        assertEquals(0.0, total, 0.001);
    }
}

