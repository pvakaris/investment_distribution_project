package entities;

/**
 * Represents an order placed by a customer.
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class Order {

    private final String productID;
    private final double price;
    private final Customer owner;

    /**
     * Constructs an Order object with the specified parameters.
     * @param productID the ID of the product being ordered
     * @param price the price of the product being ordered
     * @param owner the customer who placed the order
     */
    public Order(String productID, double price, Customer owner) {
        this.productID = productID;
        this.price = price;
        this.owner = owner;
    }

    /**
     * Returns the ID of the product.
     * @return the product ID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Returns the price of the product.
     * @return the price of the product
     */
    public double getAmount() {
        return price;
    }

    /**
     * Returns the customer who placed the order.
     * @return the customer who placed the order
     */
    public Customer getOwner() {
        return owner;
    }
}

