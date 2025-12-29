package com.hakcay.inventorymanagement.sales;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hakcay.inventorymanagement.algorithms.fileops.FileOperations;

/**
 * Repository class for persisting Sale objects to a CSV file.
 * Handles reading from and writing to sales.csv file.
 * Uses FileOperations for hash-based file integrity and atomic operations.
 */
public class SaleRepository {
    private static final String DEFAULT_CSV_FILE = "sales.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final String COMMA_PLACEHOLDER = "|COMMA|";
    private final String csvFile;

    /**
     * Default constructor that uses the default CSV file name.
     */
    public SaleRepository() {
        this.csvFile = DEFAULT_CSV_FILE;
    }

    /**
     * Constructor that accepts a custom file path.
     *
     * @param filePath the path to the CSV file
     */
    public SaleRepository(String filePath) {
        this.csvFile = filePath;
    }

    /**
     * Loads all sales from the CSV file.
     * Returns an empty list if the file doesn't exist or is empty.
     * Uses FileOperations for safe file reading.
     *
     * @return list of all sales
     */
    public List<Sale> loadAll() {
        List<Sale> sales = new ArrayList<>();
        File file = new File(csvFile);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return sales;
        }

        // Use FileOperations for safe reading
        String content = FileOperations.safeRead(csvFile);
        if (content == null || content.isEmpty()) {
            return sales;
        }

        // Parse content line by line
        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            Sale sale = parseSale(line);
            if (sale != null) {
                sales.add(sale);
            }
        }

        return sales;
    }

    /**
     * Saves all sales to the CSV file.
     * Overwrites the existing file with the new data.
     * Uses FileOperations for atomic write and hash-based integrity checking.
     *
     * @param sales the list of sales to save
     */
    public void saveAll(List<Sale> sales) {
        if (sales == null) {
            sales = new ArrayList<>();
        }

        // Build CSV content
        StringBuilder content = new StringBuilder();
        for (Sale sale : sales) {
            if (sale != null) {
                String line = formatSale(sale);
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
     * Parses a CSV line into a Sale object.
     * Handles commas in fields by replacing placeholder with actual commas.
     *
     * @param line the CSV line
     * @return Sale object or null if parsing fails
     */
    private Sale parseSale(String line) {
        try {
            String[] parts = line.split(CSV_SEPARATOR, -1);
            if (parts.length != 6) {
                return null;
            }

            int id = Integer.parseInt(parts[0].trim());
            int materialId = Integer.parseInt(parts[1].trim());
            int quantity = Integer.parseInt(parts[2].trim());
            double price = Double.parseDouble(parts[3].trim());
            String date = restoreCommas(parts[4].trim());
            String customerName = restoreCommas(parts[5].trim());

            return new Sale(id, materialId, quantity, price, date, customerName);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Formats a Sale object into a CSV line.
     * Handles commas in fields by replacing them with a placeholder.
     *
     * @param sale the sale to format
     * @return CSV formatted string
     */
    private String formatSale(Sale sale) {
        return sale.getId() + CSV_SEPARATOR +
               sale.getMaterialId() + CSV_SEPARATOR +
               sale.getQuantity() + CSV_SEPARATOR +
               sale.getPrice() + CSV_SEPARATOR +
               escapeCommas(sale.getDate() != null ? sale.getDate() : "") + CSV_SEPARATOR +
               escapeCommas(sale.getCustomerName() != null ? sale.getCustomerName() : "");
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

