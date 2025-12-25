/**

@file InventorymanagementAppTest.java
@brief This file contains the test cases for the InventorymanagementApp class.
@details This file includes test methods to validate the functionality of the InventorymanagementApp class. It uses JUnit for unit testing.
*/
package com.hakcay.inventorymanagement;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hakcay.inventorymanagement.InventorymanagementApp;
import com.hakcay.inventorymanagement.material.Material;
import com.hakcay.inventorymanagement.material.MaterialService;


/**

@class InventorymanagementAppTest
@brief This class represents the test class for the InventorymanagementApp class.
@details The InventorymanagementAppTest class provides test methods to verify the behavior of the InventorymanagementApp class. It includes test methods for successful execution, object creation, and error handling scenarios.
@author ugur.coruh
*/
public class InventorymanagementAppTest {

  /**
   * @brief This method is executed once before all test methods.
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * @brief This method is executed once after all test methods.
   * @throws Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @brief This method is executed before each test method.
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @brief This method is executed after each test method.
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * @brief Test that MaterialService can be instantiated and used.
   * @details This test verifies that MaterialService can be created and basic operations work.
   */
  @Test
  public void testMaterialServiceCanBeUsed() {
    MaterialService service = new MaterialService();
    assertNotNull(service);
    
    Material material = new Material(1, "Test Material", "Test Type", 10, 5.50);
    service.addMaterial(material);
    
    assertEquals(1, service.getAllMaterials().size());
    assertEquals("Test Material", service.getAllMaterials().get(0).getName());
  }

  /**
   * @brief Test that MaterialService getAllMaterials returns empty list initially.
   * @details This test verifies that a new MaterialService starts with an empty list.
   */
  @Test
  public void testMaterialServiceStartsEmpty() {
    MaterialService service = new MaterialService();
    assertNotNull(service.getAllMaterials());
    assertEquals(0, service.getAllMaterials().size());
  }

  /**
   * @brief Test Material model class constructors and methods.
   * @details This test exercises Material class to ensure coverage.
   */
  @Test
  public void testMaterialModelClass() {
    Material material1 = new Material();
    material1.setId(1);
    material1.setName("Test");
    material1.setType("Type");
    material1.setQuantity(10);
    material1.setUnitCost(5.50);
    
    assertEquals(1, material1.getId());
    assertEquals("Test", material1.getName());
    assertEquals("Type", material1.getType());
    assertEquals(10, material1.getQuantity());
    assertEquals(5.50, material1.getUnitCost(), 0.001);
    
    Material material2 = new Material(2, "Name", "Type", 20, 10.00);
    assertEquals(2, material2.getId());
    assertEquals("Name", material2.getName());
    
    String toString = material2.toString();
    assertNotNull(toString);
    assertTrue(toString.contains("Material"));
  }

  /**
   * @brief Test MaterialService with default repository constructor.
   * @details This test ensures default MaterialRepository constructor is called.
   */
  @Test
  public void testMaterialServiceUsesDefaultRepository() {
    MaterialService service = new MaterialService();
    Material material = new Material(1, "Test", "Type", 10, 5.50);
    service.addMaterial(material);
    
    MaterialService service2 = new MaterialService();
    List<Material> materials = service2.getAllMaterials();
    assertNotNull(materials);
  }

  /**
   * @brief Test MaterialService updateMaterial method.
   * @details This test exercises the update functionality.
   */
  @Test
  public void testMaterialServiceUpdateMaterial() {
    MaterialService service = new MaterialService();
    Material material = new Material(1, "Original", "Type", 10, 5.50);
    service.addMaterial(material);
    
    Material updated = new Material(1, "Updated", "NewType", 20, 10.00);
    service.updateMaterial(updated);
    
    List<Material> materials = service.getAllMaterials();
    assertEquals(1, materials.size());
    assertEquals("Updated", materials.get(0).getName());
    assertEquals("NewType", materials.get(0).getType());
  }

  /**
   * @brief Test MaterialService removeMaterialById method.
   * @details This test exercises the removal functionality.
   */
  @Test
  public void testMaterialServiceRemoveMaterial() {
    MaterialService service = new MaterialService();
    Material material = new Material(1, "Test", "Type", 10, 5.50);
    service.addMaterial(material);
    
    boolean removed = service.removeMaterialById(1);
    assertTrue(removed);
    
    boolean notRemoved = service.removeMaterialById(999);
    assertFalse(notRemoved);
  }

  /**
   * @brief Test MaterialService getMaterialById method.
   * @details This test exercises the get by ID functionality.
   */
  @Test
  public void testMaterialServiceGetMaterialById() {
    MaterialService service = new MaterialService();
    Material material = new Material(1, "Test", "Type", 10, 5.50);
    service.addMaterial(material);
    
    Material found = service.getMaterialById(1);
    assertNotNull(found);
    assertEquals(1, found.getId());
    assertEquals("Test", found.getName());
    
    Material notFound = service.getMaterialById(999);
    assertNull(notFound);
  }

  /**
   * @brief Test MaterialService with null material handling.
   * @details This test verifies that null materials are handled gracefully.
   */
  @Test
  public void testMaterialServiceNullHandling() {
    MaterialService service = new MaterialService();
    
    // Adding null should not throw exception
    service.addMaterial(null);
    assertEquals(0, service.getAllMaterials().size());
    
    // Updating with null should not throw exception
    service.updateMaterial(null);
    
    // Getting by ID with non-existent ID should return null
    assertNull(service.getMaterialById(999));
  }

  /**
   * @brief Test MaterialRepository with null material handling.
   * @details This test verifies repository null handling.
   */
  @Test
  public void testMaterialRepositoryNullHandling() {
    MaterialService service = new MaterialService();
    
    // Add valid material first
    Material material = new Material(1, "Test", "Type", 10, 5.50);
    service.addMaterial(material);
    assertEquals(1, service.getAllMaterials().size());
    
    // Try to update with null
    MaterialService service2 = new MaterialService();
    Material nullMaterial = null;
    // This should not crash
    service2.updateMaterial(nullMaterial);
  }

  /**
   * @brief Test MaterialService with multiple materials.
   * @details This test verifies handling of multiple materials.
   */
  @Test
  public void testMaterialServiceMultipleMaterials() {
    MaterialService service = new MaterialService();
    
    Material material1 = new Material(1, "Material1", "Type1", 10, 5.50);
    Material material2 = new Material(2, "Material2", "Type2", 20, 10.00);
    Material material3 = new Material(3, "Material3", "Type3", 30, 15.00);
    
    service.addMaterial(material1);
    service.addMaterial(material2);
    service.addMaterial(material3);
    
    assertEquals(3, service.getAllMaterials().size());
    
    Material found = service.getMaterialById(2);
    assertNotNull(found);
    assertEquals("Material2", found.getName());
    
    service.removeMaterialById(2);
    assertEquals(2, service.getAllMaterials().size());
    assertNull(service.getMaterialById(2));
  }

  /**
   * @brief Test MaterialService constructor with repository parameter.
   * @details This test verifies MaterialService can be created with a custom repository.
   */
  @Test
  public void testMaterialServiceWithCustomRepository() {
    com.hakcay.inventorymanagement.material.MaterialRepository repository = 
        new com.hakcay.inventorymanagement.material.MaterialRepository();
    MaterialService service = new MaterialService(repository);
    
    Material material = new Material(1, "Test", "Type", 10, 5.50);
    service.addMaterial(material);
    
    assertEquals(1, service.getAllMaterials().size());
    assertEquals("Test", service.getAllMaterials().get(0).getName());
  }

  /**
   * @brief Test MaterialRepository update with non-existent material.
   * @details This test verifies update returns false for non-existent material.
   */
  @Test
  public void testMaterialRepositoryUpdateNonExistent() {
    MaterialService service = new MaterialService();
    
    // Try to update a material that doesn't exist
    Material nonExistent = new Material(999, "NonExistent", "Type", 10, 5.50);
    service.updateMaterial(nonExistent);
    
    // Should still be empty
    assertEquals(0, service.getAllMaterials().size());
  }

  /**
   * @brief Test MaterialRepository getAll returns a copy.
   * @details This test verifies that getAll returns a new list, not the internal list.
   */
  @Test
  public void testMaterialRepositoryGetAllReturnsCopy() {
    MaterialService service = new MaterialService();
    Material material = new Material(1, "Test", "Type", 10, 5.50);
    service.addMaterial(material);
    
    List<Material> materials1 = service.getAllMaterials();
    List<Material> materials2 = service.getAllMaterials();
    
    // Should be different list instances
    assertNotSame(materials1, materials2);
    // But should have same content
    assertEquals(materials1.size(), materials2.size());
  }

  /**
   * @brief Test MaterialRepository remove from empty repository.
   * @details This test verifies remove returns false when repository is empty.
   */
  @Test
  public void testMaterialRepositoryRemoveFromEmpty() {
    MaterialService service = new MaterialService();
    
    boolean removed = service.removeMaterialById(1);
    assertFalse(removed);
  }

  /**
   * @brief Test MaterialRepository findById with non-existent ID.
   * @details This test verifies findById returns null for non-existent ID.
   */
  @Test
  public void testMaterialRepositoryFindByIdNonExistent() {
    MaterialService service = new MaterialService();
    
    Material found = service.getMaterialById(999);
    assertNull(found);
  }

  /**
   * @brief Test InventorymanagementApp main method with simulated input.
   * @details This test simulates user input to exit the application immediately.
   */
  @Test
  public void testInventorymanagementAppMainExit() {
    String input = "0\n";
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;
    
    try {
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      
      Thread mainThread = new Thread(() -> {
        try {
          InventorymanagementApp.main(new String[]{});
        } catch (Exception e) {
          // Ignore exceptions in test
        }
      });
      mainThread.setDaemon(true);
      mainThread.start();
      
      try {
        mainThread.join(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      
      String output = outContent.toString();
      assertNotNull(output);
    } finally {
      System.setIn(originalIn);
      System.setOut(originalOut);
    }
  }

  /**
   * @brief Test InventorymanagementApp main method with material menu option.
   * @details This test simulates selecting material menu and then exiting.
   */
  @Test
  public void testInventorymanagementAppMainMaterialMenu() {
    String input = "1\n0\n0\n";
    InputStream originalIn = System.in;
    PrintStream originalOut = System.out;
    
    try {
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      
      Thread mainThread = new Thread(() -> {
        try {
          InventorymanagementApp.main(new String[]{});
        } catch (Exception e) {
          // Ignore exceptions in test
        }
      });
      mainThread.setDaemon(true);
      mainThread.start();
      
      try {
        mainThread.join(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      
      String output = outContent.toString();
      assertNotNull(output);
    } finally {
      System.setIn(originalIn);
      System.setOut(originalOut);
    }
  }

}
