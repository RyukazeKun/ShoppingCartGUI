package org.example;

import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String name, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, name, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return this.brand;
    }


    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }


    public double calculateProductCost() {
        return this.getPrice();
    }

}
