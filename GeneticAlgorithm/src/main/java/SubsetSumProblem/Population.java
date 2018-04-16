package main.java.SubsetSumProblem;

public class Population {

    private Individual[] population;

    public Population() {
        population = new Individual[Constants.populationSize];
        for (int i = 0; i < Constants.populationSize; i++) {
            population[i] = new Individual();
        }
    }

    public Individual[] getPopulation() {
        return this.population;
    }
}
