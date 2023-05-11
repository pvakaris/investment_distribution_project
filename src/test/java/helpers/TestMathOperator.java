package helpers;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestMathOperator {

    @Test
    void testGetStandardDeviation() {
        List<Double> numbers = Arrays.asList(5.0, 9.0, 6.0, 6.0);
        double deviation = MathOperator.getStandardDeviation(numbers);
        assertEquals(1.5, deviation);
    }

    @Test
    void testGetStandardDeviationWithEmptyList() {
        List<Double> numbers = List.of();
        double deviation = MathOperator.getStandardDeviation(numbers);
        assertEquals(0.0, deviation);
    }

    @Test
    void testGetStandardDeviationWithOneElement() {
        List<Double> numbers = List.of(5.0);
        double deviation = MathOperator.getStandardDeviation(numbers);
        assertEquals(0.0, deviation);
    }

    @Test
    void testGetStandardDeviationWithNegativeNumbers() {
        List<Double> numbers = Arrays.asList(-9.0, -5.0, -6.0, -8.0, -15.0);
        double deviation = MathOperator.getStandardDeviation(numbers);
        assertEquals(3.5, Double.parseDouble(new DecimalFormat("#.#").format(deviation)));
    }
}
