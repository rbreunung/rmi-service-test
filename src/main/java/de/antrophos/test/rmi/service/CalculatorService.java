package de.antrophos.test.rmi.service;

import de.antrophos.test.rmi.exception.CustomServiceException;

/**
 * RMI Service Interface.
 * Defines the methods that can be called remotely.
 */
public interface CalculatorService {

    /**
     * Adds two numbers.
     *
     * @param a first number
     * @param b second number
     * @return the sum of a and b
     * @throws CustomServiceException if an error occurs
     */
    int add(int a, int b) throws CustomServiceException;

    /**
     * Subtracts b from a.
     *
     * @param a first number
     * @param b second number
     * @return the difference of a and b
     * @throws CustomServiceException if an error occurs
     */
    int subtract(int a, int b) throws CustomServiceException;

    /**
     * Multiplies two numbers.
     *
     * @param a first number
     * @param b second number
     * @return the product of a and b
     * @throws CustomServiceException if an error occurs
     */
    int multiply(int a, int b) throws CustomServiceException;

    /**
     * Divides a by b.
     *
     * @param a dividend
     * @param b divisor
     * @return the quotient of a and b
     * @throws CustomServiceException if b is zero or other error occurs
     */
    int divide(int a, int b) throws CustomServiceException;

    /**
     * Triggers an exception for testing purposes.
     *
     * @throws CustomServiceException always throws this exception
     */
    void triggerException() throws CustomServiceException;
}
