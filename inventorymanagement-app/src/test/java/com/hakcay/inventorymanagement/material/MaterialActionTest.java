/**
 * @file MaterialActionTest.java
 * @brief Test class for MaterialAction.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @class MaterialActionTest
 * @brief Unit tests for MaterialAction class.
 */
public class MaterialActionTest {
    
    @Test
    public void testAddAction() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        MaterialAction action = new MaterialAction(
            MaterialAction.ActionType.ADD,
            null,
            material
        );
        
        assertEquals(MaterialAction.ActionType.ADD, action.getActionType());
        assertNull(action.getMaterialBefore());
        assertNotNull(action.getMaterialAfter());
        assertEquals("Steel", action.getMaterialAfter().getName());
    }
    
    @Test
    public void testUpdateAction() {
        Material before = new Material(1, "Steel", "Metal", 100, 10.50);
        Material after = new Material(1, "Steel", "Metal", 200, 12.00);
        
        MaterialAction action = new MaterialAction(
            MaterialAction.ActionType.UPDATE,
            before,
            after
        );
        
        assertEquals(MaterialAction.ActionType.UPDATE, action.getActionType());
        assertNotNull(action.getMaterialBefore());
        assertNotNull(action.getMaterialAfter());
        assertEquals(100, action.getMaterialBefore().getQuantity());
        assertEquals(200, action.getMaterialAfter().getQuantity());
    }
    
    @Test
    public void testRemoveAction() {
        Material material = new Material(1, "Steel", "Metal", 100, 10.50);
        MaterialAction action = new MaterialAction(
            MaterialAction.ActionType.REMOVE,
            material,
            null
        );
        
        assertEquals(MaterialAction.ActionType.REMOVE, action.getActionType());
        assertNotNull(action.getMaterialBefore());
        assertNull(action.getMaterialAfter());
        assertEquals("Steel", action.getMaterialBefore().getName());
    }
    
    @Test
    public void testActionWithNullMaterials() {
        MaterialAction action = new MaterialAction(
            MaterialAction.ActionType.ADD,
            null,
            null
        );
        
        assertNull(action.getMaterialBefore());
        assertNull(action.getMaterialAfter());
    }
    
    @Test
    public void testMaterialCopy() {
        Material original = new Material(1, "Steel", "Metal", 100, 10.50);
        MaterialAction action = new MaterialAction(
            MaterialAction.ActionType.UPDATE,
            original,
            original
        );
        
        Material before = action.getMaterialBefore();
        Material after = action.getMaterialAfter();
        
        // Verify they are copies, not the same object
        assertNotSame(original, before);
        assertNotSame(original, after);
        
        // But have same values
        assertEquals(original.getId(), before.getId());
        assertEquals(original.getName(), before.getName());
    }
}

