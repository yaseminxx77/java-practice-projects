import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyParallelism {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            System.out.println("Task 1 is executing");
        };

        Runnable task2 = () -> {
            System.out.println("Task 2 is executing");
        };

        executor.submit(task1);
        executor.submit(task2);
        executor.shutdown();
    }

}