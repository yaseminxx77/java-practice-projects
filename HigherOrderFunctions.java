import java.util.function.Function;

public class HigherOrderFunctions {

    public static void main(String[] args) {

        Function<Integer, Integer> addOne = x -> x + 1;
        Function<Integer, Integer> multiplyByTwo = x -> x * 2;

        Function<Integer, Integer> composedFunction =
                addOne.andThen(multiplyByTwo);

        int result = composedFunction.apply(4);

        System.out.println("Result: " + result);
    }

}