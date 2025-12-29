package com.hakcay.inventorymanagement.sales;

/**
 * Represents a sale in the sales tracking system.
 * Contains information about sale id, material id, quantity, price, date, and customer name.
 */
public class Sale {
    private int id;
    private int materialId;
    private int quantity;
    private double price;
    private String date;
    private String customerName;

    /**
     * Default constructor.
     */
    public Sale() {
    }

    /**
     * Constructor with all fields.
     *
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
     * Gets the sale id.
     *
     * @return the sale id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the sale id.
     *
     * @param id the sale id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the material id.
     *
     * @return the material id
     */
    public int getMaterialId() {
        return materialId;
    }

    /**
     * Sets the material id.
     *
     * @param materialId the material id
     */
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    /**
     * Gets the quantity sold.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity sold.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the sale price per unit.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the sale price per unit.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the sale date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the sale date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Calculates the total sale amount (quantity * price).
     *
     * @return the total sale amount
     */
    public double getTotalAmount() {
        return quantity * price;
    }

    /**
     * Returns a string representation of the sale.
     *
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

