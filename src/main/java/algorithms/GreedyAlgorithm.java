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
 */
public class GreedyAlgorithm implements AbstractAlgorithm {

    private static final Logger logger = LoggerFactory.getLogger(GreedyAlgorithm.class);

    /**
     * The method that does the greedy search for a solution
     * @param investors a queue of investors looking to invest
     * @param investments a list of investments available for investors
     * @return AlgorithmResult containing the execution results
     * @throws RuntimeException if something horrible happens
     */
    @Override
    public AlgorithmResult run(Queue<Investor> investors, List<Investment> investments) {
        logger.info("The greedy algorithm has started");
        List<Investment> unusedInvestments = new LinkedList<>();

        // Sort investments by the amount of money they require (the most expensive at the beginning)
        Arrays.sort(investments.toArray(new Investment[0]), Collections.reverseOrder());

        // For each investment find an investor
        for (Investment investment : investments) {
            boolean assigned = false;
            Stack<Investor> tempInvestorStack = new Stack<>();
            while (!investors.isEmpty()) {
                Investor investor = investors.poll();
                tempInvestorStack.push(investor);
                if (investor.getWishlist().contains(investment.getProductID())) {
                    // Add investment to the investor's portfolio and remove from the wishlist
                    investor.removeWish(investment.getProductID());
                    investor.addInvestment(investment);
                    assigned = true;
                    break;
                }
            }
            if (!assigned) unusedInvestments.add(investment);
            while(!tempInvestorStack.isEmpty()) {
                investors.add(tempInvestorStack.pop());
            }
        }
        logger.info("The greedy algorithm has finished. Returning the result.");
        return new AlgorithmResult(investors, unusedInvestments);
    }

}
