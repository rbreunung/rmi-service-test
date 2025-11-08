package de.antrophos.test.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RMI Server Application.
 * Starts the Spring context and exposes the RMI service.
 */
public class RmiServer {

    public static void main(String[] args) {
        try {
            // Load Spring context from XML configuration
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

            System.out.println("RMI Server started successfully!");
            System.out.println("Service 'CalculatorService' is now available on port 1211");
            System.out.println("Press Ctrl+C to stop the server...");

            // Keep the server running
            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("Failed to start RMI Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

