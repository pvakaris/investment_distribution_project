package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a customer
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class Customer extends Partner {
    // A list that holds all the orders a Customer has
    private final List<Order> orders;

    /**
     * Creates a new customer with the given name.
     * @param name the name of the customer
     */
    public Customer(String name) {
        super(name);
        orders = new ArrayList<>();
    }

    /**
     * Returns the list of orders the Customer has
     * @return the list of orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Adds a new order to the list of orders
     * @param newOrder the new order to add
     */
    public void addOrder(Order newOrder) {
        orders.add(newOrder);
    }
}
