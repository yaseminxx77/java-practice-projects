import java.util.List;
import static java.util.stream.Collectors.*;

public class Exercise1 {

    record Book(String title, int pageCount) {}

    public static void main(String[] args) {

        List<Book> books = List.of(
                new Book("Refactoring", 448),
                new Book("Clean Code", 464),
                new Book("The Pragmatic Programmer", 352),
                new Book("Design Patterns", 395),
                new Book("Working Effectively", 456)
        );

        double average = books.stream()
                .collect(averagingDouble(Book::pageCount));

        System.out.println("Average page count: " + average);
    }
}