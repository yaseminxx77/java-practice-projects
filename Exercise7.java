import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class Exercise7 {

    record Ticket(String priority, String title) {}

    public static void main(String[] args) {

        List<Ticket> tickets = List.of(
                new Ticket("HIGH", "Database crash"),
                new Ticket("LOW", "Typo in footer"),
                new Ticket("MEDIUM", "Slow login"),
                new Ticket("HIGH", "Payment failure"),
                new Ticket("LOW", "Wrong icon colour"),
                new Ticket("HIGH", "Data loss"),
                new Ticket("MEDIUM", "Email not sent")
        );

        Map<String, Long> ticketCounts = tickets.stream()
                .collect(groupingBy(Ticket::priority, counting()));

        System.out.println(ticketCounts);
    }
}