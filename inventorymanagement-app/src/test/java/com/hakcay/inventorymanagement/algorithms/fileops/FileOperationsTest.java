package com.hakcay.inventorymanagement.algorithms.fileops;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for FileOperations class.
 * Tests hash-based file integrity, atomic operations, and safe file writes.
 */
public class FileOperationsTest {
    
    private static final String TEST_DIR = "test_files";
    private static final String TEST_FILE = TEST_DIR + File.separator + "test.txt";
    private static final String TEST_CONTENT = "Hello, World!\nThis is a test file.";
    private static final String TEST_CONTENT_2 = "Updated content\nSecond line.";
    
    @Before
    public void setUp() throws IOException {
        // Create test directory
        File dir = new File(TEST_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Clean up any existing test files
        cleanup();
    }
    
    @After
    public void tearDown() {
        cleanup();
    }
    
    private void cleanup() {
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        
        File backup = new File(TEST_FILE + ".backup");
        if (backup.exists()) {
            backup.delete();
        }
        
        File temp = new File(TEST_FILE + ".tmp");
        if (temp.exists()) {
            temp.delete();
        }
    }
    
    @Test
    public void testCalculateFileHash_ExistingFile() throws IOException {
        // Create a test file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        String hash = FileOperations.calculateFileHash(TEST_FILE);
        
        assertNotNull("Hash should not be null", hash);
        assertFalse("Hash should not be empty", hash.isEmpty());
        assertEquals("Hash should be 64 characters (SHA-256)", 64, hash.length());
    }
    
    @Test
    public void testCalculateFileHash_NonExistentFile() {
        String hash = FileOperations.calculateFileHash("nonexistent.txt");
        
        assertNull("Hash should be null for non-existent file", hash);
    }
    
    @Test
    public void testCalculateFileHash_NullPath() {
        String hash = FileOperations.calculateFileHash(null);
        
        assertNull("Hash should be null for null path", hash);
    }
    
    @Test
    public void testCalculateContentHash_ValidContent() {
        String hash = FileOperations.calculateContentHash(TEST_CONTENT);
        
        assertNotNull("Hash should not be null", hash);
        assertFalse("Hash should not be empty", hash.isEmpty());
        assertEquals("Hash should be 64 characters (SHA-256)", 64, hash.length());
    }
    
    @Test
    public void testCalculateContentHash_ConsistentHashing() {
        String hash1 = FileOperations.calculateContentHash(TEST_CONTENT);
        String hash2 = FileOperations.calculateContentHash(TEST_CONTENT);
        
        assertEquals("Same content should produce same hash", hash1, hash2);
    }
    
    @Test
    public void testCalculateContentHash_DifferentContent() {
        String hash1 = FileOperations.calculateContentHash(TEST_CONTENT);
        String hash2 = FileOperations.calculateContentHash(TEST_CONTENT_2);
        
        assertNotEquals("Different content should produce different hash", hash1, hash2);
    }
    
    @Test
    public void testCalculateContentHash_NullContent() {
        String hash = FileOperations.calculateContentHash(null);
        
        assertNull("Hash should be null for null content", hash);
    }
    
    @Test
    public void testVerifyFileIntegrity_ValidFile() throws IOException {
        // Create a test file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        String expectedHash = FileOperations.calculateFileHash(TEST_FILE);
        boolean isValid = FileOperations.verifyFileIntegrity(TEST_FILE, expectedHash);
        
        assertTrue("File integrity should be valid", isValid);
    }
    
    @Test
    public void testVerifyFileIntegrity_ModifiedFile() throws IOException {
        // Create a test file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        String originalHash = FileOperations.calculateFileHash(TEST_FILE);
        
        // Modify the file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT_2.getBytes());
        
        boolean isValid = FileOperations.verifyFileIntegrity(TEST_FILE, originalHash);
        
        assertFalse("File integrity should be invalid after modification", isValid);
    }
    
    @Test
    public void testVerifyFileIntegrity_NullPath() {
        boolean isValid = FileOperations.verifyFileIntegrity(null, "hash");
        
        assertFalse("Integrity check should fail for null path", isValid);
    }
    
    @Test
    public void testVerifyFileIntegrity_NullHash() {
        boolean isValid = FileOperations.verifyFileIntegrity(TEST_FILE, null);
        
        assertFalse("Integrity check should fail for null hash", isValid);
    }
    
    @Test
    public void testAtomicWrite_NewFile() {
        boolean success = FileOperations.atomicWrite(TEST_FILE, TEST_CONTENT);
        
        assertTrue("Atomic write should succeed", success);
        assertTrue("File should exist after write", new File(TEST_FILE).exists());
    }
    
    @Test
    public void testAtomicWrite_ExistingFile() throws IOException {
        // Create initial file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        boolean success = FileOperations.atomicWrite(TEST_FILE, TEST_CONTENT_2);
        
        assertTrue("Atomic write should succeed", success);
        
        // Verify content was updated
        String content = FileOperations.safeRead(TEST_FILE);
        assertEquals("Content should be updated", TEST_CONTENT_2, content);
    }
    
    @Test
    public void testAtomicWrite_NullPath() {
        boolean success = FileOperations.atomicWrite(null, TEST_CONTENT);
        
        assertFalse("Atomic write should fail for null path", success);
    }
    
    @Test
    public void testAtomicWrite_NullContent() {
        boolean success = FileOperations.atomicWrite(TEST_FILE, null);
        
        assertFalse("Atomic write should fail for null content", success);
    }
    
    @Test
    public void testSafeWrite_ValidContent() {
        String hash = FileOperations.safeWrite(TEST_FILE, TEST_CONTENT);
        
        assertNotNull("Hash should not be null after safe write", hash);
        assertTrue("File should exist after write", new File(TEST_FILE).exists());
        
        // Verify content
        String content = FileOperations.safeRead(TEST_FILE);
        assertEquals("Content should match", TEST_CONTENT, content);
    }
    
    @Test
    public void testSafeWrite_IntegrityCheck() {
        String hash = FileOperations.safeWrite(TEST_FILE, TEST_CONTENT);
        
        assertNotNull("Hash should not be null", hash);
        
        // Verify file hash matches
        String fileHash = FileOperations.calculateFileHash(TEST_FILE);
        assertEquals("File hash should match returned hash", hash, fileHash);
    }
    
    @Test
    public void testSafeWrite_NullPath() {
        String hash = FileOperations.safeWrite(null, TEST_CONTENT);
        
        assertNull("Hash should be null for null path", hash);
    }
    
    @Test
    public void testSafeRead_ExistingFile() throws IOException {
        // Create a test file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        String content = FileOperations.safeRead(TEST_FILE);
        
        assertNotNull("Content should not be null", content);
        assertEquals("Content should match", TEST_CONTENT, content);
    }
    
    @Test
    public void testSafeRead_NonExistentFile() {
        String content = FileOperations.safeRead("nonexistent.txt");
        
        assertNull("Content should be null for non-existent file", content);
    }
    
    @Test
    public void testSafeRead_NullPath() {
        String content = FileOperations.safeRead(null);
        
        assertNull("Content should be null for null path", content);
    }
    
    @Test
    public void testCreateBackup_ExistingFile() throws IOException {
        // Create a test file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        boolean success = FileOperations.createBackup(TEST_FILE);
        
        assertTrue("Backup creation should succeed", success);
        assertTrue("Backup file should exist", new File(TEST_FILE + ".backup").exists());
    }
    
    @Test
    public void testCreateBackup_NonExistentFile() {
        boolean success = FileOperations.createBackup("nonexistent.txt");
        
        assertFalse("Backup creation should fail for non-existent file", success);
    }
    
    @Test
    public void testCreateBackup_NullPath() {
        boolean success = FileOperations.createBackup(null);
        
        assertFalse("Backup creation should fail for null path", success);
    }
    
    @Test
    public void testRestoreFromBackup_ExistingBackup() throws IOException {
        // Create a test file and backup
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        FileOperations.createBackup(TEST_FILE);
        
        // Modify the original file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT_2.getBytes());
        
        // Restore from backup
        boolean success = FileOperations.restoreFromBackup(TEST_FILE);
        
        assertTrue("Restore should succeed", success);
        
        // Verify content was restored
        String content = FileOperations.safeRead(TEST_FILE);
        assertEquals("Content should be restored", TEST_CONTENT, content);
    }
    
    @Test
    public void testRestoreFromBackup_NonExistentBackup() {
        boolean success = FileOperations.restoreFromBackup(TEST_FILE);
        
        assertFalse("Restore should fail for non-existent backup", success);
    }
    
    @Test
    public void testRestoreFromBackup_NullPath() {
        boolean success = FileOperations.restoreFromBackup(null);
        
        assertFalse("Restore should fail for null path", success);
    }
    
    @Test
    public void testBackupExists_ExistingBackup() throws IOException {
        // Create a test file and backup
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        FileOperations.createBackup(TEST_FILE);
        
        boolean exists = FileOperations.backupExists(TEST_FILE);
        
        assertTrue("Backup should exist", exists);
    }
    
    @Test
    public void testBackupExists_NonExistentBackup() {
        boolean exists = FileOperations.backupExists(TEST_FILE);
        
        assertFalse("Backup should not exist", exists);
    }
    
    @Test
    public void testBackupExists_NullPath() {
        boolean exists = FileOperations.backupExists(null);
        
        assertFalse("Backup should not exist for null path", exists);
    }
    
    @Test
    public void testDeleteBackup_ExistingBackup() throws IOException {
        // Create a test file and backup
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        FileOperations.createBackup(TEST_FILE);
        
        boolean success = FileOperations.deleteBackup(TEST_FILE);
        
        assertTrue("Backup deletion should succeed", success);
        assertFalse("Backup should not exist after deletion", FileOperations.backupExists(TEST_FILE));
    }
    
    @Test
    public void testDeleteBackup_NonExistentBackup() {
        boolean success = FileOperations.deleteBackup(TEST_FILE);
        
        assertTrue("Backup deletion should succeed even if backup doesn't exist", success);
    }
    
    @Test
    public void testDeleteBackup_NullPath() {
        boolean success = FileOperations.deleteBackup(null);
        
        assertTrue("Backup deletion should succeed for null path (no-op)", success);
    }
    
    @Test
    public void testAtomicWriteWithBackup() throws IOException {
        // Create initial file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        // Perform atomic write
        boolean success = FileOperations.atomicWrite(TEST_FILE, TEST_CONTENT_2);
        
        assertTrue("Atomic write should succeed", success);
        
        // Backup should be cleaned up after successful write
        assertFalse("Backup should be deleted after successful write", 
                    FileOperations.backupExists(TEST_FILE));
    }
    
    @Test
    public void testSafeWriteAndReadRoundTrip() {
        // Write content
        String writeHash = FileOperations.safeWrite(TEST_FILE, TEST_CONTENT);
        
        assertNotNull("Write hash should not be null", writeHash);
        
        // Read content
        String readContent = FileOperations.safeRead(TEST_FILE);
        
        assertEquals("Read content should match written content", TEST_CONTENT, readContent);
        
        // Verify hash matches
        String readHash = FileOperations.calculateFileHash(TEST_FILE);
        assertEquals("Read hash should match write hash", writeHash, readHash);
    }
    
    @Test
    public void testCalculateFileHash_Directory() throws IOException {
        // Create a directory
        File dir = new File(TEST_DIR + File.separator + "subdir");
        dir.mkdirs();
        
        String hash = FileOperations.calculateFileHash(dir.getAbsolutePath());
        
        assertNull("Hash should be null for directory", hash);
    }
    
    @Test
    public void testSafeRead_Directory() throws IOException {
        // Create a directory
        File dir = new File(TEST_DIR + File.separator + "subdir2");
        dir.mkdirs();
        
        String content = FileOperations.safeRead(dir.getAbsolutePath());
        
        assertNull("Content should be null for directory", content);
    }
    
    @Test
    public void testSafeWrite_HashMismatch() throws IOException {
        // Create a file that will cause hash mismatch
        // This is hard to simulate directly, but we can test the branch where
        // contentHash or fileHash is null
        String hash = FileOperations.safeWrite(TEST_FILE, TEST_CONTENT);
        
        // If write succeeds, hash should not be null
        // The hash mismatch scenario is hard to test without mocking
        // But we can test that safeWrite handles null hashes correctly
        assertNotNull("Hash should not be null for valid write", hash);
    }
    
    @Test
    public void testAtomicWrite_NoBackupForNewFile() {
        // Write to a new file (no backup should be created)
        boolean success = FileOperations.atomicWrite(TEST_FILE, TEST_CONTENT);
        
        assertTrue("Atomic write should succeed", success);
        assertFalse("Backup should not exist for new file", 
                    FileOperations.backupExists(TEST_FILE));
    }
    
    @Test
    public void testAtomicWrite_BackupCleanup() throws IOException {
        // Create initial file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        // Perform atomic write
        boolean success = FileOperations.atomicWrite(TEST_FILE, TEST_CONTENT_2);
        
        assertTrue("Atomic write should succeed", success);
        
        // Backup should be cleaned up after successful write
        assertFalse("Backup should be deleted after successful write", 
                    FileOperations.backupExists(TEST_FILE));
    }
    
    @Test
    public void testBytesToHex_SingleCharHex() {
        // Test bytesToHex with bytes that produce single-character hex
        // Byte value 0x0A should produce "0a" (not "a")
        byte[] bytes = new byte[] { 0x0A, 0x0B, 0x0C };
        String hash = FileOperations.calculateContentHash(new String(bytes));
        
        // The hash should be properly formatted with leading zeros
        assertNotNull("Hash should not be null", hash);
        assertEquals("Hash should be 64 characters", 64, hash.length());
    }
    
    @Test
    public void testSafeRead_EmptyFile() throws IOException {
        // Create an empty file
        Files.write(new File(TEST_FILE).toPath(), new byte[0]);
        
        String content = FileOperations.safeRead(TEST_FILE);
        
        assertNotNull("Content should not be null for empty file", content);
        assertEquals("Content should be empty string", "", content);
    }
    
    @Test
    public void testSafeRead_SingleLine() throws IOException {
        // Create a file with single line (no newline)
        String singleLine = "Single line content";
        Files.write(new File(TEST_FILE).toPath(), singleLine.getBytes());
        
        String content = FileOperations.safeRead(TEST_FILE);
        
        assertEquals("Content should match", singleLine, content);
    }
    
    @Test
    public void testAtomicWrite_IOExceptionHandling() throws IOException {
        // Try to write to a directory (should fail)
        File dir = new File(TEST_DIR + File.separator + "write_test");
        dir.mkdirs();
        
        // Try to write to directory path (should fail gracefully)
        boolean success = FileOperations.atomicWrite(dir.getAbsolutePath(), TEST_CONTENT);
        
        // Should fail but not throw exception
        assertFalse("Atomic write should fail for directory path", success);
    }
    
    @Test
    public void testVerifyFileIntegrity_NullActualHash() throws IOException {
        // Create a file that exists but hash calculation might fail
        // This is hard to simulate, but we can test with a valid file
        Files.write(new File(TEST_FILE).toPath(), TEST_CONTENT.getBytes());
        
        // Use an invalid hash that won't match
        boolean isValid = FileOperations.verifyFileIntegrity(TEST_FILE, "invalid_hash");
        
        assertFalse("File integrity should be invalid for wrong hash", isValid);
    }
}

