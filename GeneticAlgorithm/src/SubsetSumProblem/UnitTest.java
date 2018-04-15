package SubsetSumProblem;

import org.junit.Test;

import java.util.Arrays;

import static java.lang.Math.abs;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class UnitTest {
    @Test
    public void testPopulation(){
        Population p = new Population();
        assertEquals(p.getPopulation().length, Constants.populationSize);
    }

    @Test
    public void testIndividual(){
        Population p = new Population();
        assertEquals(p.getPopulation()[0].getGenes().length, Constants.chromesomeSize);
    }

    @Test
    public void testInitial() {
        Genetic g = new Genetic();
        Individual[] p = g.initialPopulation();
        assertEquals(Constants.populationSize, p.length);
        assertEquals(Constants.chromesomeSize, p[0].getGenes().length);
        int t = 0, f = 0;
        for (boolean b : p[0].getGenes()) {
            if (b) t++;
            else f++;
        }
        assertTrue(t != 0 && t < Constants.chromesomeSize);
        assertTrue(f != 0 && f < Constants.chromesomeSize);
    }

    @Test
    public void testCross() {
        Genetic g = new Genetic();
        Individual[] p = g.initialPopulation();
        Individual[] pc = g.crossover(p[0], p[1]);
        assertEquals(Constants.children, pc.length);
        int i1=0, i2=0;
        for(int i = 0;i<Constants.chromesomeSize;i++){
            if(pc[0].getGenes()[i] != p[0].getGenes()[i]){
                i1 = i;
                break;
            }
        }
        for(int i = i1; i < Constants.chromesomeSize;i++){
            if(pc[0].getGenes()[i] != p[1].getGenes()[i]){
                i2=i;
                break;
            }
        }
        for(int i = i2; i< Constants.chromesomeSize;i++){
            assertEquals(pc[0].getGenes()[i], p[0].getGenes()[i]);
        }
    }

    @Test
    public void testMutation() {
        Genetic g = new Genetic();
        Individual[] p = g.initialPopulation();
        int old1 = 0, new1 = 0;
        for(boolean b : p[0].getGenes()) if(b) old1++;
        g.mutation(p[0]);
        for(boolean b : p[0].getGenes()) if(b) new1++;
        assertTrue(old1 != new1);
    }

    @Test
    public void testCalFitness() {
        Genetic g = new Genetic();
        Individual[] p = g.initialPopulation();
        int sum =0;
        for (int i = 0; i < Constants.chromesomeSize; i++) {
            if (p[0].getGenes()[i] == true) {
                sum += Constants.targetArr[i];
            }
        }
        sum = -abs(sum - Constants.targetNum);
        p[0].calFitness();
        assertEquals(p[0].getFitness(), sum);
    }

    @Test
    public void testSelection() {
        Genetic g = new Genetic();
        Individual[] p = g.initialPopulation();
        int[] a = new int[Constants.populationSize];
        for (int i = 0; i< Constants.populationSize; i++) {
            p[i].calFitness();
            a[i] = p[i].getFitness();
        }
        g.selection(p);
        Arrays.sort(a);
        for(int i = 0;i<Constants.populationSize;i++){
            assertEquals(p[i].getFitness(), a[Constants.populationSize - i-1]);
        }
    }
}
