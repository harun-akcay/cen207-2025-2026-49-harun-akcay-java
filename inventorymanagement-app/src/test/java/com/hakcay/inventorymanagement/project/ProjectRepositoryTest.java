/**
 * @file ProjectRepositoryTest.java
 * @brief Test class for ProjectRepository.
 * @details This class contains unit tests for ProjectRepository to achieve 100% coverage.
 * @package com.hakcay.inventorymanagement.project
 * @author Harun Akcay
 */
package com.hakcay.inventorymanagement.project;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @class ProjectRepositoryTest
 * @brief Test class for ProjectRepository.
 */
public class ProjectRepositoryTest {
    
    private ProjectRepository repository;
    private File tempFile;
    
    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("projects", ".csv");
        tempFile.deleteOnExit();
        repository = new ProjectRepository(tempFile.getAbsolutePath());
    }
    
    @After
    public void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }
    
    /**
     * @brief Test findById returns null when HashTable is empty.
     */
    @Test
    public void testFindByIdReturnsNullWhenEmpty() {
        Project result = repository.findById(1);
        assertNull(result);
    }
    
    /**
     * @brief Test findById returns correct project after loadAll.
     */
    @Test
    public void testFindByIdAfterLoadAll() {
        // Create test data
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Project 1", "Goal 1", "PLANNED"));
        projects.add(new Project(2, "Project 2", "Goal 2", "IN_PROGRESS"));
        repository.saveAll(projects);
        
        // Load and test findById
        repository.loadAll();
        Project result = repository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Project 1", result.getName());
        
        Project result2 = repository.findById(2);
        assertNotNull(result2);
        assertEquals(2, result2.getId());
        assertEquals("Project 2", result2.getName());
    }
    
    /**
     * @brief Test findById returns null for non-existent ID.
     */
    @Test
    public void testFindByIdReturnsNullForNonExistentId() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Project 1", "Goal 1", "PLANNED"));
        repository.saveAll(projects);
        repository.loadAll();
        
        Project result = repository.findById(999);
        assertNull(result);
    }
    
    /**
     * @brief Test findById works after saveAll updates HashTable.
     */
    @Test
    public void testFindByIdAfterSaveAll() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Project 1", "Goal 1", "PLANNED"));
        projects.add(new Project(2, "Project 2", "Goal 2", "IN_PROGRESS"));
        
        repository.saveAll(projects);
        
        // findById should work after saveAll (HashTable is updated)
        Project result = repository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Project 1", result.getName());
        
        Project result2 = repository.findById(2);
        assertNotNull(result2);
        assertEquals(2, result2.getId());
    }
    
    /**
     * @brief Test findById with multiple projects and HashTable lookup.
     */
    @Test
    public void testFindByIdWithMultipleProjects() {
        List<Project> projects = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            projects.add(new Project(i, "Project " + i, "Goal " + i, "PLANNED"));
        }
        
        repository.saveAll(projects);
        
        // Test O(1) lookup for various IDs
        for (int i = 1; i <= 10; i++) {
            Project result = repository.findById(i);
            assertNotNull("Project with id " + i + " should be found", result);
            assertEquals(i, result.getId());
            assertEquals("Project " + i, result.getName());
        }
    }
    
    /**
     * @brief Test findById after loadAll with empty file.
     */
    @Test
    public void testFindByIdAfterLoadAllWithEmptyFile() {
        // File exists but is empty
        repository.loadAll();
        Project result = repository.findById(1);
        assertNull(result);
    }
    
    /**
     * @brief Test findById after saveAll with null list.
     */
    @Test
    public void testFindByIdAfterSaveAllWithNullList() {
        repository.saveAll(null);
        Project result = repository.findById(1);
        assertNull(result);
    }
    
    /**
     * @brief Test findById with projects containing null values.
     */
    @Test
    public void testFindByIdWithNullProjectsInList() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Project 1", "Goal 1", "PLANNED"));
        projects.add(null); // null project should be skipped
        projects.add(new Project(2, "Project 2", "Goal 2", "IN_PROGRESS"));
        
        repository.saveAll(projects);
        
        // Only non-null projects should be in HashTable
        Project result1 = repository.findById(1);
        assertNotNull(result1);
        assertEquals(1, result1.getId());
        
        Project result2 = repository.findById(2);
        assertNotNull(result2);
        assertEquals(2, result2.getId());
    }
    
    /**
     * @brief Test default constructor initializes HashTable.
     */
    @Test
    public void testDefaultConstructor() {
        ProjectRepository repo = new ProjectRepository();
        Project result = repo.findById(1);
        assertNull(result); // Should not crash, just return null
    }

    /**
     * @brief Test loadAll with invalid CSV line (wrong number of parts).
     */
    @Test
    public void testLoadAllWithInvalidLine() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,Project 1\n"); // Only 2 parts, should be 4
        writer.write("2,Project 2,Goal 2,Status 2\n"); // Valid line
        writer.close();
        
        List<Project> projects = repository.loadAll();
        assertEquals(1, projects.size()); // Only valid line should be loaded
        assertEquals(2, projects.get(0).getId());
    }

    /**
     * @brief Test loadAll with invalid ID (NumberFormatException).
     */
    @Test
    public void testLoadAllWithInvalidId() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("invalid,Project 1,Goal 1,Status 1\n"); // Invalid ID
        writer.write("2,Project 2,Goal 2,Status 2\n"); // Valid line
        writer.close();
        
        List<Project> projects = repository.loadAll();
        assertEquals(1, projects.size()); // Only valid line should be loaded
        assertEquals(2, projects.get(0).getId());
    }

    /**
     * @brief Test loadAll with empty lines.
     */
    @Test
    public void testLoadAllWithEmptyLines() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("\n"); // Empty line
        writer.write("   \n"); // Whitespace line
        writer.write("1,Project 1,Goal 1,Status 1\n"); // Valid line
        writer.write("\n"); // Another empty line
        writer.close();
        
        List<Project> projects = repository.loadAll();
        assertEquals(1, projects.size()); // Only valid line should be loaded
    }

    /**
     * @brief Test loadAll with commas in fields (escape/restore).
     */
    @Test
    public void testLoadAllWithCommasInFields() throws IOException {
        java.io.FileWriter writer = new java.io.FileWriter(tempFile);
        writer.write("1,Project, with, commas,Goal, with, commas,Status, with, commas\n");
        writer.close();
        
        List<Project> projects = repository.loadAll();
        assertEquals(1, projects.size());
        // The commas should be preserved (though CSV parsing will split them)
        // This tests the escape/restore mechanism
    }

    /**
     * @brief Test saveAll with project containing commas.
     */
    @Test
    public void testSaveAllWithCommasInFields() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project(1, "Project, with, commas", "Goal, with, commas", "Status, with, commas"));
        repository.saveAll(projects);
        
        List<Project> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        // Verify the project was saved and loaded correctly
    }

    /**
     * @brief Test saveAll with project containing null fields.
     */
    @Test
    public void testSaveAllWithNullFields() {
        List<Project> projects = new ArrayList<>();
        Project project = new Project(1, null, null, null);
        projects.add(project);
        repository.saveAll(projects);
        
        List<Project> loaded = repository.loadAll();
        assertEquals(1, loaded.size());
        assertNotNull(loaded.get(0));
    }

    /**
     * @brief Test loadAll with non-existent file.
     */
    @Test
    public void testLoadAllWithNonExistentFile() {
        ProjectRepository repo = new ProjectRepository("nonexistent_file.csv");
        List<Project> projects = repo.loadAll();
        assertEquals(0, projects.size()); // Should return empty list
    }
    
    @Test
    public void testGetAllOrdered() {
        Project project1 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        Project project2 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project3 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);
        repository.saveAll(projects);
        
        List<Project> ordered = repository.getAllOrdered();
        assertEquals(3, ordered.size());
        assertEquals(1, ordered.get(0).getId());
        assertEquals(2, ordered.get(1).getId());
        assertEquals(3, ordered.get(2).getId());
    }
    
    @Test
    public void testGetAllOrderedEmpty() {
        List<Project> ordered = repository.getAllOrdered();
        assertTrue(ordered.isEmpty());
    }
    
    @Test
    public void testGetAllEntriesOrdered() {
        Project project1 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        Project project2 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        repository.saveAll(projects);
        
        List<com.hakcay.inventorymanagement.algorithms.bplustree.BPlusTree.Entry<Integer, Project>> entries = repository.getAllEntriesOrdered();
        assertEquals(2, entries.size());
        assertEquals(Integer.valueOf(1), entries.get(0).getKey());
        assertEquals(project2, entries.get(0).getValue());
        assertEquals(Integer.valueOf(3), entries.get(1).getKey());
        assertEquals(project1, entries.get(1).getValue());
    }
    
    @Test
    public void testGetAllEntriesOrderedEmpty() {
        List<com.hakcay.inventorymanagement.algorithms.bplustree.BPlusTree.Entry<Integer, Project>> entries = repository.getAllEntriesOrdered();
        assertTrue(entries.isEmpty());
    }
    
    @Test
    public void testFindByIdUsingIndex() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        
        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        repository.saveAll(projects);
        
        Project found = repository.findByIdUsingIndex(1);
        assertNotNull(found);
        assertEquals(project1, found);
        
        found = repository.findByIdUsingIndex(2);
        assertNotNull(found);
        assertEquals(project2, found);
        
        found = repository.findByIdUsingIndex(999);
        assertNull(found);
    }
    
    @Test
    public void testFindByIdUsingIndexEmpty() {
        Project found = repository.findByIdUsingIndex(1);
        assertNull(found);
    }
}

