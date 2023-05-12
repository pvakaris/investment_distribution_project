## Information

This is a solution for the Landbay assignment.

---
## Algorithms

Two different strategies were implemented for solving this problem. The greedy approach and the one that uses a genetic algorithm
that finds the optimal solution depending on the fitness function. While the greedy approach works correctly it may fail to find the
global optimal solution. Additionally, it is only good for this problem. 

On the other hand, the genetic algorithm is more flexible as depending on the ChromosomeType used, it can solve different problems.
However, in this early stage, it fails to find valid solutions. Improvements to it need to be made.
---
### GreedyAlgorithm

Assumptions made:
- Investors always look for the largest mortgage to fund
- Investors that have the smallest expected annual income from interest get to choose first, when the abstractAlgorithm considers the next investment.

Investments sorted by the amount of money needed. For each investment, the algorithm looks for an investor. It first starts from the investors,
that currently hold the smallest annual income from interest.
 ---
### Genetic algorithm

The algorithm implements a genetic algorithm, that depending on the AbstractChromosome implementation used, tries to find the optimal solution to the problem.
The ChromosomeType tells what a single solution to the problem looks like. At the moment, only one implementation of the AbstractChromosome exists - the HardBoundaryChromosome (further referred to as HBC).
The HBC can be seen as an array (`int[] slots`) where each index in the array represents an investment. If we have 5 investments, the array will be of length 5.
For each entry in the array, an investor is assigned. It could also be the case that the investment is not assigned any investor. Then the value at the index will be -1. 
For instance, if we have 3 investors (IDs 0, 1, 2) and 3 investments (IDs 0, 1, 2, 3, 4), a possible solution could look 
`[2, 1, -1, 1, 0]`.

The algorithm initially creates a Population of chromosomes and then runs an evolutionary cycle during which the best solutions
are selected that reproduce and thus as the time goes results in a population of increasingly better solutions.

The fitness function of the HBC is calculating the standard deviation of all investor annual incomes. The genetic algorithm
at this stage looks to minimise the standard deviation, aiming for each investor to earn similar amounts of money based on their investments.

The flexibility of this approach is that if we wanted to switch the perspective of the problem, allowing for multiple investors to
invest into the same investment, we could just implement a new AbstractChromosome called SoftBoundaryChromosome (further referred to as SBC), for example, with its own fitness evaluation function.

The SBC could look like this: 3 investors and 2 investments ---> an array of size 2 * 3 = 6 `[0.5, 0, 0.5, 0, 1, 0]`
This array indicates that 1st and 3rd investors divided the 1st investment equally among themselves (50/50), while the second
investment was fully funded by the 2nd investor. If the aim again was to minimise the standard deviation of average incomes, this approach
could find the perfect distribution of investments so that each investor earns the same.

Various different AbstractChromosome implementations can exist depending on the problem. The only thing that would have to be changed is what
parameters are passed into the GeneticAlgorithm.
---
## Execution

This part contains information about running the application and observing the results.

---
### Cloning and running using Maven

1. Clone the repository
2. Navigate to the directory containing the cloned code
3. Open the terminal in that directory
4. Make sure that JDK 11 and Maven are reachable in the environment.
5. Run command ```mvn clean package``` to clean the project and package a new .jar
6. Run command ```java -jar target/LandbayChallenge-1.2.jar```
7. Observe the output in the terminal. The execution logs can be found in logs/logs.log file.
8. *OPTIONAL* Run tests using ```mvn test```

---
### Making changes

In order to change what algorithm implementation is used, please go to file `src/main/java/services/Executor.java` and 
comment/uncomment lines 50-51 accordingly.

When running the GeneticAlgorithm, a verbose explanation of the result can be
printed into the console by uncommenting line 87 in `src/main/java/algorithms/GeneticAlgorithm.java`.

---
Author: **Vakaris Paulavičius**

Version: **1.2**