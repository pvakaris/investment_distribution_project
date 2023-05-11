package services;

import entities.Customer;
import entities.Investor;
import entities.Order;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * A class that handles the CSV input files
 */
public class CSVReader {

    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    /**
     * Reads investor data from a CSV file and returns a list of Investor objects.
     *
     * @param filename the name of the CSV file containing the investor data
     * @return a list of Investor objects containing the data from the CSV file
     * @throws IOException if there is an error reading the file
     */
    public static Queue<Investor> readInvestors(String filename) throws IOException {
        CSVParser parser = readFile(filename);
        // Store references to investors temporarily
        Map<String, Investor> investorsMap = new HashMap<>();
        /* Read all investors and create an object for each. Store references to the objects with
           their name as the key to allow for adding the products to their lists later */
        for (CSVRecord entry : parser) {
            String name = entry.get(0);
            String product = entry.get(1);

            Investor investor = investorsMap.get(name);
            if (investor == null) {
                investor = new Investor(name);
                investorsMap.put(name, investor);
            }

            investor.addWish(product);
        }

        // Finally return the complete list of investors
        return new PriorityQueue<>(investorsMap.values());
    }

    /**
     * Reads customer data from a CSV file and returns a list of Customer objects.
     *
     * @param filename the name of the CSV file to read from
     * @return a list of Customer objects representing the data in the file
     * @throws IOException if the file cannot be read
     */
    public static List<Customer> readCustomers(String filename) throws IOException {
        CSVParser parser = readFile(filename);
        // Store references to customers temporarily
        Map<String, Customer> customersMap = new HashMap<>();
        /* Read all customers and create an object for each. Store references to the objects with
           their name as the key to allow for adding the mortgages later */
        for (CSVRecord entry : parser) {
            String name = entry.get(0);

            Customer customer = customersMap.get(name);
            if (customer == null) {
                customer = new Customer(name);
                customersMap.put(name, customer);
            }

            customer.addOrder(new Order(entry.get(1), Double.parseDouble(entry.get(2)), customer));
        }

        // Finally return the complete list of investors
        return new ArrayList<>(customersMap.values());
    }

    /**
     * Read products from a file and return a map containing (productID, interest) pairs
     * @param filename The name of the file
     * @return A map of (productID, interest) pairs
     * @throws IOException if the file cannot be read
     */
    public static Map<String, Double> readProducts(String filename) throws IOException {
        CSVParser parser = readFile(filename);
        Map<String, Double> products = new TreeMap<>();
        for (CSVRecord entry : parser) {
            String name = entry.get(0);
            double value = Double.parseDouble(entry.get(1).replaceAll("%", ""));
            products.put(name, value);
        }
        return products;
    }

    /**
     * Reads a CSV file and returns a CSVParser object for parsing the data.
     *
     * @param filename the name of the CSV file to be read
     * @return a CSVParser object containing the parsed data from the file
     * @throws IOException if the file does not exist or cannot be read
     */
    private static CSVParser readFile(String filename) throws IOException{
        logger.info(String.format("Reading from file %s", filename));
        InputStream inputStream = CSVReader.class.getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IOException(String.format("File %s does not exist", filename));
        }
        return new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build());
    }
}
