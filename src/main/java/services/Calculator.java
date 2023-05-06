package services;

import entities.Customer;
import entities.Investor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.landbay.Main;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Calculator {

    private static final Logger logger = LoggerFactory.getLogger(Calculator.class);
    private List<Investor> investors = null;
    private List<Customer> customers = null;
    private Map<String, Double> products = null;

    private final Properties properties;

    public Calculator(Properties properties) {
        this.properties = properties;
    }

    public void start() throws RuntimeException{
        readFiles();
        // After reading the files, all the instance fields should be successfully initialised
        if (investors == null || customers == null || products == null) {
            throw new RuntimeException("The algorithm could not be run due to a failure in reading the required files");
        }



    }

    private void readFiles() {
        try {
            investors = CSVReader.readInvestors(properties.getProperty("investors.csv"));
            logger.info("Information about investors was successfully read.");

            customers = CSVReader.readCustomers(properties.getProperty("customers.csv"));
            logger.info("Information about customers was successfully read.");

            products = CSVReader.readProducts(properties.getProperty("products.csv"));
            logger.info("Information about products was successfully read.");
        }
        catch (IOException exception) {
            logger.error("An exception occurred when reading the files", exception);
        }
        catch (Exception exception) {
            logger.error("An unknown error occurred", exception);
        }
    }
}
