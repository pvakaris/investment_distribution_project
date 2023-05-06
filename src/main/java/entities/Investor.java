package entities;

import java.util.ArrayList;
import java.util.List;

public class Investor extends Partner {
    private List<String> products;

    public Investor(String name) {
        super(name);
        products = new ArrayList<>();
    }

    public List<String> getProducts() {
        return products;
    }

    public void addProduct(String newProduct) {
        products.add(newProduct);
    }
}
