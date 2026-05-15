package com.ecommerce.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    /**
     * Logs informational messages indicating standard operational processes.
     * @param message The message to log.
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Logs an error message without an exception trace.
     * @param message The error message to log.
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Logs an error message along with the exception stack trace.
     * @param message The error message to log.
     * @param e The exception whose stack trace to log.
     */
    public static void error(String message, Exception e) {
        logger.error(message, e);
    }

    /**
     * Logs debug messages for fine-grained informational events.
     * @param message The debug message to log.
     */
    public static void debug(String message) {
        logger.debug(message);
    }

    /**
     * Logs a warning message when something unexpected happens but execution can continue.
     * @param message The warning message to log.
     */
    public static void warning(String message) {
        logger.warn(message);
    }
}
