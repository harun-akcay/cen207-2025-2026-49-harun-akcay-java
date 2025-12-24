package com.hakcay.inventorymanagement.project;

/**
 * Represents a project in the project tracking system.
 * Contains information about project id, name, goal, and status.
 */
public class Project {
    private int id;
    private String name;
    private String goal;
    private String status;

    /**
     * Default constructor.
     */
    public Project() {
    }

    /**
     * Constructor with all fields.
     *
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
     * Gets the project id.
     *
     * @return the project id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the project id.
     *
     * @param id the project id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the project name.
     *
     * @return the project name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the project name.
     *
     * @param name the project name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the project goal.
     *
     * @return the project goal
     */
    public String getGoal() {
        return goal;
    }

    /**
     * Sets the project goal.
     *
     * @param goal the project goal
     */
    public void setGoal(String goal) {
        this.goal = goal;
    }

    /**
     * Gets the project status.
     *
     * @return the project status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the project status.
     *
     * @param status the project status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the project.
     *
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

