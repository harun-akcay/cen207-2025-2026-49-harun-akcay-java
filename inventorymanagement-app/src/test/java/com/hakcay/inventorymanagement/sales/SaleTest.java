package com.hakcay.inventorymanagement.sales;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for Sale model.
 */
public class SaleTest {

    /**
     * Test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        Sale sale = new Sale();
        assertEquals(0, sale.getId());
        assertEquals(0, sale.getMaterialId());
        assertEquals(0, sale.getQuantity());
        assertEquals(0.0, sale.getPrice(), 0.001);
        assertNull(sale.getDate());
        assertNull(sale.getCustomerName());
    }

    /**
     * Test constructor with all fields.
     */
    @Test
    public void testConstructorWithAllFields() {
        Sale sale = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        assertEquals(1, sale.getId());
        assertEquals(10, sale.getMaterialId());
        assertEquals(5, sale.getQuantity());
        assertEquals(25.50, sale.getPrice(), 0.001);
        assertEquals("2024-01-15", sale.getDate());
        assertEquals("John Doe", sale.getCustomerName());
    }

    /**
     * Test getters and setters.
     */
    @Test
    public void testGettersAndSetters() {
        Sale sale = new Sale();

        sale.setId(1);
        assertEquals(1, sale.getId());

        sale.setMaterialId(10);
        assertEquals(10, sale.getMaterialId());

        sale.setQuantity(5);
        assertEquals(5, sale.getQuantity());

        sale.setPrice(25.50);
        assertEquals(25.50, sale.getPrice(), 0.001);

        sale.setDate("2024-01-15");
        assertEquals("2024-01-15", sale.getDate());

        sale.setCustomerName("John Doe");
        assertEquals("John Doe", sale.getCustomerName());
    }

    /**
     * Test getTotalAmount calculation.
     */
    @Test
    public void testGetTotalAmount() {
        Sale sale1 = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        assertEquals(127.50, sale1.getTotalAmount(), 0.001); // 5 * 25.50

        Sale sale2 = new Sale(2, 20, 3, 30.00, "2024-01-16", "Jane Smith");
        assertEquals(90.00, sale2.getTotalAmount(), 0.001); // 3 * 30.00

        Sale sale3 = new Sale(3, 30, 0, 15.75, "2024-01-17", "Bob Johnson");
        assertEquals(0.0, sale3.getTotalAmount(), 0.001); // 0 * 15.75
    }

    /**
     * Test toString method.
     */
    @Test
    public void testToString() {
        Sale sale = new Sale(1, 10, 5, 25.50, "2024-01-15", "John Doe");
        String str = sale.toString();
        assertTrue(str.contains("Sale{"));
        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("materialId=10"));
        assertTrue(str.contains("quantity=5"));
        assertTrue(str.contains("price=25.5"));
        assertTrue(str.contains("date='2024-01-15'"));
        assertTrue(str.contains("customerName='John Doe'"));
        assertTrue(str.contains("totalAmount=127.5"));
    }
}

