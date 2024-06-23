import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> items;
    private double totalCost;

    public Order() {
        this.items = new ArrayList<>();
        this.totalCost = 0;
    }

    // Adiciona um item à comanda e atualiza o custo total
    public void addItem(Product product, int quantity) {
        product.setQuantity(product.getQuantity() - quantity);
        items.add(new Product(product.getName(), product.getPrice(), quantity));
        totalCost += product.getPrice() * quantity;
    }

    // Remove um item da comanda e atualiza o custo total
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

    // Retorna o custo total da comanda
    public double getTotalCost() {
        return totalCost;
    }

    // Retorna o preço de um produto na comanda
    public double getProductPrice(String name) {
        for (Product item : items) {
            if (item.getName().equals(name)) {
                return item.getPrice();
            }
        }
        return 0;
    }

    // Retorna todos os itens da comanda
    public List<Product> getItems() {
        return items;
    }

    // Aplica impostos ao custo total (10%)
    public void applyTaxes() {
        totalCost *= 1.1;
    }

    // Gera um recibo da comanda
    public void generateReceipt() {
        System.out.println("Recibo:");
        for (Product item : items) {
            System.out.println(item.getName() + " x " + item.getQuantity() + " - $" + item.getPrice() * item.getQuantity());
        }
        System.out.println("Total: $" + totalCost);
    }

    // Salva a comanda em um formato de texto
    public String toText() {
        StringBuilder builder = new StringBuilder();
        for (Product item : items) {
            builder.append(item.getName()).append(",").append(item.getPrice()).append(",").append(item.getQuantity()).append("\n");
        }
        return builder.toString();
    }

    // Carrega a comanda de um formato de texto
    public void fromText(String text) {
        items.clear();
        totalCost = 0;
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 3) continue; // Ignora linhas mal formadas
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            items.add(new Product(name, price, quantity));
            totalCost += price * quantity;
        }
    }
}
