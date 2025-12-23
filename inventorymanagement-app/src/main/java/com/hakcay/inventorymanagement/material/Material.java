package com.hakcay.inventorymanagement.material;

/**
 * Represents a material in the inventory system.
 * Contains information about material id, name, type, quantity, and unit cost.
 */
public class Material {
    private int id;
    private String name;
    private String type;
    private int quantity;
    private double unitCost;

    /**
     * Default constructor.
     */
    public Material() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id the material id
     * @param name the material name
     * @param type the material type
     * @param quantity the quantity in stock
     * @param unitCost the cost per unit
     */
    public Material(int id, String name, String type, int quantity, double unitCost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    /**
     * Gets the material id.
     *
     * @return the material id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the material id.
     *
     * @param id the material id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the material name.
     *
     * @return the material name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the material name.
     *
     * @param name the material name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the material type.
     *
     * @return the material type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the material type.
     *
     * @param type the material type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the quantity in stock.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity in stock.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the unit cost.
     *
     * @return the unit cost
     */
    public double getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the unit cost.
     *
     * @param unitCost the unit cost
     */
    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    /**
     * Returns a string representation of the material.
     *
     * @return string representation
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

