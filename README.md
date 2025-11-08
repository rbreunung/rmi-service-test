# Spring Framework 5 RMI Service Example

This project demonstrates a simple Java 8 + Spring Framework 5 RMI-based service with custom exception handling.

## Project Structure

```
spring-rmi-service/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── rmi/
│   │   │               ├── RmiServer.java
│   │   │               ├── exception/
│   │   │               │   └── CustomServiceException.java
│   │   │               └── service/
│   │   │                   ├── CalculatorService.java
│   │   │                   └── impl/
│   │   │                       └── CalculatorServiceImpl.java
│   │   └── resources/
│   │       └── applicationContext.xml
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── rmi/
│       │               ├── RmiServiceConfig.java
│       │               └── CalculatorServiceTest.java
│       └── resources/
│           └── applicationTestContext.xml
└── README.md
```

## Features

- **Java 8** compatibility
- **Spring Framework 5** (version 5.3.39)
- **XML-based bean configuration** (no annotations)
- **RMI registry only variant** exposed on port **1211**
- **RMI registry including service** exposed on port **12172**
- **Custom exception** (`CustomServiceException`) that can be thrown across RMI
- **Unit tests** with multiple RMI call scenarios

## RMI Service Interface

The `CalculatorService` interface provides the following methods:
- `int add(int a, int b)` - Adds two numbers
- `int subtract(int a, int b)` - Subtracts two numbers
- `int multiply(int a, int b)` - Multiplies two numbers
- `int divide(int a, int b)` - Divides two numbers (throws exception on division by zero)
- `void triggerException()` - Intentionally throws a custom exception

All methods can throw `CustomServiceException`.

## Build Instructions

### Prerequisites
- Java 8 JDK
- Maven 3.x

### Build the project
```bash
mvn clean compile
```

### Run tests
```bash
mvn test
```

## Running the Server

This server is not intended for productive use. This is why [applicationContext.xml](src/main/resources/applicationContext.xml)
only contains the bean definition. RMI server configuration is done only in the test setup
[applicationTestContext.xml](src/test/resources/applicationTestContext.xml) where unit tests
set up the environment.

## License

This is an example project for educational purposes.
