/**
 * @file MaterialAction.java
 * @brief This file contains the MaterialAction class for undo/redo operations.
 * @details This class represents an action that can be undone or redone.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

/**
 * @class MaterialAction
 * @brief Represents an action performed on a material.
 * @details Stores the action type and the material state before/after the action.
 */
public class MaterialAction {
    
    /** @brief Action types */
    public enum ActionType {
        ADD, UPDATE, REMOVE
    }
    
    /** @brief Type of action */
    private ActionType actionType;
    
    /** @brief Material before the action (for undo) */
    private Material materialBefore;
    
    /** @brief Material after the action (for redo) */
    private Material materialAfter;
    
    /**
     * @brief Constructor for ADD action.
     * @param materialAfter The material that was added
     */
    public MaterialAction(ActionType actionType, Material materialBefore, Material materialAfter) {
        this.actionType = actionType;
        this.materialBefore = materialBefore != null ? copyMaterial(materialBefore) : null;
        this.materialAfter = materialAfter != null ? copyMaterial(materialAfter) : null;
    }
    
    /**
     * @brief Gets the action type.
     * @return The action type
     */
    public ActionType getActionType() {
        return actionType;
    }
    
    /**
     * @brief Gets the material before the action.
     * @return The material before the action
     */
    public Material getMaterialBefore() {
        return materialBefore != null ? copyMaterial(materialBefore) : null;
    }
    
    /**
     * @brief Gets the material after the action.
     * @return The material after the action
     */
    public Material getMaterialAfter() {
        return materialAfter != null ? copyMaterial(materialAfter) : null;
    }
    
    /**
     * @brief Creates a deep copy of a material.
     * @param material The material to copy
     * @return A copy of the material
     */
    private Material copyMaterial(Material material) {
        if (material == null) {
            return null;
        }
        return new Material(
            material.getId(),
            material.getName(),
            material.getType(),
            material.getQuantity(),
            material.getUnitCost()
        );
    }
}

