package de.antrophos.test.rmi.service.calculator;

import de.antrophos.test.rmi.exception.CustomServiceException;
import de.antrophos.test.rmi.service.CalculatorService;

/**
 * Implementation of the CalculatorService.
 * This class contains the actual business logic.
 */
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public int add(int a, int b) throws CustomServiceException {
        try {
            return a + b;
        } catch (Exception e) {
            throw new CustomServiceException("Error during addition", e);
        }
    }

    @Override
    public int subtract(int a, int b) throws CustomServiceException {
        try {
            return a - b;
        } catch (Exception e) {
            throw new CustomServiceException("Error during subtraction", e);
        }
    }

    @Override
    public int multiply(int a, int b) throws CustomServiceException {
        try {
            return a * b;
        } catch (Exception e) {
            throw new CustomServiceException("Error during multiplication", e);
        }
    }

    @Override
    public int divide(int a, int b) throws CustomServiceException {
        if (b == 0) {
            throw new CustomServiceException("Division by zero is not allowed");
        }
        try {
            return a / b;
        } catch (Exception e) {
            throw new CustomServiceException("Error during division", e);
        }
    }

    @Override
    public void triggerException() throws CustomServiceException {
        throw new CustomServiceException("This is a test exception triggered intentionally");
    }
}
