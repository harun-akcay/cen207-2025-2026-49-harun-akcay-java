/**
 * @file MaterialService.java
 * @brief This file contains the MaterialService class for business logic.
 * @details This class provides business logic operations for managing materials.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

import java.util.ArrayList;
import java.util.List;

import com.hakcay.inventorymanagement.algorithms.kmp.KMPAlgorithm;
import com.hakcay.inventorymanagement.algorithms.stackqueue.Stack;

/**
 * @class MaterialService
 * @brief Service class for managing material operations.
 * @details Provides business logic for material management including
 *          adding, updating, retrieving, and removing materials.
 *          Supports undo/redo operations using Stack.
 */
public class MaterialService {
    
    /** @brief Repository for material data access */
    private MaterialRepository repository;
    
    /** @brief Stack for undo operations */
    private Stack<MaterialAction> undoStack;
    
    /** @brief Stack for redo operations */
    private Stack<MaterialAction> redoStack;
    
    /**
     * @brief Default constructor.
     * @details Creates a new MaterialService with a default MaterialRepository.
     */
    public MaterialService() {
        this.repository = new MaterialRepository();
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }
    
    /**
     * @brief Constructor with repository parameter.
     * @param repository The MaterialRepository to use
     */
    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }
    
    /**
     * @brief Adds a material to the inventory.
     * @param material The material to add
     */
    public void addMaterial(Material material) {
        if (material != null) {
            MaterialAction action = new MaterialAction(
                MaterialAction.ActionType.ADD,
                null, // No material before add
                material
            );
            repository.add(material);
            undoStack.push(action);
            redoStack.clear(); // Clear redo stack when new action is performed
        }
    }
    
    /**
     * @brief Gets all materials from the inventory.
     * @return List of all materials
     */
    public List<Material> getAllMaterials() {
        return repository.getAll();
    }
    
    /**
     * @brief Updates a material in the inventory.
     * @param material The material with updated information
     */
    public void updateMaterial(Material material) {
        if (material != null) {
            Material existing = repository.findById(material.getId());
            MaterialAction action = new MaterialAction(
                MaterialAction.ActionType.UPDATE,
                existing, // Material before update
                material  // Material after update
            );
            repository.update(material);
            undoStack.push(action);
            redoStack.clear(); // Clear redo stack when new action is performed
        }
    }
    
    /**
     * @brief Removes a material from the inventory by ID.
     * @param id The ID of the material to remove
     * @return true if the material was found and removed, false otherwise
     */
    public boolean removeMaterialById(int id) {
        Material existing = repository.findById(id);
        if (existing != null) {
            MaterialAction action = new MaterialAction(
                MaterialAction.ActionType.REMOVE,
                existing, // Material before remove
                null      // No material after remove
            );
            boolean removed = repository.remove(id);
            if (removed) {
                undoStack.push(action);
                redoStack.clear(); // Clear redo stack when new action is performed
            }
            return removed;
        }
        return false;
    }
    
    /**
     * @brief Gets a material by its ID.
     * @param id The ID of the material to find
     * @return The material with the given ID, or null if not found
     */
    public Material getMaterialById(int id) {
        return repository.findById(id);
    }
    
    /**
     * @brief Undoes the last action performed.
     * @return true if an action was undone, false if there are no actions to undo
     */
    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        
        MaterialAction action = undoStack.pop();
        
        switch (action.getActionType()) {
            case ADD:
                // Undo add: remove the material
                repository.remove(action.getMaterialAfter().getId());
                break;
            case UPDATE:
                // Undo update: restore previous state
                repository.update(action.getMaterialBefore());
                break;
            case REMOVE:
                // Undo remove: add the material back
                repository.add(action.getMaterialBefore());
                break;
        }
        
        // Push to redo stack
        redoStack.push(action);
        return true;
    }
    
    /**
     * @brief Redoes the last undone action.
     * @return true if an action was redone, false if there are no actions to redo
     */
    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        
        MaterialAction action = redoStack.pop();
        
        switch (action.getActionType()) {
            case ADD:
                // Redo add: add the material back
                repository.add(action.getMaterialAfter());
                break;
            case UPDATE:
                // Redo update: apply the update again
                repository.update(action.getMaterialAfter());
                break;
            case REMOVE:
                // Redo remove: remove the material again
                repository.remove(action.getMaterialBefore().getId());
                break;
        }
        
        // Push back to undo stack
        undoStack.push(action);
        return true;
    }
    
    /**
     * @brief Checks if there are actions available to undo.
     * @return true if undo is available, false otherwise
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    /**
     * @brief Checks if there are actions available to redo.
     * @return true if redo is available, false otherwise
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
    
    /**
     * @brief Searches for materials by name using KMP algorithm.
     * @param pattern The search pattern
     * @return List of materials whose names contain the pattern
     */
    public List<Material> searchMaterialsByName(String pattern) {
        List<Material> results = new ArrayList<>();
        if (pattern == null || pattern.isEmpty()) {
            return results;
        }
        
        List<Material> allMaterials = repository.getAll();
        for (Material material : allMaterials) {
            if (material != null && material.getName() != null) {
                if (KMPAlgorithm.containsIgnoreCase(material.getName(), pattern)) {
                    results.add(material);
                }
            }
        }
        return results;
    }
    
    /**
     * @brief Searches for materials by type using KMP algorithm.
     * @param pattern The search pattern
     * @return List of materials whose types contain the pattern
     */
    public List<Material> searchMaterialsByType(String pattern) {
        List<Material> results = new ArrayList<>();
        if (pattern == null || pattern.isEmpty()) {
            return results;
        }
        
        List<Material> allMaterials = repository.getAll();
        for (Material material : allMaterials) {
            if (material != null && material.getType() != null) {
                if (KMPAlgorithm.containsIgnoreCase(material.getType(), pattern)) {
                    results.add(material);
                }
            }
        }
        return results;
    }
    
    /**
     * @brief Searches for materials by name or type using KMP algorithm.
     * @param pattern The search pattern
     * @return List of materials whose names or types contain the pattern
     */
    public List<Material> searchMaterials(String pattern) {
        List<Material> results = new ArrayList<>();
        if (pattern == null || pattern.isEmpty()) {
            return results;
        }
        
        List<Material> allMaterials = repository.getAll();
        for (Material material : allMaterials) {
            if (material != null) {
                boolean matches = false;
                if (material.getName() != null) {
                    matches = KMPAlgorithm.containsIgnoreCase(material.getName(), pattern);
                }
                if (!matches && material.getType() != null) {
                    matches = KMPAlgorithm.containsIgnoreCase(material.getType(), pattern);
                }
                if (matches) {
                    results.add(material);
                }
            }
        }
        return results;
    }
}

