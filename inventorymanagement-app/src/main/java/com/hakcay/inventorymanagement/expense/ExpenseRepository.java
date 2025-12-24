package com.hakcay.inventorymanagement.expense;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for persisting Expense objects to a CSV file.
 * Handles reading from and writing to expenses.csv file.
 */
public class ExpenseRepository {
    private static final String DEFAULT_CSV_FILE = "expenses.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final String COMMA_PLACEHOLDER = "|COMMA|";
    private final String csvFile;

    /**
     * Default constructor that uses the default CSV file name.
     */
    public ExpenseRepository() {
        this.csvFile = DEFAULT_CSV_FILE;
    }

    /**
     * Constructor that accepts a custom file path.
     *
     * @param filePath the path to the CSV file
     */
    public ExpenseRepository(String filePath) {
        this.csvFile = filePath;
    }

    /**
     * Loads all expenses from the CSV file.
     * Returns an empty list if the file doesn't exist or is empty.
     *
     * @return list of all expenses
     */
    public List<Expense> loadAll() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(csvFile);

        // Return empty list if file doesn't exist
        if (!file.exists()) {
            return expenses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
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
        } catch (IOException e) {
            // Return empty list on error
            return new ArrayList<>();
        }

        return expenses;
    }

    /**
     * Saves all expenses to the CSV file.
     * Overwrites the existing file with the new data.
     *
     * @param expenses the list of expenses to save
     */
    public void saveAll(List<Expense> expenses) {
        if (expenses == null) {
            expenses = new ArrayList<>();
        }

        File file = new File(csvFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Expense expense : expenses) {
                String line = formatExpense(expense);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Silently handle IO errors
            // In a production system, you might want to log this
        }
    }

    /**
     * Parses a CSV line into an Expense object.
     * Handles commas in fields by replacing placeholder with actual commas.
     *
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
     * Formats an Expense object into a CSV line.
     * Handles commas in fields by replacing them with a placeholder.
     *
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

