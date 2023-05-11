package services;

import entities.Customer;
import entities.Investor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Map;

public class TestCSVReader {

    private static Properties props;

    @BeforeAll
    public static void setUp() throws IOException {
        props = new Properties();
        props.load(TestCSVReader.class.getClassLoader().getResourceAsStream("config.properties"));
    }


    @Test
    public void testReadInvestors() throws IOException {
        Queue<Investor> investors = CSVReader.readInvestors(props.getProperty("investors.csv"));

        // Ensure that the list is not null and contains at least one investor
        Assertions.assertNotNull(investors);
        Assertions.assertFalse(investors.isEmpty());

        // Ensure that each investor has a name and at least one product in their wish list
        for (Investor investor : investors) {
            Assertions.assertNotNull(investor.getName());
            Assertions.assertFalse(investor.getWishlist().isEmpty());
        }
    }

    @Test
    public void testReadCustomers() throws IOException {
        List<Customer> customers = CSVReader.readCustomers(props.getProperty("customers.csv"));

        // Ensure that the list is not null and contains at least one customer
        Assertions.assertNotNull(customers);
        Assertions.assertFalse(customers.isEmpty());

        // Ensure that each customer has a name and at least one order
        for (Customer customer : customers) {
            Assertions.assertNotNull(customer.getName());
            Assertions.assertFalse(customer.getOrders().isEmpty());
        }
    }

    @Test
    public void testReadProducts() throws IOException {
        Map<String, Double> products = CSVReader.readProducts(props.getProperty("products.csv"));

        // Ensure that the map is not null and contains at least one product
        Assertions.assertNotNull(products);
        Assertions.assertFalse(products.isEmpty());

        // Ensure that each product has a name and a value
        for (Map.Entry<String, Double> entry : products.entrySet()) {
            Assertions.assertNotNull(entry.getKey());
            Assertions.assertNotNull(entry.getValue());
        }
    }
}
