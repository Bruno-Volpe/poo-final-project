import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class POSUI extends JFrame {
    private Inventory inventory;
    private Order currentOrder;
    private JTextArea orderDetails;
    private JTextArea inventoryDetails;

    public POSUI() {
        inventory = new Inventory();
        currentOrder = new Order();
        orderDetails = new JTextArea(10, 30);
        inventoryDetails = new JTextArea(10, 30);
        setupUI();
        // Sample inventory for testing
        inventory.addProduct("Coffee", 2.5, 10);
        inventory.addProduct("Tea", 1.5, 20);
        inventory.addProduct("Sandwich", 5.0, 15);
        updateInventoryDetails();
    }

    private void setupUI() {
        setTitle("Java Café POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel for buttons
        JPanel northPanel = new JPanel();
        JButton addInventoryButton = new JButton("Adicionar ao Estoque");
        JButton addButton = new JButton("Adicionar à Comanda");
        JButton removeOrderButton = new JButton("Deletar da Comanda");
        JButton removeInventoryButton = new JButton("Deletar do Estoque");
        JButton completeButton = new JButton("Fechar a Conta");
        northPanel.add(addInventoryButton);
        northPanel.add(addButton);
        northPanel.add(removeOrderButton);
        northPanel.add(removeInventoryButton);
        northPanel.add(completeButton);
        add(northPanel, BorderLayout.NORTH);

        // Center Panel for order details and inventory
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        orderDetails.setEditable(false);
        inventoryDetails.setEditable(false);
        centerPanel.add(new JScrollPane(orderDetails));
        centerPanel.add(new JScrollPane(inventoryDetails));
        add(centerPanel, BorderLayout.CENTER);

        // Add Action Listeners
        addInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInventory();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        removeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItemFromOrder();
            }
        });

        removeInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItemFromInventory();
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeOrder();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addItem() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto:");
        if (name == null || name.isEmpty()) {
            return; // User canceled or entered invalid input
        }
        int quantity;
        try {
            quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a quantidade:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }
        Product product = inventory.getProduct(name);
        if (product != null && product.getQuantity() >= quantity) {
            currentOrder.addItem(product, quantity);
            updateOrderDetails();
            updateInventoryDetails();
        } else {
            JOptionPane.showMessageDialog(this, "Produto não disponível ou estoque insuficiente.");
        }
    }

    private void removeItemFromOrder() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto para remover da comanda:");
        if (name == null || name.isEmpty()) {
            return; // User canceled or entered invalid input
        }
        int quantity;
        try {
            quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a quantidade para remover:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }
        if (currentOrder.removeItem(name, quantity)) {
            Product product = inventory.getProduct(name);
            if (product != null) {
                product.setQuantity(product.getQuantity() + quantity); // Adiciona o item removido de volta ao estoque
            } else {
                inventory.addProduct(name, currentOrder.getProductPrice(name), quantity); // Se o produto não existe no estoque, adiciona-o novamente
            }
            updateOrderDetails();
            updateInventoryDetails();
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado na comanda ou quantidade inválida.");
        }
    }

    private void removeItemFromInventory() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto para remover do estoque:");
        if (name == null || name.isEmpty()) {
            return; // User canceled or entered invalid input
        }
        if (inventory.removeProduct(name)) {
            updateInventoryDetails();
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado no estoque.");
        }
    }

    private void completeOrder() {
        currentOrder.applyTaxes();
        JOptionPane.showMessageDialog(this, "Total com impostos: $" + currentOrder.getTotalCost());
        currentOrder.generateReceipt();
        currentOrder = new Order();
        orderDetails.setText("");
    }

    private void addInventory() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto:");
        if (name == null || name.isEmpty()) {
            return; // User canceled or entered invalid input
        }
        double price;
        try {
            price = Double.parseDouble(JOptionPane.showInputDialog(this, "Digite o preço do produto:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido.");
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Digite a quantidade:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }
        inventory.addProduct(name, price, quantity);
        updateInventoryDetails();
    }

    private void updateOrderDetails() {
        StringBuilder orderText = new StringBuilder();
        for (Product item : currentOrder.getItems()) {
            orderText.append(item.getName()).append(" x ").append(item.getQuantity()).append(" - $").append(item.getPrice() * item.getQuantity()).append("\n");
        }
        orderText.append("Total: $").append(currentOrder.getTotalCost());
        orderDetails.setText(orderText.toString());
    }

    private void updateInventoryDetails() {
        StringBuilder inventoryText = new StringBuilder();
        for (Product product : inventory.getProducts().values()) {
            inventoryText.append(product.getName()).append(" - ").append(product.getQuantity()).append(" em estoque - $").append(product.getPrice()).append("\n");
        }
        inventoryDetails.setText(inventoryText.toString());
    }
}
