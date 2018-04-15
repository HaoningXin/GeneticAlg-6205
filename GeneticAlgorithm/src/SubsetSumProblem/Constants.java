package SubsetSumProblem;

import java.util.Random;

public class Constants {
    static Random random = new Random();
    public static final int populationSize = 200;
    public static final int chromesomeSize = 10000;
    public static final int[] targetArr;
    public static final int targetNum = 281000;
    public static final int generationTimes = 2000;
    public static final double rate = 0.8; // Elimination rate
    public static final int children = 2; // Each parents has two children

    static {
        targetArr = new int[chromesomeSize];
        for (int i = 0; i < chromesomeSize; i++) {
            targetArr[i] = random.nextInt(200);
        }
    }
}
