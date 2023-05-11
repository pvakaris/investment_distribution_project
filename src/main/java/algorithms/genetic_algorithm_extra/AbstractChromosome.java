package algorithms.genetic_algorithm_extra;

/**
 * An abstraction of a chromosome.
 */
public interface AbstractChromosome {

    /**
     * Get the fitness of the chromosome
     * @return the fitness as a double
     */
    double getFitness();

    /**
     * Used to calculate the fitness and save it to an instance field
     */
    void calcFitness();

    /**
     * Get the gene array of the chromosome
     * @return the genes
     */
    int[] getSlots();

    /**
     * Get a gene value at a specific position
     * @param index index
     * @return value at that index
     */
    int getGeneAtIndex(int index);

    /**
     * Used to set a value of a gene at a specific index
     * @param index index
     * @param gene value to set to
     */
    void setGeneAtIndex(int index, int gene);

    /**
     * Get the length of the chromosome aka how many genes there are
     * @return the chromosome length
     */
    int getLength();

    /**
     * Return if the chromosome is valid
     * @return true if it is a valid chromosome, false otherwise.
     */
    boolean isValidChromosome();
}
