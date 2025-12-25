/**
 * @file Material.java
 * @brief This file contains the Material model class for inventory management.
 * @details This class represents a material item in the inventory with properties
 *          such as id, name, type, quantity, and unit cost.
 * @package com.hakcay.inventorymanagement.material
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.material;

/**
 * @class Material
 * @brief Represents a material item in the inventory.
 * @details The Material class stores information about a material including
 *          its unique identifier, name, type, quantity in stock, and unit cost.
 */
public class Material {
    
    /** @brief Unique identifier for the material */
    private int id;
    
    /** @brief Name of the material */
    private String name;
    
    /** @brief Type or category of the material */
    private String type;
    
    /** @brief Quantity of the material in stock */
    private int quantity;
    
    /** @brief Unit cost of the material */
    private double unitCost;
    
    /**
     * @brief Default constructor.
     * @details Creates an empty Material object.
     */
    public Material() {
        this.id = 0;
        this.name = "";
        this.type = "";
        this.quantity = 0;
        this.unitCost = 0.0;
    }
    
    /**
     * @brief Constructor with parameters.
     * @param id Unique identifier for the material
     * @param name Name of the material
     * @param type Type or category of the material
     * @param quantity Quantity of the material in stock
     * @param unitCost Unit cost of the material
     */
    public Material(int id, String name, String type, int quantity, double unitCost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }
    
    /**
     * @brief Gets the material ID.
     * @return The unique identifier of the material
     */
    public int getId() {
        return id;
    }
    
    /**
     * @brief Sets the material ID.
     * @param id The unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @brief Gets the material name.
     * @return The name of the material
     */
    public String getName() {
        return name;
    }
    
    /**
     * @brief Sets the material name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @brief Gets the material type.
     * @return The type of the material
     */
    public String getType() {
        return type;
    }
    
    /**
     * @brief Sets the material type.
     * @param type The type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @brief Gets the material quantity.
     * @return The quantity in stock
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * @brief Sets the material quantity.
     * @param quantity The quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @brief Gets the unit cost.
     * @return The unit cost of the material
     */
    public double getUnitCost() {
        return unitCost;
    }
    
    /**
     * @brief Sets the unit cost.
     * @param unitCost The unit cost to set
     */
    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }
    
    /**
     * @brief Returns a string representation of the Material.
     * @return String containing material information
     */
    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", unitCost=" + unitCost +
                '}';
    }
}

