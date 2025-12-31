/**
 * @file Sale.java
 * @brief This file contains the Sale model class for sales tracking.
 * @details This class represents a sale in the sales tracking system with properties
 *          such as id, material id, quantity, price, date, and customer name.
 * @package com.hakcay.inventorymanagement.sales
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.sales;

/**
 * @class Sale
 * @brief Represents a sale in the sales tracking system.
 * @details Contains information about sale id, material id, quantity, price, date, and customer name.
 */
public class Sale {
    /** @brief Unique identifier for the sale */
    private int id;
    /** @brief Material ID associated with this sale */
    private int materialId;
    /** @brief Quantity of items sold */
    private int quantity;
    /** @brief Price per unit */
    private double price;
    /** @brief Date of the sale */
    private String date;
    /** @brief Name of the customer */
    private String customerName;

    /**
     * @brief Default constructor.
     */
    public Sale() {
    }

    /**
     * @brief Constructor with all fields.
     * @param id the sale id
     * @param materialId the material id associated with this sale
     * @param quantity the quantity sold
     * @param price the sale price per unit
     * @param date the sale date
     * @param customerName the customer name
     */
    public Sale(int id, int materialId, int quantity, double price, String date, String customerName) {
        this.id = id;
        this.materialId = materialId;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.customerName = customerName;
    }

    /**
     * @brief Gets the sale id.
     * @return the sale id
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Sets the sale id.
     * @param id the sale id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @brief Gets the material id.
     * @return the material id
     */
    public int getMaterialId() {
        return materialId;
    }

    /**
     * @brief Sets the material id.
     * @param materialId the material id
     */
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    /**
     * @brief Gets the quantity sold.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @brief Sets the quantity sold.
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @brief Gets the sale price per unit.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @brief Sets the sale price per unit.
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @brief Gets the sale date.
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @brief Sets the sale date.
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @brief Gets the customer name.
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @brief Sets the customer name.
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @brief Calculates the total sale amount (quantity * price).
     * @return the total sale amount
     */
    public double getTotalAmount() {
        return quantity * price;
    }

    /**
     * @brief Returns a string representation of the sale.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", materialId=" + materialId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", customerName='" + customerName + '\'' +
                ", totalAmount=" + getTotalAmount() +
                '}';
    }
}

