package com.hakcay.inventorymanagement.material;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Material operations.
 * Provides business logic for adding, updating, removing, and retrieving materials.
 */
public class MaterialService {
    private MaterialRepository repository;
    private List<Material> materials;

    /**
     * Constructor that initializes the repository and loads existing materials.
     */
    public MaterialService() {
        this.repository = new MaterialRepository();
        this.materials = new ArrayList<>(repository.loadAll());
    }

    /**
     * Constructor that accepts a repository (for testing purposes).
     *
     * @param repository the material repository to use
     */
    public MaterialService(MaterialRepository repository) {
        this.repository = repository;
        this.materials = new ArrayList<>(repository.loadAll());
    }

    /**
     * Adds a new material to the inventory.
     * Enforces unique id constraint - throws IllegalArgumentException if id already exists.
     *
     * @param material the material to add
     * @throws IllegalArgumentException if material id already exists
     */
    public void addMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }

        // Check for duplicate id
        for (Material existing : materials) {
            if (existing.getId() == material.getId()) {
                throw new IllegalArgumentException("Material with id " + material.getId() + " already exists");
            }
        }

        materials.add(material);
        repository.saveAll(materials);
    }

    /**
     * Updates an existing material in the inventory.
     * Throws IllegalArgumentException if material with the given id is not found.
     *
     * @param material the material to update
     * @throws IllegalArgumentException if material id not found
     */
    public void updateMaterial(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }

        boolean found = false;
        for (int i = 0; i < materials.size(); i++) {
            if (materials.get(i).getId() == material.getId()) {
                materials.set(i, material);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Material with id " + material.getId() + " not found");
        }

        repository.saveAll(materials);
    }

    /**
     * Removes a material from the inventory by id.
     *
     * @param id the id of the material to remove
     * @return true if material was removed, false if not found
     */
    public boolean removeMaterialById(int id) {
        boolean removed = materials.removeIf(material -> material.getId() == id);
        if (removed) {
            repository.saveAll(materials);
        }
        return removed;
    }

    /**
     * Gets all materials in the inventory.
     *
     * @return list of all materials
     */
    public List<Material> getAllMaterials() {
        return new ArrayList<>(materials);
    }
}

