import java.util.List;

public class Exercise5 {

    record Transaction(String id, double amount, boolean flagged) {}

    public static void main(String[] args) {

        List<Transaction> transactions = List.of(
                new Transaction("T1", 150.0, false),
                new Transaction("T2", 3200.0, true),
                new Transaction("T3", 45.0, false),
                new Transaction("T4", 870.0, false),
                new Transaction("T5", 1500.0, true)
        );
        boolean anyFlagged = transactions.stream()
                .anyMatch(Transaction::flagged);
        boolean allPositive = transactions.stream()
                .allMatch(transaction -> transaction.amount() > 0);
        boolean noneExceed = transactions.stream()
                .noneMatch(transaction -> transaction.amount() > 10000);

        System.out.println("Any flagged: " + anyFlagged);
        System.out.println("All positive: " + allPositive);
        System.out.println("None exceed 10000: " + noneExceed);
    }
}