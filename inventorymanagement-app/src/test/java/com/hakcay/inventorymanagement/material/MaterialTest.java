package com.hakcay.inventorymanagement.material;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaterialTest {
    
    @Test
    public void testDefaultConstructor() {
        Material material = new Material();
        assertEquals(0, material.getId());
        assertEquals("", material.getName());
        assertEquals("", material.getType());
        assertEquals(0, material.getQuantity());
        assertEquals(0.0, material.getUnitCost(), 0.001);
    }
    
    @Test
    public void testParameterizedConstructor() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        assertEquals(1, material.getId());
        assertEquals("Steel", material.getName());
        assertEquals("Metal", material.getType());
        assertEquals(100, material.getQuantity());
        assertEquals(10.50, material.getUnitCost(), 0.001);
    }
    
    @Test
    public void testSettersAndGetters() {
        Material material = new Material();
        
        material.setId(5);
        assertEquals(5, material.getId());
        
        material.setName("Wood");
        assertEquals("Wood", material.getName());
        
        material.setType("Building");
        assertEquals("Building", material.getType());
        
        material.setQuantity(200);
        assertEquals(200, material.getQuantity());
        
        material.setUnitCost(15.75);
        assertEquals(15.75, material.getUnitCost(), 0.001);
    }
    
    @Test
    public void testToString() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        String str = material.toString();
        assertTrue(str.contains("Material"));
        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("name='Steel'"));
        assertTrue(str.contains("type='Metal'"));
        assertTrue(str.contains("quantity=100"));
        assertTrue(str.contains("unitCost=10.5"));
    }
    
    @Test
    public void testSetNameNull() {
        Material material = new Material();
        material.setName(null);
        assertNull(material.getName());
    }
    
    @Test
    public void testSetTypeNull() {
        Material material = new Material();
        material.setType(null);
        assertNull(material.getType());
    }
    
    @Test
    public void testNegativeQuantity() {
        Material material = new Material();
        material.setQuantity(-10);
        assertEquals(-10, material.getQuantity());
    }
    
    @Test
    public void testNegativeUnitCost() {
        Material material = new Material();
        material.setUnitCost(-5.0);
        assertEquals(-5.0, material.getUnitCost(), 0.001);
    }
    
    @Test
    public void testZeroUnitCost() {
        Material material = new Material(1, "Free", "Type", 10, 0.0);
        assertEquals(0.0, material.getUnitCost(), 0.001);
    }
    
    @Test
    public void testLargeValues() {
        Material material = new Material(Integer.MAX_VALUE, "Large", "Type", Integer.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, material.getId());
        assertEquals(Integer.MAX_VALUE, material.getQuantity());
        assertEquals(Double.MAX_VALUE, material.getUnitCost(), 0.001);
    }
}

