package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an investor.
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class Investor extends Partner {

    private final List<String> wishlist;

    /**
     * Creates a new investor with the given name.
     * @param name The name of the investor.
     */
    public Investor(String name) {
        super(name);
        wishlist = new ArrayList<>();
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
     * Removes a product from the investor's wishlist.
     * @param wish The ID of the product to remove from the wishlist.
     */
    public void removeWish(String wish) {
        wishlist.remove(wish);
    }
}

