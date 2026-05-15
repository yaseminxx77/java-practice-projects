import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;
public class Exercise8 {
    record Sale(String region, int units) {}
    public static void main(String[] args) {
        List<Sale> sales = List.of(
                new Sale("North", 120),
                new Sale("South", 85),
                new Sale("North", 200),
                new Sale("East", 60),
                new Sale("South", 140),
                new Sale("East", 95),
                new Sale("North", 75)
        );
        Map<String, Integer> totalUnits = sales.stream()
                .collect(groupingBy(Sale::region,
                        summingInt(Sale::units)));
        Map<String, Integer> saleCounts = sales.stream()
                .collect(groupingBy(
                        Sale::region,
                        collectingAndThen(
                                counting(),
                                Long::intValue
                        )
                ));
        System.out.println(totalUnits);
        System.out.println(saleCounts);
    }
}