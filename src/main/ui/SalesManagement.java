package ui;

import java.util.List;
import java.util.Scanner;

import model.*;

// A sales management application that allows user to track products and purchases
public class SalesManagement {
    private Inventory inventory;
    private PurchaseRecord purchaseRecord;
    private Integer currentIndex;
    private static final Integer BOX_LENGTH = 68;

    private Scanner scanner;
    private boolean isProgramRunning;

    // EFFECTS: creates an instance of the SalesManagement console ui application
    public SalesManagement() {
        init();

        System.out.println("Welcome to the Sales Management app!");

        while(this.isProgramRunning) {
            handleMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the application with the starting state
    public void init() {
        this.inventory = new Inventory();
        this.purchaseRecord = new PurchaseRecord();
        this.currentIndex = 0;
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
    }

    // EFFECTS: display and processes inputs for the main menu
    public void handleMenu() {
        displayMenu();
        String input = this.scanner.nextLine();
        processMenuCommands(input);
    }

    // EFFECTS: display a list of commands that can be used in the main menu
    public void displayMenu() {
        printTop();
        System.out.println("║                               MAIN MENU                            ║");
        printDivider();
        System.out.println("║ [a]: add a product to inventory                                    ║");
        System.out.println("║ [i]: look up a product by id                                       ║");
        System.out.println("║ [n]: look up products by search term                               ║");
        System.out.println("║ [u]: update a product in inventory                                 ║");
        System.out.println("║ [r]: make and record a new purchase                                ║");
        System.out.println("║ [p]: view all purchases from date range                            ║");
        System.out.println("║ [v]: view unreviewed purchases with selected of payment in one day ║");
        System.out.println("║ [f]: get profit from date range                                    ║");
        System.out.println("║ [q]: exit the application                                          ║");
        printBottom();
    }

    // EFFECTS: process the user's input in the main menu
    public void processMenuCommands(String input) {
        switch (input) {
            case "a":
                addProduct();
                break;
            case "i": 
                findProductWithId();
                break;
            case "n":
                findProductWithName();
                break;
            case "u":
                updateProduct();
                break;
            case "r":
                addPurchase();
                break;
            // case "p":
            //     getPurchasesBetween();
            //     break;
            // case "v":
            //     getOneDayPurchasesMethod();
            //     break;
            // case "f":
            //     getProfit();
            //     break;
            case "q":
                quitApplication();
            default:
                System.out.println(" Invalid option inputted. Please try again. ");
        }
    }

    // EFFECTS: create a new product and add to inventory
    private void addProduct() {
        printTop();
        System.out.println("║                              ADD PRODUCT                           ║");
        printBottom();
        
        System.out.println("Please enter the product's name: ");
        String name = this.scanner.nextLine();
        
        System.out.println("Please enter the product's ID: ");
        String id = this.scanner.nextLine();
        for (Product p : inventory.getProducts()) {
            if (p.getId().equals(id)) {
                System.out.println(" ID already existed. Please try again.");
                return;
            }
        }

        System.out.println(" Please enter the product's unit price: ");
        String unitPrice = this.scanner.nextLine();

        Product p = new Product(name, id, Double.valueOf(unitPrice));

        System.out.println(" Would you like to specify the selling price and quantity?");
        System.out.println(" [y]: yes");
        System.out.println(" [n]: no");
        String input = this.scanner.nextLine();
        processAddProductCommand(input, p);
        
        if (input.equals("y") || input.equals("n")) {
            inventory.addProduct(p);
            System.out.println(" Product successfully added to inventory!");
        }
    }

    // EFFECTS: process the user's input on the Add Product menu
    public void processAddProductCommand(String input, Product product) {
        switch(input) {
            case "y":
                System.out.println(" Please enter the product's selling price: ");
                String sellingPrice = this.scanner.nextLine();
                product.setSellingPrice(Double.valueOf(sellingPrice));

                System.out.println(" Please enter the product's quantity: ");
                String quantity = this.scanner.nextLine();
                product.restock(Integer.valueOf(quantity));
                break;
            case "n":
                break;
            default:
                System.out.println(" Invalid option inputted. Please try again. ");
        }
    }

    // EFFECTS: display the product's information given their id
    public void findProductWithId() {
        printTop();
        System.out.println("║                          FIND PRODUCT BY ID                        ║");
        printBottom();
        
        System.out.println(" Please enter the product's ID: ");
        String typedId = this.scanner.nextLine();
        Product p = this.inventory.findProductWithId(typedId);
        if (p == null) {
            System.out.println(" Error: No product was found. Please try again.");
        }
        else {
            printTop();
            displayOneProduct(p);
        }   
    }

    // EFFECTS: display the products' information given a search term, one product at a time
    public void findProductWithName() {
        printTop();
        System.out.println("║                         FIND PRODUCT BY NAME                       ║");
        printBottom();

        
        System.out.println(" Please enter the search term: ");
        String searchTerm = this.scanner.nextLine();
        List<Product> products = inventory.findProductWithName(searchTerm);
        if (products.isEmpty()) {
            System.out.println(" Error: No product was found. Please try again.");
            return;
        }
        else {
            String command = "";
            while (!command.equals("q")) {
                printTop();
                System.out.println("║                         FIND PRODUCT BY NAME                       ║");
                printDivider();
                System.out.println("║ [n]: move to the next product                                      ║");
                System.out.println("║ [p]: move to the previous product                                  ║");
                System.out.println("║ [q]: return to the main menu                                       ║");
                printDivider();
                Product currentProduct = products.get(currentIndex);
                displayOneProduct(currentProduct);
                command = this.scanner.nextLine();
                processTraverseProductsList(command, products);
            }
            currentIndex = 0;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: process the user's input by modifying the current card index or exit the menu
    public void processTraverseProductsList(String command, List<Product> products) {
        switch (command) {
            case "n":
                if (currentIndex >= products.size() - 1) {
                    System.out.println(" Error: No more new products to display.");
                }
                else {
                    currentIndex++;
                }
                break;
            case "p":
                if (currentIndex <= 0) {
                    System.out.println(" Error: No more previous products to display.");
                }
                else {
                    currentIndex--;
                }
                break;
            case "q":
                return;
            default: 
                System.out.println(" Invalid option inputted. Please try again.");
        }
    }
    // EFFECTS: display one product's information
    public void displayOneProduct(Product p) {
        String name = p.getName();
        String id = p.getId();
        String sellingPrice = p.getSellingPrice().toString();
        String unitPrice = p.getUnitPrice().toString();
        String quantity = Integer.toString(p.getQuantity());

        System.out.println("║ Product's name: " + name + " ".repeat(BOX_LENGTH - 17 - name.length()) + "║");
        System.out.println("║ Product's ID: " + id + " ".repeat(BOX_LENGTH - 15 - id.length()) + "║");
        System.out.println("║ Product's selling price: " + sellingPrice + " ".repeat(BOX_LENGTH - 26 - sellingPrice.length()) + "║");
        System.out.println("║ Product's unit price: " + unitPrice + " ".repeat(BOX_LENGTH - 23 - unitPrice.length()) + "║");
        System.out.println("║ Product's quantity: " + quantity + " ".repeat(BOX_LENGTH - 21 - quantity.length()) + "║");
        printBottom();
    }

    // EFFECTS: change a product's information given the id
    public void updateProduct() {
        printTop();
        System.out.println("║                    UPDATE PRODUCT'S INFORMATION                    ║");
        printBottom();
        
        System.out.println(" Please enter the product's ID: ");
        String typedId = this.scanner.nextLine();
        Product p = this.inventory.findProductWithId(typedId);
        if (p == null) {
            System.out.println(" Error: No product was found. Please try again.");
        }
        else {
            String command = "";
            while (!command.equals("q")) {
                if (command.equals("r") && !inventory.getProducts().contains(p)) {
                    return;
                }
                else {
                    printTop();
                    System.out.println("║                    UPDATE PRODUCT'S INFORMATION                    ║");
                    printDivider();
                    System.out.println("║ [n]: update product's name                                         ║");
                    System.out.println("║ [s]: update product's selling price                                ║");
                    System.out.println("║ [u]: update product's unit price                                   ║");
                    System.out.println("║ [t]: update product's quantity                                     ║");
                    System.out.println("║ [r]: remove product from inventory                                 ║");
                    System.out.println("║ [q]: return to the main menu                                       ║");
                    printBottom();
                    command = this.scanner.nextLine();
                    processUpdateListCommand(command, p);
                }
            }
        } 
    }

    // EFFECTS: process user's input in the update product's information menu
    public void processUpdateListCommand(String input, Product p) {
        switch(input) {
            case "n":
                System.out.println("Please enter new name: ");
                String name = this.scanner.nextLine();
                p.setName(name);
                System.out.println(" Product's name successfully changed to " + name);
                break;
            case "s":
                System.out.println("Please enter new selling price: ");
                String sellingPrice = this.scanner.nextLine();
                p.setSellingPrice(Double.valueOf(sellingPrice));
                System.out.println(" Product's selling price successfully changed to " + sellingPrice);
                break;
            case "u":
                System.out.println("Please enter new unit price: ");
                String unitPrice = this.scanner.nextLine();
                p.setUnitPrice(Double.valueOf(unitPrice));
                System.out.println(" Product's unit price successfully changed to " + unitPrice);
                break;
            case "t":
                System.out.println("Please enter restock amount: ");
                String amount = this.scanner.nextLine();
                p.restock(Integer.valueOf(amount));
                System.out.println(" Product successfully restocked by " + amount + " items");
                break;
            case "r":
                System.out.println(" Are you sure you want to remove the product from inventory?");
                System.out.println(" [y]: yes");
                System.out.println(" [n]: no");
                String command = this.scanner.nextLine();
                switch (command) {
                    case "y":
                        inventory.removeProduct(p);
                        System.out.println("Product successfully removed.");
                        return;
                    case "n":
                        break; 
                    default: 
                        System.out.println(" Invalid option inputted. Please try again.");
                }
            case "q":
                return;
            default:
                System.out.println(" Invalid option inputted. Please try again.");
        }
    }

    // EFFECTS: create a new purchase and add to inventory
    private void addPurchase() {
        printTop();
        System.out.println("║                              ADD PURCHASE                          ║");
        printBottom();

        System.out.println("Please enter the payment received: ");
        Double actualPaidAmount = Double.valueOf(this.scanner.nextLine());
            
        System.out.println("Please enter the payment method: ");
        String paymentMethod = this.scanner.nextLine();

        Purchase p = new Purchase(actualPaidAmount, paymentMethod);
        
        String input = "";
        while (!input.equals("n")) {
            System.out.println(" Would you like to record the products sold?");
            System.out.println(" [y]: yes");
            System.out.println(" [n]: no");
            input = this.scanner.nextLine();
            processAddPurchaseCommand(input, p);
        
        }
        if (input.equals("n")) {
            purchaseRecord.addPurchase(p);
            System.out.println(" Purchase successfully recorded!");
        }
    }

    // EFFECTS: process the user's input on the Add purchase menu
    public void processAddPurchaseCommand(String input, Purchase p) {
        switch (input) {
            case "y":
                System.out.println("Please enter the product's ID: ");
                String id = this.scanner.nextLine();
                Product product = this.inventory.findProductWithId(id);
                if (product == null) {
                    System.out.println(" Error: No product was found. Please try again.");

                }
                else {
                    System.out.println("Please enter the product's amount: ");
                    Integer amount = Integer.valueOf(this.scanner.nextLine());
                    p.addProduct(product, amount);
                    System.out.println("Product " + product.getName() + " (ID: " + product.getId() + ")" + " successfully recorded");
                }
                break;
            case "n":
                break;
            default:
                System.out.println(" Invalid option inputted. Please try again. ");
        }
    }

    // EFFECTS:
    // MODIFIES: this
    // EFFECTS: Marks the program as not running
    public void quitApplication() {
        this.isProgramRunning = false;
    }

    // EFFECTS: prints out the top line of the box
    private void printTop() {
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
    }

    // EFFECTS: prints out the divider in the box
    private void printDivider() {
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
    }
    
    // EFFECTS: prints out the bottom line of the box
    private void printBottom() {
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
    }
}
