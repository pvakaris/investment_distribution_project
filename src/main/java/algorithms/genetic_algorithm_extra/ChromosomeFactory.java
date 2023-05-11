package algorithms.genetic_algorithm_extra;

import entities.Investment;
import entities.Investor;

import java.util.*;

/**
 * Used to build chromosomes of various types
 */
public class ChromosomeFactory {

    private final Random random = new Random();
    private final Data data = Data.getInstance();
    private final ChromosomeType type;

    /**
     * Get a ChromosomeFactory that produces chromosomes of a particular type
     * @param type The type of the chromosome
     */
    public ChromosomeFactory(ChromosomeType type) {
        this.type = type;
    }

    /**
     * Used to build and return chromosomes with randomly initialised slot arrays (allocations)
     * @return a new fully setup AbstractChromosome
     */
    public AbstractChromosome createRandomChromosome() {
        switch (type) {
            case HARD_BOUNDARY:
                return buildRandomHardBoundaryChromosome();
            default:
                // Should never be accessed
                throw new IllegalArgumentException("Invalid chromosome type: " + type);
        }
    }

    /**
     * Return a chromosome with an empty slots array
     * @return a new empty AbstractChromosome
     */
    public AbstractChromosome createEmptyChromosome() {
        switch (type) {
            case HARD_BOUNDARY:
                return new HardBoundaryChromosome(data.getInvestorMap(), data.getInvestmentMap());
            default:
                // Should never be accessed
                throw new IllegalArgumentException("Invalid chromosome type: " + type);
        }
    }

    /**
     * Used to create a HardBoundaryChromosome. For each position in the slots array where each
     * position resembles an investment, assign one investor's id or -1 meaning that the investment
     * is not allocated to any investor.
     * @return a new HardBoundaryChromosome
     */
    private HardBoundaryChromosome buildRandomHardBoundaryChromosome() {
        Map<Integer, Investor> investors = data.getInvestorMap();
        Map<Integer, Investment> investments = data.getInvestmentMap();

        int[] slots = new int[investments.size()];
        var keySet = investors.keySet();
        List<Integer> keyList = new ArrayList<>(keySet);
        keyList.add(-1); // Add -1 to the key list

        Collections.shuffle(keyList, random);
        for (int i = 0; i < slots.length; i++) {
            int randomIndex = random.nextInt(keyList.size());
            slots[i] = keyList.get(randomIndex);
        }
        return new HardBoundaryChromosome(investors, investments, slots);
    }
}
