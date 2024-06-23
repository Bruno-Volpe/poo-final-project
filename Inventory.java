import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;

    public Inventory() {
        products = new HashMap<>();
    }

    public void addProduct(String name, double price, int quantity) {
        products.put(name, new Product(name, price, quantity));
    }

    public boolean removeProduct(String name) {
        return products.remove(name) != null;
    }

    public Product getProduct(String name) {
        return products.get(name);
    }

    public Map<String, Product> getProducts() {
        return products;
    }
}
