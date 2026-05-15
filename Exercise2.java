import java.util.IntSummaryStatistics;
import java.util.List;
import static java.util.stream.Collectors.*;

public class Exercise2 {

    record Sensor(String id, int reading) {}

    public static void main(String[] args) {

        List<Sensor> sensors = List.of(
                new Sensor("S1", 42),
                new Sensor("S2", 87),
                new Sensor("S3", 15),
                new Sensor("S4", 103),
                new Sensor("S5", 56),
                new Sensor("S6", 87)
        );
        IntSummaryStatistics statistics = sensors.stream()
                .collect(summarizingInt(Sensor::reading));

        System.out.println("Count: " + statistics.getCount());
        System.out.println("Sum: " + statistics.getSum());
        System.out.println("Average: " + statistics.getAverage());
        System.out.println("Min: " + statistics.getMin());
        System.out.println("Max: " + statistics.getMax());
    }
}