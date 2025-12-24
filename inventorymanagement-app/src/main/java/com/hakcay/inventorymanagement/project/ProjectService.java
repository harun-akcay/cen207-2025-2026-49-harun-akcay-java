package com.hakcay.inventorymanagement.project;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing Project operations.
 * Provides business logic for adding, updating, removing, and retrieving projects.
 */
public class ProjectService {
    private ProjectRepository repository;
    private List<Project> projects;

    /**
     * Constructor that initializes the repository and loads existing projects.
     */
    public ProjectService() {
        this.repository = new ProjectRepository();
        this.projects = new ArrayList<>(repository.loadAll());
    }

    /**
     * Constructor that accepts a repository (for testing purposes).
     *
     * @param repository the project repository to use
     */
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
        this.projects = new ArrayList<>(repository.loadAll());
    }

    /**
     * Adds a new project to the tracking system.
     * Enforces unique id constraint - throws IllegalArgumentException if id already exists.
     *
     * @param project the project to add
     * @throws IllegalArgumentException if project id already exists
     */
    public void addProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        // Check for duplicate id
        for (Project existing : projects) {
            if (existing.getId() == project.getId()) {
                throw new IllegalArgumentException("Project with id " + project.getId() + " already exists");
            }
        }

        projects.add(project);
        repository.saveAll(projects);
    }

    /**
     * Updates an existing project in the tracking system.
     * Throws IllegalArgumentException if project with the given id is not found.
     *
     * @param project the project to update
     * @throws IllegalArgumentException if project id not found
     */
    public void updateProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }

        boolean found = false;
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getId() == project.getId()) {
                projects.set(i, project);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Project with id " + project.getId() + " not found");
        }

        repository.saveAll(projects);
    }

    /**
     * Removes a project from the tracking system by id.
     *
     * @param id the id of the project to remove
     * @return true if project was removed, false if not found
     */
    public boolean removeProjectById(int id) {
        boolean removed = projects.removeIf(project -> project.getId() == id);
        if (removed) {
            repository.saveAll(projects);
        }
        return removed;
    }

    /**
     * Gets all projects in the tracking system.
     *
     * @return list of all projects
     */
    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
    }
}

