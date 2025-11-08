package de.antrophos.test.rmi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RmiServiceConfig {

    private static final Logger logger = LogManager.getLogger(RmiServiceConfig.class);

    // Static port configuration (not best practice, but required)
    public static final int REGISTRY_PORT = 1211;
    public static final int CALCULATOR_SERVICE_PORT = 12172;

    // Service name
    public static final String SERVICE_NAME = "CalculatorService";

    // Service URL for RMI lookup
    public static final String SERVICE_URL = "rmi://localhost:" + REGISTRY_PORT + "/" + SERVICE_NAME;

    // Direct service URL (without registry)
    public static final String DIRECT_SERVICE_URL = "rmi://localhost:" + CALCULATOR_SERVICE_PORT + "/" + SERVICE_NAME;

    static {
        logger.info("RMI Service Configuration initialized:");
        logger.info("  Registry Port: {}", REGISTRY_PORT);
        logger.info("  Calculator Service Port: {}", CALCULATOR_SERVICE_PORT);
        logger.info("  Service Name: {}", SERVICE_NAME);
        logger.info("  Service URL: {}", SERVICE_URL);
        logger.info("  Direct Service URL: {}", DIRECT_SERVICE_URL);
    }

    private RmiServiceConfig() {
        // Prevent instantiation
    }
}