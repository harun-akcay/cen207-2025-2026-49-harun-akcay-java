/**
 * @file MaterialService.java
 * @brief This file contains the MaterialService class for business logic.
 * @details This class provides business logic operations for managing materials.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

import java.util.List;

/**
 * @class MaterialService
 * @brief Service class for managing material operations.
 * @details Provides business logic for material management including
 *          adding, updating, retrieving, and removing materials.
 */
public class MaterialService {
    
    /** @brief Repository for material data access */
    private MaterialRepository repository;
    
    /**
     * @brief Default constructor.
     * @details Creates a new MaterialService with a default MaterialRepository.
     */
    public MaterialService() {
        this.repository = new MaterialRepository();
    }
    
    /**
     * @brief Constructor with repository parameter.
     * @param repository The MaterialRepository to use
     */
    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
    }
    
    /**
     * @brief Adds a material to the inventory.
     * @param material The material to add
     */
    public void addMaterial(Material material) {
        if (material != null) {
            repository.add(material);
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
            repository.update(material);
        }
    }
    
    /**
     * @brief Removes a material from the inventory by ID.
     * @param id The ID of the material to remove
     * @return true if the material was found and removed, false otherwise
     */
    public boolean removeMaterialById(int id) {
        return repository.remove(id);
    }
    
    /**
     * @brief Gets a material by its ID.
     * @param id The ID of the material to find
     * @return The material with the given ID, or null if not found
     */
    public Material getMaterialById(int id) {
        return repository.findById(id);
    }
}

