import java.util.stream.Stream;

public class LazyEvaluation {

    public static void main(String[] args) {

        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);

        Stream<Integer> squaredStream = stream.map(x -> {
            System.out.println("Doubling " + x);
            return x * 2;
        });

        squaredStream.forEach(System.out::println);

    }

}