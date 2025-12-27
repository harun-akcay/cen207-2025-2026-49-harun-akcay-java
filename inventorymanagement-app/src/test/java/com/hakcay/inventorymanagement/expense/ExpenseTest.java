package com.hakcay.inventorymanagement.expense;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExpenseTest {
    
    @Test
    public void testDefaultConstructor() {
        Expense expense = new Expense();
        assertEquals(0, expense.getId());
        assertEquals(0, expense.getMaterialId());
        assertEquals(0, expense.getProjectId());
        assertEquals(0.0, expense.getAmount(), 0.001);
        assertNull(expense.getDescription());
    }
    
    @Test
    public void testParameterizedConstructor() {
        Expense expense = new Expense(1, 10, 20, 100.50, "Material purchase");
        assertEquals(1, expense.getId());
        assertEquals(10, expense.getMaterialId());
        assertEquals(20, expense.getProjectId());
        assertEquals(100.50, expense.getAmount(), 0.001);
        assertEquals("Material purchase", expense.getDescription());
    }
    
    @Test
    public void testSettersAndGetters() {
        Expense expense = new Expense();
        
        expense.setId(5);
        assertEquals(5, expense.getId());
        
        expense.setMaterialId(15);
        assertEquals(15, expense.getMaterialId());
        
        expense.setProjectId(25);
        assertEquals(25, expense.getProjectId());
        
        expense.setAmount(200.75);
        assertEquals(200.75, expense.getAmount(), 0.001);
        
        expense.setDescription("New description");
        assertEquals("New description", expense.getDescription());
    }
    
    @Test
    public void testToString() {
        Expense expense = new Expense(1, 10, 20, 100.50, "Material purchase");
        String str = expense.toString();
        assertTrue(str.contains("Expense"));
        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("materialId=10"));
        assertTrue(str.contains("projectId=20"));
        assertTrue(str.contains("amount=100.5"));
        assertTrue(str.contains("description='Material purchase'"));
    }
    
    @Test
    public void testSetDescriptionNull() {
        Expense expense = new Expense();
        expense.setDescription(null);
        assertNull(expense.getDescription());
    }
    
    @Test
    public void testNegativeAmount() {
        Expense expense = new Expense();
        expense.setAmount(-50.0);
        assertEquals(-50.0, expense.getAmount(), 0.001);
    }
    
    @Test
    public void testZeroAmount() {
        Expense expense = new Expense(1, 10, 20, 0.0, "Free");
        assertEquals(0.0, expense.getAmount(), 0.001);
    }
    
    @Test
    public void testLargeValues() {
        Expense expense = new Expense(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Double.MAX_VALUE, "Large");
        assertEquals(Integer.MAX_VALUE, expense.getId());
        assertEquals(Integer.MAX_VALUE, expense.getMaterialId());
        assertEquals(Integer.MAX_VALUE, expense.getProjectId());
        assertEquals(Double.MAX_VALUE, expense.getAmount(), 0.001);
    }
    
    @Test
    public void testNegativeIds() {
        Expense expense = new Expense();
        expense.setId(-1);
        expense.setMaterialId(-2);
        expense.setProjectId(-3);
        assertEquals(-1, expense.getId());
        assertEquals(-2, expense.getMaterialId());
        assertEquals(-3, expense.getProjectId());
    }
    
    @Test
    public void testEmptyDescription() {
        Expense expense = new Expense(1, 10, 20, 100.0, "");
        assertEquals("", expense.getDescription());
    }
}

