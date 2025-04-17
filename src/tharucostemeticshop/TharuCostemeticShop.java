/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tharucostemeticshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Sasindi Weerarathne
 */
public class TharuCostemeticShop {

    /**
     * @param args the command line arguments
     */
    
    static ArrayList<User> userList = new ArrayList<>();
    static ArrayList<Product> productList = new ArrayList<>();


    public static void main(String[] args) {
        Scanner mainScanner = new Scanner(System.in);
        loadUsers();


        int choice;
        do {
            System.out.println("             WELCOME TO THARU COSMETIC SHOP         ");
            System.out.println("      ==================================================                 ");
            System.out.println("1. Login");
            System.out.println("2. Create Customer Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = mainScanner.nextInt();
            mainScanner.nextLine();

            switch (choice) {
                case 1:
                    login(mainScanner);
                    break;
                case 2:
                    createAccount(mainScanner);
                    break;
                case 3:
                    System.out.println("Logging out... See you soon!");
                    break;
                default:
                    System.out.println("That option doesn’t exist. Try once more");
            }

        } while (choice != 3);

        mainScanner.close();
    }

    
    //user login 
    public static void login(Scanner sc) {
        System.out.print("Enter username: ");
        String inputUsername = sc.nextLine();
        System.out.print("Enter password: ");
        String inputPassword = sc.nextLine();

        User loggedUser = null;
        for (User u : userList) {
            if (u.login(inputUsername, inputPassword)) {
                loggedUser = u;
                break;
            }
        }

       if (loggedUser != null) {
    System.out.println("Login successful! Welcome, " + loggedUser.username);
    
    Scanner mainScanner = new Scanner(System.in);
    int choice;

    if (loggedUser instanceof Admin) {
        System.out.println("Hello Admin, you're now logged in.");

        do {
            System.out.println("                             ADMIN MENU         ");
            System.out.println("                     =========================         ");
            System.out.println("1. View All Users");
            System.out.println("2. View All Products");
            System.out.println("3. Search Products");
            System.out.println("4. Add New Product");
            System.out.println("5. Delete Products");
            System.out.println("6. Add stock");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            choice = mainScanner.nextInt();
            mainScanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("  All Users ");
                    System.out.println("------------- ");
                    for (User u : userList) {
                        System.out.println(u.username + " | " + u.role);
                    }
                    break;
                case 2:
                    FileHandler.readProduct();
                    break;
                    
                case 3:
                   System.out.println("Search by:");
                   System.out.println("1. Name");
                   System.out.println("2. Category");
                   System.out.print("Enter your choice: ");
                   int searchChoice = mainScanner.nextInt();
                   mainScanner.nextLine(); 

                   switch (searchChoice) {
                   case 1:
                   System.out.print("Enter product name: ");
                   String nameKeyword = mainScanner.nextLine();
                   Product.searchByName(nameKeyword);
                   break;

                   case 2:
                   System.out.print("Enter product category: ");
                   String categoryKeyword = mainScanner.nextLine();
                   Product.searchByCategory(categoryKeyword);
                   break;

                   default:
                   System.out.println("Invalid choice. Please enter 1 or 2.");
                   }


                    break;
                case 4:
                   addNewProduct(mainScanner);
                    break;
                case 5:
                    deleteProduct(mainScanner);

                    break;
                    
                case 6:
                    addStock(mainScanner);

                    break;
                case 7:
                    System.out.println("Logging out ...");
                    break;
                default:
                    System.out.println("Invalid choice, Please enter valid number.");
            }
        } while (choice != 7);

    } else if (loggedUser instanceof Customer) {
        System.out.println("Hello Customer, you're now logged in.");

        do {
            System.out.println("                             CUSTOMER MENU         ");
            System.out.println("                     ===========================        ");
            System.out.println("1. View Products");
            System.out.println("2. Order Product ");
            System.out.println("3. Search Products");  
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = mainScanner.nextInt();
            mainScanner.nextLine();

            switch (choice) {
                case 1:
                    FileHandler.readProduct();
                    break;
                case 2:
                    buyProduct(mainScanner, loggedUser);
                    break;
                    
                case 3:
                    System.out.println("Search by:");
                   System.out.println("1. Name");
                   System.out.println("2. Category");
                   System.out.print("Enter your choice: ");
                   int searchChoice = mainScanner.nextInt();
                   mainScanner.nextLine(); 

                   switch (searchChoice) {
                   case 1:
                   System.out.print("Enter product name: ");
                   String nameKeyword = mainScanner.nextLine();
                   Product.searchByName(nameKeyword);
                   break;

                   case 2:
                   System.out.print("Enter product category: ");
                   String categoryKeyword = mainScanner.nextLine();
                   Product.searchByCategory(categoryKeyword);
                   break;

                   default:
                   System.out.println("Invalid choice. Please enter number 1 or 2.");
                   }

                    break;
                    
                
                case 4:
                    System.out.println("Logging out Successfully ...");
                    break;
                default:
                    System.out.println("Invalid choice, Please enter valid number.");
            }
        } while (choice != 4);
    }

} else {
    System.out.println("Invalid credentials.");
}
}
    
    
// new customer account
    public static void createAccount(Scanner sc) {
        System.out.println("   CREATE CUSTOMER ACCOUNT ");
        System.out.println("  -------------------------- ");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Customer newCustomer = new Customer(username, password);
        userList.add(newCustomer);
        System.out.println("Account created successfully for " + username + " as a Customer.");

        saveUserToFile(newCustomer);
    }

// save the new user to file
    public static void saveUserToFile(User user) {
        try (FileWriter writer = new FileWriter("C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/users.csv", true)) {
            writer.write(user.username + "," + user.password + "," + user.role + "\n");
            System.out.println("User details saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }

    
//loding users from the csv file
    public static void loadUsers() {
        try {
            File file = new File("C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/users.csv");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (line.isEmpty() || line.startsWith("role")) continue;

                String[] parts = line.split(",");
                if (parts.length == 3) {
                String username = parts[0].trim();
                String password = parts[1].trim();
                String role = parts[2].trim();

                    if (role.equalsIgnoreCase("Admin")) {
                        userList.add(new Admin(username, password));
                    } else if (role.equalsIgnoreCase("Customer")) {
                        userList.add(new Customer(username, password));
                    }
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading users: users.csv not found.");
        }
    } 
    
//  adding new products
    public static void addNewProduct(Scanner sc) {
    System.out.println("Enter product details:");

    System.out.print("Enter product name: ");
    String name = sc.nextLine();

    System.out.print("Enter product price: ");
    double price = sc.nextDouble();
    sc.nextLine(); 

    System.out.print("Enter product category: ");
    String category = sc.nextLine();

    System.out.print("Enter product quantity: ");
    int quantity = sc.nextInt();
    sc.nextLine(); 

    Product newProduct = new Product(name,  category, quantity , price);
    productList.add(newProduct);

    // Save the new product to the CSV file
    saveProductToFile(newProduct);

    System.out.println("New product added successfully.");
}
    
    public static void saveProductToFile(Product product) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/ProductDetails.csv", true))) {
        writer.write(product.getName() + "," + product.getcategory() + "," + product.getQuantity() + "," + product.getPrice());

        writer.newLine();
    } catch (IOException e) {
        System.out.println("Error saving product to file.");
    }
}

//delete 
public static void deleteProduct(Scanner sc) {
    System.out.println("   DELETE PRODUCT");
    System.out.println(" ------------------");
    System.out.print("Enter the name of the product to delete: ");
    String productToDelete = sc.nextLine();
    
    String filePath = "C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/ProductDetails.csv";
    List<String> remainingProducts = new ArrayList<>();
    boolean found = false;
    
    try {
       
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 1) {
                String name = parts[0].trim();
 
                if (!name.equalsIgnoreCase(productToDelete)) {
                    remainingProducts.add(line);
                } else {
                    found = true;
                }
            } else {
                remainingProducts.add(line);
            }
        }
        reader.close();
        
        if (found) {

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String product : remainingProducts) {
                writer.write(product);
                writer.newLine();
            }
            writer.close();
            System.out.println("Product '" + productToDelete + "' deleted successfully.");
        } else {
            System.out.println("Product '" + productToDelete + "' not found.");
        }
        
    } catch (IOException e) {
        System.out.println("Error deleting product: " + e.getMessage());
    }
}


//product order
public static void buyProduct(Scanner sc, User currentUser) {
    FileHandler.readProduct();

    System.out.println("   ORDER PRODUCT");
    System.out.println("  -------------- ");
    System.out.print("Enter the name of the product you want to buy: ");
    String productToBuy = sc.nextLine();

    System.out.print("Enter quantity to purchase: ");
    int quantityToBuy = sc.nextInt();
    sc.nextLine(); 

    String filePath = "C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/ProductDetails.csv";
    List<String> updatedProducts = new ArrayList<>();
    boolean found = false;
    double totalCost = 0;

    try {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                try {
                    String name = parts[0].trim();
                    String category = parts[1].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());

                    if (name.equalsIgnoreCase(productToBuy)) {
                        found = true;

                        if (quantity >= quantityToBuy) {
                            int newQuantity = quantity - quantityToBuy;
                            totalCost = price * quantityToBuy;

                            updatedProducts.add(name + "," + category + "," + newQuantity + "," + price);

                            System.out.println("   PURCHASE SUMMARY ");
                            System.out.println(" ------------------- ");
                            System.out.println("Product: " + name);
                            System.out.println("Quantity: " + quantityToBuy);
                            System.out.println("Price per item: Rs" + price);
                            System.out.println("Total cost: Rs" + totalCost);
                        } else {
                            System.out.println("Not enough stock available. Current stock: " + quantity);
                            updatedProducts.add(line);
                        }
                    } else {
                        updatedProducts.add(line);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error in line: " + line);
                    System.out.println("Details: " + e.getMessage());
                    updatedProducts.add(line);
                }
            } else {
                updatedProducts.add(line);
            }
        }
        reader.close();

        if (found && totalCost > 0) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String product : updatedProducts) {
                writer.write(product);
                writer.newLine();
            }
            writer.close();

            System.out.println("\nThank you for your purchase!");
            System.out.println("Your order has been processed successfully.");
        } else if (!found) {
            System.out.println("Product '" + productToBuy + "' not found.");
        }

    } catch (IOException e) {
        System.out.println("Error processing purchase: " + e.getMessage());
    }
}


//add stock (only quantity)
public static void addStock(Scanner sc) {
    System.out.println("  ADD STOCK ");
    System.out.println("-------------- ");
    System.out.print("Enter product name to add stock: ");
    String productName = sc.nextLine().trim(); 

    System.out.print("Enter quantity to add: ");
    int quantityToAdd = sc.nextInt();
    sc.nextLine(); 
    
    String filePath = "C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/ProductDetails.csv";
    List<String> updatedProducts = new ArrayList<>();
    boolean found = false;
    
    try {

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.toLowerCase().contains("name") && line.toLowerCase().contains("category")) {
                updatedProducts.add(line); 
                continue; 
            }

            String[] parts = line.split(",");
            if (parts.length == 4) {
                try {
                    String name = parts[0].trim();
                    String category = parts[1].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());

                    if (name.equalsIgnoreCase(productName)) {
                        found = true;
                        int newQuantity = quantity + quantityToAdd;
                        updatedProducts.add(name + "," + category + "," + newQuantity + "," + price);
                        System.out.println("Stock updated successfully for " + name + ". New quantity: " + newQuantity);
                    } else {
                        updatedProducts.add(line); 
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                    updatedProducts.add(line); 
                }
            } else {
                updatedProducts.add(line); 
            }
        }

        reader.close();

        if (found) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String product : updatedProducts) {
                writer.write(product);
                writer.newLine();
            }
            writer.close();
            System.out.println("Stock has been updated.");
        } else {
            System.out.println("Product '" + productName + "' not found in the inventory.");
        }

    } catch (IOException e) {
        System.out.println("Error processing stock update: " + e.getMessage());
    }
}

}




 
class User {
    protected String username;
    protected String password;
    protected String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean login(String inputUsername, String inputPassword) {
        return inputUsername.equals(username) && inputPassword.equals(password);
    }
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "Admin");
    }
}

class Customer extends User {
    public Customer(String username, String password) {
        super(username, password, "Customer");
    }
}





//read both users and product file
 class FileHandler {

    public static void readFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner fileRead = new Scanner(file);
            while (fileRead.hasNextLine()) {
                String data = fileRead.nextLine();
                System.out.println(data);
            }
            fileRead.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading: " + filePath);
            e.printStackTrace();
        }
    }
    
    public static void readProduct() {
        readFile("C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/ProductDetails.csv");
    }

    public static void readUser() {
        readFile("C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/users.csv");
    }
    
 }



//product class
class Product {
    private String name;
    private double price;
    private String category;
    private int quantity;

    public Product(String name, String category, int quantity, double price) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getcategory() {
        return category;
    }
    
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
    return "Product Name: " + name + " | Price: " + price + " | Category: " + category + " | Quantity: " + quantity;
    }
    
//search product by name
    public static void searchByName(String keyword) {
    String filePath = "C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/ProductDetails.csv";

    try {
        File file = new File(filePath);
        Scanner fileRead = new Scanner(file);
        boolean found = false;

        while (fileRead.hasNextLine()) {
            String line = fileRead.nextLine();
            String[] parts = line.split(",");

            if (parts.length == 4) {
                String name = parts[0].trim();
                String category = parts[1].trim();
                String quantity = parts[2].trim();
                String price = parts[3].trim();

                if (name.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println("Name: " + name + " | Category: " + category + " | Quantity: " + quantity + " | Price: " + price);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No matching product found for: " + keyword);
        }

        fileRead.close();
    } catch (FileNotFoundException e) {
        System.out.println("Product file not found: " + filePath);
    }
}
    
//search product by category   
    public static void searchByCategory(String keyword) {
    String filePath = "C:/users/Sasindi Weerarathne/OneDrive/Documents/NetBeansProjects/TharuCostemeticShop/ProductDetails.csv";

    try {
        File file = new File(filePath);
        Scanner fileRead = new Scanner(file);
        boolean found = false;

        while (fileRead.hasNextLine()) {
            String line = fileRead.nextLine();
            String[] parts = line.split(",");

            if (parts.length == 4) {
                String name = parts[0].trim();
                String category = parts[1].trim();
                String quantity = parts[2].trim();
                String price = parts[3].trim();

                if (category.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println("Name: " + name + " | Category: " + category + " | Quantity: " + quantity + " | Price: " + price);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No product found in category: " + keyword);
        }

        fileRead.close();
    } catch (FileNotFoundException e) {
        System.out.println("File not found.");
    }
}
}

    
    
    


