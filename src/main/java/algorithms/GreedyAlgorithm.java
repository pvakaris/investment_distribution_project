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
    private static final boolean debugMode = false;

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
        Collections.sort(investments);
        if(debugMode) printInvestments(investments);
        // For each investment find an investor
        for (Investment investment : investments) {
            boolean assigned = false;
            if(debugMode) printInvestors(investors);
            Stack<Investor> tempInvestorStack = new Stack<>();
            while (!investors.isEmpty()) {
                Investor investor = investors.poll();
                if(debugMode) System.out.println("Considering investor " + investor.getName());
                tempInvestorStack.push(investor);
                if (investor.getWishlist().contains(investment.getProductID())) {
                    // Add investment to the investor's portfolio and remove from the wishlist
                    investor.removeWish(investment.getProductID());
                    investor.addInvestment(investment);
                    assigned = true;
                    if(debugMode) System.out.println("The investor wanted the investment with annual interest: " + investment.getAnnualYield());
                    break;
                }
                if(debugMode) System.out.println("The investor did not want the investment");
            }
            if (!assigned) unusedInvestments.add(investment);
            while(!tempInvestorStack.isEmpty()) {
                investors.add(tempInvestorStack.pop());
            }
        }
        logger.info("The greedy algorithm has finished. Returning the result.");
        return new AlgorithmResult(investors, unusedInvestments);
    }

    // ===================   USED FOR DEBUGGING   ===================

    /**
     * Used to print the list of investments
     * @param investments Investments to print
     */
    private void printInvestments(List<Investment> investments) {
        for(Investment inv: investments) {
            System.out.println(inv);
        }
    }

    /**
     * Used to print the queue of investors
     * @param investors Investments to print
     */
    private void printInvestors(Queue<Investor> investors) {
        System.out.println();
        for(Investor inv: investors) {
            System.out.println(inv.getName() + " --> " + inv.getAnnualYield());
        }
        System.out.println("\n");
    }

}
