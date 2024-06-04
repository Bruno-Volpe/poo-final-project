import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;

    public Inventory() {
        this.products = new HashMap<>();
    }

    // Adiciona um novo produto ao estoque
    public void addProduct(String name, double price, int quantity) {
        products.put(name, new Product(name, price, quantity));
    }

    // Retorna um produto com base no nome
    public Product getProduct(String name) {
        return products.get(name);
    }

    // Remove um produto do estoque
    public boolean removeProduct(String name) {
        if (products.containsKey(name)) {
            products.remove(name);
            return true;
        }
        return false;
    }

    // Retorna todos os produtos no estoque
    public Map<String, Product> getProducts() {
        return products;
    }
}
