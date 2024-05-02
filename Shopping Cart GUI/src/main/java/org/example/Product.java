package org.example;

public abstract class Product {
    private String productId;
    private String name;
    private int availableItems;
    private double price;
    private String category;

    public Product(String productId, String name, int availableItems, double price) {
        this.productId = productId;
        this.name = name;
        this.availableItems = availableItems;
        this.price = price;
    }

    @Override
    public String toString() {
        // Custom string representation of the product
        return String.format("ID: %s - Name: %s - Price: £%.2f - Available: %d",
                this.productId, this.name, this.price, this.availableItems);
    }
    public String getProductId() {
        return this.productId;
    }

    public String getName() {
        return this.name;
    }

    public int getAvailableItems() {
        return this.availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return this.price;
    }

    public abstract double calculateProductCost();

    public void setCategory(String newCategory){
        this.category = newCategory;
    }
    public String getCategory() {
        return this.category;
    }
}

