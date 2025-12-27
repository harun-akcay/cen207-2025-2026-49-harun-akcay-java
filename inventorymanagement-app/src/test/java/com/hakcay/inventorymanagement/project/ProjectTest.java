package com.hakcay.inventorymanagement.project;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProjectTest {
    
    @Test
    public void testDefaultConstructor() {
        Project project = new Project();
        assertEquals(0, project.getId());
        assertNull(project.getName());
        assertNull(project.getGoal());
        assertNull(project.getStatus());
    }
    
    @Test
    public void testParameterizedConstructor() {
        Project project = new Project(1, "Website", "Build website", "PLANNED");
        assertEquals(1, project.getId());
        assertEquals("Website", project.getName());
        assertEquals("Build website", project.getGoal());
        assertEquals("PLANNED", project.getStatus());
    }
    
    @Test
    public void testSettersAndGetters() {
        Project project = new Project();
        
        project.setId(5);
        assertEquals(5, project.getId());
        
        project.setName("Mobile App");
        assertEquals("Mobile App", project.getName());
        
        project.setGoal("Build mobile app");
        assertEquals("Build mobile app", project.getGoal());
        
        project.setStatus("IN_PROGRESS");
        assertEquals("IN_PROGRESS", project.getStatus());
    }
    
    @Test
    public void testToString() {
        Project project = new Project(1, "Website", "Build website", "PLANNED");
        String str = project.toString();
        assertTrue(str.contains("Project"));
        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("name='Website'"));
        assertTrue(str.contains("goal='Build website'"));
        assertTrue(str.contains("status='PLANNED'"));
    }
    
    @Test
    public void testSetNameNull() {
        Project project = new Project();
        project.setName(null);
        assertNull(project.getName());
    }
    
    @Test
    public void testSetGoalNull() {
        Project project = new Project();
        project.setGoal(null);
        assertNull(project.getGoal());
    }
    
    @Test
    public void testSetStatusNull() {
        Project project = new Project();
        project.setStatus(null);
        assertNull(project.getStatus());
    }
    
    @Test
    public void testEmptyStrings() {
        Project project = new Project(1, "", "", "");
        assertEquals("", project.getName());
        assertEquals("", project.getGoal());
        assertEquals("", project.getStatus());
    }
    
    @Test
    public void testLargeId() {
        Project project = new Project();
        project.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, project.getId());
    }
    
    @Test
    public void testNegativeId() {
        Project project = new Project();
        project.setId(-1);
        assertEquals(-1, project.getId());
    }
}

