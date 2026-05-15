public class PureFunctions {

    public static int add(int a, int b) {
        return a + b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static void main(String[] args) {

        int result1 = add(3, 4);
        int result2 = multiply(3, 4);

        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
    }
}