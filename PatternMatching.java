public class PatternMatching {

    public static void main(String[] args) {

        Object obj = "Hello";

        if (obj instanceof String str) {
            System.out.println("Length of the string: " + str.length());
        } else {
            System.out.println("Not a string");
        }

    }

}