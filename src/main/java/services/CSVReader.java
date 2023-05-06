package services;

import entities.Customer;
import entities.Investor;
import entities.Mortgage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CSVReader {

    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    public static List<Investor> readInvestors(String filename) throws IOException {
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

            investor.addProduct(product);
        }

        // Finally return the complete list of investors
        return new ArrayList<>(investorsMap.values());
    }

    public static List<Customer> readCustomers(String filename) throws IOException {
        CSVParser parser = readFile(filename);
        // Store references to customers temporarily
        Map<String, Customer> customersMap = new HashMap<>();
        /* Read all customers and create an object for each. Store references to the objects with
           their name as the key to allow for adding the mortgages later */
        for (CSVRecord entry : parser) {
            String name = entry.get(0);
            Mortgage mortgage = new Mortgage(entry.get(1), Double.parseDouble(entry.get(2)));

            Customer customer = customersMap.get(name);
            if (customer == null) {
                customer = new Customer(name);
                customersMap.put(name, customer);
            }

            customer.addMortgage(mortgage);
        }

        // Finally return the complete list of investors
        return new ArrayList<>(customersMap.values());
    }

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

    private static CSVParser readFile(String filename) throws IOException{
        logger.info(String.format("Reading from file %s", filename));
        InputStream inputStream = CSVReader.class.getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IOException(String.format("File %s does not exist", filename));
        }
        return new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build());
    }
}
