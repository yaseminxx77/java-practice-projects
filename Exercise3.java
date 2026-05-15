import java.util.List;

public class Exercise3 {

    record Employee(String firstName, String lastName, String department) {

        public String fullName() {
            return firstName + " " + lastName;
        }
    }

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee("Anna", "Kowalski", "Engineering"),
                new Employee("Piotr", "Nowak", "Marketing"),
                new Employee("Maria", "Wiśniewska", "Engineering"),
                new Employee("Jan", "Zieliński", "HR")
        );

        List<String> fullNames = employees.stream()
                .map(Employee::fullName)
                .toList();

        System.out.println(fullNames);
    }
}