package algorithms.genetic_algorithm_extra;

import entities.Investment;
import entities.Investor;

import java.util.Map;

/**
 * A class that stores two maps and provides access to the data
 * Used when calculating the fitness
 *
 * SINGLETON PATTERN
 */
public class Data {

    private static Data instance = null;
    private Map<Integer, Investor> investorMap = null;
    private Map<Integer, Investment> investmentMap = null;

    private Data() {}

    /**
     * Get instance of the Data object
     * @return reference to the Data object
     */
    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    /**
     * Set the values which to hold
     * @param investorMap investors
     * @param investmentMap investments
     */
    public void initialise(Map<Integer, Investor> investorMap, Map<Integer, Investment> investmentMap) {
        // To ensure that the maps can be initialised only once
        if(this.investorMap == null) {
            this.investorMap = investorMap;
            this.investmentMap = investmentMap;
        }
    }

    /**
     * Get Investor map. Unique ID ---> Investor
     * @return investor map
     */
    public Map<Integer, Investor> getInvestorMap() {
        return investorMap;
    }

    /**
     * Get Investment map. Unique ID ---> Investment
     * @return investment map
     */
    public Map<Integer, Investment> getInvestmentMap() {
        return investmentMap;
    }

}
