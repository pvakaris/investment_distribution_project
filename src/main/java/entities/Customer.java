package entities;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Partner {

    private final List<Mortgage> mortgages;

    public Customer(String name) {
        super(name);
        mortgages = new ArrayList<>();
    }

    public List<Mortgage> getMortgages() {
        return mortgages;
    }

    public void addMortgage(Mortgage newMortgage) {
        mortgages.add(newMortgage);
    }
}
