import java.util.function.Function;

public class FirstClassFunctions {

    public static void main(String[] args) {

        Function<Integer, Integer> doubleValue = x -> x * 2;
        Function<Integer, Integer> squareValue = x -> x * x;

        int result1 = doubleValue.apply(5);
        int result2 = squareValue.apply(5);

        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
    }

}