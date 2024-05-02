package org.example;

import jdk.jfr.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class ShoppingGUI {
    private JFrame frame;
    private JComboBox<String> categoryDropdown;
    private JTable productTable;
    private JTextArea productDetails;
    private JButton addToCartButton, viewCartButton;
    private JPanel detailsPanel;
    private JLabel productIdLabel, nameLabel, CategoryLabel, sizeLabel, colorLabel, availableItemsLabel;
    private ShoppingCart shoppingCart = new ShoppingCart();

    public ShoppingGUI(List<Product> products) {
        this.initializeUI(products);
    }

    private void initializeUI(final List<Product> products) {
        this.frame = new JFrame("Westminster Shopping Centre");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        // Top panel with category dropdown and shopping cart button
        JPanel topPanel = new JPanel(new BorderLayout());
        String[] categories = new String[]{"All", "Electronics", "Clothing"};
        this.categoryDropdown = new JComboBox<>(categories);
        topPanel.add(this.categoryDropdown, BorderLayout.WEST);

        this.viewCartButton = new JButton("Shopping Cart");
        topPanel.add(this.viewCartButton, BorderLayout.EAST);

        this.frame.add(topPanel, BorderLayout.NORTH);

        // Product table initialization
        this.productTable = new JTable();
        String[] columnNames = new String[]{"Product ID", "Name", "Category", "Price(£)", "Info"};

        // Assume ProductTableModel exists and is properly set up to manage Product objects
        DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);
        for (Product product: products){
            if (product instanceof Clothing) {
                Clothing cloc = (Clothing) product;
                dataModel.addRow(new Object[]{cloc.getProductId(), cloc.getName(), cloc.getClass().getSimpleName(),
                        cloc.getPrice(), cloc.getSize() + ", "+ cloc.getColor()});

            } else if (product instanceof Electronics) {
                Electronics elec = (Electronics) product;
                dataModel.addRow(new Object[] {elec.getProductId(), elec.getName(), elec.getClass().getSimpleName(),
                        elec.getPrice(), elec.getBrand() + ", " + elec.getWarrantyPeriod()});
            }
        }
        this.productTable.setModel(dataModel);
        this.frame.add(new JScrollPane(this.productTable), BorderLayout.CENTER);

        // Details panel below the product table
        detailsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        productIdLabel = new JLabel();
        nameLabel = new JLabel();
        CategoryLabel = new JLabel();
        sizeLabel = new JLabel(); // Size might be specific to clothing
        colorLabel = new JLabel(); // Color might be specific to clothing
        availableItemsLabel = new JLabel();

        detailsPanel.add(new JLabel("Product ID:"));
        detailsPanel.add(productIdLabel);
        detailsPanel.add(new JLabel("Category:"));
        detailsPanel.add(CategoryLabel);
        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(nameLabel);
        detailsPanel.add(new JLabel("Size:"));
        detailsPanel.add(sizeLabel);
        detailsPanel.add(new JLabel("Color:")); // Only add if applicable
        detailsPanel.add(colorLabel);
        detailsPanel.add(new JLabel("Available Items:"));
        detailsPanel.add(availableItemsLabel);

        this.addToCartButton = new JButton("Add to Shopping Cart");
        detailsPanel.add(this.addToCartButton);

        this.frame.add(detailsPanel, BorderLayout.SOUTH);

        // Event listeners for buttons and table
        setupEventListeners(products);

        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void setupEventListeners(List<Product> products) {
        // Listener for table row selection to update product details
        productTable.getSelectionModel().addListSelectionListener(event -> {
            int viewRow = productTable.getSelectedRow();
            if (viewRow >= 0) {
                // Assuming table model is a subclass of AbstractTableModel
                String productID = productTable.getValueAt(viewRow, 0).toString();
                for (int i = 0; i<products.size();i++){
                    Product product = products.get(i);
                    if (product.getProductId().equals(productID)){
                        updateProductDetails(product);
                    }
                }
            }
        });

        // Listener for category dropdown to filter products
        categoryDropdown.addActionListener(e -> {
            String selectedCategory = (String) categoryDropdown.getSelectedItem();
            List<Product> filteredProducts = products.stream()
                    .filter(p -> selectedCategory.equals("All") || p.getCategory().equalsIgnoreCase(selectedCategory))
                    .collect(Collectors.toList());
            productTable.setModel(new ProductTableModel(filteredProducts));
        });

        // Listener for add to cart button
        addToCartButton.addActionListener(e -> {
            int viewRow = productTable.getSelectedRow();
            if (viewRow >= 0) {
                // Assuming table model is a subclass of AbstractTableModel
                String productID = productTable.getValueAt(viewRow, 0).toString();
                for (int i = 0; i<products.size();i++){
                    Product product = products.get(i);
                    if (product.getProductId().equals(productID)){
                        shoppingCart.addProduct(product);
                    }
                }
            }
        });

        // Listener for view cart button
        viewCartButton.addActionListener(e -> {
            JDialog cartDialog = new JDialog(frame, "Shopping Cart", true);
            cartDialog.setLayout(new BorderLayout());
            JList<Product> cartList = new JList<>(new Vector<>(shoppingCart.getProducts()));
            cartDialog.add(new JScrollPane(cartList), BorderLayout.CENTER);

            // Calculate and display the total cost
            JLabel totalCostLabel = new JLabel("Total Cost: " + shoppingCart.calculateTotalCost());
            cartDialog.add(totalCostLabel, BorderLayout.SOUTH);

            cartDialog.pack();
            cartDialog.setLocationRelativeTo(frame);
            cartDialog.setVisible(true);
        });
    }

    private void updateProductDetails(Product selectedProduct) {
        productIdLabel.setText(selectedProduct.getProductId());
        CategoryLabel.setText(selectedProduct.getClass().getSimpleName());
        nameLabel.setText(selectedProduct.getName());
        sizeLabel.setText(selectedProduct instanceof Clothing ? ((Clothing)selectedProduct).getSize() : ((Electronics)selectedProduct).getBrand());
        colorLabel.setText(selectedProduct instanceof Clothing ? ((Clothing)selectedProduct).getColor() : String.valueOf(((Electronics)selectedProduct).getWarrantyPeriod()));
        availableItemsLabel.setText(String.valueOf(selectedProduct.getAvailableItems()));
    }
}





