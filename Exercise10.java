import java.util.List;
import java.util.Optional;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class Exercise10 {
    record Student(String name, double gpa) {}
    record GpaRange(String lowestGpa, String highestGpa) {}
    public static void main(String[] args) {
        List<Student> students = List.of(
                new Student("Alice", 3.8),
                new Student("Bob", 2.9),
                new Student("Carol", 3.5),
                new Student("David", 3.2),
                new Student("Eve", 3.9),
                new Student("Frank", 2.7)
        );
        GpaRange result = students.stream()
                .collect(teeing(
                        minBy(comparing(Student::gpa)),
                        maxBy(comparing(Student::gpa)),
                        (Optional<Student> min, Optional<Student> max) ->
                                new GpaRange(
                                        min.map(Student::name).orElse("none"),
                                        max.map(Student::name).orElse("none")
                                )
                ));
        System.out.println(result);
    }
}