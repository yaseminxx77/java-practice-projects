import java.util.List;

public class Exercise4 {

    record Department(String name, List<String> employeeNames) {}

    public static void main(String[] args) {

        List<Department> departments = List.of(
                new Department("Engineering", List.of("Anna", "Maria", "Tomek")),
                new Department("Marketing", List.of("Piotr")),
                new Department("HR", List.of("Jan", "Zofia")),
                new Department("Legal", List.of())
        );

        List<String> allEmployees = departments.stream()
                .flatMap(department -> department.employeeNames().stream())
                .toList();

        System.out.println(allEmployees);
    }
}