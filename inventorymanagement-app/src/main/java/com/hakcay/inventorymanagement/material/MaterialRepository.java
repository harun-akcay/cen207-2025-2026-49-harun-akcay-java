/**
 * @file MaterialRepository.java
 * @brief This file contains the MaterialRepository class for data access.
 * @details This class provides in-memory storage and retrieval operations for materials.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

import java.util.ArrayList;
import java.util.List;

import com.hakcay.inventorymanagement.algorithms.hashtable.HashTable;
import com.hakcay.inventorymanagement.algorithms.doublylinkedlist.DoubleLinkedList;

/**
 * @class MaterialRepository
 * @brief Repository class for managing material data storage.
 * @details Provides in-memory storage and CRUD operations for materials.
 *          Uses HashTable for O(1) lookup performance.
 *          Uses DoubleLinkedList for material history tracking.
 */
public class MaterialRepository {
    
    /** @brief In-memory list to store materials (for getAll() method) */
    private List<Material> materials;
    
    /** @brief HashTable for O(1) lookup by ID */
    private HashTable<Integer, Material> materialMap;
    
    /** @brief DoubleLinkedList for material history tracking (add/remove operations) */
    private DoubleLinkedList<Material> materialHistory;
    
    /**
     * @brief Default constructor.
     * @details Initializes an empty list, hash table, and history for materials.
     */
    public MaterialRepository() {
        this.materials = new ArrayList<>();
        this.materialMap = new HashTable<>();
        this.materialHistory = new DoubleLinkedList<>();
    }
    
    /**
     * @brief Adds a material to the repository.
     * @details Also adds the material to history for tracking.
     * @param material The material to add
     */
    public void add(Material material) {
        if (material != null) {
            materials.add(material);
            materialMap.put(material.getId(), material);
            // Create a copy for history to avoid reference issues
            Material historyCopy = new Material(
                material.getId(),
                material.getName(),
                material.getType(),
                material.getQuantity(),
                material.getUnitCost()
            );
            materialHistory.add(historyCopy);
        }
    }
    
    /**
     * @brief Gets all materials from the repository.
     * @return List of all materials
     */
    public List<Material> getAll() {
        return new ArrayList<>(materials);
    }
    
    /**
     * @brief Finds a material by its ID.
     * @details Uses HashTable for O(1) lookup performance.
     * @param id The ID of the material to find
     * @return The material with the given ID, or null if not found
     */
    public Material findById(int id) {
        return materialMap.get(id);
    }
    
    /**
     * @brief Updates a material in the repository.
     * @param material The material with updated information
     * @return true if the material was found and updated, false otherwise
     */
    public boolean update(Material material) {
        if (material == null) {
            return false;
        }
        
        Material existing = findById(material.getId());
        if (existing != null) {
            existing.setName(material.getName());
            existing.setType(material.getType());
            existing.setQuantity(material.getQuantity());
            existing.setUnitCost(material.getUnitCost());
            return true;
        }
        return false;
    }
    
    /**
     * @brief Removes a material from the repository by ID.
     * @details Also adds the removed material to history for tracking.
     * @param id The ID of the material to remove
     * @return true if the material was found and removed, false otherwise
     */
    public boolean remove(int id) {
        Material material = materialMap.get(id);
        if (material != null) {
            materialMap.remove(id);
            boolean removed = materials.remove(material);
            if (removed) {
                // Create a copy for history to track removal
                Material historyCopy = new Material(
                    material.getId(),
                    material.getName(),
                    material.getType(),
                    material.getQuantity(),
                    material.getUnitCost()
                );
                materialHistory.add(historyCopy);
            }
            return removed;
        }
        return false;
    }
    
    /**
     * @brief Gets the material history (all add/remove operations).
     * @details Returns a list of materials in the order they were added or removed.
     * @return List of materials in chronological order
     */
    public List<Material> getHistory() {
        return materialHistory.toList();
    }
    
    /**
     * @brief Gets the material history in reverse order (most recent first).
     * @return List of materials in reverse chronological order
     */
    public List<Material> getHistoryReverse() {
        return materialHistory.toReverseList();
    }
    
    /**
     * @brief Clears the material history.
     */
    public void clearHistory() {
        materialHistory.clear();
    }
    
    /**
     * @brief Gets the number of operations in history.
     * @return The size of the history
     */
    public int getHistorySize() {
        return materialHistory.size();
    }
}

