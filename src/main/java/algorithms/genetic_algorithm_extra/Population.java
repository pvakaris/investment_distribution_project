package algorithms.genetic_algorithm_extra;

public class Population {
    private final int populationSize;
    private final AbstractChromosome[] population;
    private final boolean minimisationAlgorithm;

    public Population(int populationSize, boolean minimisationAlgorithm, AbstractChromosome[] initialPopulation) {
        this.populationSize = populationSize;
        population = initialPopulation;
        this.minimisationAlgorithm = minimisationAlgorithm;
    }

    public AbstractChromosome getFittestChromosome(){
        double bestFit = population[0].getFitness();
        int bestFitIndex = 0;
        for (int i = 1; i < population.length; i++) {
            if ((!minimisationAlgorithm && bestFit <= population[i].getFitness()) ||
                    (minimisationAlgorithm && bestFit >= population[i].getFitness())) {
                bestFit = population[i].getFitness();
                bestFitIndex = i;
            }
        }
        return population[bestFitIndex];
    }

    public AbstractChromosome getSecondFittestChromosome() {
        int bestFit1 = 0;
        int bestFit2 = 0;
        for (int i = 1; i < population.length; i++) {
            if ((!minimisationAlgorithm && population[i].getFitness() > population[bestFit1].getFitness()) ||
                    (minimisationAlgorithm && population[i].getFitness() < population[bestFit1].getFitness())) {
                bestFit2 = bestFit1;
                bestFit1 = i;
            } else if ((!minimisationAlgorithm && population[i].getFitness() > population[bestFit2].getFitness()) ||
                    (minimisationAlgorithm && population[i].getFitness() > population[bestFit2].getFitness())) {
                bestFit2 = i;
            }
        }
        return population[bestFit2];
    }

    public int getLeastFittestIndex() {
        double leastFitVal = population[0].getFitness();
        int leastFitIndex = 0;
        for (int i = 1; i < population.length; i++) {
            if ((minimisationAlgorithm && leastFitVal <= population[i].getFitness()) ||
                    (!minimisationAlgorithm && leastFitVal >= population[i].getFitness())) {
                leastFitVal = population[i].getFitness();
                leastFitIndex = i;
            }
        }
        return leastFitIndex;
    }

    public void calculateAllFitness(){
        for (AbstractChromosome abstractChromosome : population) {
            abstractChromosome.calcFitness();
        }
    }

    public AbstractChromosome[] getChromosomes() {
        return population;
    }
}