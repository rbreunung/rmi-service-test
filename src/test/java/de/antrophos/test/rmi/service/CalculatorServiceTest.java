package de.antrophos.test.rmi.service;

import de.antrophos.test.rmi.exception.CustomServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit test for the RMI Calculator Service.
 * Tests multiple RMI calls including exception handling.
 * Tests both registry-based and direct RMI connections.
 */
public class CalculatorServiceTest {

    private static final Logger logger = LogManager.getLogger(CalculatorServiceTest.class);

    private static ApplicationContext serverContext;
    private static CalculatorService calculatorServiceViaRegistry;
    private static CalculatorService calculatorServiceDirect;

    @SuppressWarnings("deprecation")
    @BeforeAll
    public static void setUp() throws Exception {
        logger.info("===============================================");
        logger.info("Setting up test environment...");
        logger.info("===============================================");

        // Start the RMI server
        serverContext = new ClassPathXmlApplicationContext("classpath:applicationTestContext.xml");
        logger.info("Spring context loaded successfully");

        // Give the server some time to initialize
        Thread.sleep(2_000L);
        logger.info("Server initialization delay completed");

        // Create RMI client proxy for registry-based connection
        logger.info("Creating RMI proxy for registry-based connection to {}", RmiServiceConfig.SERVICE_URL);
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl(RmiServiceConfig.SERVICE_URL);
        factoryBean.setServiceInterface(CalculatorService.class);
        factoryBean.afterPropertiesSet();
        calculatorServiceViaRegistry = (CalculatorService) factoryBean.getObject();
        logger.info("Registry-based proxy created successfully");

        // Create RMI client proxy for direct connection (without registry)
        logger.info("Creating RMI proxy for direct connection to {}", RmiServiceConfig.DIRECT_SERVICE_URL);
        RmiProxyFactoryBean directFactoryBean = new RmiProxyFactoryBean();
        directFactoryBean.setServiceUrl(RmiServiceConfig.DIRECT_SERVICE_URL);
        directFactoryBean.setServiceInterface(CalculatorService.class);
        directFactoryBean.afterPropertiesSet();
        calculatorServiceDirect = (CalculatorService) directFactoryBean.getObject();
        logger.info("Direct proxy created successfully");

        logger.info("===============================================");
        logger.info("Test setup completed - RMI clients connected");
        logger.info("===============================================");
    }

    @AfterAll
    public static void tearDown() {
        if (serverContext != null) {
            ((ClassPathXmlApplicationContext) serverContext).close();
            logger.info("RMI server stopped and context closed");
        }
    }

    // ============== Registry-Based Connection Tests ==============

    @Test
    public void testAdditionViaRegistry() throws CustomServiceException {
        logger.info("Test 1: Addition via Registry");
        int result = calculatorServiceViaRegistry.add(5, 3);
        assertEquals(8, result);
        logger.info("  5 + 3 = {} ✓", result);
    }

    @Test
    public void testSubtractionViaRegistry() throws CustomServiceException {
        logger.info("Test 2: Subtraction via Registry");
        int result = calculatorServiceViaRegistry.subtract(10, 4);
        assertEquals(6, result);
        logger.info("  10 - 4 = {} ✓", result);
    }

    @Test
    public void testMultiplicationViaRegistry() throws CustomServiceException {
        logger.info("Test 3: Multiplication via Registry");
        int result = calculatorServiceViaRegistry.multiply(6, 7);
        assertEquals(42, result);
        logger.info("  6 * 7 = {} ✓", result);
    }

    @Test
    public void testDivisionViaRegistry() throws CustomServiceException {
        logger.info("Test 4: Division via Registry");
        int result = calculatorServiceViaRegistry.divide(20, 5);
        assertEquals(4, result);
        logger.info("  20 / 5 = {} ✓", result);
    }

    @Test
    public void testMultipleCallsViaRegistry() throws CustomServiceException {
        logger.info("Test 5: Multiple consecutive calls via Registry");

        int result1 = calculatorServiceViaRegistry.add(1, 1);
        assertEquals(2, result1);
        logger.info("  Call 1: 1 + 1 = {} ✓", result1);

        int result2 = calculatorServiceViaRegistry.multiply(3, 4);
        assertEquals(12, result2);
        logger.info("  Call 2: 3 * 4 = {} ✓", result2);

        int result3 = calculatorServiceViaRegistry.subtract(100, 50);
        assertEquals(50, result3);
        logger.info("  Call 3: 100 - 50 = {} ✓", result3);

        int result4 = calculatorServiceViaRegistry.divide(144, 12);
        assertEquals(12, result4);
        logger.info("  Call 4: 144 / 12 = {} ✓", result4);

        int result5 = calculatorServiceViaRegistry.add(999, 1);
        assertEquals(1000, result5);
        logger.info("  Call 5: 999 + 1 = {} ✓", result5);
    }

    // ============== Direct Connection Tests (Without Registry) ==============

    @Test
    public void testAdditionDirect() throws CustomServiceException {
        logger.info("Test 6: Addition via Direct Connection");
        int result = calculatorServiceDirect.add(15, 8);
        assertEquals(23, result);
        logger.info("  15 + 8 = {} (Direct) ✓", result);
    }

    @Test
    public void testSubtractionDirect() throws CustomServiceException {
        logger.info("Test 7: Subtraction via Direct Connection");
        int result = calculatorServiceDirect.subtract(50, 20);
        assertEquals(30, result);
        logger.info("  50 - 20 = {} (Direct) ✓", result);
    }

    @Test
    public void testMultiplicationDirect() throws CustomServiceException {
        logger.info("Test 8: Multiplication via Direct Connection");
        int result = calculatorServiceDirect.multiply(12, 5);
        assertEquals(60, result);
        logger.info("  12 * 5 = {} (Direct) ✓", result);
    }

    @Test
    public void testDivisionDirect() throws CustomServiceException {
        logger.info("Test 9: Division via Direct Connection");
        int result = calculatorServiceDirect.divide(100, 25);
        assertEquals(4, result);
        logger.info("  100 / 25 = {} (Direct) ✓", result);
    }

    @Test
    public void testMultipleCallsDirect() throws CustomServiceException {
        logger.info("Test 10: Multiple consecutive calls via Direct Connection");

        int result1 = calculatorServiceDirect.add(7, 3);
        assertEquals(10, result1);
        logger.info("  Call 1: 7 + 3 = {} (Direct) ✓", result1);

        int result2 = calculatorServiceDirect.multiply(8, 8);
        assertEquals(64, result2);
        logger.info("  Call 2: 8 * 8 = {} (Direct) ✓", result2);

        int result3 = calculatorServiceDirect.subtract(75, 25);
        assertEquals(50, result3);
        logger.info("  Call 3: 75 - 25 = {} (Direct) ✓", result3);

        int result4 = calculatorServiceDirect.divide(256, 16);
        assertEquals(16, result4);
        logger.info("  Call 4: 256 / 16 = {} (Direct) ✓", result4);

        int result5 = calculatorServiceDirect.add(111, 222);
        assertEquals(333, result5);
        logger.info("  Call 5: 111 + 222 = {} (Direct) ✓", result5);
    }

    // ============== Exception Handling Tests - Division by Zero ==============

    @Test
    public void testDivisionByZeroViaRegistry() {
        logger.info("Test 11: Division by zero via Registry (should throw exception)");
        try {
            calculatorServiceViaRegistry.divide(10, 0);
            fail("Expected CustomServiceException for division by zero");
        } catch (CustomServiceException e) {
            assertTrue(e.getMessage().contains("Division by zero"));
            logger.info("  Exception caught correctly via Registry: {} ✓", e.getMessage());
        }
    }

    @Test
    public void testDivisionByZeroDirect() {
        logger.info("Test 12: Division by zero via Direct Connection (should throw exception)");
        try {
            calculatorServiceDirect.divide(42, 0);
            fail("Expected CustomServiceException for division by zero");
        } catch (CustomServiceException e) {
            assertTrue(e.getMessage().contains("Division by zero"));
            logger.info("  Exception caught correctly via Direct: {} ✓", e.getMessage());
        }
    }

    // ============== Exception Handling Tests - Intentional Exception ==============

    @Test
    public void testTriggerExceptionViaRegistry() {
        logger.info("Test 13: Trigger exception via Registry");
        try {
            calculatorServiceViaRegistry.triggerException();
            fail("Expected CustomServiceException to be thrown");
        } catch (CustomServiceException e) {
            assertTrue(e.getMessage().contains("test exception"));
            logger.info("  Intentional exception caught via Registry: {} ✓", e.getMessage());
        }
    }

    @Test
    public void testTriggerExceptionDirect() {
        logger.info("Test 14: Trigger exception via Direct Connection");
        try {
            calculatorServiceDirect.triggerException();
            fail("Expected CustomServiceException to be thrown");
        } catch (CustomServiceException e) {
            assertTrue(e.getMessage().contains("test exception"));
            logger.info("  Intentional exception caught via Direct: {} ✓", e.getMessage());
        }
    }

    // ============== Exception Behavior Verification ==============

    @Test
    public void testExceptionMessagePreservation() {
        logger.info("Test 15: Exception message preservation across RMI");
        String expectedMessage = "Division by zero is not allowed";
        try {
            calculatorServiceViaRegistry.divide(5, 0);
            fail("Expected CustomServiceException");
        } catch (CustomServiceException e) {
            assertEquals(expectedMessage, e.getMessage());
            logger.info("  Exception message preserved correctly: {} ✓", e.getMessage());
        }
    }

    @Test
    public void testExceptionType() {
        logger.info("Test 16: Exception type verification");
        try {
            calculatorServiceDirect.divide(10, 0);
            fail("Expected CustomServiceException");
        } catch (CustomServiceException e) {
            assertTrue(e instanceof CustomServiceException);
            assertNotNull(e.getMessage());
            logger.info("  Exception type and message verified ✓");
        }
    }

    @Test
    public void testMultipleExceptionsInSequence() {
        logger.info("Test 17: Multiple exceptions in sequence");
        int exceptionCount = 0;

        // First exception
        try {
            calculatorServiceViaRegistry.divide(10, 0);
        } catch (CustomServiceException e) {
            exceptionCount++;
            logger.info("  Exception 1 caught via Registry: {}", e.getMessage());
        }

        // Second exception
        try {
            calculatorServiceDirect.divide(20, 0);
        } catch (CustomServiceException e) {
            exceptionCount++;
            logger.info("  Exception 2 caught via Direct: {}", e.getMessage());
        }

        // Third exception
        try {
            calculatorServiceViaRegistry.triggerException();
        } catch (CustomServiceException e) {
            exceptionCount++;
            logger.info("  Exception 3 caught via Registry: {}", e.getMessage());
        }

        // Fourth exception
        try {
            calculatorServiceDirect.triggerException();
        } catch (CustomServiceException e) {
            exceptionCount++;
            logger.info("  Exception 4 caught via Direct: {}", e.getMessage());
        }

        assertEquals(4, exceptionCount);
        logger.info("  All 4 exceptions caught successfully ✓");
    }
}