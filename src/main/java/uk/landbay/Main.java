package uk.landbay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.Executor;

import java.io.IOException;
import java.util.Properties;

/**
 * The starting point of the application
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Method that starts the application
     * @param args the default
     */
    public static void main(String[] args) {
        logger.info("The program has started");
        try {
            // Retrieve the properties
            Properties properties = Main.getProperties();
            // Initialise the executor and execute the task
            Executor executor = new Executor(properties);
            executor.run();
        }
        catch (Exception exception) {
            logger.error("An exception occurred", exception);
        }
        finally {
            logger.info("The program has ended");
        }
    }

    /**
     * Extract properties from the resources and returns them
     * @return a Properties object containing important resources
     */
    private static Properties getProperties () {
        Properties props = new Properties();
        try {
            props.load(Executor.class.getClassLoader().getResourceAsStream("config.properties"));
        }
        catch (IOException exception) {
            logger.error("Could not load the config.properties file");
        }
        catch (Exception exception) {
            logger.error("An unknown exception occurred", exception);
        }
        return props;
    }
}