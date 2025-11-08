package de.antrophos.test.rmi.service;

import de.antrophos.test.rmi.exception.CustomServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(locations = {"classpath:applicationContext.xml"}, classes = CalculatorServiceDirectTest.ClientConfig.class)
public class CalculatorServiceDirectTest {

    private static final String DIRECT_SERVICE_URL = "rmi://localhost:12172/CalculatorService";
    private static final Logger logger = LogManager.getLogger(CalculatorServiceDirectTest.class);

    // Autowire the proxy directly. Spring now manages its creation.
    @Autowired
    private CalculatorService calculatorServiceDirect;

    // Remove the manual setUp() method entirely, Spring handles the initialization

    // Define the client proxy bean within the test context
    @Configuration
    static class ClientConfig {
        @Bean
        public RmiProxyFactoryBean calculatorServiceDirect() {
            logger.info("create direct");
            RmiProxyFactoryBean factoryBean = new RmiProxyFactoryBean();
            factoryBean.setServiceUrl(DIRECT_SERVICE_URL);
            factoryBean.setServiceInterface(CalculatorService.class);
            // We don't need to call afterPropertiesSet() manually here;
            // Spring's bean lifecycle handles it.
            return factoryBean;
        }
    }

    // ============== Direct Connection Tests (Without Registry) ==============

    @Test
    public void testAdditionDirect() throws CustomServiceException {
        logger.info("Test 6: Addition via Direct Connection");
        // The service is ready to use immediately
        int result = calculatorServiceDirect.add(15, 8);
        assertEquals(23, result);
        logger.info("  15 + 8 = {} (Direct) ✓", result);
    }

    // ... all other tests ...
}