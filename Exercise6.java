import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class Exercise6 {

    record Product(String name, boolean inStock) {}

    public static void main(String[] args) {

        List<Product> products = List.of(
                new Product("Laptop", true),
                new Product("Tablet", false),
                new Product("Phone", true),
                new Product("Monitor", false),
                new Product("Keyboard", true),
                new Product("Headset", true)
        );

        Map<Boolean, List<Product>> partitionedProducts = products.stream()
                .collect(partitioningBy(Product::inStock));

        System.out.println("In stock: " + partitionedProducts.get(true).size());
        System.out.println("Out of stock: " + partitionedProducts.get(false).size());
    }
}