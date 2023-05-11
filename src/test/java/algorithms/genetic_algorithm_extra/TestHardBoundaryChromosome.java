package algorithms.genetic_algorithm_extra;

import entities.Customer;
import entities.Investment;
import entities.Investor;
import entities.Order;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHardBoundaryChromosome {

    private static Map<Integer, Investor> investorMap;
    private static Map<Integer, Investment> investmentMap;
    private static int[] slots;

    @BeforeAll
    static void setUp() {
        // Initialize the test data
        investorMap = initialiseInvestorMap();
        investmentMap = initialiseInvestmentMap();
        slots = new int[]{2,2,3,1,1,2,2,2,-1,0,-1,-1,1};
    }

    @Test
    void testCalcFitness() {
        // Create a chromosome using the constructor with all arguments
        HardBoundaryChromosome chromosome = new HardBoundaryChromosome(investorMap, investmentMap, slots);

        // Calculate the fitness score
        chromosome.calcFitness();

        // Assert that the fitness score is the real standard deviation value
        assertEquals((int) chromosome.getFitness(), 2627);
    }

    private static Map<Integer, Investor> initialiseInvestorMap() {
        Investor inv1 = new Investor("Eagle");
        inv1.addWish("P1");
        inv1.addWish("P2");
        inv1.addWish("P5");

        Investor inv2 = new Investor("Vulture");
        inv2.addWish("P1");
        inv2.addWish("P3");


        Investor inv3 = new Investor("Seagull");
        inv3.addWish("P1");
        inv3.addWish("P2");
        inv3.addWish("P3");

        Investor inv4 = new Investor("Finch");
        inv4.addWish("P1");
        inv4.addWish("P2");
        inv4.addWish("P3");
        inv4.addWish("P4");
        inv4.addWish("P5");
        inv4.addWish("P6");


        List<Investor> investors = List.of(inv2, inv3, inv4, inv1);
        Map<Integer, Investor> map = new HashMap<>();
        for(int i = 0; i < investors.size(); i++) {
            map.put(i, investors.get(i));
        }

        return map;
    }

    private static Map<Integer, Investment> initialiseInvestmentMap() {
        List<Investment> investments = List.of(
                new Investment(2.6, new Order("P5", 100000, new Customer("Customer1"))),
                new Investment(2.1, new Order("P3", 50000, new Customer("Customer2"))),
                new Investment(2.5, new Order("P1", 48000, new Customer("Customer3"))),

                new Investment(2.1, new Order("P3", 17412, new Customer("Customer4"))),
                new Investment(2.5, new Order("P1", 10000, new Customer("Customer5"))),
                new Investment(2.5, new Order("P1", 30000, new Customer("Customer6"))),

                new Investment(3.5, new Order("P2", 12345, new Customer("Customer1"))),
                new Investment(2.5, new Order("P1", 50000, new Customer("Customer2"))),
                new Investment(2.5, new Order("P1", 42000, new Customer("Customer3"))),

                new Investment(2.1, new Order("P3", 50000, new Customer("Customer4"))),
                new Investment(2.5, new Order("P1", 73412, new Customer("Customer5"))),
                new Investment(5.0, new Order("P4", 100000, new Customer("Customer2"))),

                new Investment(3.5, new Order("P2", 63000, new Customer("Customer8")))
        );

        Map<Integer, Investment> map = new HashMap<>();
        for(int i = 0; i < investments.size(); i++) {
            map.put(i, investments.get(i));
        }

        return map;
    }

}
