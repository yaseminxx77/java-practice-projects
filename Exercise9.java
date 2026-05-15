import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;
public class Exercise9 {
    record Author(String country, List<String> bookTitles) {}
    public static void main(String[] args) {
        List<Author> authors = List.of(
                new Author("Poland", List.of("Solaris", "The Cyberiad")),
                new Author("UK", List.of("1984", "Animal Farm", "Brave New World")),
                new Author("Poland", List.of("The Doll")),
                new Author("UK", List.of("Fahrenheit 451")),
                new Author("France", List.of("The Little Prince"))
        );
        Map<String, List<String>> booksByCountry = authors.stream()
                .collect(groupingBy(
                        Author::country,
                        flatMapping(
                                author -> author.bookTitles().stream(),
                                filtering(
                                        title -> title.contains("a")
                                                && !title.equals("The Cyberiad"),
                                        toList()
                                )
                        )
                ));
        System.out.println(booksByCountry);
    }
}