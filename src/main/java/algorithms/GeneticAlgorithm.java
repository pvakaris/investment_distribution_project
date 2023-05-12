package algorithms;

import algorithms.genetic_algorithm_extra.ChromosomeType;
import algorithms.genetic_algorithm_extra.Generator;
import algorithms.genetic_algorithm_extra.Data;
import entities.Investment;
import entities.Investor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * The genetic algorithm implementation.
 */
public class GeneticAlgorithm implements AbstractAlgorithm {

    private static final Logger logger = LoggerFactory.getLogger(GreedyAlgorithm.class);
    private final ChromosomeType chromosomeType;
    private final boolean minimisationAlgorithm;

    // HYPER PARAMETERS
    private static final int populationSize = 200;
    private static final int minGenerations = 200;
    private static final int maxGenerations = 200;
    private static final double mutationRate = 0.05;

    public GeneticAlgorithm(ChromosomeType chromosomeType, boolean minimisation) {
        this.chromosomeType = chromosomeType;
        this.minimisationAlgorithm = minimisation;
    }

    /**
     * The method that looks for a solution using an evolutionary algorithm
     * @param investors a queue of investors looking to invest
     * @param investments a list of investments available for investors
     * @return AlgorithmResult containing the execution results
     * @throws RuntimeException if something horrible happens
     */
    @Override
    public AlgorithmResult run(Queue<Investor> investors, List<Investment> investments) throws RuntimeException{
        logger.info("The genetic algorithm has started");

        int generationCount = 0;
        // Save investors and investments to a singleton data structure for global access
        initialiseData(investors, investments);
        // Initialise a generator that will facilitate the genetic algorithm execution
        Generator generator = new Generator(populationSize, chromosomeType, minimisationAlgorithm);
        // Calculate the initial fitness of the population
        generator.getPopulation().calculateAllFitness();

        while (generationCount < maxGenerations || generator.getFittestChromosomeAllocations() == null) {
            logger.info("Performing operations of generation " + generationCount);
            generationCount++;
            // Perform selection
            generator.selection();
            // Perform crossover
            generator.crossover();
            // Perform mutation
            generator.mutation(mutationRate);
            // Add the fittest offspring to the population in the place of the weakest entry
            generator.addOffSpring();
            // Calculate new fitness value
            generator.getPopulation().calculateAllFitness();
        }

        // Once the algorithm is finished, get the fittest child from the population, process it and return it
        logger.info("The genetic algorithm has run for " + generationCount + " times. It is now finished");
        AlgorithmResult result = processResults(generator.getFittestChromosomeAllocations(), generator.getFittestChromosomeFitness());
        logger.info("Returning the results");
        return result;
    }

    /**
     * Used to process the results obtained from the Genetic algorithm and return in an appropriate format
     * @param slots The chromosome that won the genetic battle
     * @param fitness The fitness of that chromosome
     * @return AlgorithmResult for representation
     */
    private AlgorithmResult processResults(int[] slots, double fitness) {
        logger.info("Processing the results");
        Data data = Data.getInstance();
        Map<Integer, Investor> investorMap = data.getInvestorMap();
        Map<Integer, Investment> investmentMap = data.getInvestmentMap();

        // This can be uncommented to get a better explanation of the results
        // printExplanation(slots, fitness, investorMap, investmentMap);

        List<Investment> unused = new ArrayList<>();
        for(int i = 0; i < slots.length; i++) {
            if(slots[i] == -1) {
                unused.add(investmentMap.get(i));
            }
            else {
                investorMap.get(slots[i]).addInvestment(investmentMap.get(i));
            }
        }
        Queue<Investor> investors = new PriorityQueue<>();
        for(Integer i : investorMap.keySet()) {
            investors.add(investorMap.get(i));
        }
        logger.info("Results were successfully processed");
        return new AlgorithmResult(investors, unused);
    }

    /**
     * Used to initialise the singleton structure that will hold references to the maps.
     * @param investors Queue of investors which to turn into a map
     * @param investments List of investments which to turn into a map
     */
    private void initialiseData(Queue<Investor> investors, List<Investment> investments){
        // Two maps with constant IDs. The internals of both maps never change during the execution of the algorithm
        logger.info("Initialising a Data singleton that will hold investor and investment maps");
        Map<Integer, Investment> investmentMap = new HashMap<>();
        Map<Integer, Investor> investorMap = new HashMap<>();

        List<Investor> inv = new ArrayList<>(investors);
        for(int i = 0; i < inv.size(); i ++) {
            investorMap.put(i, inv.get(i));
        }

        for(int i = 0; i < investments.size(); i++) {
            investmentMap.put(i, investments.get(i));
        }

        Data data = Data.getInstance();
        data.initialise(investorMap, investmentMap);
        logger.info("The Data singleton ahs been successfully initialised");
    }

    /**
     * Used to print the explanation of how each investment was allocated and how maps are structured
     * @param slots The final solution array
     * @param fitness The best fitness that was reached with that solution
     * @param investors The investors ID ---> Investor
     * @param investments The investments ---> Investment
     */
    private void printExplanation(int[] slots, double fitness, Map<Integer, Investor> investors, Map<Integer, Investment> investments) {
        logger.info("Printing an elaborate explanation");
        System.out.println("The fitness of this allocation is: " + fitness);
        System.out.println(Arrays.toString(slots));
        System.out.println("INVESTORS:");
        for(Integer i : investors.keySet()) {
            System.out.println(i + " : " + investors.get(i).getName());
        }
        System.out.println("\nINVESTMENTS:");
        for(Integer i : investments.keySet()) {
            Investment inv = investments.get(i);
            System.out.println(i + " : Name ---> " + inv.getProductID() + ", annual interest ---> " + inv.getAnnualYield()
                    + ", rate ---> " + inv.getRate() + ", amount ---> " + inv.getOrder().getAmount());
        }
        System.out.println("\n");
    }
}
