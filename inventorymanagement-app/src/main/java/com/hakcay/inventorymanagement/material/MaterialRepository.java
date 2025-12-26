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

/**
 * @class MaterialRepository
 * @brief Repository class for managing material data storage.
 * @details Provides in-memory storage and CRUD operations for materials.
 *          Uses HashTable for O(1) lookup performance.
 */
public class MaterialRepository {
    
    /** @brief In-memory list to store materials (for getAll() method) */
    private List<Material> materials;
    
    /** @brief HashTable for O(1) lookup by ID */
    private HashTable<Integer, Material> materialMap;
    
    /**
     * @brief Default constructor.
     * @details Initializes an empty list and hash table for materials.
     */
    public MaterialRepository() {
        this.materials = new ArrayList<>();
        this.materialMap = new HashTable<>();
    }
    
    /**
     * @brief Adds a material to the repository.
     * @param material The material to add
     */
    public void add(Material material) {
        if (material != null) {
            materials.add(material);
            materialMap.put(material.getId(), material);
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
     * @param id The ID of the material to remove
     * @return true if the material was found and removed, false otherwise
     */
    public boolean remove(int id) {
        Material material = materialMap.get(id);
        if (material != null) {
            materialMap.remove(id);
            return materials.remove(material);
        }
        return false;
    }
}

