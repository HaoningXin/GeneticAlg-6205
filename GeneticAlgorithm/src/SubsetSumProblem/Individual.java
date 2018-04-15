package SubsetSumProblem;

import java.util.Random;

import static java.lang.Math.abs;

public class Individual {
    private int fitness;
    private int sum;
    private boolean[] genes;
    Random random = new Random();

    public Individual() {
        genes = new boolean[Constants.chromesomeSize];
    }

    public int calFitness(){
        sum = 0;
        int c = 0;
        fitness = 0;
        for (int i = 0; i < Constants.chromesomeSize; i++) {
            if (genes[i] == true) {
                sum += Constants.targetArr[i];
            }
        }
        return fitness = -abs(sum - Constants.targetNum);
    }

    public int getFitness() {
        return this.fitness;
    }

    public boolean[] getGenes(){
        return this.genes;
    }
}
