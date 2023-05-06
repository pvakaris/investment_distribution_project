package entities;

public class Mortgage {

    private final String type;
    private final double amount;

    public Mortgage(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
