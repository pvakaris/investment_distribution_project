package helpers;

import java.util.List;

/**
 * Static class that is used to perform mathematical operations
 */
public class MathOperator {

    /**
     * Get the standard deviation of a list of numbers
     * @param numbers the list
     * @return standard deviation as a double
     */
    public static double getStandardDeviation(List<Double> numbers) {
        if(numbers == null || numbers.size() == 0) {
            return 0;
        }
        double sum = 0;
        double mean = 0;
        double variance = 0;
        int n = numbers.size();

        // Calculate the mean
        for (double number : numbers) {
            sum += number;
        }
        mean = sum / n;

        // Calculate the sum of squared differences
        double sumOfSquares = 0;
        for (double number : numbers) {
            sumOfSquares += Math.pow(number - mean, 2);
        }

        // Calculate the variance
        variance = sumOfSquares / n;

        // Calculate the standard deviation
        double standardDeviation = Math.sqrt(variance);

        return standardDeviation;
    }
}
