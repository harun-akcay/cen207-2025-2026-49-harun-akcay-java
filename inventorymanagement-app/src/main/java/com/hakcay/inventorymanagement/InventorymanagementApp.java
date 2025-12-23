/**
 *
 * @file InventorymanagementApp.java
 * @brief This file serves as the main application file for the Inventorymanagement App.
 * @details This file contains the entry point of the application, which is the main method. It initializes the necessary components and executes the Inventorymanagement App.
 */
/**
 *
 * @package com.hakcay.inventorymanagement
 * @brief The com.hakcay.inventorymanagement package contains all the classes and files related to the Inventorymanagement App.
 */
package com.hakcay.inventorymanagement;

import java.util.List;
import java.util.Scanner;

import com.hakcay.inventorymanagement.material.Material;
import com.hakcay.inventorymanagement.material.MaterialService;

/**
 *
 * @class InventorymanagementApp
 * @brief This class represents the main application class for the Inventorymanagement
 *        App.
 * @details The InventorymanagementApp class provides the entry point for the Inventorymanagement
 *          App. It initializes the necessary components, performs calculations,
 *          and handles exceptions.
 * @author ugur.coruh
 */
public class InventorymanagementApp {
    private static MaterialService materialService;
    private static Scanner scanner;

    /**
     * Main method that starts the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        materialService = new MaterialService();
        scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleMaterialInventory();
                    break;
                case "0":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    /**
     * Displays the main menu.
     */
    private static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1) Material Inventory");
        System.out.println("0) Exit");
        System.out.print("Select an option: ");
    }

    /**
     * Handles the Material Inventory sub-menu.
     */
    private static void handleMaterialInventory() {
        boolean back = false;
        while (!back) {
            showMaterialMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewMaterials();
                    break;
                case "2":
                    addMaterial();
                    break;
                case "3":
                    updateMaterial();
                    break;
                case "4":
                    removeMaterial();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    /**
     * Displays the Material Inventory menu.
     */
    private static void showMaterialMenu() {
        System.out.println("\n=== Material Inventory ===");
        System.out.println("1) View Materials");
        System.out.println("2) Add Material");
        System.out.println("3) Update Material");
        System.out.println("4) Remove Material");
        System.out.println("0) Back");
        System.out.print("Select an option: ");
    }

    /**
     * Displays all materials.
     */
    private static void viewMaterials() {
        System.out.println("\n=== All Materials ===");
        List<Material> materials = materialService.getAllMaterials();
        if (materials.isEmpty()) {
            System.out.println("No materials found.");
        } else {
            for (Material material : materials) {
                System.out.println(material.toString());
            }
        }
    }

    /**
     * Adds a new material.
     */
    private static void addMaterial() {
        System.out.println("\n=== Add Material ===");
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Type: ");
            String type = scanner.nextLine().trim();

            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Unit Cost: ");
            double unitCost = Double.parseDouble(scanner.nextLine().trim());

            Material material = new Material(id, name, type, quantity, unitCost);
            materialService.addMaterial(material);
            System.out.println("Material added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Updates an existing material.
     */
    private static void updateMaterial() {
        System.out.println("\n=== Update Material ===");
        try {
            System.out.print("Enter Material ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter new Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter new Type: ");
            String type = scanner.nextLine().trim();

            System.out.print("Enter new Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter new Unit Cost: ");
            double unitCost = Double.parseDouble(scanner.nextLine().trim());

            Material material = new Material(id, name, type, quantity, unitCost);
            materialService.updateMaterial(material);
            System.out.println("Material updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Removes a material by ID.
     */
    private static void removeMaterial() {
        System.out.println("\n=== Remove Material ===");
        try {
            System.out.print("Enter Material ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            boolean removed = materialService.removeMaterialById(id);
            if (removed) {
                System.out.println("Material removed successfully!");
            } else {
                System.out.println("Material with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter a valid ID.");
        }
    }
}
