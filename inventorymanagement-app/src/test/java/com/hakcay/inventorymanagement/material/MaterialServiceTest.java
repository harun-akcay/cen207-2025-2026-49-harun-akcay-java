package com.hakcay.inventorymanagement.material;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for MaterialService.
 * Uses temporary files to avoid affecting real project files.
 */
public class MaterialServiceTest {
    private MaterialService service;
    private MaterialRepository repository;
    private File tempFile;

    /**
     * Sets up test fixtures before each test.
     * Creates a temporary file for each test.
     *
     * @throws IOException if file creation fails
     */
    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("materials", ".csv");
        tempFile.deleteOnExit();
        repository = new MaterialRepository(tempFile.getAbsolutePath());
        service = new MaterialService(repository);
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
     * Test that addMaterial adds a material successfully.
     */
    @Test
    public void testAddMaterialAddsMaterialSuccessfully() {
        Material material = new Material(1, "Steel", "Metal", 100, 25.50);
        service.addMaterial(material);

        List<Material> materials = service.getAllMaterials();
        assertEquals(1, materials.size());
        assertEquals(1, materials.get(0).getId());
        assertEquals("Steel", materials.get(0).getName());
        assertEquals("Metal", materials.get(0).getType());
        assertEquals(100, materials.get(0).getQuantity());
        assertEquals(25.50, materials.get(0).getUnitCost(), 0.001);
    }

    /**
     * Test that addMaterial throws IllegalArgumentException if material id already exists.
     */
    @Test
    public void testAddMaterialThrowsExceptionWhenIdExists() {
        Material material1 = new Material(1, "Steel", "Metal", 100, 25.50);
        Material material2 = new Material(1, "Iron", "Metal", 50, 20.00);

        service.addMaterial(material1);

        try {
            service.addMaterial(material2);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("already exists"));
        }
    }

    /**
     * Test that updateMaterial updates an existing material.
     */
    @Test
    public void testUpdateMaterialUpdatesExistingMaterial() {
        Material material = new Material(1, "Steel", "Metal", 100, 25.50);
        service.addMaterial(material);

        Material updatedMaterial = new Material(1, "Steel", "Metal", 150, 30.00);
        service.updateMaterial(updatedMaterial);

        List<Material> materials = service.getAllMaterials();
        assertEquals(1, materials.size());
        assertEquals(1, materials.get(0).getId());
        assertEquals(150, materials.get(0).getQuantity());
        assertEquals(30.00, materials.get(0).getUnitCost(), 0.001);
    }

    /**
     * Test that updateMaterial throws exception if material does not exist.
     */
    @Test
    public void testUpdateMaterialThrowsExceptionWhenMaterialNotFound() {
        Material material = new Material(1, "Steel", "Metal", 100, 25.50);

        try {
            service.updateMaterial(material);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    /**
     * Test that removeMaterialById returns true when removed.
     */
    @Test
    public void testRemoveMaterialByIdReturnsTrueWhenRemoved() {
        Material material = new Material(1, "Steel", "Metal", 100, 25.50);
        service.addMaterial(material);

        boolean removed = service.removeMaterialById(1);
        assertTrue(removed);

        List<Material> materials = service.getAllMaterials();
        assertEquals(0, materials.size());
    }

    /**
     * Test that removeMaterialById returns false when id not found.
     */
    @Test
    public void testRemoveMaterialByIdReturnsFalseWhenIdNotFound() {
        Material material = new Material(1, "Steel", "Metal", 100, 25.50);
        service.addMaterial(material);

        boolean removed = service.removeMaterialById(999);
        assertFalse(removed);

        List<Material> materials = service.getAllMaterials();
        assertEquals(1, materials.size());
    }

    /**
     * Test that getAllMaterials returns correct list size.
     */
    @Test
    public void testGetAllMaterialsReturnsCorrectListSize() {
        assertEquals(0, service.getAllMaterials().size());

        Material material1 = new Material(1, "Steel", "Metal", 100, 25.50);
        Material material2 = new Material(2, "Wood", "Lumber", 50, 15.00);
        Material material3 = new Material(3, "Plastic", "Polymer", 200, 5.75);

        service.addMaterial(material1);
        assertEquals(1, service.getAllMaterials().size());

        service.addMaterial(material2);
        assertEquals(2, service.getAllMaterials().size());

        service.addMaterial(material3);
        assertEquals(3, service.getAllMaterials().size());

        service.removeMaterialById(2);
        assertEquals(2, service.getAllMaterials().size());
    }
}
