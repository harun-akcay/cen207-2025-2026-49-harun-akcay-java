/**
 * @file Project.java
 * @brief This file contains the Project model class for project management.
 * @details This class represents a project in the project tracking system with properties
 *          such as id, name, goal, and status.
 * @package com.hakcay.inventorymanagement.project
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.project;

/**
 * @class Project
 * @brief Represents a project in the project tracking system.
 * @details Contains information about project id, name, goal, and status.
 */
public class Project {
    /** @brief Unique identifier for the project */
    private int id;
    /** @brief Name of the project */
    private String name;
    /** @brief Goal or objective of the project */
    private String goal;
    /** @brief Current status of the project (e.g., PLANNED, IN_PROGRESS, DONE) */
    private String status;

    /**
     * @brief Default constructor.
     * @details Creates an empty Project object.
     */
    public Project() {
    }

    /**
     * @brief Constructor with all fields.
     * @param id the project id
     * @param name the project name
     * @param goal the project goal
     * @param status the project status (e.g. PLANNED, IN_PROGRESS, DONE)
     */
    public Project(int id, String name, String goal, String status) {
        this.id = id;
        this.name = name;
        this.goal = goal;
        this.status = status;
    }

    /**
     * @brief Gets the project id.
     * @return the project id
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Sets the project id.
     * @param id the project id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @brief Gets the project name.
     * @return the project name
     */
    public String getName() {
        return name;
    }

    /**
     * @brief Sets the project name.
     * @param name the project name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @brief Gets the project goal.
     * @return the project goal
     */
    public String getGoal() {
        return goal;
    }

    /**
     * @brief Sets the project goal.
     * @param goal the project goal
     */
    public void setGoal(String goal) {
        this.goal = goal;
    }

    /**
     * @brief Gets the project status.
     * @return the project status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @brief Sets the project status.
     * @param status the project status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @brief Returns a string representation of the project.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", goal='" + goal + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

