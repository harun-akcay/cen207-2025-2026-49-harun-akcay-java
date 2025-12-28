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

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.hakcay.inventorymanagement.expense.Expense;
import com.hakcay.inventorymanagement.expense.ExpenseService;
import com.hakcay.inventorymanagement.material.Material;
import com.hakcay.inventorymanagement.material.MaterialService;
import com.hakcay.inventorymanagement.project.Project;
import com.hakcay.inventorymanagement.project.ProjectService;

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
  
  private static final Logger logger = (Logger) LoggerFactory.getLogger(InventorymanagementApp.class);
  private static MaterialService materialService;
  private static ProjectService projectService;
  private static ExpenseService expenseService;
  private static Scanner scanner;
  
  /**
   * @brief Main method - entry point of the application.
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    System.out.println("Inventory Management App");
    System.out.println("========================");
    
    try {
      // Initialize services
      materialService = new MaterialService();
      projectService = new ProjectService();
      expenseService = new ExpenseService();
      scanner = new Scanner(System.in);
      
      // Main menu loop
      boolean running = true;
      while (running) {
        showMainMenu();
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
          case 1:
            materialMenu();
            break;
          case 2:
            projectMenu();
            break;
          case 3:
            expenseMenu();
            break;
          case 0:
            running = false;
            System.out.println("Thank you for using Inventory Management App!");
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      }
    } catch (Exception e) {
      logger.error("Error in main application: " + e.getMessage(), e);
      System.out.println("An error occurred: " + e.getMessage());
    } finally {
      if (scanner != null) {
        scanner.close();
      }
    }
  }
  
  /**
   * @brief Displays the main menu.
   */
  private static void showMainMenu() {
    System.out.println("\n=== Main Menu ===");
    System.out.println("1. Material Management");
    System.out.println("2. Project Management");
    System.out.println("3. Expense Management");
    System.out.println("0. Exit");
  }
  
  /**
   * @brief Handles material management menu.
   */
  private static void materialMenu() {
    boolean back = false;
    while (!back) {
      System.out.println("\n=== Material Management ===");
      System.out.println("1. Add Material");
      System.out.println("2. View All Materials");
      System.out.println("3. Find Material by ID");
      System.out.println("4. Update Material");
      System.out.println("5. Remove Material");
      System.out.println("6. Undo");
      System.out.println("7. Redo");
      System.out.println("0. Back to Main Menu");
      
      int choice = getIntInput("Enter your choice: ");
      
      switch (choice) {
        case 1:
          addMaterial();
          break;
        case 2:
          viewAllMaterials();
          break;
        case 3:
          findMaterialById();
          break;
        case 4:
          updateMaterial();
          break;
        case 5:
          removeMaterial();
          break;
        case 6:
          undoMaterial();
          break;
        case 7:
          redoMaterial();
          break;
        case 0:
          back = true;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
  
  /**
   * @brief Handles project management menu.
   */
  private static void projectMenu() {
    boolean back = false;
    while (!back) {
      System.out.println("\n=== Project Management ===");
      System.out.println("1. Add Project");
      System.out.println("2. View All Projects");
      System.out.println("3. Find Project by ID");
      System.out.println("4. Update Project");
      System.out.println("5. Remove Project");
      System.out.println("6. Add Dependency");
      System.out.println("7. Check for Cycles");
      System.out.println("0. Back to Main Menu");
      
      int choice = getIntInput("Enter your choice: ");
      
      switch (choice) {
        case 1:
          addProject();
          break;
        case 2:
          viewAllProjects();
          break;
        case 3:
          findProjectById();
          break;
        case 4:
          updateProject();
          break;
        case 5:
          removeProject();
          break;
        case 6:
          addDependency();
          break;
        case 7:
          checkCycles();
          break;
        case 0:
          back = true;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
  
  /**
   * @brief Handles expense management menu.
   */
  private static void expenseMenu() {
    boolean back = false;
    while (!back) {
      System.out.println("\n=== Expense Management ===");
      System.out.println("1. Add Expense");
      System.out.println("2. View All Expenses");
      System.out.println("3. Find Expense by ID");
      System.out.println("4. Remove Expense");
      System.out.println("0. Back to Main Menu");
      
      int choice = getIntInput("Enter your choice: ");
      
      switch (choice) {
        case 1:
          addExpense();
          break;
        case 2:
          viewAllExpenses();
          break;
        case 3:
          findExpenseById();
          break;
        case 4:
          removeExpense();
          break;
        case 0:
          back = true;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
  
  // Material operations
  private static void addMaterial() {
    try {
      int id = getIntInput("Enter Material ID: ");
      String name = getStringInput("Enter Material Name: ");
      String type = getStringInput("Enter Material Type: ");
      int quantity = getIntInput("Enter Quantity: ");
      double unitCost = getDoubleInput("Enter Unit Cost: ");
      
      Material material = new Material(id, name, type, quantity, unitCost);
      materialService.addMaterial(material);
      System.out.println("Material added successfully!");
    } catch (Exception e) {
      System.out.println("Error adding material: " + e.getMessage());
    }
  }
  
  private static void viewAllMaterials() {
    List<Material> materials = materialService.getAllMaterials();
    if (materials.isEmpty()) {
      System.out.println("No materials found.");
    } else {
      System.out.println("\n=== All Materials ===");
      for (Material material : materials) {
        System.out.println(material);
      }
    }
  }
  
  private static void findMaterialById() {
    int id = getIntInput("Enter Material ID: ");
    Material material = materialService.getMaterialById(id);
    if (material != null) {
      System.out.println("Material found: " + material);
    } else {
      System.out.println("Material not found with ID: " + id);
    }
  }
  
  private static void updateMaterial() {
    try {
      int id = getIntInput("Enter Material ID to update: ");
      Material existing = materialService.getMaterialById(id);
      if (existing == null) {
        System.out.println("Material not found with ID: " + id);
        return;
      }
      
      String name = getStringInput("Enter new Material Name (current: " + existing.getName() + "): ");
      String type = getStringInput("Enter new Material Type (current: " + existing.getType() + "): ");
      int quantity = getIntInput("Enter new Quantity (current: " + existing.getQuantity() + "): ");
      double unitCost = getDoubleInput("Enter new Unit Cost (current: " + existing.getUnitCost() + "): ");
      
      Material updated = new Material(id, name, type, quantity, unitCost);
      materialService.updateMaterial(updated);
      System.out.println("Material updated successfully!");
    } catch (Exception e) {
      System.out.println("Error updating material: " + e.getMessage());
    }
  }
  
  private static void removeMaterial() {
    int id = getIntInput("Enter Material ID to remove: ");
    boolean removed = materialService.removeMaterialById(id);
    if (removed) {
      System.out.println("Material removed successfully!");
    } else {
      System.out.println("Material not found with ID: " + id);
    }
  }
  
  private static void undoMaterial() {
    if (materialService.canUndo()) {
      materialService.undo();
      System.out.println("Undo operation completed!");
    } else {
      System.out.println("No operation to undo.");
    }
  }
  
  private static void redoMaterial() {
    if (materialService.canRedo()) {
      materialService.redo();
      System.out.println("Redo operation completed!");
    } else {
      System.out.println("No operation to redo.");
    }
  }
  
  // Project operations
  private static void addProject() {
    try {
      int id = getIntInput("Enter Project ID: ");
      String name = getStringInput("Enter Project Name: ");
      String goal = getStringInput("Enter Project Goal: ");
      String status = getStringInput("Enter Project Status: ");
      
      Project project = new Project(id, name, goal, status);
      projectService.addProject(project);
      System.out.println("Project added successfully!");
    } catch (Exception e) {
      System.out.println("Error adding project: " + e.getMessage());
    }
  }
  
  private static void viewAllProjects() {
    List<Project> projects = projectService.getAllProjects();
    if (projects.isEmpty()) {
      System.out.println("No projects found.");
    } else {
      System.out.println("\n=== All Projects ===");
      for (Project project : projects) {
        System.out.println(project);
      }
    }
  }
  
  private static void findProjectById() {
    int id = getIntInput("Enter Project ID: ");
    Project project = projectService.findProjectById(id);
    if (project != null) {
      System.out.println("Project found: " + project);
    } else {
      System.out.println("Project not found with ID: " + id);
    }
  }
  
  private static void updateProject() {
    try {
      int id = getIntInput("Enter Project ID to update: ");
      Project existing = projectService.findProjectById(id);
      if (existing == null) {
        System.out.println("Project not found with ID: " + id);
        return;
      }
      
      String name = getStringInput("Enter new Project Name (current: " + existing.getName() + "): ");
      String goal = getStringInput("Enter new Project Goal (current: " + existing.getGoal() + "): ");
      String status = getStringInput("Enter new Project Status (current: " + existing.getStatus() + "): ");
      
      Project updated = new Project(id, name, goal, status);
      projectService.updateProject(updated);
      System.out.println("Project updated successfully!");
    } catch (Exception e) {
      System.out.println("Error updating project: " + e.getMessage());
    }
  }
  
  private static void removeProject() {
    int id = getIntInput("Enter Project ID to remove: ");
    boolean removed = projectService.removeProjectById(id);
    if (removed) {
      System.out.println("Project removed successfully!");
    } else {
      System.out.println("Project not found with ID: " + id);
    }
  }
  
  private static void addDependency() {
    try {
      int fromId = getIntInput("Enter Project ID that depends on another: ");
      int toId = getIntInput("Enter Project ID that is depended upon: ");
      projectService.addDependency(fromId, toId);
      System.out.println("Dependency added successfully!");
    } catch (Exception e) {
      System.out.println("Error adding dependency: " + e.getMessage());
    }
  }
  
  private static void checkCycles() {
    if (projectService.hasDependencyCycle()) {
      System.out.println("WARNING: Dependency cycle detected!");
      List<List<Integer>> cycles = projectService.findDependencyCycles();
      System.out.println("Cycles found: " + cycles.size());
      for (List<Integer> cycle : cycles) {
        System.out.println("Cycle: " + cycle);
      }
    } else {
      System.out.println("No dependency cycles found.");
    }
  }
  
  // Expense operations
  private static void addExpense() {
    try {
      int id = getIntInput("Enter Expense ID: ");
      int projectId = getIntInput("Enter Project ID: ");
      int materialId = getIntInput("Enter Material ID: ");
      double amount = getDoubleInput("Enter Amount: ");
      String description = getStringInput("Enter Description: ");
      
      Expense expense = new Expense(id, projectId, materialId, amount, description);
      expenseService.addExpense(expense);
      System.out.println("Expense added successfully!");
    } catch (Exception e) {
      System.out.println("Error adding expense: " + e.getMessage());
    }
  }
  
  private static void viewAllExpenses() {
    List<Expense> expenses = expenseService.getAllExpenses();
    if (expenses.isEmpty()) {
      System.out.println("No expenses found.");
    } else {
      System.out.println("\n=== All Expenses ===");
      for (Expense expense : expenses) {
        System.out.println(expense);
      }
    }
  }
  
  private static void findExpenseById() {
    int id = getIntInput("Enter Expense ID: ");
    Expense expense = expenseService.getExpenseById(id);
    if (expense != null) {
      System.out.println("Expense found: " + expense);
    } else {
      System.out.println("Expense not found with ID: " + id);
    }
  }
  
  private static void removeExpense() {
    int id = getIntInput("Enter Expense ID to remove: ");
    boolean removed = expenseService.removeExpenseById(id);
    if (removed) {
      System.out.println("Expense removed successfully!");
    } else {
      System.out.println("Expense not found with ID: " + id);
    }
  }
  
  // Helper methods for input
  private static int getIntInput(String prompt) {
    System.out.print(prompt);
    try {
      return Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a valid integer.");
      return getIntInput(prompt);
    }
  }
  
  private static double getDoubleInput(String prompt) {
    System.out.print(prompt);
    try {
      return Double.parseDouble(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
      System.out.println("Invalid input. Please enter a valid number.");
      return getDoubleInput(prompt);
    }
  }
  
  private static String getStringInput(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }
}
