import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String [] args) {

        AVLTree tree = new AVLTree();
        Random random = new Random();

        ArrayList<Long> measures = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            long current = System.nanoTime();
            tree.insert(random.nextInt(100000));
            long result = System.nanoTime() - current;
            measures.add(result);

        }
        System.out.println("Час вставки у нс: ");
        stat(measures);
        System.out.println("==========");

        measures.clear();
        for (int i = 0; i < 100000; i++) {
            long current = System.nanoTime();
            tree.searchTree(random.nextInt(100000));
            long result = System.nanoTime() - current;
            measures.add(result);

        }
        System.out.println("Час пошуку у нс: ");
        stat(measures);
        System.out.println("==========");

        measures.clear();
        for (int i = 0; i < 100000; i++) {
            long current = System.nanoTime();
            tree.deleteNode(random.nextInt(100000));
            long result = System.nanoTime() - current;
            measures.add(result);

        }
        System.out.println("Час видалення у нс: ");
        stat(measures);
        System.out.println("==========");

    }


    public static void stat(ArrayList<Long> measures) {
        long max = measures.get(0);
        long min = measures.get(0);
        long sum = 0;
        for (long i : measures) {
            sum += i;
            if(i < min) {
                min = i;
                continue;
            }
            if(i > max) {
                max = i;
            }
        }
        double average = sum / measures.size();
        System.out.println("Максимальний " + max);
        System.out.println("Мінімальний " + min);
        System.out.println("Середній " + average);

    }
}
