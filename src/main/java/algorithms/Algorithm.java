package algorithms;

import entities.Investment;
import entities.Investor;

import java.util.List;

/**
 * An interface for an algorithm that can be used to find the best mapping of investments among investors
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public interface Algorithm {

    /**
     * The method that starts the algorithm
     * @param investors a list of investors looking to invest
     * @param investments a list of investments available for investors
     * @return AlgorithmResult containing the execution results
     * @throws RuntimeException if something horrible happens
     */
    AlgorithmResult run(List<Investor> investors, List<Investment> investments) throws RuntimeException;
}
