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
│       │               └── CalculatorServiceTest.java
│       └── resources/
│           └── applicationContext.xml
└── README.md
```

## Features

- **Java 8** compatibility
- **Spring Framework 5** (version 5.3.30)
- **XML-based bean configuration** (no annotations)
- **RMI service** exposed on port **1211**
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

### From command line:
```bash
mvn exec:java -Dexec.mainClass="com.example.rmi.RmiServer"
```

### Or compile and run manually:
```bash
mvn clean package
java -cp target/classes:~/.m2/repository/org/springframework/spring-context/5.3.30/spring-context-5.3.30.jar:~/.m2/repository/org/springframework/spring-beans/5.3.30/spring-beans-5.3.30.jar:~/.m2/repository/org/springframework/spring-core/5.3.30/spring-core-5.3.30.jar:~/.m2/repository/org/springframework/spring-context-support/5.3.30/spring-context-support-5.3.30.jar:~/.m2/repository/org/springframework/spring-aop/5.3.30/spring-aop-5.3.30.jar:~/.m2/repository/commons-logging/commons-logging/1.2/commons-logging-1.2.jar com.example.rmi.RmiServer
```

The server will start and display:
```
RMI Server started successfully!
Service 'CalculatorService' is now available on port 1211
Press Ctrl+C to stop the server...
```

## Configuration Details

### Spring XML Configuration (applicationContext.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Service Implementation Bean -->
    <bean id="calculatorService" 
          class="com.example.rmi.service.impl.CalculatorServiceImpl"/>

    <!-- RMI Registry on port 1211 -->
    <bean id="rmiRegistry" 
          class="org.springframework.remoting.rmi.RmiRegistryFactoryBean">
        <property name="port" value="1211"/>
    </bean>

    <!-- RMI Service Exporter -->
    <bean id="calculatorServiceExporter" 
          class="org.springframework.remoting.rmi.RmiServiceExporter">
        <property name="service" ref="calculatorService"/>
        <property name="serviceInterface" value="com.example.rmi.service.CalculatorService"/>
        <property name="serviceName" value="CalculatorService"/>
        <property name="registryPort" value="1211"/>
    </bean>

</beans>
```

### Important Notes

1. **Port Configuration**: The originally requested port 121172 exceeds the valid port range (1-65535). The service is configured to use port **1211** instead.

2. **Bean Definitions**: All beans are defined in XML as requested:
    - `calculatorService` - The service implementation
    - `rmiRegistry` - Creates the RMI registry
    - `calculatorServiceExporter` - Exposes the service via RMI

3. **Custom Exception**: `CustomServiceException` extends `Exception` and is `Serializable`, making it suitable for RMI transmission.

## Unit Tests

The `CalculatorServiceTest` class includes the following test cases:

1. **testAddition** - Tests the add method
2. **testSubtraction** - Tests the subtract method
3. **testMultiplication** - Tests the multiply method
4. **testDivision** - Tests the divide method
5. **testDivisionByZero** - Tests custom exception on division by zero
6. **testTriggerException** - Tests intentional exception throwing
7. **testMultipleCalls** - Tests multiple consecutive RMI calls

The tests automatically:
- Start the RMI server in `@BeforeClass`
- Create an RMI client proxy
- Execute multiple RMI calls
- Verify exception handling
- Stop the server in `@AfterClass`

## Dependencies

- **Spring Context** 5.3.39
- **Spring Context Support** 5.3.39 (for RMI support)
- **Spring Test** 5.3.39
- **JUnit** 4.13.2

## License

This is an example project for educational purposes.
