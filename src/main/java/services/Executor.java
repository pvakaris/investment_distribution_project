package services;

import algorithms.AbstractAlgorithm;
import algorithms.AlgorithmResult;
import algorithms.GeneticAlgorithm;
import algorithms.GreedyAlgorithm;
import algorithms.genetic_algorithm_extra.ChromosomeType;
import entities.Customer;
import entities.Investment;
import entities.Investor;
import entities.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * The Executor of the calculations. Extracts data from the CSV files, employs an algorithm to do all the hard work,
 * modifies the output and outputs the results.
 */
public class Executor {

    private static final Logger logger = LoggerFactory.getLogger(Executor.class);
    private Queue<Investor> investors = null;
    private List<Customer> customers = null;
    private Map<String, Double> productInterest = null;
    private final Properties properties;

    /**
     * Creates an Executor.
     * @param properties the properties from the config file
     */
    public Executor(Properties properties) {
        this.properties = properties;
    }

    /**
     * The only method of the Executor that can be called externally. Used to start the algorithm allocating the investments.
     * @throws RuntimeException if something wrong happens during the execution
     */
    public void run() throws RuntimeException{
        readFiles();
        // After reading the files, all the instance fields should be successfully initialised
        if (investors == null || customers == null || productInterest == null) {
            throw new RuntimeException("The algorithm could not be run due to a failure in reading the required files");
        }

        try {
            // Selecting the strategy
            AbstractAlgorithm algorithm = new GreedyAlgorithm();
            //AbstractAlgorithm algorithm = new GeneticAlgorithm(ChromosomeType.HARD_BOUNDARY, true);
            StringBuilder result = runAlgorithm(algorithm);

            // As suggested in the task sheet, the answer is printed out into the terminal
            if (result != null) {
                System.out.println(result);
            }
            else {
                System.out.println("Something completely wrong happened...");
            }
        }
        catch (Exception exception) {
            logger.error("An exception occurred when running the algorithm", exception);
        }
    }

    /**
     * The method that runs the chosen strategy and obtains the result of the execution.
     * @param abstractAlgorithm the strategy chosen
     * @return A StringBuilder containing the result of calculations
     */
    private StringBuilder runAlgorithm(AbstractAlgorithm abstractAlgorithm) {
        List<Investment> investments = getInvestments();
        logger.info("Investments were successfully prepared for the market");

        AlgorithmResult algorithmResult = null;
        try {
            // Run the algorithm specified as the strategy
            algorithmResult = abstractAlgorithm.run(investors, investments);
        }
        catch (RuntimeException exception) {
            logger.error("An exception occurred when trying to run the algorithm", exception);
        }
        // Return a StringBuilder or null if the operation failed
        return (algorithmResult != null) ? prepareOutput(algorithmResult) : null;
    }

    /**
     * Retrieves all orders from each customer and creates investments for them based on product interest rates.
     * @return a list of investments
     */
    private List<Investment> getInvestments() {
        List<Investment> investments = new ArrayList<>();
        // Retrieve all orders from each customer and create investments for them
        for(Customer customer : customers) {
            for(Order order : customer.getOrders()) {
                String productID = order.getProductID();
                investments.add(new Investment(productInterest.get(productID), order));
            }
        }
        return investments;
    }

    /**
     * Prepare the output based on the algorithm results
     * @param algorithmResults the object containing information about the results
     * @return A well-defined StringBuilder ready to be used
     */
    private StringBuilder prepareOutput(AlgorithmResult algorithmResults) {
        StringBuilder builder = new StringBuilder();

        prepareAllocationOutput(builder, algorithmResults.getInvestors());
        prepareUnusedInvestmentOutput(builder, algorithmResults.getUnusedInvestments());
        return builder;
    }

    /**
     * Prepare information about the allocation of investments
     * @param builder The StringBuilder which to modify
     * @param investors A queue of investors
     */
    private void prepareAllocationOutput(StringBuilder builder, Queue<Investor> investors) {
        List<Investor> investorList = new ArrayList<>(investors);
        Collections.sort(investorList);
        for(Investor investor : investorList) {
            builder.append(String.format("The investments that were allocated to %s are:\n", investor.getName()));
            for(Investment investment : investor.getInvestments()) {
                builder.append("  - ").append(investment).append("\n");
            }
            builder.append(String.format("The total amount of money earned annually is: %f\n\n", investor.getAnnualYield()));
        }
        builder.append("\n");
    }

    /**
     * Prepare information about the unused investments
     * @param builder The StringBuilder which to modify
     * @param unusedInvestments A list of investments that were not allocated to any of the investors
     */
    private void prepareUnusedInvestmentOutput(StringBuilder builder, List<Investment> unusedInvestments) {
        if(unusedInvestments.size() == 0) {
            builder.append("No unused investments!");
        }
        else {
            builder.append(("The unused investments are the following:\n"));
            for(Investment i : unusedInvestments) {
                builder.append(i).append("\n");
            }
            builder.append("\n");
        }
    }

    /**
     * Reads the CSV files containing information about investors, customers, and products.
     */
    private void readFiles() {
        try {
            investors = CSVReader.readInvestors(properties.getProperty("investors.csv"));
            logger.info("Information about investors was successfully read");

            customers = CSVReader.readCustomers(properties.getProperty("customers.csv"));
            logger.info("Information about customers was successfully read");

            productInterest = CSVReader.readProducts(properties.getProperty("products.csv"));
            logger.info("Information about products was successfully read");
        }
        catch (IOException exception) {
            logger.error("An exception occurred when reading the files", exception);
        }
        catch (Exception exception) {
            logger.error("An unknown error occurred", exception);
        }
    }
}
