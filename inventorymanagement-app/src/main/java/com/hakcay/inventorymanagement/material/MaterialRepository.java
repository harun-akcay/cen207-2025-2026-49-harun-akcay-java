package com.hakcay.inventorymanagement.material;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for persisting Material objects to a CSV file.
 * Handles reading from and writing to materials.csv file.
 */
public class MaterialRepository {
    private static final String DEFAULT_CSV_FILE = "materials.csv";
    private static final String CSV_SEPARATOR = ",";
    private final String csvFile;

    /**
     * Default constructor that uses the default CSV file name.
     */
    public MaterialRepository() {
        this.csvFile = DEFAULT_CSV_FILE;
    }

    /**
     * Constructor that accepts a custom file path.
     *
     * @param filePath the path to the CSV file
     */
    public MaterialRepository(String filePath) {
        this.csvFile = filePath;
    }

    /**
     * Loads all materials from the CSV file.
     * Returns an empty list if the file doesn't exist or is empty.
     *
     * @return list of all materials
     */
    public List<Material> loadAll() {
        List<Material> materials = new ArrayList<>();
        File file = new File(csvFile);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return materials;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                Material material = parseMaterial(line);
                if (material != null) {
                    materials.add(material);
                }
            }
        } catch (IOException e) {
            // Return empty list on error
            return new ArrayList<>();
        }

        return materials;
    }

    /**
     * Saves all materials to the CSV file.
     * Overwrites the existing file with the new data.
     *
     * @param materials the list of materials to save
     */
    public void saveAll(List<Material> materials) {
        if (materials == null) {
            materials = new ArrayList<>();
        }

        File file = new File(csvFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Material material : materials) {
                String line = formatMaterial(material);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Silently handle IO errors
            // In a production system, you might want to log this
        }
    }

    /**
     * Parses a CSV line into a Material object.
     *
     * @param line the CSV line
     * @return Material object or null if parsing fails
     */
    private Material parseMaterial(String line) {
        try {
            String[] parts = line.split(CSV_SEPARATOR, -1);
            if (parts.length != 5) {
                return null;
            }

            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String type = parts[2].trim();
            int quantity = Integer.parseInt(parts[3].trim());
            double unitCost = Double.parseDouble(parts[4].trim());

            return new Material(id, name, type, quantity, unitCost);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Formats a Material object into a CSV line.
     *
     * @param material the material to format
     * @return CSV formatted string
     */
    private String formatMaterial(Material material) {
        return material.getId() + CSV_SEPARATOR +
               (material.getName() != null ? material.getName() : "") + CSV_SEPARATOR +
               (material.getType() != null ? material.getType() : "") + CSV_SEPARATOR +
               material.getQuantity() + CSV_SEPARATOR +
               material.getUnitCost();
    }
}

