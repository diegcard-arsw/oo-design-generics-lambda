# Statistics Calculator

A Java application that calculates the mean and standard deviation of a set of real numbers read from a file, using a custom implementation of a LinkedList compliant with Java's Collections API.

## Project Information

- **Author**: Diego Cardenas ![Diego Cardenas](https://github.com/diegcard)
- **Version**: 1.0.0
- **Java Version**: 11+
- **Build Tool**: Maven

## Features

- Custom LinkedList implementation compliant with Java Collections API
- Statistical calculations (mean and standard deviation)
- File-based data input support
- Comprehensive error handling
- Full unit test coverage
- Javadoc documentation

## Design Overview

### Architecture

The application follows a modular design pattern with separation of concerns:

```
edu.escuelaing.arsw.statistics/
├── CustomLinkedList.java     - Custom implementation of LinkedList
├── StatisticsCalculator.java - Statistical computation engine
├── FileDataReader.java       - File I/O operations
└── StatisticsApp.java        - Main application and CLI interface
```

### Core Components

#### 1. CustomLinkedList\<E\>

A complete implementation of Java's `List<E>` interface using a doubly-linked list structure:

- **Design Pattern**: Iterator pattern for traversal
- **Data Structure**: Doubly-linked nodes with head and tail pointers
- **Performance**: O(1) insertion/deletion at ends, O(n) random access
- **Features**:
  - Full Collections API compliance
  - Support for null values
  - Custom ListIterator implementation
  - Concurrent modification detection

**Key Design Decisions**:
- Used doubly-linked structure for efficient bidirectional traversal
- Maintained size counter for O(1) size operations
- Implemented fail-fast iterators with modification count tracking

#### 2. StatisticsCalculator

Pure utility class for statistical computations:

- **Design Pattern**: Static utility methods (no state)
- **Formulas Implemented**:
  - Mean: `μ = (Σ xi) / n`
  - Standard Deviation: `σ = √[(Σ (xi - μ)²) / (n - 1)]`
- **Error Handling**: Comprehensive validation for edge cases

**Key Design Decisions**:
- Used sample standard deviation (n-1) as per assignment requirements
- Immutable result objects for thread safety
- Extensive parameter validation to prevent runtime errors

#### 3. FileDataReader

Handles all file I/O operations with robust error handling:

- **Design Pattern**: Static utility methods
- **Supported Formats**: Text files with one number per line
- **Features**:
  - Multiple input sources (file path, InputStream)
  - Empty line tolerance
  - Detailed error reporting with line numbers

**Key Design Decisions**:
- Used Java NIO for modern file handling
- Implemented both file and stream-based reading for flexibility
- Comprehensive validation before processing

#### 4. StatisticsApp

Main application class providing CLI interface:

- **Design Pattern**: Facade pattern for system interaction
- **Features**:
  - Command-line argument processing
  - Formatted output with proper locale support
  - Comprehensive error handling and user feedback

### Data Flow

```
File Input → FileDataReader → CustomLinkedList → StatisticsCalculator → Results
     ↓              ↓              ↓                    ↓
   Validation   Parsing        Storage            Computation
```

## Installation and Usage

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Building the Project

```bash
# Clone the repository
git clone https://github.com/diegcard-arsw/oo-design-generics-lambda.git
cd oo-design-generics-lambda

# Compile and run tests
mvn clean test

# Create executable JAR
mvn clean package

# Generate documentation
mvn javadoc:javadoc
```

### Running the Application

#### Using Maven Exec Plugin
```bash
mvn exec:java -Dexec.args="path/to/your/data.txt"
```

#### Using JAR File
```bash
java -jar target/statistics-calculator-1.0.0.jar path/to/your/data.txt
```

#### Example Usage
```bash
# Using provided test data
mvn exec:java -Dexec.args="src/main/resources/data/column1.txt"
mvn exec:java -Dexec.args="src/main/resources/data/column2.txt"
```

### Input File Format

- Text file with one number per line
- Empty lines are ignored
- Supports both integers and decimals
- Example:
```
160
591.5
114
229.4
```

## Testing

The project includes comprehensive unit tests covering:

- CustomLinkedList functionality and Collections API compliance
- Statistical calculations with known datasets
- File reading operations and error conditions
- Integration testing of the complete application

### Running Tests
```bash
mvn test
```
## Project Structure

```
.
├── pom.xml                                 # Maven configuration
├── README.md                              # This file
├── src/
│   ├── main/
│   │   ├── java/edu/escuelaing/arsw/statistics/
│   │   │   ├── CustomLinkedList.java     # Custom LinkedList implementation
│   │   │   ├── StatisticsCalculator.java # Statistical calculations
│   │   │   ├── FileDataReader.java       # File I/O operations
│   │   │   └── StatisticsApp.java        # Main application
│   │   └── resources/data/
│   │       ├── column1.txt               # Test data (Column 1)
│   │       └── column2.txt               # Test data (Column 2)
```

## License

This project is developed as part of an academic assignment for ARSW (Architectures and Software Patterns) course.