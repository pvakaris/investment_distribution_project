package algorithms;

import entities.Investment;
import entities.Investor;

import java.util.List;
import java.util.Queue;

/**
 * The result of the algorithm.
 */
public class AlgorithmResult {
    private final Queue<Investor> investors;
    private final List<Investment> unusedInvestments;

    /**
     * Creates a new instance of AlgorithmResult.
     * @param investors The queue of investors.
     * @param unusedInvestments The list of unused investments.
     */
    public AlgorithmResult(Queue<Investor> investors, List<Investment> unusedInvestments) {
        this.investors = investors;
        this.unusedInvestments = unusedInvestments;
    }

    /**
     * Returns the queue of investors
     * @return The queue of investors
     */
    public Queue<Investor> getInvestors() {
        return investors;
    }

    /**
     * Returns the list of unused investments.
     * @return The list of unused investments.
     */
    public List<Investment> getUnusedInvestments() {
        return unusedInvestments;
    }
}
