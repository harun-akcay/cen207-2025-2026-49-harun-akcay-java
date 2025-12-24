package com.hakcay.inventorymanagement.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for persisting Project objects to a CSV file.
 * Handles reading from and writing to projects.csv file.
 */
public class ProjectRepository {
    private static final String DEFAULT_CSV_FILE = "projects.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final String COMMA_PLACEHOLDER = "|COMMA|";
    private final String csvFile;

    /**
     * Default constructor that uses the default CSV file name.
     */
    public ProjectRepository() {
        this.csvFile = DEFAULT_CSV_FILE;
    }

    /**
     * Constructor that accepts a custom file path.
     *
     * @param filePath the path to the CSV file
     */
    public ProjectRepository(String filePath) {
        this.csvFile = filePath;
    }

    /**
     * Loads all projects from the CSV file.
     * Returns an empty list if the file doesn't exist or is empty.
     *
     * @return list of all projects
     */
    public List<Project> loadAll() {
        List<Project> projects = new ArrayList<>();
        File file = new File(csvFile);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return projects;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                Project project = parseProject(line);
                if (project != null) {
                    projects.add(project);
                }
            }
        } catch (IOException e) {
            // Return empty list on error
            return new ArrayList<>();
        }

        return projects;
    }

    /**
     * Saves all projects to the CSV file.
     * Overwrites the existing file with the new data.
     *
     * @param projects the list of projects to save
     */
    public void saveAll(List<Project> projects) {
        if (projects == null) {
            projects = new ArrayList<>();
        }

        File file = new File(csvFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Project project : projects) {
                String line = formatProject(project);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Silently handle IO errors
            // In a production system, you might want to log this
        }
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

