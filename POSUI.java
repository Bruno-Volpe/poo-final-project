import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class POSUI extends JFrame {
    private Inventory inventory;
    private Order currentOrder;
    private JTextArea orderDetails;

    public POSUI() {
        inventory = new Inventory();
        currentOrder = new Order();
        orderDetails = new JTextArea(10, 30);
        setupUI();
    }

    private void setupUI() {
        setTitle("Java Café POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // North Panel for buttons
        JPanel northPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton completeButton = new JButton("Complete Order");
        JButton viewInventoryButton = new JButton("View Inventory");
        JButton viewSalesButton = new JButton("View Sales");
        northPanel.add(addButton);
        northPanel.add(completeButton);
        northPanel.add(viewInventoryButton);
        northPanel.add(viewSalesButton);
        add(northPanel, BorderLayout.NORTH);

        // Center Panel for order details
        JPanel centerPanel = new JPanel();
        orderDetails.setEditable(false);
        centerPanel.add(new JScrollPane(orderDetails));
        add(centerPanel, BorderLayout.CENTER);

        // Add Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completeOrder();
            }
        });

        viewInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewInventory();
            }
        });

        viewSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSales();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addItem() {
        String name = JOptionPane.showInputDialog(this, "Enter product name:");
        int quantity = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter quantity:"));
        Product product = inventory.getProduct(name);
        if (product != null && product.getQuantity() >= quantity) {
            currentOrder.addItem(product, quantity);
            updateOrderDetails();
        } else {
            JOptionPane.showMessageDialog(this, "Product not available or insufficient stock.");
        }
    }

    private void completeOrder() {
        currentOrder.applyTaxes();
        currentOrder.generateReceipt();
        currentOrder = new Order();
        orderDetails.setText("");
    }

    private void viewInventory() {
        StringBuilder inventoryDetails = new StringBuilder();
        for (Product product : inventory.getProducts().values()) {
            inventoryDetails.append(product.getName()).append(" - ").append(product.getQuantity())
                    .append(" in stock\n");
        }
        JOptionPane.showMessageDialog(this, inventoryDetails.toString());
    }

    private void viewSales() {
        // Implement view sales logic
    }

    private void updateOrderDetails() {
        StringBuilder orderText = new StringBuilder();
        for (Product item : currentOrder.getItems()) {
            orderText.append(item.getName()).append(" x ").append(item.getQuantity()).append(" - $")
                    .append(item.getPrice() * item.getQuantity()).append("\n");
        }
        orderText.append("Total: $").append(currentOrder.getTotalCost());
        orderDetails.setText(orderText.toString());
    }
}
