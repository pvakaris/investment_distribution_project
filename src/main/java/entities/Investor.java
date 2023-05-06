package entities;

import java.util.ArrayList;
import java.util.List;

public class Investor {

    private final String name;
    private List<String> products;

    public Investor(String name) {
        this.name = name;
        products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getProducts() {
        return products;
    }

    public void addProduct(String newProduct) {
        products.add(newProduct);
    }
}
