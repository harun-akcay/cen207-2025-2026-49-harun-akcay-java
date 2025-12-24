package com.hakcay.inventorymanagement.project;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ProjectService.
 * Uses temporary files to avoid affecting real project files.
 */
public class ProjectServiceTest {
    private ProjectService service;
    private ProjectRepository repository;
    private File tempFile;

    /**
     * Sets up test fixtures before each test.
     * Creates a temporary file for each test.
     *
     * @throws IOException if file creation fails
     */
    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("projects", ".csv");
        tempFile.deleteOnExit();
        repository = new ProjectRepository(tempFile.getAbsolutePath());
        service = new ProjectService(repository);
    }

    /**
     * Cleans up after each test.
     * Deletes the temporary file if it still exists.
     */
    @After
    public void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * Test that addProject adds a project correctly.
     */
    @Test
    public void testAddProjectAddsCorrectly() {
        Project project = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project);

        List<Project> projects = service.getAllProjects();
        assertEquals(1, projects.size());
        assertEquals(1, projects.get(0).getId());
        assertEquals("Website Redesign", projects.get(0).getName());
        assertEquals("Redesign company website", projects.get(0).getGoal());
        assertEquals("PLANNED", projects.get(0).getStatus());
    }

    /**
     * Test that addProject throws IllegalArgumentException when duplicate id is used.
     */
    @Test
    public void testAddProjectDuplicateIdThrows() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        Project project2 = new Project(1, "Mobile App", "Build mobile application", "IN_PROGRESS");

        service.addProject(project1);

        try {
            service.addProject(project2);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("already exists"));
        }
    }

    /**
     * Test that updateProject updates fields correctly.
     */
    @Test
    public void testUpdateProjectUpdatesFields() {
        Project project = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project);

        Project updatedProject = new Project(1, "Website Redesign", "Redesign company website with new features", "IN_PROGRESS");
        service.updateProject(updatedProject);

        List<Project> projects = service.getAllProjects();
        assertEquals(1, projects.size());
        assertEquals(1, projects.get(0).getId());
        assertEquals("Website Redesign", projects.get(0).getName());
        assertEquals("Redesign company website with new features", projects.get(0).getGoal());
        assertEquals("IN_PROGRESS", projects.get(0).getStatus());
    }

    /**
     * Test that updateProject throws IllegalArgumentException when id is missing.
     */
    @Test
    public void testUpdateProjectMissingIdThrows() {
        Project project = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");

        try {
            service.updateProject(project);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }

    /**
     * Test that removeProjectById returns true when project is removed.
     */
    @Test
    public void testRemoveProjectByIdReturnsTrueWhenRemoved() {
        Project project = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project);

        boolean removed = service.removeProjectById(1);
        assertTrue(removed);

        List<Project> projects = service.getAllProjects();
        assertEquals(0, projects.size());
    }

    /**
     * Test that removeProjectById returns false when id not found.
     */
    @Test
    public void testRemoveProjectByIdReturnsFalseWhenIdNotFound() {
        Project project = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project);

        boolean removed = service.removeProjectById(999);
        assertFalse(removed);

        List<Project> projects = service.getAllProjects();
        assertEquals(1, projects.size());
    }

    /**
     * Test that load/save persistence works correctly.
     */
    @Test
    public void testLoadSavePersistenceWorks() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        Project project2 = new Project(2, "Mobile App", "Build mobile application", "IN_PROGRESS");
        Project project3 = new Project(3, "Database Migration", "Migrate to new database", "DONE");

        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);

        // Create a new service with the same repository to test persistence
        ProjectService newService = new ProjectService(repository);
        List<Project> loadedProjects = newService.getAllProjects();

        assertEquals(3, loadedProjects.size());
        assertEquals(1, loadedProjects.get(0).getId());
        assertEquals("Website Redesign", loadedProjects.get(0).getName());
        assertEquals("Redesign company website", loadedProjects.get(0).getGoal());
        assertEquals("PLANNED", loadedProjects.get(0).getStatus());

        assertEquals(2, loadedProjects.get(1).getId());
        assertEquals("Mobile App", loadedProjects.get(1).getName());
        assertEquals("Build mobile application", loadedProjects.get(1).getGoal());
        assertEquals("IN_PROGRESS", loadedProjects.get(1).getStatus());

        assertEquals(3, loadedProjects.get(2).getId());
        assertEquals("Database Migration", loadedProjects.get(2).getName());
        assertEquals("Migrate to new database", loadedProjects.get(2).getGoal());
        assertEquals("DONE", loadedProjects.get(2).getStatus());
    }
}

