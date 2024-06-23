import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class POSUI extends JFrame {
    private Inventory inventory;
    private Map<String, Order> orders;
    private JTabbedPane tabbedPane;
    private JTextArea inventoryDetails;
    private DecimalFormat df = new DecimalFormat("#.00");
    private static final String INVENTORY_FILE = "inventory.txt";
    private static final String ORDERS_FILE = "orders.txt";

    public POSUI() {
        // Inicializa os componentes da interface
        tabbedPane = new JTabbedPane();
        inventoryDetails = new JTextArea(10, 30);

        // Configura a interface do usuário
        setupUI();

        // Carrega o inventário e os pedidos
        loadInventory();
        loadOrders();

        // Atualiza os detalhes da interface
        updateInventoryDetails();
    }

    private void setupUI() {
        setTitle("Java Café POS System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel norte para os botões
        JPanel northPanel = new JPanel();
        JButton newOrderButton = new JButton("Nova Comanda");
        JButton closeOrderButton = new JButton("Fechar Comanda Atual");
        JButton addInventoryButton = new JButton("Adicionar ao Estoque");
        JButton addButton = new JButton("Adicionar à Comanda");
        JButton removeOrderButton = new JButton("Deletar da Comanda");
        JButton removeInventoryButton = new JButton("Deletar do Estoque");
        northPanel.add(newOrderButton);
        northPanel.add(closeOrderButton);
        northPanel.add(addInventoryButton);
        northPanel.add(addButton);
        northPanel.add(removeOrderButton);
        northPanel.add(removeInventoryButton);
        add(northPanel, BorderLayout.NORTH);

        // Painel central para detalhes das comandas e inventário
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(tabbedPane);
        inventoryDetails.setEditable(false);
        centerPanel.add(new JScrollPane(inventoryDetails));
        add(centerPanel, BorderLayout.CENTER);

        // Adiciona ActionListeners aos botões
        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewOrder();
            }
        });

        closeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeCurrentOrder();
            }
        });

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

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveInventory();
                saveOrders();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createNewOrder() {
        String orderName = JOptionPane.showInputDialog(this, "Digite o nome da nova comanda:");
        if (orderName == null || orderName.trim().isEmpty() || orders.containsKey(orderName)) {
            return; // Usuário cancelou, inseriu entrada inválida ou nome já existe
        }
        Order newOrder = new Order();
        orders.put(orderName, newOrder);
        JTextArea orderDetails = new JTextArea(10, 30);
        orderDetails.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderDetails);
        tabbedPane.addTab(orderName, scrollPane);
    }

    private void closeCurrentOrder() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex != -1) {
            String orderName = tabbedPane.getTitleAt(selectedIndex);
            Order currentOrder = orders.get(orderName);
            if (currentOrder != null) {
                currentOrder.applyTaxes();
                String totalCost = df.format(currentOrder.getTotalCost());
                JOptionPane.showMessageDialog(this, "Total com impostos: $" + totalCost);
                currentOrder.generateReceipt();
            }
            orders.remove(orderName);
            tabbedPane.remove(selectedIndex);
        }
    }

    private Order getCurrentOrder() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex == -1) {
            return null;
        }
        String orderName = tabbedPane.getTitleAt(selectedIndex);
        return orders.get(orderName);
    }

    private JTextArea getCurrentOrderDetailsArea() {
        int selectedIndex = tabbedPane.getSelectedIndex();
        if (selectedIndex == -1) {
            return null;
        }
        JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(selectedIndex);
        return (JTextArea) scrollPane.getViewport().getView();
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
        Order currentOrder = getCurrentOrder();
        if (currentOrder == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma comanda selecionada.");
            return;
        }
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
        Order currentOrder = getCurrentOrder();
        if (currentOrder == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma comanda selecionada.");
            return;
        }
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

    // Atualiza os detalhes da comanda na interface
    private void updateOrderDetails() {
        JTextArea orderDetails = getCurrentOrderDetailsArea();
        Order currentOrder = getCurrentOrder();
        if (orderDetails == null || currentOrder == null) {
            return;
        }
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

    private void saveInventory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Product product : inventory.getProducts().values()) {
                writer.write(product.getName() + "," + product.getPrice() + "," + product.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadInventory() {
        inventory = new Inventory();
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            return; // Arquivo não existe, inicializa inventário vazio
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue; // Ignora linhas mal formadas
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                inventory.addProduct(name, price, quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOrders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE))) {
            for (Map.Entry<String, Order> entry : orders.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue().toText());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {
        orders = new HashMap<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) {
            return; // Arquivo não existe, inicializa pedidos vazios
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length < 2) continue; // Ignora linhas mal formadas
                String orderName = parts[0];
                Order order = new Order();
                order.fromText(parts[1]);
                orders.put(orderName, order);
                JTextArea orderDetails = new JTextArea(10, 30);
                orderDetails.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(orderDetails);
                tabbedPane.addTab(orderName, scrollPane);
                updateOrderDetails();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
