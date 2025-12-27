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

    /**
     * Test that addProject throws IllegalArgumentException when project is null.
     */
    @Test
    public void testAddProjectNullThrows() {
        try {
            service.addProject(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("cannot be null"));
        }
    }

    /**
     * Test that updateProject throws IllegalArgumentException when project is null.
     */
    @Test
    public void testUpdateProjectNullThrows() {
        try {
            service.updateProject(null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("cannot be null"));
        }
    }

    /**
     * Test default constructor.
     */
    @Test
    public void testDefaultConstructor() {
        ProjectService defaultService = new ProjectService();
        assertNotNull(defaultService);
        assertEquals(0, defaultService.getAllProjects().size());
    }
    
    /**
     * Test adding a dependency between projects.
     */
    @Test
    public void testAddDependency() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        service.addDependency(1, 2);
        
        assertTrue(service.hasDependencyPath(1, 2));
    }
    
    /**
     * Test adding dependency with non-existent project throws exception.
     */
    @Test
    public void testAddDependencyNonExistentProjectThrows() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        service.addProject(project1);
        
        try {
            service.addDependency(1, 999);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }
    
    /**
     * Test getting dependencies using BFS.
     */
    @Test
    public void testGetDependenciesBFS() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(2, 3);
        
        List<Integer> dependencies = service.getDependenciesBFS(1);
        assertEquals(2, dependencies.size());
        assertTrue(dependencies.contains(2));
        assertTrue(dependencies.contains(3));
    }
    
    /**
     * Test getting dependencies using DFS.
     */
    @Test
    public void testGetDependenciesDFS() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(2, 3);
        
        List<Integer> dependencies = service.getDependenciesDFS(1);
        assertEquals(2, dependencies.size());
        assertTrue(dependencies.contains(2));
        assertTrue(dependencies.contains(3));
    }
    
    /**
     * Test getting dependents using BFS.
     */
    @Test
    public void testGetDependentsBFS() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(3, 2);
        
        List<Integer> dependents = service.getDependentsBFS(2);
        assertEquals(2, dependents.size());
        assertTrue(dependents.contains(1));
        assertTrue(dependents.contains(3));
    }
    
    /**
     * Test hasDependencyPath returns true when path exists.
     */
    @Test
    public void testHasDependencyPathTrue() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(2, 3);
        
        assertTrue(service.hasDependencyPath(1, 3));
    }
    
    /**
     * Test hasDependencyPath returns false when no path exists.
     */
    @Test
    public void testHasDependencyPathFalse() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        assertFalse(service.hasDependencyPath(1, 2));
    }
    
    /**
     * Test findDependencyPath returns correct path.
     */
    @Test
    public void testFindDependencyPath() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(2, 3);
        
        List<Integer> path = service.findDependencyPath(1, 3);
        assertEquals(3, path.size());
        assertEquals(Integer.valueOf(1), path.get(0));
        assertEquals(Integer.valueOf(2), path.get(1));
        assertEquals(Integer.valueOf(3), path.get(2));
    }
    
    /**
     * Test findDependencyPath returns empty list when no path exists.
     */
    @Test
    public void testFindDependencyPathNoPath() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        List<Integer> path = service.findDependencyPath(1, 2);
        assertTrue(path.isEmpty());
    }
    
    /**
     * Test removing a dependency.
     */
    @Test
    public void testRemoveDependency() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        service.addDependency(1, 2);
        assertTrue(service.hasDependencyPath(1, 2));
        
        service.removeDependency(1, 2);
        assertFalse(service.hasDependencyPath(1, 2));
    }
    
    /**
     * Test getting dependencies for non-existent project returns empty list.
     */
    @Test
    public void testGetDependenciesNonExistentProject() {
        List<Integer> dependencies = service.getDependenciesBFS(999);
        assertTrue(dependencies.isEmpty());
    }
    
    /**
     * Test hasDependencyCycle returns false when no cycles exist.
     */
    @Test
    public void testHasDependencyCycleFalse() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        service.addDependency(1, 2);
        assertFalse(service.hasDependencyCycle());
    }
    
    /**
     * Test hasDependencyCycle returns true when cycle exists.
     */
    @Test
    public void testHasDependencyCycleTrue() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(2, 3);
        service.addDependency(3, 1); // Creates cycle
        
        assertTrue(service.hasDependencyCycle());
    }
    
    /**
     * Test findDependencyCycles returns empty list when no cycles exist.
     */
    @Test
    public void testFindDependencyCyclesNoCycles() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        service.addDependency(1, 2);
        List<List<Integer>> cycles = service.findDependencyCycles();
        assertTrue(cycles.isEmpty());
    }
    
    /**
     * Test findDependencyCycles returns cycles when they exist.
     */
    @Test
    public void testFindDependencyCyclesWithCycles() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(2, 3);
        service.addDependency(3, 1); // Creates cycle
        
        List<List<Integer>> cycles = service.findDependencyCycles();
        assertFalse(cycles.isEmpty());
    }
    
    /**
     * Test isProjectInCycle returns false when project is not in cycle.
     */
    @Test
    public void testIsProjectInCycleFalse() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        service.addDependency(1, 2);
        assertFalse(service.isProjectInCycle(1));
        assertFalse(service.isProjectInCycle(2));
    }
    
    /**
     * Test isProjectInCycle returns true when project is in cycle.
     */
    @Test
    public void testIsProjectInCycleTrue() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependency(1, 2);
        service.addDependency(2, 3);
        service.addDependency(3, 1); // Creates cycle
        
        assertTrue(service.isProjectInCycle(1));
        assertTrue(service.isProjectInCycle(2));
        assertTrue(service.isProjectInCycle(3));
    }
    
    /**
     * Test isProjectInCycle returns false for non-existent project.
     */
    @Test
    public void testIsProjectInCycleNonExistent() {
        assertFalse(service.isProjectInCycle(999));
    }
    
    /**
     * Test addDependencySafe prevents cycle creation.
     */
    @Test
    public void testAddDependencySafePreventsCycle() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        Project project3 = new Project(3, "Project 3", "Goal 3", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        service.addDependencySafe(1, 2);
        service.addDependencySafe(2, 3);
        
        // This should throw exception because it would create a cycle
        try {
            service.addDependencySafe(3, 1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("cycle"));
        }
        
        // Verify cycle was not created
        assertFalse(service.hasDependencyCycle());
    }
    
    /**
     * Test addDependencySafe allows non-cycle dependencies.
     */
    @Test
    public void testAddDependencySafeAllowsNonCycle() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        Project project2 = new Project(2, "Project 2", "Goal 2", "PLANNED");
        service.addProject(project1);
        service.addProject(project2);
        
        service.addDependencySafe(1, 2);
        assertTrue(service.hasDependencyPath(1, 2));
        assertFalse(service.hasDependencyCycle());
    }
    
    /**
     * Test addDependencySafe throws exception for non-existent project.
     */
    @Test
    public void testAddDependencySafeNonExistentProject() {
        Project project1 = new Project(1, "Project 1", "Goal 1", "PLANNED");
        service.addProject(project1);
        
        try {
            service.addDependencySafe(1, 999);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("not found"));
        }
    }
    
    @Test
    public void testSearchProjectsByName() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        Project project2 = new Project(2, "Mobile App", "Build mobile application", "IN_PROGRESS");
        Project project3 = new Project(3, "Website Maintenance", "Maintain existing website", "DONE");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        List<Project> results = service.searchProjectsByName("Website");
        assertEquals(2, results.size());
        assertTrue(results.contains(project1));
        assertTrue(results.contains(project3));
    }
    
    @Test
    public void testSearchProjectsByNameCaseInsensitive() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project1);
        
        List<Project> results = service.searchProjectsByName("website");
        assertEquals(1, results.size());
        assertEquals(project1, results.get(0));
    }
    
    @Test
    public void testSearchProjectsByNameNoMatch() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project1);
        
        List<Project> results = service.searchProjectsByName("Mobile");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchProjectsByNameNullPattern() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project1);
        
        List<Project> results = service.searchProjectsByName(null);
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchProjectsByNameEmptyPattern() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        service.addProject(project1);
        
        List<Project> results = service.searchProjectsByName("");
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void testSearchProjectsByGoal() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        Project project2 = new Project(2, "Mobile App", "Build mobile application", "IN_PROGRESS");
        Project project3 = new Project(3, "Website Maintenance", "Maintain existing website", "DONE");
        service.addProject(project1);
        service.addProject(project2);
        service.addProject(project3);
        
        List<Project> results = service.searchProjectsByGoal("website");
        assertEquals(2, results.size());
        assertTrue(results.contains(project1));
        assertTrue(results.contains(project3));
    }
    
    @Test
    public void testSearchProjects() {
        Project project1 = new Project(1, "Website Redesign", "Redesign company website", "PLANNED");
        Project project2 = new Project(2, "Mobile App", "Build mobile application", "IN_PROGRESS");
        service.addProject(project1);
        service.addProject(project2);
        
        List<Project> results = service.searchProjects("Website");
        assertEquals(1, results.size());
        assertEquals(project1, results.get(0));
        
        results = service.searchProjects("mobile");
        assertEquals(1, results.size());
        assertEquals(project2, results.get(0));
    }
}

