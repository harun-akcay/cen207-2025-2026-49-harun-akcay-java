/**
 * @file ProjectService.java
 * @brief This file contains the ProjectService class for business logic.
 * @details This class provides business logic for managing projects including
 *          adding, updating, removing, and retrieving projects.
 *          Includes BFS/DFS integration for project dependency graph traversal.
 * @package com.hakcay.inventorymanagement.project
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.project;

import java.util.ArrayList;
import java.util.List;

import com.hakcay.inventorymanagement.algorithms.graph.BFS;
import com.hakcay.inventorymanagement.algorithms.graph.DFS;
import com.hakcay.inventorymanagement.algorithms.graph.Graph;
import com.hakcay.inventorymanagement.algorithms.graph.StronglyConnectedComponents;
import com.hakcay.inventorymanagement.algorithms.kmp.KMPAlgorithm;

/**
 * @class ProjectService
 * @brief Service class for managing Project operations.
 * @details Provides business logic for adding, updating, removing, and retrieving projects.
 *          Includes BFS/DFS integration for project dependency graph traversal.
 */
public class ProjectService {
    private ProjectRepository repository;
    private List<Project> projects;
    /** @brief Graph for tracking project dependencies (directed graph) */
    private Graph<Integer> dependencyGraph;

    /**
     * @brief Constructor that initializes the repository and loads existing projects.
     * @details Creates a new ProjectService instance and rebuilds the dependency graph.
     */
    public ProjectService() {
        this.repository = new ProjectRepository();
        this.projects = new ArrayList<>(repository.loadAll());
        this.dependencyGraph = new Graph<>(true); // Directed graph for dependencies
        rebuildDependencyGraph();
    }

    /**
     * @brief Constructor that accepts a repository (for testing purposes).
     * @param repository the project repository to use
     */
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
        this.projects = new ArrayList<>(repository.loadAll());
        this.dependencyGraph = new Graph<>(true); // Directed graph for dependencies
        rebuildDependencyGraph();
    }
    
    /**
     * @brief Rebuilds the dependency graph from current projects.
     * @details This is called after projects are loaded or modified.
     */
    private void rebuildDependencyGraph() {
        dependencyGraph.clear();
        for (Project project : projects) {
            dependencyGraph.addVertex(project.getId());
        }
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
        dependencyGraph.addVertex(project.getId());
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
            // Remove all edges connected to this vertex
            dependencyGraph.clear();
            rebuildDependencyGraph();
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
    
    /**
     * Adds a dependency between two projects.
     * Project 'from' depends on project 'to'.
     *
     * @param fromId The ID of the project that depends on another
     * @param toId The ID of the project that is depended upon
     * @throws IllegalArgumentException if either project ID doesn't exist
     */
    public void addDependency(int fromId, int toId) {
        Project from = findProjectById(fromId);
        Project to = findProjectById(toId);
        
        if (from == null) {
            throw new IllegalArgumentException("Project with id " + fromId + " not found");
        }
        if (to == null) {
            throw new IllegalArgumentException("Project with id " + toId + " not found");
        }
        
        dependencyGraph.addEdge(fromId, toId);
    }
    
    /**
     * Removes a dependency between two projects.
     *
     * @param fromId The ID of the project that depends on another
     * @param toId The ID of the project that is depended upon
     */
    public void removeDependency(int fromId, int toId) {
        dependencyGraph.removeEdge(fromId, toId);
    }
    
    /**
     * Gets all projects that a given project depends on (direct and indirect) using BFS.
     *
     * @param projectId The ID of the project
     * @return List of project IDs that the given project depends on
     */
    public List<Integer> getDependenciesBFS(int projectId) {
        if (findProjectById(projectId) == null) {
            return new ArrayList<>();
        }
        List<Integer> dependencyIds = BFS.traverse(dependencyGraph, projectId);
        // Remove the starting project itself
        dependencyIds.remove(0);
        return dependencyIds;
    }
    
    /**
     * Gets all projects that a given project depends on (direct and indirect) using DFS.
     *
     * @param projectId The ID of the project
     * @return List of project IDs that the given project depends on
     */
    public List<Integer> getDependenciesDFS(int projectId) {
        if (findProjectById(projectId) == null) {
            return new ArrayList<>();
        }
        List<Integer> dependencyIds = DFS.traverse(dependencyGraph, projectId);
        // Remove the starting project itself
        dependencyIds.remove(0);
        return dependencyIds;
    }
    
    /**
     * Gets all projects that depend on a given project using BFS (reverse traversal).
     *
     * @param projectId The ID of the project
     * @return List of project IDs that depend on the given project
     */
    public List<Integer> getDependentsBFS(int projectId) {
        if (findProjectById(projectId) == null) {
            return new ArrayList<>();
        }
        // Create reverse graph for finding dependents
        Graph<Integer> reverseGraph = new Graph<>(true);
        for (Integer vertex : dependencyGraph.getVertices()) {
            reverseGraph.addVertex(vertex);
        }
        for (Integer from : dependencyGraph.getVertices()) {
            for (Integer to : dependencyGraph.getNeighbors(from)) {
                reverseGraph.addEdge(to, from); // Reverse the edge
            }
        }
        List<Integer> dependentIds = BFS.traverse(reverseGraph, projectId);
        // Remove the starting project itself
        dependentIds.remove(0);
        return dependentIds;
    }
    
    /**
     * Checks if there is a dependency path from one project to another.
     *
     * @param fromId The ID of the source project
     * @param toId The ID of the target project
     * @return true if a path exists, false otherwise
     */
    public boolean hasDependencyPath(int fromId, int toId) {
        return BFS.hasPath(dependencyGraph, fromId, toId);
    }
    
    /**
     * Finds the dependency path from one project to another.
     *
     * @param fromId The ID of the source project
     * @param toId The ID of the target project
     * @return List of project IDs representing the path, or empty list if no path exists
     */
    public List<Integer> findDependencyPath(int fromId, int toId) {
        return BFS.findPath(dependencyGraph, fromId, toId);
    }
    
    /**
     * Checks if the dependency graph has any cycles.
     *
     * @return true if cycles exist, false otherwise
     */
    public boolean hasDependencyCycle() {
        return StronglyConnectedComponents.hasCycle(dependencyGraph);
    }
    
    /**
     * Finds all cycles in the dependency graph.
     *
     * @return List of cycles, where each cycle is a list of project IDs
     */
    public List<List<Integer>> findDependencyCycles() {
        return StronglyConnectedComponents.findCycles(dependencyGraph);
    }
    
    /**
     * Checks if a specific project is part of a dependency cycle.
     *
     * @param projectId The project ID to check
     * @return true if project is in a cycle, false otherwise
     */
    public boolean isProjectInCycle(int projectId) {
        if (findProjectById(projectId) == null) {
            return false;
        }
        return StronglyConnectedComponents.isInCycle(dependencyGraph, projectId);
    }
    
    /**
     * Adds a dependency between two projects with cycle detection.
     * Throws IllegalArgumentException if adding the dependency would create a cycle.
     *
     * @param fromId The ID of the project that depends on another
     * @param toId The ID of the project that is depended upon
     * @throws IllegalArgumentException if either project ID doesn't exist or if adding dependency would create a cycle
     */
    public void addDependencySafe(int fromId, int toId) {
        Project from = findProjectById(fromId);
        Project to = findProjectById(toId);
        
        if (from == null) {
            throw new IllegalArgumentException("Project with id " + fromId + " not found");
        }
        if (to == null) {
            throw new IllegalArgumentException("Project with id " + toId + " not found");
        }
        
        // Temporarily add the edge to check for cycles
        dependencyGraph.addEdge(fromId, toId);
        
        // Check if this creates a cycle
        if (StronglyConnectedComponents.hasCycle(dependencyGraph)) {
            // Remove the edge if it creates a cycle
            dependencyGraph.removeEdge(fromId, toId);
            throw new IllegalArgumentException("Adding dependency would create a cycle");
        }
    }
    
    /**
     * Searches for projects by name using KMP algorithm.
     *
     * @param pattern The search pattern
     * @return List of projects whose names contain the pattern
     */
    public List<Project> searchProjectsByName(String pattern) {
        List<Project> results = new ArrayList<>();
        if (pattern == null || pattern.isEmpty()) {
            return results;
        }
        
        for (Project project : projects) {
            if (project != null && project.getName() != null) {
                if (KMPAlgorithm.containsIgnoreCase(project.getName(), pattern)) {
                    results.add(project);
                }
            }
        }
        return results;
    }
    
    /**
     * Searches for projects by goal using KMP algorithm.
     *
     * @param pattern The search pattern
     * @return List of projects whose goals contain the pattern
     */
    public List<Project> searchProjectsByGoal(String pattern) {
        List<Project> results = new ArrayList<>();
        if (pattern == null || pattern.isEmpty()) {
            return results;
        }
        
        for (Project project : projects) {
            if (project != null && project.getGoal() != null) {
                if (KMPAlgorithm.containsIgnoreCase(project.getGoal(), pattern)) {
                    results.add(project);
                }
            }
        }
        return results;
    }
    
    /**
     * Searches for projects by name or goal using KMP algorithm.
     *
     * @param pattern The search pattern
     * @return List of projects whose names or goals contain the pattern
     */
    public List<Project> searchProjects(String pattern) {
        List<Project> results = new ArrayList<>();
        if (pattern == null || pattern.isEmpty()) {
            return results;
        }
        
        for (Project project : projects) {
            if (project != null) {
                boolean matches = false;
                if (project.getName() != null) {
                    matches = KMPAlgorithm.containsIgnoreCase(project.getName(), pattern);
                }
                if (!matches && project.getGoal() != null) {
                    matches = KMPAlgorithm.containsIgnoreCase(project.getGoal(), pattern);
                }
                if (matches) {
                    results.add(project);
                }
            }
        }
        return results;
    }
    
    /**
     * @brief Finds a project by its ID.
     * @param id the project ID
     * @return the project if found, null otherwise
     */
    public Project findProjectById(int id) {
        for (Project project : projects) {
            if (project.getId() == id) {
                return project;
            }
        }
        return null;
    }
}

