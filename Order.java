import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> items;
    private double totalCost;

    public Order() {
        this.items = new ArrayList<>();
        this.totalCost = 0;
    }

    public void addItem(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        items.add(new Product(product.getName(), product.getPrice(), quantity));
        totalCost += product.getPrice() * quantity;
    }

    public boolean removeItem(String name, int quantity) {
        for (Product item : items) {
            if (item.getName().equals(name) && item.getQuantity() >= quantity) {
                item.setQuantity(item.getQuantity() - quantity);
                totalCost -= item.getPrice() * quantity;
                if (item.getQuantity() == 0) {
                    items.remove(item);
                }
                return true;
            }
        }
        return false;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getProductPrice(String name) {
        for (Product item : items) {
            if (item.getName().equals(name)) {
                return item.getPrice();
            }
        }
        return 0;
    }

    public List<Product> getItems() {
        return items;
    }

    public void applyTaxes() {
        totalCost *= 1.1; // Assuming 10% tax rate
    }

    public void generateReceipt() {
        System.out.println("Receipt:");
        for (Product item : items) {
            System.out.println(item.getName() + " x " + item.getQuantity() + " - $" + item.getPrice() * item.getQuantity());
        }
        System.out.println("Total: $" + totalCost);
    }
}
