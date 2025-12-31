/**
 * @file ProjectRepository.java
 * @brief This file contains the ProjectRepository class for data access.
 * @details This class provides persistence operations for projects using CSV files.
 *          Uses HashTable for O(1) lookup performance.
 *          Uses B+ Tree for ordered indexing and file indexing.
 *          Uses FileOperations for hash-based file integrity and atomic operations.
 * @package com.hakcay.inventorymanagement.project
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hakcay.inventorymanagement.algorithms.bplustree.BPlusTree;
import com.hakcay.inventorymanagement.algorithms.fileops.FileOperations;
import com.hakcay.inventorymanagement.algorithms.hashtable.HashTable;

/**
 * @class ProjectRepository
 * @brief Repository class for persisting Project objects to a CSV file.
 * @details Handles reading from and writing to projects.csv file.
 *          Uses HashTable for O(1) lookup performance.
 *          Uses B+ Tree for ordered indexing and file indexing.
 *          Uses FileOperations for hash-based file integrity and atomic operations.
 */
public class ProjectRepository {
    /** @brief Default CSV file name for projects */
    private static final String DEFAULT_CSV_FILE = "projects.csv";
    /** @brief CSV field separator */
    private static final String CSV_SEPARATOR = ",";
    /** @brief Placeholder for commas in CSV fields */
    private static final String COMMA_PLACEHOLDER = "|COMMA|";
    /** @brief Path to the CSV file */
    private final String csvFile;
    
    /** @brief HashTable for O(1) lookup by ID */
    private HashTable<Integer, Project> projectMap;
    
    /** @brief B+ Tree for ordered indexing by ID */
    private BPlusTree<Integer, Project> projectIndex;

    /**
     * @brief Default constructor that uses the default CSV file name.
     * @details Initializes HashTable and B+ Tree for indexing.
     */
    public ProjectRepository() {
        this.csvFile = DEFAULT_CSV_FILE;
        this.projectMap = new HashTable<>();
        this.projectIndex = new BPlusTree<>();
    }

    /**
     * @brief Constructor that accepts a custom file path.
     * @param filePath the path to the CSV file
     */
    public ProjectRepository(String filePath) {
        this.csvFile = filePath;
        this.projectMap = new HashTable<>();
        this.projectIndex = new BPlusTree<>();
    }

    /**
     * Loads all projects from the CSV file.
     * Returns an empty list if the file doesn't exist or is empty.
     * Also populates the HashTable for O(1) lookup.
     * Uses FileOperations for safe file reading.
     *
     * @return list of all projects
     */
    public List<Project> loadAll() {
        List<Project> projects = new ArrayList<>();
        projectMap = new HashTable<>(); // Reset HashTable
        projectIndex = new BPlusTree<>(); // Reset B+ Tree
        File file = new File(csvFile);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return projects;
        }

        // Use FileOperations for safe reading
        String content = FileOperations.safeRead(csvFile);
        if (content == null || content.isEmpty()) {
            return projects;
        }

        // Parse content line by line
        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            Project project = parseProject(line);
            if (project != null) {
                projects.add(project);
                projectMap.put(project.getId(), project); // Add to HashTable
                projectIndex.insert(project.getId(), project); // Add to B+ Tree
            }
        }

        return projects;
    }

    /**
     * Saves all projects to the CSV file.
     * Overwrites the existing file with the new data.
     * Also updates the HashTable to keep it in sync.
     * Uses FileOperations for atomic write and hash-based integrity checking.
     *
     * @param projects the list of projects to save
     */
    public void saveAll(List<Project> projects) {
        if (projects == null) {
            projects = new ArrayList<>();
        }

        // Update HashTable and B+ Tree to keep them in sync
        projectMap = new HashTable<>();
        projectIndex = new BPlusTree<>();
        for (Project project : projects) {
            if (project != null) {
                projectMap.put(project.getId(), project);
                projectIndex.insert(project.getId(), project);
            }
        }

        // Build CSV content
        StringBuilder content = new StringBuilder();
        for (Project project : projects) {
            if (project != null) { // Skip null projects
                String line = formatProject(project);
                content.append(line);
                content.append('\n');
            }
        }

        // Remove trailing newline
        String contentStr = content.toString();
        if (contentStr.endsWith("\n")) {
            contentStr = contentStr.substring(0, contentStr.length() - 1);
        }

        // Use FileOperations for atomic write with integrity checking
        FileOperations.safeWrite(csvFile, contentStr);
    }

    /**
     * Finds a project by its ID.
     * Uses HashTable for O(1) lookup performance.
     *
     * @param id The ID of the project to find
     * @return The project with the given ID, or null if not found
     */
    public Project findById(int id) {
        return projectMap.get(id);
    }
    
    /**
     * Gets all projects in ascending ID order using B+ Tree indexing.
     *
     * @return List of projects sorted by ID
     */
    public List<Project> getAllOrdered() {
        return projectIndex.getAllValues();
    }
    
    /**
     * Gets all project entries in ascending ID order using B+ Tree indexing.
     *
     * @return List of project entries sorted by ID
     */
    public List<BPlusTree.Entry<Integer, Project>> getAllEntriesOrdered() {
        return projectIndex.getAllEntries();
    }
    
    /**
     * Searches for a project using B+ Tree (alternative to HashTable lookup).
     *
     * @param id The ID of the project to find
     * @return The project with the given ID, or null if not found
     */
    public Project findByIdUsingIndex(int id) {
        return projectIndex.search(id);
    }

    /**
     * Parses a CSV line into a Project object.
     * Handles commas in fields by replacing placeholder with actual commas.
     *
     * @param line the CSV line
     * @return Project object or null if parsing fails
     */
    private Project parseProject(String line) {
        try {
            String[] parts = line.split(CSV_SEPARATOR, -1);
            if (parts.length != 4) {
                return null;
            }

            int id = Integer.parseInt(parts[0].trim());
            String name = restoreCommas(parts[1].trim());
            String goal = restoreCommas(parts[2].trim());
            String status = restoreCommas(parts[3].trim());

            return new Project(id, name, goal, status);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Formats a Project object into a CSV line.
     * Handles commas in fields by replacing them with a placeholder.
     *
     * @param project the project to format
     * @return CSV formatted string
     */
    private String formatProject(Project project) {
        return project.getId() + CSV_SEPARATOR +
               escapeCommas(project.getName() != null ? project.getName() : "") + CSV_SEPARATOR +
               escapeCommas(project.getGoal() != null ? project.getGoal() : "") + CSV_SEPARATOR +
               escapeCommas(project.getStatus() != null ? project.getStatus() : "");
    }

    /**
     * Escapes commas in a string by replacing them with a placeholder.
     *
     * @param value the string to escape
     * @return escaped string
     */
    private String escapeCommas(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(",", COMMA_PLACEHOLDER);
    }

    /**
     * Restores commas in a string by replacing placeholder with actual commas.
     *
     * @param value the string to restore
     * @return string with restored commas
     */
    private String restoreCommas(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(COMMA_PLACEHOLDER, ",");
    }
}

