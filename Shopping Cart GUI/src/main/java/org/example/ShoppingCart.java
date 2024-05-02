package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ShoppingCart {
    private List<Product> products = new ArrayList();

    public ShoppingCart() {
    }
    public boolean containsProduct(Product product) {
        return products.contains(product);
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setAvailableItems(product.getAvailableItems() - 1); // Decrease availability
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products); // Return a copy of the products list
    }

    public void removeProduct(int index) {
        if (index >= 0 && index < products.size()) {
            Product product = products.get(index);
            product.setAvailableItems(product.getAvailableItems() + 1); // Increment availability
            products.remove(index);
        }
    }

    public double calculateTotalCost() {
        double totalCost = 0.0;

        Product product;
        for(Iterator var3 = this.products.iterator(); var3.hasNext(); totalCost += product.calculateProductCost()) {
            product = (Product)var3.next();
        }

        if (this.meetsDiscountCondition()) {
            totalCost *= 0.8;
        }

        return totalCost;
    }

    private boolean meetsDiscountCondition() {
        return this.products.size() >= 3;
    }

    public String getProductsInfo () {
        StringBuilder info = new StringBuilder();
        Iterator var2 = this.products.iterator();

        while(var2.hasNext()) {
            Product product = (Product)var2.next();
            info.append(product.getName()).append(" - ").append(product.getPrice()).append("\n");
        }

        info.append("Total Cost: ").append(this.calculateTotalCost());
        return info.toString();
    }
}
