package de.antrophos.test.rmi.service;

import de.antrophos.test.rmi.exception.CustomServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test for the RMI Calculator Service.
 * Tests multiple RMI calls including exception handling.
 */
@SpringJUnitConfig(locations = {"classpath:applicationContext.xml"})
public class CalculatorServiceTest1 {

    private static CalculatorService calculatorService;

    @BeforeEach
    public void setUp() throws Exception {

        // Give the server some time to initialize
        Thread.sleep(2_000L);

        // Create RMI client proxy
        RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
        factoryBean.setServiceUrl("rmi://localhost:1211/CalculatorService");
        factoryBean.setServiceInterface(CalculatorService.class);
        factoryBean.afterPropertiesSet();

        calculatorService = (CalculatorService) factoryBean.getObject();

        System.out.println("Test setup completed - RMI client connected");
    }

    @AfterAll
    public static void tearDown() throws Exception {
    }

    @Test
    public void testAddition() throws CustomServiceException {
        System.out.println("Test 1: Addition");
        int result = calculatorService.add(5, 3);
        assertEquals(8, result);
        System.out.println("  5 + 3 = " + result + " ✓");
    }

    @Test
    public void testSubtraction() throws CustomServiceException {
        System.out.println("Test 2: Subtraction");
        int result = calculatorService.subtract(10, 4);
        assertEquals(6, result);
        System.out.println("  10 - 4 = " + result + " ✓");
    }

    @Test
    public void testMultiplication() throws CustomServiceException {
        System.out.println("Test 3: Multiplication");
        int result = calculatorService.multiply(6, 7);
        assertEquals(42, result);
        System.out.println("  6 * 7 = " + result + " ✓");
    }

    @Test
    public void testDivision() throws CustomServiceException {
        System.out.println("Test 4: Division");
        int result = calculatorService.divide(20, 5);
        assertEquals(4, result);
        System.out.println("  20 / 5 = " + result + " ✓");
    }

    @Test
    public void testDivisionByZero() {
        System.out.println("Test 5: Division by zero (should throw exception)");
        try {
            calculatorService.divide(10, 0);
            fail("Expected CustomServiceException for division by zero");
        } catch (CustomServiceException e) {
            assertTrue(e.getMessage().contains("Division by zero"));
            System.out.println("  Exception caught correctly: " + e.getMessage() + " ✓");
        }
    }

    @Test
    public void testTriggerException() {
        System.out.println("Test 6: Trigger exception intentionally");
        try {
            calculatorService.triggerException();
            fail("Expected CustomServiceException to be thrown");
        } catch (CustomServiceException e) {
            assertTrue(e.getMessage().contains("test exception"));
            System.out.println("  Exception caught correctly: " + e.getMessage() + " ✓");
        }
    }

    @Test
    public void testMultipleCalls() throws CustomServiceException {
        System.out.println("Test 7: Multiple consecutive calls");

        int result1 = calculatorService.add(1, 1);
        assertEquals(2, result1);
        System.out.println("  Call 1: 1 + 1 = " + result1 + " ✓");

        int result2 = calculatorService.multiply(3, 4);
        assertEquals(12, result2);
        System.out.println("  Call 2: 3 * 4 = " + result2 + " ✓");

        int result3 = calculatorService.subtract(100, 50);
        assertEquals(50, result3);
        System.out.println("  Call 3: 100 - 50 = " + result3 + " ✓");

        int result4 = calculatorService.divide(144, 12);
        assertEquals(12, result4);
        System.out.println("  Call 4: 144 / 12 = " + result4 + " ✓");

        int result5 = calculatorService.add(999, 1);
        assertEquals(1000, result5);
        System.out.println("  Call 5: 999 + 1 = " + result5 + " ✓");
    }
}
