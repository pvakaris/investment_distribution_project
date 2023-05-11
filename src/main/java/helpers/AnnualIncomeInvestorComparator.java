package helpers;

import entities.Investor;

/**
 * Concrete implementation of an investor comparator
 * If investor one earns more than investor two, 1 is returned. If vice versa -1, if draw 0.
 */
public class AnnualIncomeInvestorComparator implements AbstractInvestorComparator {

    @Override
    public int compare(Investor one, Investor two) {
        return Double.compare(one.getAnnualYield(), two.getAnnualYield());
    }
}
