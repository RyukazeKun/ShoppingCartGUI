package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {

    public WestminsterShoppingManager() throws IOException {
        this.loadProductsFromFile();
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public void runMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Westminster Shopping Manager Menu:");
            System.out.println("1. Add a new product");
            System.out.println("2. Delete a product");
            System.out.println("3. Print list of products");
            System.out.println("4. Save products to file");
            System.out.println("5. Loads products from file");
            System.out.println("6. Shopping Cart");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    if (productsList.size() >= 50) {
                        System.out.println("Maximum product limit reached.");
                    } else {
                        this.addNewProduct(scanner);
                    }
                    break;
                case 2:
                    this.deleteProduct(scanner);
                    break;
                case 3:
                    this.printProductList();
                    break;
                case 4:
                    this.saveProductsToFile();
                    break;
                case 5:
                    this.loadProductsFromFile();
                    break;
                case 6:
                    new ShoppingGUI(productsList);
                    break;
                case 7:
                    System.out.println("Exiting Westminster Shopping Manager.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while(choice != 7);
    }

    private void addNewProduct(Scanner scanner) {
        System.out.print("Enter the product type (Electronics -> 1 / Clothing -> 2): ");
        String productType = scanner.nextLine();
        if (productType.equalsIgnoreCase("1")) {
            productsList.add(this.createElectronicsProduct(scanner));
        } else if (productType.equalsIgnoreCase("2")) {
            productsList.add(this.createClothingProduct(scanner));
        } else {
            System.out.println("Invalid product type.");
        }

    }

    private Electronics createElectronicsProduct(Scanner scanner) {
        System.out.println("Enter the details for the Electronics product:");
        System.out.print("Product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Available Items: ");
        int availableItems = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Warranty Period (in months): ");
        int warrantyPeriod = scanner.nextInt();
        scanner.nextLine();
        Electronics product = new Electronics(productId, name, availableItems, price, brand, warrantyPeriod);
        product.setCategory("Electronics");
        return product;
    }

    private Clothing createClothingProduct(Scanner scanner) {
        System.out.println("Enter the details for the Clothing product:");
        System.out.print("Product ID: ");
        String productId = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Available Items: ");
        int availableItems = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Size: ");
        String size = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        Clothing product = new Clothing(productId, name, availableItems, price, size, color);
        product.setCategory("Clothing");
        return product;
    }

    private void deleteProduct(Scanner scanner) {
        System.out.print("Enter the product ID to delete: ");
        String productId = scanner.nextLine();
        Product productToDelete = null;
        Iterator var4 = productsList.iterator();

        while(var4.hasNext()) {
            Product product = (Product)var4.next();
            if (product.getProductId().equalsIgnoreCase(productId)) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete != null) {
            productsList.remove(productToDelete);
            PrintStream var10000 = System.out;
            String var10001 = productToDelete.getName();
            var10000.println("Deleted Product: " + var10001 + " (" + (productToDelete instanceof Electronics ? "Electronics" : "Clothing") + ")");
            System.out.println("Total products left: " + productsList.size());
        } else {
            System.out.println("Product not found.");
        }

    }

    private void printProductList() {
        Collections.sort(productsList, Comparator.comparing(Product::getProductId));
        Iterator var1 = productsList.iterator();

        while(var1.hasNext()) {
            Product product = (Product)var1.next();
            PrintStream var10000 = System.out;
            String var10001 = product.getProductId();
            var10000.println("Product ID: " + var10001);
            var10000 = System.out;
            var10001 = product.getName();
            var10000.println("Name: " + var10001);
            System.out.println("Type: " + (product instanceof Electronics ? "Electronics" : "Clothing"));
            if (product instanceof Electronics electronics) {
                System.out.println("Brand: " + electronics.getBrand());
                System.out.println("Warranty Period (months): " + electronics.getWarrantyPeriod());
            }

            if (product instanceof Clothing clothing) {
                System.out.println("Size: " + clothing.getSize());
            }

            System.out.println("Available Items: " + product.getAvailableItems());
            System.out.println("Price: £" + product.getPrice());
            System.out.println();
        }

    }

    private void saveProductsToFile() {
        try {
            FileWriter fileWriter = new FileWriter("products.csv");
            try {
                BufferedWriter writer = new BufferedWriter(fileWriter);
                try {
                    for(Iterator var3 = productsList.iterator(); var3.hasNext(); writer.newLine()) {
                        Product product = (Product)var3.next();
                        String var10001;
                        if (product instanceof Electronics electronics) {
                            var10001 = electronics.getProductId();
                            writer.write(var10001 + "," + electronics.getName() + "," + electronics.getAvailableItems() + "," + electronics.getPrice() + "," + electronics.getBrand() + "," + electronics.getWarrantyPeriod());
                        } else if (product instanceof Clothing clothing) {
                            var10001 = clothing.getProductId();
                            writer.write(var10001 + "," + clothing.getName() + "," + clothing.getAvailableItems() + "," + clothing.getPrice() + "," + clothing.getSize() + "," + clothing.getColor());
                        }
                    }

                    System.out.println("Products saved to products.csv successfully.");
                } catch (Throwable var8) {
                    try {
                        writer.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }

                    throw var8;
                }

                writer.close();
            } catch (Throwable var9) {
                try {
                    fileWriter.close();
                } catch (Throwable var6) {
                    var9.addSuppressed(var6);
                }

                throw var9;
            }

            fileWriter.close();
        } catch (IOException var10) {
            var10.printStackTrace();
        }

    }

    public List<Product> loadProductsFromFile() {
        List<Product> loadedProducts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("products.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String productID = parts[0];
                    String productName = parts[1];
                    int numberOfAvailable = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);

                    if (isInteger(parts[5])) {
                        String brandName = parts[4];
                        int warranty = Integer.parseInt(parts[5]);
                        loadedProducts.add(new Electronics(productID, productName, numberOfAvailable, price, brandName, warranty));
                    } else {
                        String size = parts[4];
                        String colour = parts[5];
                        loadedProducts.add(new Clothing(productID, productName, numberOfAvailable, price, size, colour));
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }

        System.out.println("Products loaded from project file successfully.");
        return loadedProducts;
    }

    public static void main(String[] args) throws IOException {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.runMenu();
    }
}