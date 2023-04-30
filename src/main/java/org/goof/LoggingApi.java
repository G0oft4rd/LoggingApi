package org.goof;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A wrapper class for log4j2's LogManager that adds guards for async loggers
 */
public class LoggingApi {
    private static boolean addedGuards = false;

    /**
     * A method for getting a logger for a specific class, returns the root logger if not logger is defined for that class
     * @param callingClass The class of the logger
     * @return A logger of that class or root logger if not defined
     */
    public static Logger getLogger(Class callingClass) {
        if (!addedGuards) {
            addedGuards = true;
            addAsyncLoggerGuards();
        }

        return LogManager.getLogger(callingClass);
    }

    private static void addAsyncLoggerGuards() {
        Runtime.getRuntime().addShutdownHook(new Thread(LogManager::shutdown));
        Thread.setDefaultUncaughtExceptionHandler((ignoredThread, ignoredException) -> LogManager.shutdown());
    }
}
