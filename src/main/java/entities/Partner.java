package entities;

/**
 * A partner (both customers and investors) THE SUPERCLASS
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class Partner {
    private final String name;

    /**
     * Constructs a partner with the given name.
     * @param name the name of the partner
     */
    public Partner(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the partner.
     * @return the name of the partner
     */
    public String getName() {
        return name;
    }
}

