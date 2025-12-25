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

/**
 * @class MaterialRepository
 * @brief Repository class for managing material data storage.
 * @details Provides in-memory storage and CRUD operations for materials.
 */
public class MaterialRepository {
    
    /** @brief In-memory list to store materials */
    private List<Material> materials;
    
    /**
     * @brief Default constructor.
     * @details Initializes an empty list of materials.
     */
    public MaterialRepository() {
        this.materials = new ArrayList<>();
    }
    
    /**
     * @brief Adds a material to the repository.
     * @param material The material to add
     */
    public void add(Material material) {
        if (material != null) {
            materials.add(material);
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
     * @param id The ID of the material to find
     * @return The material with the given ID, or null if not found
     */
    public Material findById(int id) {
        for (Material material : materials) {
            if (material.getId() == id) {
                return material;
            }
        }
        return null;
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
        Material material = findById(id);
        if (material != null) {
            return materials.remove(material);
        }
        return false;
    }
}

