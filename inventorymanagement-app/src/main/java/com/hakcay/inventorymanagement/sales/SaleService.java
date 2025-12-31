/**
 * @file SaleService.java
 * @brief This file contains the SaleService class for business logic.
 * @details This class provides business logic for managing sales including
 *          adding, removing, and retrieving sales.
 * @package com.hakcay.inventorymanagement.sales
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.sales;

import java.util.ArrayList;
import java.util.List;

/**
 * @class SaleService
 * @brief Service class for managing Sale operations.
 * @details Provides business logic for adding, removing, and retrieving sales.
 */
public class SaleService {
    /** @brief Repository for persisting sales data */
    private SaleRepository repository;
    /** @brief In-memory list of sales */
    private List<Sale> sales;

    /**
     * @brief Constructor that initializes the repository and loads existing sales.
     */
    public SaleService() {
        this.repository = new SaleRepository();
        this.sales = new ArrayList<>(repository.loadAll());
    }

    /**
     * @brief Constructor that accepts a repository (for testing purposes).
     * @param repository the sale repository to use
     */
    public SaleService(SaleRepository repository) {
        this.repository = repository;
        this.sales = new ArrayList<>(repository.loadAll());
    }

    /**
     * @brief Adds a new sale to the tracking system.
     * @details Enforces unique id constraint - throws IllegalArgumentException if id already exists.
     * @param sale the sale to add
     * @throws IllegalArgumentException if sale id already exists
     */
    public void addSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("Sale cannot be null");
        }

        // Check for duplicate id
        for (Sale existing : sales) {
            if (existing.getId() == sale.getId()) {
                throw new IllegalArgumentException("Sale with id " + sale.getId() + " already exists");
            }
        }

        sales.add(sale);
        repository.saveAll(sales);
    }

    /**
     * @brief Removes a sale from the tracking system by id.
     * @param id the id of the sale to remove
     * @return true if sale was removed, false if not found
     */
    public boolean removeSaleById(int id) {
        boolean removed = sales.removeIf(sale -> sale.getId() == id);
        if (removed) {
            repository.saveAll(sales);
        }
        return removed;
    }

    /**
     * @brief Gets all sales in the tracking system.
     * @return list of all sales
     */
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    /**
     * @brief Gets a sale by its ID.
     * @param id the sale ID
     * @return the sale if found, null otherwise
     */
    public Sale getSaleById(int id) {
        for (Sale sale : sales) {
            if (sale.getId() == id) {
                return sale;
            }
        }
        return null;
    }

    /**
     * @brief Gets all sales for a specific material.
     * @param materialId the material id
     * @return list of sales for the material
     */
    public List<Sale> getSalesByMaterialId(int materialId) {
        List<Sale> result = new ArrayList<>();
        for (Sale sale : sales) {
            if (sale.getMaterialId() == materialId) {
                result.add(sale);
            }
        }
        return result;
    }

    /**
     * @brief Calculates the total revenue from all sales.
     * @return the total revenue
     */
    public double getTotalRevenue() {
        double total = 0.0;
        for (Sale sale : sales) {
            total += sale.getTotalAmount();
        }
        return total;
    }

    /**
     * @brief Calculates the total revenue for a specific material.
     * @param materialId the material id
     * @return the total revenue for the material
     */
    public double getTotalRevenueForMaterial(int materialId) {
        double total = 0.0;
        for (Sale sale : sales) {
            if (sale.getMaterialId() == materialId) {
                total += sale.getTotalAmount();
            }
        }
        return total;
    }

    /**
     * @brief Gets the total quantity sold for a specific material.
     * @param materialId the material id
     * @return the total quantity sold
     */
    public int getTotalQuantitySoldForMaterial(int materialId) {
        int total = 0;
        for (Sale sale : sales) {
            if (sale.getMaterialId() == materialId) {
                total += sale.getQuantity();
            }
        }
        return total;
    }
}

