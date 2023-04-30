package org.goof;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingApi {
    private static boolean addedGuards = false;

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
