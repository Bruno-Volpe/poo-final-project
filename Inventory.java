import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;

    public Inventory() {
        this.products = new HashMap<>();
    }

    public void addProduct(String name, double price, int quantity) {
        products.put(name, new Product(name, price, quantity));
    }

    public Product getProduct(String name) {
        return products.get(name);
    }

    public void updateProduct(String name, int quantity) {
        if (products.containsKey(name)) {
            Product product = products.get(name);
            product.setQuantity(quantity);
        }
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void checkLowStock() {
        for (Product product : products.values()) {
            if (product.getQuantity() < 5) {
                System.out.println("Low stock alert: " + product.getName());
            }
        }
    }
}
