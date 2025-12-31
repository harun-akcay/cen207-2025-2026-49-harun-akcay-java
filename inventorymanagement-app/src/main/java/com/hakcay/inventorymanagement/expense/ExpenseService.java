/**
 * @file ExpenseService.java
 * @brief This file contains the ExpenseService class for business logic.
 * @details This class provides business logic for managing expenses including
 *          adding, removing, and retrieving expenses.
 * @package com.hakcay.inventorymanagement.expense
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.expense;

import java.util.ArrayList;
import java.util.List;

/**
 * @class ExpenseService
 * @brief Service class for managing Expense operations.
 * @details Provides business logic for adding, removing, and retrieving expenses.
 */
public class ExpenseService {
    private ExpenseRepository repository;
    private List<Expense> expenses;

    /**
     * @brief Constructor that initializes the repository and loads existing expenses.
     */
    public ExpenseService() {
        this.repository = new ExpenseRepository();
        this.expenses = new ArrayList<>(repository.loadAll());
    }

    /**
     * @brief Constructor that accepts a repository (for testing purposes).
     * @param repository the expense repository to use
     */
    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
        this.expenses = new ArrayList<>(repository.loadAll());
    }

    /**
     * @brief Adds a new expense to the logging system.
     * @details Enforces unique id constraint - throws IllegalArgumentException if id already exists.
     * @param expense the expense to add
     * @throws IllegalArgumentException if expense id already exists
     */
    public void addExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }

        // Check for duplicate id
        for (Expense existing : expenses) {
            if (existing.getId() == expense.getId()) {
                throw new IllegalArgumentException("Expense with id " + expense.getId() + " already exists");
            }
        }

        expenses.add(expense);
        repository.saveAll(expenses);
    }

    /**
     * @brief Removes an expense from the logging system by id.
     * @param id the id of the expense to remove
     * @return true if expense was removed, false if not found
     */
    public boolean removeExpenseById(int id) {
        boolean removed = expenses.removeIf(expense -> expense.getId() == id);
        if (removed) {
            repository.saveAll(expenses);
        }
        return removed;
    }

    /**
     * @brief Gets all expenses in the logging system.
     * @return list of all expenses
     */
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    /**
     * @brief Gets an expense by its ID.
     * @param id the expense ID
     * @return the expense if found, null otherwise
     */
    public Expense getExpenseById(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return expense;
            }
        }
        return null;
    }

    /**
     * @brief Calculates the total expense amount for a specific project.
     * @param projectId the project id
     * @return the total expense amount for the project
     */
    public double getTotalExpenseForProject(int projectId) {
        double total = 0.0;
        for (Expense expense : expenses) {
            if (expense.getProjectId() == projectId) {
                total += expense.getAmount();
            }
        }
        return total;
    }
}

