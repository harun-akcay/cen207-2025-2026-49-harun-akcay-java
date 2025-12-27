/**
 * @file MaterialServiceTest.java
 * @brief Test class for MaterialService with undo/redo functionality.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @class MaterialServiceTest
 * @brief Comprehensive unit tests for MaterialService with undo/redo.
 */
public class MaterialServiceTest {
    
    private MaterialService service;
    
    @Before
    public void setUp() {
        service = new MaterialService();
    }
    
    @Test
    public void testAddMaterial() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        
        List<Material> materials = service.getAllMaterials();
        assertEquals(1, materials.size());
        assertEquals("Steel", materials.get(0).getName());
    }
    
    @Test
    public void testUndoAdd() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        
        assertTrue(service.canUndo());
        assertTrue(service.undo());
        
        List<Material> materials = service.getAllMaterials();
        assertEquals(0, materials.size());
        assertFalse(service.canUndo());
        assertTrue(service.canRedo());
    }
    
    @Test
    public void testRedoAdd() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        service.undo();
        
        assertTrue(service.canRedo());
        assertTrue(service.redo());
        
        List<Material> materials = service.getAllMaterials();
        assertEquals(1, materials.size());
        assertEquals("Steel", materials.get(0).getName());
    }
    
    @Test
    public void testUndoUpdate() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        
        Material updated = new Material(1, "Steel", "Metal", 200, 12.00);
        service.updateMaterial(updated);
        
        assertEquals(200, service.getMaterialById(1).getQuantity());
        
        assertTrue(service.undo());
        Material restored = service.getMaterialById(1);
        assertEquals(100, restored.getQuantity());
        assertEquals(10.50, restored.getUnitCost(), 0.001);
    }
    
    @Test
    public void testRedoUpdate() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        
        Material updated = new Material(1, "Steel", "Metal", 200, 12.00);
        service.updateMaterial(updated);
        service.undo();
        
        assertTrue(service.redo());
        Material reUpdated = service.getMaterialById(1);
        assertEquals(200, reUpdated.getQuantity());
        assertEquals(12.00, reUpdated.getUnitCost(), 0.001);
    }
    
    @Test
    public void testUndoRemove() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        service.removeMaterialById(1);
        
        assertNull(service.getMaterialById(1));
        
        assertTrue(service.undo());
        Material restored = service.getMaterialById(1);
        assertNotNull(restored);
        assertEquals("Steel", restored.getName());
    }
    
    @Test
    public void testRedoRemove() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        service.removeMaterialById(1);
        service.undo();
        
        assertTrue(service.redo());
        assertNull(service.getMaterialById(1));
    }
    
    @Test
    public void testMultipleUndo() {
        Material m1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material m2 = new Material(2, "Wood", "Material", 50, 5.00);
        Material m3 = new Material(3, "Plastic", "Material", 200, 2.50);
        
        service.addMaterial(m1);
        service.addMaterial(m2);
        service.addMaterial(m3);
        
        assertEquals(3, service.getAllMaterials().size());
        
        service.undo();
        assertEquals(2, service.getAllMaterials().size());
        
        service.undo();
        assertEquals(1, service.getAllMaterials().size());
        
        service.undo();
        assertEquals(0, service.getAllMaterials().size());
        
        assertFalse(service.canUndo());
    }
    
    @Test
    public void testMultipleRedo() {
        Material m1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material m2 = new Material(2, "Wood", "Material", 50, 5.00);
        
        service.addMaterial(m1);
        service.addMaterial(m2);
        service.undo();
        service.undo();
        
        assertEquals(0, service.getAllMaterials().size());
        
        service.redo();
        assertEquals(1, service.getAllMaterials().size());
        
        service.redo();
        assertEquals(2, service.getAllMaterials().size());
        
        assertFalse(service.canRedo());
    }
    
    @Test
    public void testUndoWhenEmpty() {
        assertFalse(service.canUndo());
        assertFalse(service.undo());
    }
    
    @Test
    public void testRedoWhenEmpty() {
        assertFalse(service.canRedo());
        assertFalse(service.redo());
    }
    
    @Test
    public void testNewActionClearsRedo() {
        Material m1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material m2 = new Material(2, "Wood", "Material", 50, 5.00);
        
        service.addMaterial(m1);
        service.undo();
        
        assertTrue(service.canRedo());
        
        service.addMaterial(m2);
        
        assertFalse(service.canRedo());
        assertTrue(service.canUndo());
    }
    
    @Test
    public void testUndoRedoSequence() {
        Material m1 = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(m1);
        
        Material updated = new Material(1, "Steel", "Metal", 200, 12.00);
        service.updateMaterial(updated);
        
        service.undo(); // Undo update
        assertEquals(100, service.getMaterialById(1).getQuantity());
        
        service.undo(); // Undo add
        assertNull(service.getMaterialById(1));
        
        service.redo(); // Redo add
        assertNotNull(service.getMaterialById(1));
        
        service.redo(); // Redo update
        assertEquals(200, service.getMaterialById(1).getQuantity());
    }
    
    @Test
    public void testGetAllMaterials() {
        Material m1 = new Material(1, "Steel", "Metal", 100, 10.50);
        Material m2 = new Material(2, "Wood", "Material", 50, 5.00);
        
        service.addMaterial(m1);
        service.addMaterial(m2);
        
        List<Material> materials = service.getAllMaterials();
        assertEquals(2, materials.size());
    }
    
    @Test
    public void testGetMaterialById() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        service.addMaterial(material);
        
        Material found = service.getMaterialById(1);
        assertNotNull(found);
        assertEquals("Steel", found.getName());
        
        Material notFound = service.getMaterialById(999);
        assertNull(notFound);
    }
    
    @Test
    public void testAddNullMaterial() {
        service.addMaterial(null);
        assertEquals(0, service.getAllMaterials().size());
        assertFalse(service.canUndo());
    }
    
    @Test
    public void testUpdateNullMaterial() {
        service.updateMaterial(null);
        assertFalse(service.canUndo());
    }
    
    @Test
    public void testRemoveNonExistentMaterial() {
        boolean removed = service.removeMaterialById(999);
        assertFalse(removed);
        assertFalse(service.canUndo());
    }
    
    @Test
    public void testSearchMaterialsByName() {
        Material material1 = new Material(1, "Steel Bar", "Metal", 100, 10.50);
        Material material2 = new Material(2, "Wood Plank", "Wood", 50, 5.00);
        Material material3 = new Material(3, "Steel Wire", "Metal", 200, 2.50);
        service.addMaterial(material1);
        service.addMaterial(material2);
        service.addMaterial(material3);
        
        List<Material> results = service.searchMaterialsByName("Steel");
        assertEquals(2, results.size());
        assertTrue(results.contains(material1));
        assertTrue(results.contains(material3));
    }
    
    @Test
    public void testSearchMaterialsByNameCaseInsensitive() {
        Material material1 = new Material(1, "Steel Bar", "Metal", 100, 10.50);
        service.addMaterial(material1);
        
        List<Material> results = service.searchMaterialsByName("steel");
        assertEquals(1, results.size());
        assertEquals(material1, results.get(0));
    }
    
    @Test
    public void testSearchMaterialsByNameNoMatch() {
        Material material1 = new Material(1, "Steel Bar", "Metal", 100, 10.50);
        service.addMaterial(material1);
        
        List<Material> results = service.searchMaterialsByName("Wood");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchMaterialsByNameNullPattern() {
        Material material1 = new Material(1, "Steel Bar", "Metal", 100, 10.50);
        service.addMaterial(material1);
        
        List<Material> results = service.searchMaterialsByName(null);
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchMaterialsByNameEmptyPattern() {
        Material material1 = new Material(1, "Steel Bar", "Metal", 100, 10.50);
        service.addMaterial(material1);
        
        List<Material> results = service.searchMaterialsByName("");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchMaterialsByType() {
        Material material1 = new Material(1, "Steel Bar", "Metal", 100, 10.50);
        Material material2 = new Material(2, "Wood Plank", "Wood", 50, 5.00);
        Material material3 = new Material(3, "Steel Wire", "Metal", 200, 2.50);
        service.addMaterial(material1);
        service.addMaterial(material2);
        service.addMaterial(material3);
        
        List<Material> results = service.searchMaterialsByType("Metal");
        assertEquals(2, results.size());
        assertTrue(results.contains(material1));
        assertTrue(results.contains(material3));
    }
    
    @Test
    public void testSearchMaterials() {
        Material material1 = new Material(1, "Steel Bar", "Metal", 100, 10.50);
        Material material2 = new Material(2, "Wood Plank", "Wood", 50, 5.00);
        service.addMaterial(material1);
        service.addMaterial(material2);
        
        List<Material> results = service.searchMaterials("Steel");
        assertEquals(1, results.size());
        assertEquals(material1, results.get(0));
        
        results = service.searchMaterials("Wood");
        assertEquals(1, results.size());
        assertEquals(material2, results.get(0));
    }
}

