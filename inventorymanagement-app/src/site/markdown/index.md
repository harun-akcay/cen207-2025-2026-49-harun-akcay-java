# About inventorymanagement-app

**Inventory Management Application** - A comprehensive Java-based inventory management system with advanced data structures and algorithms for managing materials, projects, and expenses.

## Project Overview

This is an Inventory Management Application developed as a Java Maven project with JUnit4. The application provides comprehensive functionality for managing materials, projects, and expenses with advanced data structures and algorithms implementation. The system is designed to handle complex inventory operations efficiently while maintaining data integrity and providing robust error handling.

## Core Functionality

### Material Management
- Complete CRUD operations for inventory materials
- Undo/redo functionality for material operations
- Efficient storage and retrieval using advanced data structures
- Material tracking with quantity and cost management

### Project Management
- Project creation, update, and deletion
- Dependency management between projects
- Cycle detection using graph algorithms
- Project search and filtering capabilities
- Graph-based algorithms (BFS, DFS) for dependency analysis
- Strongly Connected Components detection

### Expense Management
- Expense logging and tracking
- Association with projects and materials
- Expense reporting and analysis
- Total expense calculation per project

## Advanced Data Structures

### B+ Trees
- Efficient indexing for large datasets
- Range queries with optimal performance
- Balanced tree structure for consistent access times

### Hash Tables
- Fast key-value lookups with O(1) average time complexity
- Dynamic resizing for optimal memory usage
- Collision resolution using chaining

## Algorithm Implementations

### Huffman Coding
- Data compression algorithm for efficient storage
- Frequency-based encoding
- Optimal prefix codes for data representation

### KMP Algorithm
- Efficient string pattern matching
- Linear time complexity for pattern searches
- Used for searching and filtering operations

### Graph Algorithms
- **BFS (Breadth-First Search)**: For finding shortest paths and dependencies
- **DFS (Depth-First Search)**: For dependency traversal and cycle detection
- **Strongly Connected Components**: For identifying circular dependencies

## Data Integrity Features

### Atomic File Operations
- Safe file writes with integrity checks
- Transaction-like behavior for data consistency
- Prevention of data corruption during write operations

### Backup and Restore
- Automatic backup creation before critical operations
- Restore functionality for data recovery
- Integrity verification for backup files

## Quality Metrics

- **Test Coverage**: 93.27% (JaCoCo)
  - Comprehensive unit tests covering all major components
  - Edge case testing and error handling validation
  - 716 test cases executed successfully

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

## Project Information

- **Version**: 1.0-SNAPSHOT
- **Organization**: Recep Tayyip Erdoğan University (RTEU)
- **Inception Year**: 2025
- **Build System**: Maven 3.x
- **Java Version**: 1.8

## Development Team

- **Harun Akcay** - Developer, Designer
- **Sudenaz Orhan** - Developer
- **Zumre Uykun** - Developer
- **Guler Dinc** - Developer

## Getting Started

### Prerequisites
- JDK 8 or higher
- Apache Maven 3.6+
- Git (for cloning the repository)

### Building the Project
```bash
mvn clean test site package
```

### Running the Application
```bash
java -jar target/inventorymanagement-app-1.0-SNAPSHOT.jar
```

### Viewing Documentation
```bash
mvn site:run
```
Then navigate to `http://localhost:9000` in your browser.

## Documentation Links

- [Project Overview](./overview.html) - Detailed project overview and features
- [JaCoCo Test Coverage Report](./jacoco/index.html) - View detailed test coverage metrics
- [Coverxygen Documentation Coverage Report](./coverxygen/index.html) - View documentation coverage analysis
- [Doxygen API Documentation](./doxygen/html/index.html) - Browse comprehensive API documentation
- [Project README](./readme.html) - Full project documentation and setup instructions
- [Project Summary](./summary.html) - Project summary and statistics

## Repository

- **GitHub**: [https://github.com/ucoruh/eclipse-java-maven-template](https://github.com/ucoruh/eclipse-java-maven-template)

## Support

For questions, issues, or contributions, please refer to the project repository or contact the development team.

