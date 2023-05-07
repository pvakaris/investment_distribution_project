package entities;

/**
 * Represents an investment that can be made by an investor
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class Investment implements Comparable<Investment> {

    private final double rate;
    private final Order order;

    /**
     * Constructs an Investment object with the given rate and a reference to an order.
     * @param rate The rate of interest for the investment.
     * @param order The order for which the investment is made.
     */
    public Investment(double rate, Order order) {
        this.rate = rate;
        this.order = order;
    }

    /**
     * Returns the rate of interest for the investment.
     * @return The rate of interest for the investment.
     */
    public double getRate() {
        return rate;
    }

    /**
     * Returns the annual yield for the investment.
     * @return The annual yield for the investment.
     */
    public double getAnnualYield() {
        return rate / 100.0 * order.getAmount();
    }

    /**
     * Returns the product ID for the investment.
     * @return The product ID for the investment.
     */
    public String getProductID() {
        return order.getProductID();
    }

    /**
     * Returns the order of a customer that this investment references
     * @return The order for which the investment is made.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Compares this investment with another investment.
     * The comparison is performed by comparing the cost of investment.
     * When used in a data structure with sorting, a descending sequence will be produced.
     * @param other The investment to be compared.
     * @return A negative integer, zero, or a positive integer as this investment is less than, equal to,
     * or greater than the specified investment.
     */
    @Override
    public int compareTo(Investment other) {
        // Compare by cost first, then by annual interest
        int costCompare = Double.compare(other.getOrder().getAmount(), order.getAmount());
        if (costCompare != 0) {
            return costCompare;
        }
        return Double.compare(other.getRate(), rate);
    }

    /**
     * Returns a string representation of this investment.
     * @return A string representation of this investment.
     */
    @Override
    public String toString() {
        return String.format("The product ID is %s, the amount invested is %f, the interest rate is %f percent. The borrower is %s.", getProductID(), order.getAmount(), rate, order.getOwner().getName());
    }
}

