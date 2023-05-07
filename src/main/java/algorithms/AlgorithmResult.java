package algorithms;

import entities.Investment;
import entities.Investor;

import java.util.List;
import java.util.Map;

/**
 * The result of the algorithm.
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class AlgorithmResult {
    private final Map<Investor, List<Investment>> allocations;
    private final List<Investment> unusedInvestments;

    /**
     * Creates a new instance of AlgorithmResult.
     * @param allocation The map of investor allocations.
     * @param unusedInvestments The list of unused investments.
     */
    public AlgorithmResult(Map<Investor, List<Investment>> allocation, List<Investment> unusedInvestments) {
        this.allocations = allocation;
        this.unusedInvestments = unusedInvestments;
    }

    /**
     * Returns the map of investor allocations.
     * @return The map of investor allocations.
     */
    public Map<Investor, List<Investment>> getAllocations() {
        return allocations;
    }

    /**
     * Returns the list of unused investments.
     * @return The list of unused investments.
     */
    public List<Investment> getUnusedInvestments() {
        return unusedInvestments;
    }
}
