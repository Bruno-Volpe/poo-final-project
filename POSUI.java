import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class POSUI extends JFrame {
    private Inventory inventory;
    private Order currentOrder;
    private JTextArea orderDetails;
    private JTextArea inventoryDetails;
    private DecimalFormat df = new DecimalFormat("#.00");

    public POSUI() {
        inventory = new Inventory();
        currentOrder = new Order();
        orderDetails = new JTextArea(10, 30);
        inventoryDetails = new JTextArea(10, 30);
        setupUI();
    }

    private void setupUI() {
        setTitle("Java Café POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel norte para os botões
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

        // Painel central para detalhes da comanda e inventário
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        orderDetails.setEditable(false);
        inventoryDetails.setEditable(false);
        centerPanel.add(new JScrollPane(orderDetails));
        centerPanel.add(new JScrollPane(inventoryDetails));
        add(centerPanel, BorderLayout.CENTER);

        // Adiciona ActionListeners aos botões
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

    // Adiciona um item ao estoque
    private void addInventory() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto:");
        if (name == null || name.trim().isEmpty()) {
            return; // Usuário cancelou ou inseriu entrada inválida
        }
        double price;
        try {
            String priceInput = JOptionPane.showInputDialog(this, "Digite o preço do produto:");
            if (priceInput == null || priceInput.trim().isEmpty()) {
                return; // Usuário cancelou ou inseriu entrada inválida
            }
            price = Double.parseDouble(priceInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido.");
            return;
        }
        int quantity;
        try {
            String quantityInput = JOptionPane.showInputDialog(this, "Digite a quantidade:");
            if (quantityInput == null || quantityInput.trim().isEmpty()) {
                return; // Usuário cancelou ou inseriu entrada inválida
            }
            quantity = Integer.parseInt(quantityInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }
        inventory.addProduct(name, price, quantity);
        updateInventoryDetails();
    }

    // Adiciona um item à comanda
    private void addItem() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto:");
        if (name == null || name.trim().isEmpty()) {
            return; // Usuário cancelou ou inseriu entrada inválida
        }
        int quantity;
        try {
            String quantityInput = JOptionPane.showInputDialog(this, "Digite a quantidade:");
            if (quantityInput == null || quantityInput.trim().isEmpty()) {
                return; // Usuário cancelou ou inseriu entrada inválida
            }
            quantity = Integer.parseInt(quantityInput);
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

    // Remove um item da comanda
    private void removeItemFromOrder() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto para remover da comanda:");
        if (name == null || name.trim().isEmpty()) {
            return; // Usuário cancelou ou inseriu entrada inválida
        }
        int quantity;
        try {
            String quantityInput = JOptionPane.showInputDialog(this, "Digite a quantidade para remover:");
            if (quantityInput == null || quantityInput.trim().isEmpty()) {
                return; // Usuário cancelou ou inseriu entrada inválida
            }
            quantity = Integer.parseInt(quantityInput);
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

    // Remove um item do estoque
    private void removeItemFromInventory() {
        String name = JOptionPane.showInputDialog(this, "Digite o nome do produto para remover do estoque:");
        if (name == null || name.trim().isEmpty()) {
            return; // Usuário cancelou ou inseriu entrada inválida
        }
        if (inventory.removeProduct(name)) {
            updateInventoryDetails();
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado no estoque.");
        }
    }

    // Fecha a conta aplicando impostos e arredonda para 2 casas decimais
    private void completeOrder() {
        currentOrder.applyTaxes();
        String totalCost = df.format(currentOrder.getTotalCost());
        JOptionPane.showMessageDialog(this, "Total com impostos: $" + totalCost);
        currentOrder.generateReceipt();
        currentOrder = new Order();
        orderDetails.setText("");
    }

    // Atualiza os detalhes da comanda na interface
    private void updateOrderDetails() {
        StringBuilder orderText = new StringBuilder();
        for (Product item : currentOrder.getItems()) {
            orderText.append(item.getName()).append(" x ").append(item.getQuantity()).append(" - $").append(item.getPrice() * item.getQuantity()).append("\n");
        }
        orderText.append("Total: $").append(df.format(currentOrder.getTotalCost()));
        orderDetails.setText(orderText.toString());
    }

    // Atualiza os detalhes do estoque na interface
    private void updateInventoryDetails() {
        StringBuilder inventoryText = new StringBuilder();
        for (Product product : inventory.getProducts().values()) {
            inventoryText.append(product.getName()).append(" - ").append(product.getQuantity()).append(" em estoque - $").append(product.getPrice()).append("\n");
        }
        inventoryDetails.setText(inventoryText.toString());
    }
}
