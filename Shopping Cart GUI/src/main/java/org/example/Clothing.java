package org.example;

import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    private String size;
    private String color;

    public Clothing(String productId, String name, int availableItems, double price, String size, String color) {
        super(productId, name, availableItems, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return this.size;
    }

    public String getColor() {
        return this.color;
    }

    public double calculateProductCost() {
        return this.getPrice();
    }

}