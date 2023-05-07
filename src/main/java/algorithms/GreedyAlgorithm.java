package algorithms;

import entities.Investment;
import entities.Investor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The greedy algorithm implementation. It is greedy, because it may not find the most optimal solution to the problem.
 * Implemented as the initial MVP for the assignment.
 *
 * The greedy approach: sort all investments from the most costly to the least, loop through all the investments and
 * look for an investor that would be interested in the investment. After each investment is allocated to some investor,
 * sort the list of investors so that the ones that have the lowest annual income are first to get their hand onto the
 * next investment.
 *
 * The time complexity though is quite large: O(n log n) on average and O(n^2 log n) in the worst case.
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class GreedyAlgorithm implements Algorithm {

    private static final Logger logger = LoggerFactory.getLogger(GreedyAlgorithm.class);

    /**
     * The method that does the greedy search for a solution
     * @param investorsList a list of investors looking to invest
     * @param investments a list of investments available for investors
     * @return AlgorithmResult containing the execution results
     * @throws RuntimeException if something horrible happens
     */
    @Override
    public AlgorithmResult run(List<Investor> investorsList, List<Investment> investments) throws RuntimeException {
        logger.info("The greedy algorithm has started");
        // Map that will hold the investment assignments
        Map<Investor, List<Investment>> results = new HashMap<>();
        List<Investment> unusedInvestments = new LinkedList<>();

        // Sort investments by the amount of money they require (the most expensive at the beginning)
        Collections.sort(investments);

        // Create a comparator to compare two investors by the amount of money they will gain from their investments annually
        Comparator<Investor> investorComparator = Comparator.comparingDouble(investor -> {
            List<Investment> inv = results.getOrDefault(investor, Collections.emptyList());
            double sum = 0.0;
            for (Investment investment : inv) {
                sum += investment.getAnnualYield();
            }
            return sum;
        });

        // For each investment find an investor
        for (Investment investment : investments) {
            boolean assigned = false;
            for (Investor investor : investorsList) {
                if (investor.getWishlist().contains(investment.getProductID())) {
                    if (results.containsKey(investor)) {
                        results.get(investor).add(investment);
                    } else {
                        results.put(investor, new LinkedList<>(List.of(investment)));
                    }
                    // Remove the wish from the investor's wishlist
                    investor.removeWish(investment.getProductID());
                    assigned = true;
                    // After an investment was added, resort the list so that those investors who earn the least, get the privilege in the next iteration
                    investorsList.sort(investorComparator);
                    break;
                }
            }
            if (!assigned) {
                // If this place was reached, it means that none of the investors wanted the investment. Add to the unused list.
                unusedInvestments.add(investment);
            }
        }
        logger.info("The greedy algorithm has finished. Returning the result.");
        return new AlgorithmResult(results, unusedInvestments);
    }
}
