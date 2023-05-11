package algorithms.genetic_algorithm_extra;

import java.util.*;

// Singleton
public class Generator {
    // RESULT
    private int[] fittestChromosomeGlobal;
    private Double fittestChromosomeFitness;

    private Population population;
    private AbstractChromosome fittestChromosome;
    private AbstractChromosome secondFittestChromosome;
    private AbstractChromosome offSpringChromosome;
    private int populationSize;
    private ChromosomeFactory factory;
    private final Data data = Data.getInstance();
    private final Random random = new Random();
    private boolean minimisationAlgorithm;

    /**
     * The constructor
     * @param populationSize how many chromosomes to create
     * @param chromosomeType the type of the chromosomes
     * @param minimisationAlgorithm if this is going to be a minimisation problem or maximisation
     */
    public Generator(int populationSize, ChromosomeType chromosomeType, boolean minimisationAlgorithm){
        this.populationSize = populationSize;
        factory = new ChromosomeFactory(chromosomeType);
        population = generateNewPopulation(minimisationAlgorithm);
        this.minimisationAlgorithm = minimisationAlgorithm;
        fittestChromosomeGlobal = null;
        fittestChromosomeFitness = null;
    }

    /**
     * If n is the size of the population, then create n random chromosomes
     * @return a new Population
     */
    private Population generateNewPopulation(boolean minimisationAlgorithm) {
        AbstractChromosome[] chromosomes = new AbstractChromosome[populationSize];
        for(int i = 0; i < populationSize; i++) {
            chromosomes[i] = factory.createRandomChromosome();
        }
        return new Population(populationSize, minimisationAlgorithm, chromosomes);
    }

    /**
     * Select the two fittest chromosomes
     */
    public void selection(){
        fittestChromosome = population.getFittestChromosome();
        secondFittestChromosome = population.getSecondFittestChromosome();
        updateFittestChromosome(fittestChromosome);
    }

    /**
     * Perform crossover operator on the population
     * Uniform Crossover Operator
     */
    public void crossover(){
        AbstractChromosome offSpring = factory.createEmptyChromosome();
        AbstractChromosome father = fittestChromosome;
        AbstractChromosome mother = secondFittestChromosome;

        // Choose each gene for the child randomly from parent genes
        for(int i=0;i<fittestChromosome.getLength();i++) {
            int fatherGene = father.getGeneAtIndex(i);
            int motherGene = mother.getGeneAtIndex(i);
            double rand = Math.random();
            if(rand < 0.5){
                offSpring.setGeneAtIndex(i, fatherGene);
            } else {
                offSpring.setGeneAtIndex(i, motherGene);
            }
        }
        offSpringChromosome = offSpring;
    }

    /**
     * Perform the random mutation on each of the chromosomes in the population
     * @param mutationRate The probability that a gene in a chromosome will be mutated
     */
    public void mutation(double mutationRate){
        Set<Integer> valuesThatGeneCanTake = data.getInvestorMap().keySet();
        for(AbstractChromosome chromosome : population.getChromosomes()) {
            // For each gene within the chromosome
            for(int i = 0; i < chromosome.getLength(); i++) {
                // Check if the gene should be mutated based on mutation rate
                if(random.nextDouble() < mutationRate) {
                    int oldValue = chromosome.getGeneAtIndex(i);
                    List<Integer> possibleNewValues = new ArrayList<>(valuesThatGeneCanTake);
                    possibleNewValues.remove(Integer.valueOf(oldValue)); // remove the old value from the possible new values
                    int newValue = possibleNewValues.get(random.nextInt(possibleNewValues.size())); // select a new value randomly
                    chromosome.setGeneAtIndex(i, newValue); // update the chromosome with the new gene
                }
            }
        }
    }

    /**
     * Put the new offspring into the place of the least-fittest chromosome
     */
    public void addOffSpring(){
        int leastFittestIndex = population.getLeastFittestIndex();
        population.getChromosomes()[leastFittestIndex] = offSpringChromosome;
    }

    /**
     * Used to get the population
     * @return the Population object
     */
    public Population getPopulation() {
        return population;
    }

    /**
     * Used to retrieve the fittest chromosome over the whole generation
     * @return the fittest chromosome
     */
    public int[] getFittestChromosomeAllocations() {
        return fittestChromosomeGlobal;
    }

    /**
     * Get the fitness of the fittest valid chromosome
     * @return return the fitness value of the chromosome
     */
    public double getFittestChromosomeFitness() {
        return fittestChromosomeFitness;
    }

    /**
     * Get the fittest chromosome from the current iteration, check if it is a valid chromosome.
     * If it is valid, and it is more fit than the previous fittest chromosome, update.
     * @param chromosome The chromosome which to consider
     */
    private void updateFittestChromosome(AbstractChromosome chromosome) {
        if(fittestChromosomeGlobal != null) {
            if(minimisationAlgorithm) {
                updateFittestChromosomeForMinimisation(chromosome);
            } else {
                updateFittestChromosomeForMaximisation(chromosome);
            }
        } else {
            fittestChromosomeGlobal = chromosome.getSlots();
            fittestChromosomeFitness = chromosome.getFitness();
        }
    }

    /**
     * Used to update the values of the fields holding information about the fittest chromosome.
     * @param chromosome Chromosome to use to update
     */
    private void updateFittestChromosomeForMinimisation(AbstractChromosome chromosome) {
        if(chromosome.getFitness() <= fittestChromosomeFitness) {
            fittestChromosomeGlobal = chromosome.getSlots();
            fittestChromosomeFitness = chromosome.getFitness();
        }
    }

    /**
     * Used to update the values of the fields holding information about the fittest chromosome.
     * @param chromosome Chromosome to use to update
     */
    private void updateFittestChromosomeForMaximisation(AbstractChromosome chromosome) {
        if(chromosome.getFitness() >= fittestChromosomeFitness) {
            fittestChromosomeGlobal = chromosome.getSlots();
            fittestChromosomeFitness = chromosome.getFitness();
        }
    }

}