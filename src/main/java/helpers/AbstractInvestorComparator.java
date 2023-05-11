package helpers;

import entities.Investor;

/**
 * An abstract comparator for Investor objects
 */
public interface AbstractInvestorComparator {

    /**
     * Compare two Investors on some criteria
     * @param one the first investor
     * @param two the second investor
     * @return 1 if investor one has higher position, -1 if investor tw has higher position, 0 if they are equal
     */
    int compare(Investor one, Investor two);

}
