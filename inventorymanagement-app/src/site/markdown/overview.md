# Inventory Management Application

Welcome to the Inventory Management Application! This project is designed to provide comprehensive functionality for managing materials, projects, and expenses with advanced data structures and algorithms implementation. The application offers a robust solution for inventory tracking, project management, and expense logging with efficient data storage and retrieval mechanisms.

## Features

### Material Management
- **Complete CRUD Operations**: Add, view, update, and remove materials from inventory
- **Undo/Redo Functionality**: Revert or redo material operations for better user experience
- **Efficient Storage**: Advanced data structures ensure fast access and retrieval
- **Quantity and Cost Tracking**: Monitor material quantities and unit costs

### Project Management
- **Project Lifecycle Management**: Create, update, and track projects throughout their lifecycle
- **Dependency Management**: Define and manage dependencies between projects
- **Cycle Detection**: Automatically detect circular dependencies using graph algorithms
- **Advanced Search**: Search projects by name, goal, or status
- **Graph Algorithms**: BFS and DFS for dependency analysis and traversal
- **Strongly Connected Components**: Identify circular dependencies in project graphs

### Expense Management
- **Expense Logging**: Record expenses associated with projects and materials
- **Expense Tracking**: Track all expenses with detailed descriptions
- **Project Expense Calculation**: Calculate total expenses for specific projects
- **Expense Reporting**: View and analyze expense data

### Advanced Data Structures
- **B+ Trees**: Efficient indexing and range queries for large datasets
  - Balanced tree structure for consistent access times
  - Optimal for range-based searches
  - Memory-efficient storage

- **Hash Tables**: Fast key-value lookups with O(1) average time complexity
  - Dynamic resizing for optimal memory usage
  - Collision resolution using chaining
  - Efficient for frequent lookups

### Algorithm Implementations
- **Huffman Coding**: Data compression algorithm for efficient storage
  - Frequency-based encoding
  - Optimal prefix codes
  - Reduced storage requirements

- **KMP Algorithm**: Efficient string pattern matching
  - Linear time complexity for pattern searches
  - Used for searching and filtering operations
  - Handles overlapping patterns

- **Graph Algorithms**:
  - **BFS (Breadth-First Search)**: Find shortest paths and dependencies
  - **DFS (Depth-First Search)**: Dependency traversal and cycle detection
  - **Strongly Connected Components**: Identify circular dependencies

### Data Integrity
- **Atomic File Operations**: Safe file writes with integrity checks
  - Transaction-like behavior for data consistency
  - Prevention of data corruption during write operations
  - Automatic rollback on failure

- **Backup and Restore**: Automatic backup creation and restoration
  - Backup before critical operations
  - Restore functionality for data recovery
  - Integrity verification for backup files

## Usage

To use this project, follow these steps:

1. **Build the Project**: 
   ```bash
   mvn clean test site package
   ```
   This command will compile the code, run all tests, generate documentation, and create a JAR file.

2. **Run the Application**: 
   ```bash
   java -jar target/inventorymanagement-app-1.0-SNAPSHOT.jar
   ```
   This will start the interactive console application where you can manage materials, projects, and expenses.

3. **View Documentation**: 
   ```bash
   mvn site:run
   ```
   Then navigate to `http://localhost:9000` in your browser to view:
   - Project documentation
   - Test coverage reports (JaCoCo)
   - Documentation coverage reports (Coverxygen)
   - API documentation (Doxygen)

## Quality Metrics

- **Test Coverage**: 93.27% (JaCoCo)
  - 716 test cases executed successfully
  - Comprehensive unit tests covering all major components
  - Edge case testing and error handling validation

- **Documentation Coverage**: 97.8% (Coverxygen)
  - Complete API documentation with Doxygen
  - Inline code documentation
  - Comprehensive class and method descriptions

## Technology Stack

- **Language**: Java 8
- **Build Tool**: Apache Maven
- **Testing Framework**: JUnit 4.13.2
- **Logging**: SLF4J with Logback
- **Documentation**: Doxygen with Coverxygen
- **Code Coverage**: JaCoCo

## Documentation

For more detailed information, refer to the following documentation:

- [About Page](./index.html) - Project overview and introduction
- [JaCoCo Test Coverage Report](./jacoco/index.html) - View detailed test coverage metrics
- [Coverxygen Documentation Coverage Report](./coverxygen/index.html) - View documentation coverage analysis
- [Doxygen API Documentation](./doxygen/html/index.html) - Browse comprehensive API documentation
- [Project README](./readme.html) - Full project documentation and setup instructions
- [Project Summary](./summary.html) - Project summary and statistics

