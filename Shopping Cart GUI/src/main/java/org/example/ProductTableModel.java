package org.example;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {
    private final List<Product> products;
    private final String[] columnNames = new String[]{"Product ID", "Name", "Category", "Price(£)", "Info"};

    public ProductTableModel(List<Product> products) {
        this.products = products;
    }

    public int getRowCount() {
        return this.products.size();
    }

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = (Product)this.products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getProductId();
            case 1:
                return product.getName();
            case 2:
                return product.getCategory();
            case 3:
                return product.getPrice();
            case 4:
                return product instanceof Electronics ? "Electronics" : "Clothing";
            default:
                return null;
        }
    }

    public Product getProductAt(int i) {
        int rowIndex = 0;
        return products.get(rowIndex);
    }
}


