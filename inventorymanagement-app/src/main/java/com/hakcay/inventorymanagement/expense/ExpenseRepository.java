/**
 * @file ExpenseRepository.java
 * @brief This file contains the ExpenseRepository class for data access.
 * @details This class provides persistence operations for expenses using CSV files.
 *          Uses FileOperations for hash-based file integrity and atomic operations.
 * @package com.hakcay.inventorymanagement.expense
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.expense;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hakcay.inventorymanagement.algorithms.fileops.FileOperations;

/**
 * @class ExpenseRepository
 * @brief Repository class for persisting Expense objects to a CSV file.
 * @details Handles reading from and writing to expenses.csv file.
 *          Uses FileOperations for hash-based file integrity and atomic operations.
 */
public class ExpenseRepository {
    private static final String DEFAULT_CSV_FILE = "expenses.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final String COMMA_PLACEHOLDER = "|COMMA|";
    private final String csvFile;

    /**
     * @brief Default constructor that uses the default CSV file name.
     */
    public ExpenseRepository() {
        this.csvFile = DEFAULT_CSV_FILE;
    }

    /**
     * @brief Constructor that accepts a custom file path.
     * @param filePath the path to the CSV file
     */
    public ExpenseRepository(String filePath) {
        this.csvFile = filePath;
    }

    /**
     * @brief Loads all expenses from the CSV file.
     * @details Returns an empty list if the file doesn't exist or is empty.
     * Uses FileOperations for safe file reading.
     * @return list of all expenses
     */
    public List<Expense> loadAll() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(csvFile);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return expenses;
        }

        // Use FileOperations for safe reading
        String content = FileOperations.safeRead(csvFile);
        if (content == null || content.isEmpty()) {
            return expenses;
        }

        // Parse content line by line
        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            Expense expense = parseExpense(line);
            if (expense != null) {
                expenses.add(expense);
            }
        }

        return expenses;
    }

    /**
     * @brief Saves all expenses to the CSV file.
     * @details Overwrites the existing file with the new data.
     * Uses FileOperations for atomic write and hash-based integrity checking.
     * @param expenses the list of expenses to save
     */
    public void saveAll(List<Expense> expenses) {
        if (expenses == null) {
            expenses = new ArrayList<>();
        }

        // Build CSV content
        StringBuilder content = new StringBuilder();
        for (Expense expense : expenses) {
            if (expense != null) {
                String line = formatExpense(expense);
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
     * @brief Parses a CSV line into an Expense object.
     * @details Handles commas in fields by replacing placeholder with actual commas.
     * @param line the CSV line
     * @return Expense object or null if parsing fails
     */
    private Expense parseExpense(String line) {
        try {
            String[] parts = line.split(CSV_SEPARATOR, -1);
            if (parts.length != 5) {
                return null;
            }

            int id = Integer.parseInt(parts[0].trim());
            int materialId = Integer.parseInt(parts[1].trim());
            int projectId = Integer.parseInt(parts[2].trim());
            double amount = Double.parseDouble(parts[3].trim());
            String description = restoreCommas(parts[4].trim());

            return new Expense(id, materialId, projectId, amount, description);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * @brief Formats an Expense object into a CSV line.
     * @details Handles commas in fields by replacing them with a placeholder.
     * @param expense the expense to format
     * @return CSV formatted string
     */
    private String formatExpense(Expense expense) {
        return expense.getId() + CSV_SEPARATOR +
               expense.getMaterialId() + CSV_SEPARATOR +
               expense.getProjectId() + CSV_SEPARATOR +
               expense.getAmount() + CSV_SEPARATOR +
               escapeCommas(expense.getDescription() != null ? expense.getDescription() : "");
    }

    /**
     * @brief Escapes commas in a string by replacing them with a placeholder.
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
     * @brief Restores commas in a string by replacing placeholder with actual commas.
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

