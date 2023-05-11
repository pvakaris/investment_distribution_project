package algorithms.genetic_algorithm_extra;

import entities.Investment;
import entities.Investor;
import helpers.MathOperator;

import java.util.*;

/**
 * The idea of this chromosome is to have an array of genes (int[] slots), where each position in the array
 * means an investment. Each value at the index position is an id of an investor. The fitness of the chromosome is
 * the standard deviation of all investor earnings. The smaller the deviation, the better the fitness.
 */
public class HardBoundaryChromosome implements AbstractChromosome {

    // Index ---> investment, value at index ---> investor
    private final int[] slots;
    private final Map<Integer, Investor> investorMap;
    private final Map<Integer, Investment> investmentMap;
    private double fitnessScore;
    private final int length;
    private boolean validSolution = true;
    private double penaltyScore = -5000;

    /**
     * Create a chromosome with a predefined gene collection
     * @param investorMap reference to the investor map
     * @param investmentMap reference to the investment map
     * @param slots the genes
     */
    public HardBoundaryChromosome(Map<Integer, Investor> investorMap, Map<Integer, Investment> investmentMap, int[] slots){

        this.investmentMap = investmentMap;
        this.investorMap = investorMap;
        this.slots = slots;
        fitnessScore = 0;
        length = investmentMap.size();
    }

    /**
     * Create a chromosome without any genes. Used when offsprings are created. Then parent genes are added.
     * @param investorMap reference to the investor map
     * @param investmentMap reference to the investment map
     */
    public HardBoundaryChromosome(Map<Integer, Investor> investorMap, Map<Integer, Investment> investmentMap){

        this.investmentMap = investmentMap;
        this.investorMap = investorMap;
        length = investmentMap.size();
        this.slots = new int[length];
        fitnessScore = 0;

    }

    /**
     * The fitness calculation function that finds the standard deviation of all the average income values of all investors.
     * The idea is to try and minimise the standard deviation resulting in investors earning similar amounts of money from
     * their investments.
     */
    @Override
    public void calcFitness(){

        Map<Investor, List<Investment>> map = prepareInvestorInvestmentMap();
        addInvestorsWithoutInvestments(map);

        List<Double> list = new ArrayList<>();

        // Calculate fitness
        for(Investor investor: map.keySet()) {
            list.add(calculateSingleInvestorAverageIncome(investor, map.get(investor)));
        }
        fitnessScore = MathOperator.getStandardDeviation(list);
        // Restore the default penaltyScore for next iteration
        penaltyScore = -5000;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public double getFitness() {
        return fitnessScore;
    }

    @Override
    public int[] getSlots() {
        return slots;
    }

    @Override
    public int getGeneAtIndex(int index) {
        return slots[index];
    }

    @Override
    public void setGeneAtIndex(int index, int gene) {
        slots[index] = gene;
    }

    /**
     * Used to calculate the average income of one investor given the investments.
     * @param investor The investor
     * @param investments The list of investments the investor made.
     * @return the average sum the investor will be earning. If the sum is highly negative, it means that the
     * chromosome is invalid. The investor is not interested in the investments assigned to him.
     */
    private double calculateSingleInvestorAverageIncome(Investor investor, List<Investment> investments) {
        if(investments != null && investments.size() != 0) {
            double sum = 0;
            // Create a copy of the investor's wishlist
            List<String> wishlist = new ArrayList<>(investor.getWishlist());
            for (Investment investment : investments) {
                // If investor really wants the investment
                if (wishlist.contains(investment.getProductID())) {
                    sum += investment.getAnnualYield();
                    wishlist.remove(investment.getProductID());
                } else {
                    /* If this was reached, the chromosome is invalid as it contains some investment allocations
                        to investors that are not looking to invest in that type of product. PENALIZE! */
                    validSolution = false;
                    sum = penaltyScore * investments.size();
                    penaltyScore -= 5000;
                    break;
                }
            }
            return sum / investments.size();
        }
        else {
            return 0.0;
        }
    }

    /**
     * From slots, for each investor map a list of his investments. Helps with calculations.
     * @return a new map of Investor ---> List of his investments
     */
    private Map<Investor, List<Investment>> prepareInvestorInvestmentMap() {
        Map<Investor, List<Investment>> map = new HashMap<>();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != -1) {
                Investor investor = investorMap.get(slots[i]);
                map.computeIfAbsent(investor, k -> new LinkedList<>()).add(investmentMap.get(i));
            }
        }
        return map;
    }

    /**
     * Used to add investors to the map returned by prepareInvestorInvestmentMap() that were not allocated any investments.
     * @param map the same map
     */
    private void addInvestorsWithoutInvestments(Map<Investor, List<Investment>> map) {
        investorMap.values().stream()
                .filter(investor -> !map.containsKey(investor))
                .forEach(investor -> map.put(investor, new ArrayList<>()));
    }

    /**
     * Returns if the chromosome is valid
     * @return true if it is, false otherwise
     */
    public boolean isValidChromosome() {
        return validSolution;
    }
}