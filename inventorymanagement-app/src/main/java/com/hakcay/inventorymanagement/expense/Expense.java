package com.hakcay.inventorymanagement.expense;

/**
 * Represents an expense in the expense logging system.
 * Contains information about expense id, material id, project id, amount, and description.
 */
public class Expense {
    private int id;
    private int materialId;
    private int projectId;
    private double amount;
    private String description;

    /**
     * Default constructor.
     */
    public Expense() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id the expense id
     * @param materialId the material id associated with this expense
     * @param projectId the project id associated with this expense
     * @param amount the expense amount
     * @param description the expense description
     */
    public Expense(int id, int materialId, int projectId, double amount, String description) {
        this.id = id;
        this.materialId = materialId;
        this.projectId = projectId;
        this.amount = amount;
        this.description = description;
    }

    /**
     * Gets the expense id.
     *
     * @return the expense id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the expense id.
     *
     * @param id the expense id
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
     * Gets the project id.
     *
     * @return the project id
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the expense amount.
     *
     * @return the expense amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the expense amount.
     *
     * @param amount the expense amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the expense description.
     *
     * @return the expense description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the expense description.
     *
     * @param description the expense description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the expense.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", materialId=" + materialId +
                ", projectId=" + projectId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}

