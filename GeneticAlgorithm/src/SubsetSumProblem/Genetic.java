package SubsetSumProblem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Genetic {
    static Random random = new Random();

    public Individual[] initialPopulation() {
        Population population = new Population(); // Beginning pop
        Individual[] oldP = population.getPopulation();
        for (int i = 0; i < Constants.populationSize; i++) {
            for (int j = 0; j < Constants.chromesomeSize; j++) {
                if (random.nextInt(2) == 1) oldP[i].getGenes()[j] = false;
                else oldP[i].getGenes()[j] = true;
            }
//            System.out.println(oldP[i].calFitness());
//            int c = 0;
//            for(int k=0;k<Constants.chromesomeSize;k++){
//                if(oldP[i].getGenes()[k] == true) c++;
//            }
//            System.out.println(c);
        }
        return oldP;
    }

    public Individual[] crossover(Individual p1, Individual p2) {
        Individual[] children = new Individual[Constants.children];
        for (int i = 0; i < Constants.children; i++) {
            children[i] = new Individual();
        }
        for (int i = 0; i < Constants.children; i++) {
            int point1 = random.nextInt(Constants.chromesomeSize / 2 - 2) + 1;
            int point2 = random.nextInt(Constants.chromesomeSize / 2 - 1) + Constants.chromesomeSize / 2;
            for (int j = 0; j < point1; j++) children[i].getGenes()[j] = p1.getGenes()[j];
            for (int j = point1; j < point2; j++) children[i].getGenes()[j] = p2.getGenes()[j];
            for (int j = point2; j < Constants.chromesomeSize; j++) children[i].getGenes()[j] = p1.getGenes()[j];
        }
        return children;
    }

    public void mutation(Individual individual) {
        for (int j = 0; j < 50; j++) {
            int i = random.nextInt(Constants.chromesomeSize);
            if (individual.getGenes()[i]) individual.getGenes()[i] = false;
            else individual.getGenes()[i] = true;
        }
    }

    public void selection(Individual[] population) {
        for (Individual individual : population) {
            individual.calFitness();
        }
//        for(int i =0;i<Constants.populationSize;i++){
//            System.out.println(population[i].getFitness());
//        }
        Arrays.sort(population, (i1, i2) -> i2.getFitness() - i1.getFitness());
    }

    public static void main(String[] args) {
        File file = new File("Genetic.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }


        int count = 0;
        Genetic genetic = new Genetic();
        Individual[] oldP = genetic.initialPopulation();
        genetic.selection(oldP); // Sorted beginning pop
        System.out.println("Generation: " + count + " Fittest one is: " + oldP[0].getFitness());
        try {
            bufferedWriter.newLine();
            bufferedWriter.write( "0," + oldP[0].getFitness() );
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < Constants.generationTimes; i++) {
            count++;
            Population population = new Population();
            Individual[] newP = population.getPopulation();
            newP[0] = oldP[0];
            for (int j = 1; j < Constants.populationSize; j = j + Constants.children) {
                int p1 = random.nextInt((int) (oldP.length * Constants.rate));
                int p2 = random.nextInt((int) (oldP.length * Constants.rate));
                Individual[] children = genetic.crossover(oldP[p1], oldP[p2]);
                for (int k = 0; k < children.length; k++) {
                    if (j + k == Constants.populationSize) break;
                    if (random.nextInt(100) < 1) {
//                        System.out.println(123123123);
                        genetic.mutation(children[k]);
                    }
                    newP[j + k] = children[k];
                }
            }
            genetic.selection(newP);
            if (newP[0].getFitness() == 0) {
                System.out.println("Find the fittest! On generation " + count);
                return;
            }
            System.out.println("Generation: " + count + " Fittest one is: " + newP[0].getFitness());

            try {
                bufferedWriter.write(count+","+newP[0].getFitness());
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            oldP = newP;
        }

        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
