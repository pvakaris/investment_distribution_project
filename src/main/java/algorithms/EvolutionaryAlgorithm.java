package algorithms;

import entities.Investment;
import entities.Investor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The evolutionary algorithm implementation
 *
 * @author Vakaris Paulavicius
 * @version 1.0
 */
public class EvolutionaryAlgorithm implements Algorithm {

    private static final Logger logger = LoggerFactory.getLogger(GreedyAlgorithm.class);

    /**
     * The method that looks for a solution using an evolutionary algorithm
     * @param investorsList a list of investors looking to invest
     * @param investments a list of investments available for investors
     * @return AlgorithmResult containing the execution results
     * @throws RuntimeException if something horrible happens
     */
    @Override
    public AlgorithmResult run(List<Investor> investorsList, List<Investment> investments) throws RuntimeException{
        logger.info("The evolutionary algorithm has started");



        return new AlgorithmResult(null, null);
    }
}
