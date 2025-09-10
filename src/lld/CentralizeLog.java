package lld;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class CentralizeLog {

}
/**
 * Main class to demonstrate the end-to-end flow of the centralized logging system.
 * This file contains all classes necessary to run the example.
 * The data flow is a linear path that begins with the Logger, moves to the Log Shipper, and ends at the Log Aggregator.
 *
 * Log Creation: An application calls a method on the Logger (e.g., info(), error()).
 * The Logger checks if the log level is appropriate and, if so, packages the information into a new LogMessage object.
 *
 * Asynchronous Transfer: The Logger then passes this LogMessage to the LogShipper's addLog() method.
 * This is a non-blocking call that adds the message to an internal BlockingQueue.
 * This queue acts as a buffer, ensuring the application is never blocked by the logging process.
 *
 * Shipper's Work: In the background, a separate thread continuously runs the LogShipper's run() method.
 * This thread waits for new messages to appear in its queue. When a message is available,
 * it takes it from the queue and sends it to the LogAggregator.
 *
 * Aggregation and Storage: The LogAggregator receives the log message and processes it.
 * In this code, it simply adds the log to an in-memory list (logStorage).
 * In a production system, this component would be responsible for storing the data in a database for analysis.
 */
 class CentralizedLoggingSystem {

    // --- Core Components ---

    /**
     * Enum for different log levels.
     * This provides a clear hierarchy for log severity.
     */
    public enum LogLevel {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    /**
     * Represents a single log message.
     * This is the data model that is passed between components.
     * It's crucial for standardization across the system.
     */
    public static class LogMessage {
        private final long timestamp;
        private final LogLevel level;
        private final String appName;
        private final String message;
        private final String metadata;

        public LogMessage(LogLevel level, String appName, String message, String metadata) {
            this.timestamp = System.currentTimeMillis();
            this.level = level;
            this.appName = appName;
            this.message = message;
            this.metadata = metadata;
        }

        @Override
        public String toString() {
            return String.format("[%s] [%s] [%s] %s - %s",
                    new java.util.Date(timestamp), level, appName, message, metadata);
        }

        public long getTimestamp() {
            return timestamp;
        }

        public LogLevel getLevel() {
            return level;
        }

        public String getAppName() {
            return appName;
        }

        public String getMessage() {
            return message;
        }

        public String getMetadata() {
            return metadata;
        }
    }

    // --- Logging Library (Client-Side) ---

    /**
     * The core logging class used by applications.
     * It sends log messages to the LogShipper.
     */
    public static class Logger {
        private final String appName;
        private final LogLevel minLevel;
        private final LogShipper logShipper;

        public Logger(String appName, LogLevel minLevel, LogShipper logShipper) {
            this.appName = appName;
            this.minLevel = minLevel;
            this.logShipper = logShipper;
        }

        public void log(LogLevel level, String message, String metadata) {
            // Only log if the message level is at or above the minimum configured level.
            if (level.ordinal() >= minLevel.ordinal()) {
                LogMessage logMessage = new LogMessage(level, appName, message, metadata);
                System.out.println("Local Logger: " + logMessage);
                // Asynchronously send the log message to the shipper.
                logShipper.addLog(logMessage);
            }
        }

        // Convenience methods for different log levels
        public void debug(String message, String metadata) {
            log(LogLevel.DEBUG, message, metadata);
        }

        public void info(String message, String metadata) {
            log(LogLevel.INFO, message, metadata);
        }

        public void error(String message, String metadata) {
            log(LogLevel.ERROR, message, metadata);
        }
    }

    // --- Log Shipper (Agent) ---

    /**
     * A background process (daemon) that runs on each application server.
     * It buffers logs in a thread-safe queue and sends them to the aggregator.
     * The use of a BlockingQueue decouples the logger from the network call.
     */
    public static class LogShipper implements Runnable {
        private final BlockingQueue<LogMessage> logQueue = new LinkedBlockingQueue<>();
        private final LogAggregator logAggregator;
        private volatile boolean running = true;

        public LogShipper(LogAggregator logAggregator) {
            this.logAggregator = logAggregator;
        }

        public void addLog(LogMessage logMessage) {
            try {
                // The put() method blocks if the queue is full, providing back-pressure.
                logQueue.put(logMessage);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Log shipper was interrupted while adding a log.");
            }
        }

        @Override
        public void run() {
            try {
                while (running) {
                    // Use take() to block and wait for a new log message.
                    LogMessage message = logQueue.take();
                    logAggregator.ingestLog(message);
                    System.out.println("Shipper: Sent log to aggregator.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Log shipper thread interrupted.");
            }
        }

        public void shutdown() {
            running = false;
        }
    }

    // --- Log Aggregator (Central Service) ---

    /**
     * The central service that receives logs from all shippers.
     * It's responsible for processing and storing logs.
     * For this example, we'll use a simple list to simulate storage.
     */
    public static class LogAggregator {
        private final List<LogMessage> logStorage = new ArrayList<>();

        // In a real system, this would write to a database like Elasticsearch or a message broker.
        public synchronized void ingestLog(LogMessage log) {
            // Simulate data enrichment or processing before storing.
            System.out.println("Aggregator: Ingesting log -> " + log.getMessage());
            logStorage.add(log);
        }

        public List<LogMessage> getLogs() {
            return logStorage;
        }
    }

    // --- Main Method to run the simulation ---

    public static void main(String[] args) {
        System.out.println("Starting Centralized Logging System simulation...");

        // 1. Initialize the central components
        LogAggregator aggregator = new LogAggregator();
        LogShipper logShipper = new LogShipper(aggregator);

        // We use a separate thread for the LogShipper to run continuously in the background.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(logShipper);

        // 2. Create multiple application loggers
        Logger app1Logger = new Logger("WebApp-A", LogLevel.INFO, logShipper);
        Logger app2Logger = new Logger("PaymentService-B", LogLevel.INFO, logShipper);
        Logger app3Logger = new Logger("Auth-C", LogLevel.ERROR, logShipper);

        // 3. Simulate applications generating logs
        System.out.println("\n--- Simulating Log Generation ---");
        app1Logger.info("User 'JohnDoe' logged in successfully.", "session_id=12345");
        app2Logger.info("Transaction processed for user 'JaneSmith'.", "transaction_id=98765");
        app1Logger.debug("This message should not be logged because level is INFO.", "debug_info=test");
        app3Logger.error("Failed to authenticate user 'Admin'.", "reason=password_mismatch");

        // Give the shipper time to process logs
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Final State of Log Aggregator ---");
        System.out.println("Total logs ingested: " + aggregator.getLogs().size());
        aggregator.getLogs().forEach(System.out::println);

        // 4. Clean up resources
        executor.shutdown();
        logShipper.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("\nSimulation complete.");
    }
}
