package com.hakcay.inventorymanagement.sales;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for SaleService.
 * Uses temporary files to avoid affecting real sales files.
 */
public class SaleServiceTest {
    private SaleService service;
    private SaleRepository repository;
    private File tempFile;

    /**
     * Sets up test fixtures before each test.
     * Creates a temporary file for each test.
     *
     * @throws IOException if file creation fails
     */
    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("sales", ".csv");
        tempFile.deleteOnExit();
        repository = new SaleRepository(tempFile.getAbsolutePath());
        service = new SaleService(repository);
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
     * Test that addSale works correctly.
     */
    @Test
    public void testAddSaleWorks() {
        Sale sale = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        service.addSale(sale);

        List<Sale> sales = service.getAllSales();
        assertEquals(1, sales.size());
        assertEquals(1, sales.get(0).getId());
        assertEquals(10, sales.get(0).getMaterialId());
        assertEquals(5, sales.get(0).getQuantity());
        assertEquals(25.50, sales.get(0).getPrice(), 0.001);
        assertEquals("2024-01-15", sales.get(0).getDate());
        assertEquals("John Doe", sales.get(0).getCustomerName());
        assertEquals(127.50, sales.get(0).getTotalAmount(), 0.001); // 5 * 25.50
    }

    /**
     * Test that addSale throws IllegalArgumentException when duplicate id is used.
     */
    @Test
    public void testAddSaleDuplicateIdThrowsException() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        Sale sale2 = new Sale(1, 20, 3, 30.00, "2024-01-16", "Jane Smith");

        service.addSale(sale1);

        try {
            service.addSale(sale2);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("already exists"));
        }
    }

    /**
     * Test that removeSaleById works correctly.
     */
    @Test
    public void testRemoveSaleByIdWorks() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        Sale sale2 = new Sale(2, 20, 3, 30.00, "2024-01-16", "Jane Smith");
        Sale sale3 = new Sale(3, 30, 2, 15.75, "2024-01-17", "Bob Johnson");

        service.addSale(sale1);
        service.addSale(sale2);
        service.addSale(sale3);

        boolean removed = service.removeSaleById(2);
        assertTrue(removed);

        List<Sale> sales = service.getAllSales();
        assertEquals(2, sales.size());
        assertEquals(1, sales.get(0).getId());
        assertEquals(3, sales.get(1).getId());

        // Test removing non-existent id
        boolean notRemoved = service.removeSaleById(999);
        assertFalse(notRemoved);
        assertEquals(2, sales.size());
    }

    /**
     * Test that getTotalRevenue calculates correctly.
     */
    @Test
    public void testGetTotalRevenueIsCalculatedCorrectly() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe"); // 127.50
        Sale sale2 = new Sale(2, 20, 3, 30.00, "2024-01-16", "Jane Smith"); // 90.00
        Sale sale3 = new Sale(3, 30, 2, 15.75, "2024-01-17", "Bob Johnson"); // 31.50

        service.addSale(sale1);
        service.addSale(sale2);
        service.addSale(sale3);

        // Total: 127.50 + 90.00 + 31.50 = 249.00
        double total = service.getTotalRevenue();
        assertEquals(249.00, total, 0.001);
    }

    /**
     * Test that getTotalRevenueForMaterial calculates correctly.
     */
    @Test
    public void testGetTotalRevenueForMaterialIsCalculatedCorrectly() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe"); // 127.50
        Sale sale2 = new Sale(2, 10, 3, 30.00, "2024-01-16", "Jane Smith"); // 90.00
        Sale sale3 = new Sale(3, 20, 2, 15.75, "2024-01-17", "Bob Johnson"); // 31.50

        service.addSale(sale1);
        service.addSale(sale2);
        service.addSale(sale3);

        // Total for material 10: 127.50 + 90.00 = 217.50
        double totalMaterial10 = service.getTotalRevenueForMaterial(10);
        assertEquals(217.50, totalMaterial10, 0.001);

        // Total for material 20: 31.50
        double totalMaterial20 = service.getTotalRevenueForMaterial(20);
        assertEquals(31.50, totalMaterial20, 0.001);

        // Total for non-existent material: 0.0
        double totalMaterial999 = service.getTotalRevenueForMaterial(999);
        assertEquals(0.0, totalMaterial999, 0.001);
    }

    /**
     * Test that getTotalQuantitySoldForMaterial calculates correctly.
     */
    @Test
    public void testGetTotalQuantitySoldForMaterialIsCalculatedCorrectly() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        Sale sale2 = new Sale(2, 10, 3, 30.00, "2024-01-16", "Jane Smith");
        Sale sale3 = new Sale(3, 20, 2, 15.75, "2024-01-17", "Bob Johnson");

        service.addSale(sale1);
        service.addSale(sale2);
        service.addSale(sale3);

        // Total quantity for material 10: 5 + 3 = 8
        int totalQuantityMaterial10 = service.getTotalQuantitySoldForMaterial(10);
        assertEquals(8, totalQuantityMaterial10);

        // Total quantity for material 20: 2
        int totalQuantityMaterial20 = service.getTotalQuantitySoldForMaterial(20);
        assertEquals(2, totalQuantityMaterial20);

        // Total quantity for non-existent material: 0
        int totalQuantityMaterial999 = service.getTotalQuantitySoldForMaterial(999);
        assertEquals(0, totalQuantityMaterial999);
    }

    /**
     * Test that getSalesByMaterialId works correctly.
     */
    @Test
    public void testGetSalesByMaterialIdWorks() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        Sale sale2 = new Sale(2, 10, 3, 30.00, "2024-01-16", "Jane Smith");
        Sale sale3 = new Sale(3, 20, 2, 15.75, "2024-01-17", "Bob Johnson");

        service.addSale(sale1);
        service.addSale(sale2);
        service.addSale(sale3);

        List<Sale> salesMaterial10 = service.getSalesByMaterialId(10);
        assertEquals(2, salesMaterial10.size());
        assertEquals(1, salesMaterial10.get(0).getId());
        assertEquals(2, salesMaterial10.get(1).getId());

        List<Sale> salesMaterial20 = service.getSalesByMaterialId(20);
        assertEquals(1, salesMaterial20.size());
        assertEquals(3, salesMaterial20.get(0).getId());

        List<Sale> salesMaterial999 = service.getSalesByMaterialId(999);
        assertEquals(0, salesMaterial999.size());
    }

    /**
     * Test that persistence works correctly.
     */
    @Test
    public void testPersistenceWorks() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        Sale sale2 = new Sale(2, 20, 3, 30.00, "2024-01-16", "Jane Smith");
        Sale sale3 = new Sale(3, 30, 2, 15.75, "2024-01-17", "Bob Johnson");

        service.addSale(sale1);
        service.addSale(sale2);
        service.addSale(sale3);

        // Create a new service with the same repository to test persistence
        SaleService newService = new SaleService(repository);
        List<Sale> loadedSales = newService.getAllSales();

        assertEquals(3, loadedSales.size());
        assertEquals(1, loadedSales.get(0).getId());
        assertEquals(10, loadedSales.get(0).getMaterialId());
        assertEquals(5, loadedSales.get(0).getQuantity());
        assertEquals(25.50, loadedSales.get(0).getPrice(), 0.001);
        assertEquals("2024-01-15", loadedSales.get(0).getDate());
        assertEquals("John Doe", loadedSales.get(0).getCustomerName());

        assertEquals(2, loadedSales.get(1).getId());
        assertEquals(20, loadedSales.get(1).getMaterialId());
        assertEquals(3, loadedSales.get(1).getQuantity());
        assertEquals(30.00, loadedSales.get(1).getPrice(), 0.001);
        assertEquals("2024-01-16", loadedSales.get(1).getDate());
        assertEquals("Jane Smith", loadedSales.get(1).getCustomerName());

        assertEquals(3, loadedSales.get(2).getId());
        assertEquals(30, loadedSales.get(2).getMaterialId());
        assertEquals(2, loadedSales.get(2).getQuantity());
        assertEquals(15.75, loadedSales.get(2).getPrice(), 0.001);
        assertEquals("2024-01-17", loadedSales.get(2).getDate());
        assertEquals("Bob Johnson", loadedSales.get(2).getCustomerName());
    }

    /**
     * Test that addSale throws IllegalArgumentException when sale is null.
     */
    @Test
    public void testAddSaleNullThrows() {
        try {
            service.addSale(null);
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
        SaleService defaultService = new SaleService();
        assertNotNull(defaultService);
        assertEquals(0, defaultService.getAllSales().size());
    }

    /**
     * Test getTotalRevenue with empty sales.
     */
    @Test
    public void testGetTotalRevenueWithEmptySales() {
        double total = service.getTotalRevenue();
        assertEquals(0.0, total, 0.001);
    }

    /**
     * Test getSaleById works correctly.
     */
    @Test
    public void testGetSaleByIdWorks() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        Sale sale2 = new Sale(2, 20, 3, 30.00, "2024-01-16", "Jane Smith");

        service.addSale(sale1);
        service.addSale(sale2);

        Sale found = service.getSaleById(1);
        assertNotNull(found);
        assertEquals(1, found.getId());
        assertEquals(10, found.getMaterialId());
        assertEquals("John Doe", found.getCustomerName());

        Sale notFound = service.getSaleById(999);
        assertNull(notFound);
    }
}

