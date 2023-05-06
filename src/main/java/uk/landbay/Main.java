package uk.landbay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.Calculator;

import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("The program has started");
        try {
            Properties properties = Main.getProperties();
            Calculator calculator = new Calculator(properties);
            calculator.start();
        }
        catch (Exception exception) {
            logger.error("An exception occurred", exception);
        }
        finally {
            logger.info("The program has ended");
        }
    }

    private static Properties getProperties () {
        Properties props = new Properties();
        try {
            props.load(Calculator.class.getClassLoader().getResourceAsStream("config.properties"));
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