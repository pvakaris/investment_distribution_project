package entities;

import helpers.AbstractInvestorComparator;
import helpers.AnnualIncomeInvestorComparator;

import java.util.*;

/**
 * Represents an investor.
 */
public class Investor extends Partner implements Comparable<Investor>{

    private final List<String> wishlist;

    private final List<Investment> investments;

    private final AbstractInvestorComparator comparator;

    private double annualYield = 0;

    /**
     * Creates a new investor with the given name.
     * @param name The name of the investor.
     */
    public Investor(String name) {
        super(name);
        wishlist = new ArrayList<>();
        investments = new ArrayList<>();
        comparator = new AnnualIncomeInvestorComparator();
    }

    /**
     * Creates a new investor with the given name and a comparator.
     * @param name The name of the investor.
     * @param comparator The comparator implementation
     */
    public Investor(String name, AbstractInvestorComparator comparator) {
        super(name);
        wishlist = new ArrayList<>();
        investments = new ArrayList<>();
        this.comparator = comparator;
    }

    /**
     * Gets the list of product IDs that the investor is interested in.
     * @return The wishlist of the investor.
     */
    public List<String> getWishlist() {
        return wishlist;
    }

    /**
     * Adds a new product to the investor's wishlist.
     * @param newProduct The ID of the new product to add to the wishlist.
     */
    public void addWish(String newProduct) {
        wishlist.add(newProduct);
    }

    /**
     * Get the list of investments
     * @return investments
     */
    public List<Investment> getInvestments() {
        return investments;
    }

    /**
     * Add new investment
     * @param investment to add
     */
    public void addInvestment(Investment investment) {
        investments.add(investment);
        annualYield += investment.getAnnualYield();
        /* Update how much money is earned in total every time the investment is added
           Makes the access for comparison O(1) */
    }

    /**
     * Get a sum of money that the investor earns from the investments
     * @return the amount of money
     */
    public double getAnnualYield() {
        return annualYield;
    }

    /**
     * Removes a product from the investor's wishlist.
     * @param wish The ID of the product to remove from the wishlist.
     */
    public void removeWish(String wish) {
        wishlist.remove(wish);
    }

    /**
     * Compare two investors
     * @param o the investor to be compared to
     * @return the result
     */
    @Override
    public int compareTo(Investor o) {
        return comparator.compare(this, o);
    }
}

