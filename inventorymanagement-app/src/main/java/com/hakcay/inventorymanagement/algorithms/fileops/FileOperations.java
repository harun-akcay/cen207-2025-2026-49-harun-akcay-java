package com.hakcay.inventorymanagement.algorithms.fileops;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * File Operations utility class for hash-based file integrity checking,
 * atomic file operations, and safe file writes.
 * Provides persistent storage operations with data integrity guarantees.
 */
public class FileOperations {
    
    private static final String BACKUP_SUFFIX = ".backup";
    private static final String TEMP_SUFFIX = ".tmp";
    private static final String HASH_ALGORITHM = "SHA-256";
    
    /**
     * Calculates the SHA-256 hash of a file for integrity checking.
     *
     * @param filePath the path to the file
     * @return the hexadecimal hash string, or null if calculation fails
     */
    public static String calculateFileHash(String filePath) {
        if (filePath == null) {
            return null;
        }
        
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }
            
            byte[] hashBytes = digest.digest();
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }
    
    /**
     * Calculates the hash of a string content.
     *
     * @param content the content to hash
     * @return the hexadecimal hash string, or null if calculation fails
     */
    public static String calculateContentHash(String content) {
        if (content == null) {
            return null;
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(content.getBytes("UTF-8"));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            return null;
        }
    }
    
    /**
     * Verifies file integrity by comparing current hash with expected hash.
     *
     * @param filePath the path to the file
     * @param expectedHash the expected hash value
     * @return true if hashes match, false otherwise
     */
    public static boolean verifyFileIntegrity(String filePath, String expectedHash) {
        if (filePath == null || expectedHash == null) {
            return false;
        }
        
        String actualHash = calculateFileHash(filePath);
        return actualHash != null && actualHash.equals(expectedHash);
    }
    
    /**
     * Performs an atomic file write operation.
     * Writes to a temporary file first, then atomically replaces the original file.
     * Creates a backup of the original file before replacement.
     * Normalizes line separators in content to \n for consistency.
     *
     * @param filePath the path to the file
     * @param content the content to write
     * @return true if the operation succeeds, false otherwise
     */
    public static boolean atomicWrite(String filePath, String content) {
        if (filePath == null || content == null) {
            return false;
        }
        
        // Normalize line separators to \n
        String normalizedContent = content.replace("\r\n", "\n").replace("\r", "\n");
        
        File file = new File(filePath);
        File tempFile = new File(filePath + TEMP_SUFFIX);
        File backupFile = new File(filePath + BACKUP_SUFFIX);
        
        try {
            // Write to temporary file first
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(normalizedContent);
            }
            
            // Create backup if original file exists
            if (file.exists()) {
                Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            
            // Atomically replace original file with temporary file
            Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            
            // Clean up backup file after successful write
            if (backupFile.exists()) {
                backupFile.delete();
            }
            
            return true;
        } catch (IOException e) {
            // Restore from backup if write failed
            if (backupFile.exists() && file.exists()) {
                try {
                    Files.copy(backupFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException restoreException) {
                    // Backup restore failed
                }
            }
            
            // Clean up temporary file
            if (tempFile.exists()) {
                tempFile.delete();
            }
            
            return false;
        }
    }
    
    /**
     * Safely writes content to a file with integrity checking.
     * Uses atomic write and calculates hash for verification.
     *
     * @param filePath the path to the file
     * @param content the content to write
     * @return the hash of the written content, or null if write fails
     */
    public static String safeWrite(String filePath, String content) {
        if (!atomicWrite(filePath, content)) {
            return null;
        }
        
        // Verify integrity after write
        String contentHash = calculateContentHash(content);
        String fileHash = calculateFileHash(filePath);
        
        if (contentHash != null && fileHash != null && contentHash.equals(fileHash)) {
            return fileHash;
        }
        
        return null;
    }
    
    /**
     * Reads content from a file safely.
     * Normalizes line separators to \n for consistency.
     *
     * @param filePath the path to the file
     * @return the file content as a string, or null if read fails
     */
    public static String safeRead(String filePath) {
        if (filePath == null) {
            return null;
        }
        
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (!firstLine) {
                    content.append('\n');
                }
                content.append(line);
                firstLine = false;
            }
            
            return content.toString();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Creates a backup of a file.
     *
     * @param filePath the path to the file
     * @return true if backup succeeds, false otherwise
     */
    public static boolean createBackup(String filePath) {
        if (filePath == null) {
            return false;
        }
        
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        
        File backupFile = new File(filePath + BACKUP_SUFFIX);
        try {
            Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Restores a file from backup.
     *
     * @param filePath the path to the file
     * @return true if restore succeeds, false otherwise
     */
    public static boolean restoreFromBackup(String filePath) {
        if (filePath == null) {
            return false;
        }
        
        File backupFile = new File(filePath + BACKUP_SUFFIX);
        if (!backupFile.exists() || !backupFile.isFile()) {
            return false;
        }
        
        File file = new File(filePath);
        try {
            Files.copy(backupFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Checks if a backup file exists for the given file.
     *
     * @param filePath the path to the file
     * @return true if backup exists, false otherwise
     */
    public static boolean backupExists(String filePath) {
        if (filePath == null) {
            return false;
        }
        
        File backupFile = new File(filePath + BACKUP_SUFFIX);
        return backupFile.exists() && backupFile.isFile();
    }
    
    /**
     * Deletes a backup file.
     *
     * @param filePath the path to the file
     * @return true if deletion succeeds or backup doesn't exist, false otherwise
     */
    public static boolean deleteBackup(String filePath) {
        if (filePath == null) {
            return true; // Null path, consider it deleted (no-op)
        }
        
        File backupFile = new File(filePath + BACKUP_SUFFIX);
        if (backupFile.exists()) {
            return backupFile.delete();
        }
        return true; // Backup doesn't exist, consider it deleted
    }
    
    /**
     * Converts a byte array to hexadecimal string.
     *
     * @param bytes the byte array
     * @return the hexadecimal string representation
     */
    private static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

