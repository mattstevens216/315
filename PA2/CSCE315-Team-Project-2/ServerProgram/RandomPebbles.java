/**
 * Created by huaiwu on 3/29/17.
 */
import java.lang.*;
import java.util.Random;
import  java.util.*;
public class RandomPebbles {
    public static List<Integer> RandomPebbles(int targetSum, int numberOfDraws) {
        Random r = new Random();
        List<Integer> load = new ArrayList<>();

        //random numbers
        int sum = 0;
        for (int i = 0; i < numberOfDraws; i++) {
            int next = r.nextInt(targetSum) + 1;
            load.add(next);
            sum += next;
        }

        //scale to the desired target sum
        double scale = 1d * targetSum / sum;
        sum = 0;
        for (int i = 0; i < numberOfDraws; i++) {
            load.set(i, (int) (load.get(i) * scale));
            sum += load.get(i);
        }

        //take rounding issues into account
        while(sum++ < targetSum) {
            int i = r.nextInt(numberOfDraws);
            load.set(i, load.get(i) + 1);
        }

        return load;
    }
}
