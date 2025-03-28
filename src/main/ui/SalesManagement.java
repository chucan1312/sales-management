package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.*;
import persistence.*;

// A sales management application that allows user to track products and purchases
public class SalesManagement {
    private static final String JSON_STORE = "./data/workspace";
    private WorkSpace workSpace;
    private Integer currentIndex;
    private static final Integer BOX_LENGTH = 68;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner scanner;
    private boolean isProgramRunning;

    // EFFECTS: creates an instance of the SalesManagement console ui application
    public SalesManagement() {
        init();

        System.out.println("Welcome to the Sales Management app!");

        while (this.isProgramRunning) {
            handleMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the application with the starting state
    public void init() {
        this.workSpace = new WorkSpace(new Inventory(), new PurchaseRecord());
        this.currentIndex = 0;
        this.scanner = new Scanner(System.in);
        this.isProgramRunning = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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
        printTitle("main menu");
        printDivider();
        System.out.println("║ [a]: add a product to inventory                                    ║");
        System.out.println("║ [o]: view all products in inventory                                ║");
        System.out.println("║ [i]: look up a product by id                                       ║");
        System.out.println("║ [n]: look up products by search term                               ║");
        System.out.println("║ [u]: update a product in inventory                                 ║");
        System.out.println("║ [r]: make and record a new purchase                                ║");
        System.out.println("║ [p]: view all purchases from date range                            ║");
        System.out.println("║ [v]: view unreviewed purchases by day (financial reconciliation)   ║");
        System.out.println("║ [s]: save data to file                                             ║");
        System.out.println("║ [l]: load data from file                                           ║");
        // System.out.println("║ [d]: update a purchase in purchase record ║");
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
            case "o":
                getProducts();
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
            case "p":
                getPurchasesBetween();
                break;
            case "v":
                getOneDayPurchasesMethod();
                break;
            // case "d":
            // updatePurchase();
            // break;
            case "f":
                getProfit();
                break;
            case "s":
                save();
                break;
            case "l":
                load();
                break;
            case "q":
                quitApplication();
            default:
                System.out.println(" ERROR: Invalid option inputted. Please try again. ");
        }
    }

    // MODIFIES: inventory, workSpace
    // EFFECTS: create a new product, add to inventory and update workspace
    private void addProduct() {
        printTop();
        printTitle("add product");
        printBottom();

        System.out.println("Please enter the product's name: ");
        String name = this.scanner.nextLine();

        System.out.println("Please enter the product's ID: ");
        String id = this.scanner.nextLine();
        for (Product p : workSpace.getInventory().getProducts()) {
            if (p.getId().equals(id)) {
                System.out.println(" ERROR: ID already existed. Please try again.");
                return;
            }
        }

        System.out.println(" Please enter the product's unit price: ");
        String unitPrice = this.scanner.nextLine();

        System.out.println(" Please enter the product's selling price: ");
        String sellingPrice = this.scanner.nextLine();

        System.out.println(" Please enter the product's quantity: ");
        String quantity = this.scanner.nextLine();

        Product p = new Product(name, id, Double.valueOf(unitPrice), Double.valueOf(sellingPrice),
                Integer.valueOf(quantity));

        workSpace.getInventory().addProduct(p);
        System.out.println(" Product successfully added to inventory!");
    }

    // EFFECTS: display all products' information, one product at a time
    public void getProducts() {
        List<Product> result = workSpace.getInventory().getProducts();

        if (result.isEmpty()) {
            System.out.println(" ERROR: No product was found. Please try again.");
            return;
        } else {
            String command = "";
            while (!command.equals("q")) {
                printTop();
                printTitle("find product by name");
                printDivider();
                System.out.println("║ [n]: move to the next product                                      ║");
                System.out.println("║ [p]: move to the previous product                                  ║");
                System.out.println("║ [q]: return to the main menu                                       ║");
                printDivider();
                String displayResult = " Showing result #" + Integer.toString((currentIndex + 1)) + " out of "
                        + Integer.toString(result.size()) + " result(s):";
                System.out.println("║" + displayResult + " ".repeat(BOX_LENGTH - displayResult.length()) + "║");
                Product currentProduct = result.get(currentIndex);
                displayOneProduct(currentProduct);
                command = this.scanner.nextLine();
                processTraverseProductsList(command, result);
            }
            currentIndex = 0;
        }
    }

    // EFFECTS: display the product's information given their id
    public void findProductWithId() {
        printTop();
        printTitle("find product by id");
        printBottom();

        System.out.println(" Please enter the product's ID: ");
        String typedId = this.scanner.nextLine();
        Product p = workSpace.getInventory().findProductWithId(typedId);
        if (p == null) {
            System.out.println(" ERROR: No product was found. Please try again.");
        } else {
            printTop();
            displayOneProduct(p);
        }
    }

    // EFFECTS: display the products' information given a search term, one product
    // at a time
    public void findProductWithName() {
        printTop();
        printTitle("find product by name");
        printBottom();

        System.out.println(" Please enter the search term: ");
        String searchTerm = this.scanner.nextLine();
        List<Product> result = workSpace.getInventory().findProductWithName(searchTerm);
        if (result.isEmpty()) {
            System.out.println(" ERROR: No product was found. Please try again.");
            return;
        } else {
            String command = "";
            while (!command.equals("q")) {
                printTop();
                printTitle("find product by name");
                printDivider();
                System.out.println("║ [n]: move to the next product                                      ║");
                System.out.println("║ [p]: move to the previous product                                  ║");
                System.out.println("║ [q]: return to the main menu                                       ║");
                printDivider();
                String displayResult = " Showing result #" + Integer.toString((currentIndex + 1)) + " out of "
                        + Integer.toString(result.size()) + " result(s):";
                System.out.println("║" + displayResult + " ".repeat(BOX_LENGTH - displayResult.length()) + "║");
                Product currentProduct = result.get(currentIndex);
                displayOneProduct(currentProduct);
                command = this.scanner.nextLine();
                processTraverseProductsList(command, result);
            }
            currentIndex = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: process the user's input by modifying the current index or exit the
    // menu
    public void processTraverseProductsList(String command, List<Product> products) {
        switch (command) {
            case "n":
                if (currentIndex >= products.size() - 1) {
                    System.out.println(" ERROR: No more new product to display.");
                } else {
                    currentIndex++;
                }
                break;
            case "p":
                if (currentIndex <= 0) {
                    System.out.println(" ERROR: No more previous product to display.");
                } else {
                    currentIndex--;
                }
                break;
            case "q":
                return;
            default:
                System.out.println(" ERROR: Invalid option inputted. Please try again.");
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
        System.out.println("║ Product's selling price: " + sellingPrice
                + " ".repeat(BOX_LENGTH - 26 - sellingPrice.length()) + "║");
        System.out.println(
                "║ Product's unit price: " + unitPrice + " ".repeat(BOX_LENGTH - 23 - unitPrice.length()) + "║");
        System.out.println("║ Product's quantity: " + quantity + " ".repeat(BOX_LENGTH - 21 - quantity.length()) + "║");
        printBottom();
    }

    // EFFECTS: change a product's information given the id
    public void updateProduct() {
        printTop();
        printTitle("update product's information");
        printBottom();

        System.out.println(" Please enter the product's ID: ");
        String typedId = this.scanner.nextLine();
        Product p = workSpace.getInventory().findProductWithId(typedId);
        if (p == null) {
            System.out.println(" Error: No product was found. Please try again.");
        } else {
            String command = "";
            while (!command.equals("q")) {
                if (!workSpace.getInventory().getProducts().contains(p)) {
                    return;
                } else {
                    printTop();
                    printTitle("update product's information");
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
        switch (input) {
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
                        workSpace.getInventory().removeProduct(p);
                        System.out.println("Product successfully removed.");
                        return;
                    case "n":
                        break;
                    default:
                        System.out.println(" ERROR: Invalid option inputted. Please try again.");
                }
            case "q":
                return;
            default:
                System.out.println(" ERROR: Invalid option inputted. Please try again.");
        }
    }

    // EFFECTS: create a new purchase and add to inventory
    private void addPurchase() {
        printTop();
        printTitle("add purchase");
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
        workSpace.getPurchaseRecord().addPurchase(p);
        System.out.println(" Purchase successfully recorded!");
    }

    // EFFECTS: process the user's input on the Add purchase menu
    public void processAddPurchaseCommand(String input, Purchase p) {
        switch (input) {
            case "y":
                System.out.println("Please enter the product's ID: ");
                String id = this.scanner.nextLine();
                Product product = workSpace.getInventory().findProductWithId(id);

                if (product == null) {
                    System.out.println(" ERROR: No product was found. Please try again.");

                } else {
                    System.out.println("Please enter the product's amount: ");
                    Integer amount = 999999999;
                    while (amount > product.getQuantity()) {
                        amount = Integer.valueOf(this.scanner.nextLine());
                        if (amount > product.getQuantity()) {
                            System.out.println(" ERROR: Not enough items in stock. Please try again.");
                        }
                    }
                    p.addProduct(product, amount);
                    System.out.println("Product " + product.getName() + " (ID: " + product.getId() + ")"
                            + " successfully recorded");
                }
                break;
            case "n":
                break;
            default:
                System.out.println(" ERROR: Invalid option inputted. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: display the purchases in purchaseRecord in between given dates, one
    // purchase at a time
    public void getPurchasesBetween() {
        printTop();
        printTitle("find purchase between");
        printBottom();

        System.out.println("Please enter the starting year: ");
        Integer startYear = Integer.valueOf(this.scanner.nextLine());
        System.out.println("Please enter the starting month: ");
        Integer startMonth = Integer.valueOf(this.scanner.nextLine());
        System.out.println("Please enter the starting day: ");
        Integer startDay = Integer.valueOf(this.scanner.nextLine());

        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

        LocalDate endDate = LocalDate.of(-9999, 1, 1);
        while (endDate.isBefore(startDate)) {
            System.out.println("Please enter the ending year: ");
            Integer endYear = Integer.valueOf(this.scanner.nextLine());
            System.out.println("Please enter the ending month: ");
            Integer endMonth = Integer.valueOf(this.scanner.nextLine());
            System.out.println("Please enter the ending day: ");
            Integer endDay = Integer.valueOf(this.scanner.nextLine());
            endDate = LocalDate.of(endYear, endMonth, endDay);
            if (endDate.isBefore(startDate)) {
                System.out.println(" ERROR: End date must not be before start date. Please try again.");
            }
        }

        List<Purchase> result = workSpace.getPurchaseRecord().getPurchasesBetween(startDate, endDate);
        if (result.isEmpty()) {
            System.out.println(
                    " There's no purchase recorded from " + startDate.toString() + " to " + endDate.toString() + ".");
        } else {
            String command = "";
            while (!command.equals("q")) {
                printTop();
                printTitle("find purchase between");
                printDivider();
                System.out.println("║ [n]: move to the next purchase                                     ║");
                System.out.println("║ [p]: move to the previous purchase                                 ║");
                System.out.println("║ [r]: mark purchase as reviewed                                     ║");
                System.out.println("║ [u]: mark purchase as not reviewed                                 ║");
                System.out.println("║ [q]: return to the main menu                                       ║");
                printDivider();
                String displayResult = " Showing result #" + Integer.toString((currentIndex + 1)) + " out of "
                        + Integer.toString(result.size()) + " result(s)";
                System.out.println("║" + displayResult + " ".repeat(BOX_LENGTH - displayResult.length()) + "║");
                Purchase currentPurchase = result.get(currentIndex);
                displayOnePurchase(currentPurchase);
                command = this.scanner.nextLine();
                processTraversePurchasesList(command, result);
            }
            currentIndex = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: process the user's input after purchases have been searched in find
    // purchase menus
    public void processTraversePurchasesList(String command, List<Purchase> purchases) {
        switch (command) {
            case "n":
                if (currentIndex >= purchases.size() - 1) {
                    System.out.println(" ERROR: No more new purchase to display.");
                } else {
                    currentIndex++;
                }
                break;
            case "p":
                if (currentIndex <= 0) {
                    System.out.println(" ERROR: No more previous purchase to display.");
                } else {
                    currentIndex--;
                }
                break;
            case "r":
                purchases.get(currentIndex).reviewPurchase();
                System.out.println(" Purchase has been marked reviewed!");
                break;
            case "u":
                purchases.get(currentIndex).unreviewPurchase();
                System.out.println(" Purchase has been marked not reviewed!");
                break;
            case "q":
                return;
            default:
                System.out.println(" ERROR: Invalid option inputted. Please try again.");
        }
    }

    // MODFIFES: this
    // EFFECTS: display all unreviewed purchases with specified method of payment in
    // one day, one purchase at a time
    public void getOneDayPurchasesMethod() {
        printTop();
        printTitle("financial reconciliation");
        printBottom();

        System.out.println(" Please enter the year: ");
        Integer year = Integer.valueOf(this.scanner.nextLine());
        System.out.println("Please enter the month: ");
        Integer month = Integer.valueOf(this.scanner.nextLine());
        System.out.println("Please enter the day: ");
        Integer day = Integer.valueOf(this.scanner.nextLine());
        System.out.println("Please enter the method of payment: ");
        String paymentMethod = this.scanner.nextLine();
        LocalDate date = LocalDate.of(year, month, day);

        List<Purchase> result = workSpace.getPurchaseRecord().getOneDayPurchasesMethod(date, paymentMethod);
        if (result.isEmpty()) {
            System.out
                    .println(" There's no unreviewed purchase on " + date.toString() + " using " + paymentMethod + ".");
        } else {
            String command = "";
            while (!command.equals("q")) {
                printTop();
                printTitle("financial reconciliation");
                printDivider();
                System.out.println("║ [n]: move to the next purchase                                     ║");
                System.out.println("║ [p]: move to the previous purchase                                 ║");
                System.out.println("║ [r]: mark purchase as reviewed                                     ║");
                System.out.println("║ [u]: mark purchase as not reviewed                                 ║");
                System.out.println("║ [q]: return to the main menu                                       ║");
                printDivider();
                Purchase currentPurchase = result.get(currentIndex);
                String displayResult = " Showing result #" + Integer.toString((currentIndex + 1)) + " out of "
                        + Integer.toString(result.size()) + " result(s)";
                System.out.println("║" + displayResult + " ".repeat(BOX_LENGTH - displayResult.length()) + "║");
                displayOnePurchase(currentPurchase);
                command = this.scanner.nextLine();
                processTraversePurchasesList(command, result);
            }
            currentIndex = 0;
        }
    }

    // // MODIFIES: this
    // // EFFECTS:
    // public void updatePurchase() {
    // printTop();
    // printTitle("update purchase's information");
    // printDivider();
    // List<Purchase> result = purchaseRecord.getPurchases();
    // if (result.isEmpty()) {
    // System.out.println(" Error: No purchase was found. Please try again.");
    // }
    // else {
    // String command = "";
    // while (!command.equals("q")) {
    // printTop();
    // printTitle("update purchase's information");
    // printDivider();
    // System.out.println("║ [p]: update purchase's sold products ║");
    // System.out.println("║ [r]: update purchase's received payment ║");
    // System.out.println("║ [m]: update purchase's payment method ║");
    // System.out.println("║ [t]: update purchase's quantity ║");
    // System.out.println("║ [r]: remove purchase from purchase record ║");
    // System.out.println("║ [q]: return to the main menu ║");
    // printBottom();
    // command = this.scanner.nextLine();
    // processUpdatePurchaseCommands(command, result);
    // }
    // }
    // }

    // EFFECTS: display one purchase's information
    public void displayOnePurchase(Purchase p) {
        String date = p.getDate().toString();
        Map<Product, Integer> products = p.getPurchasedProducts();
        String totalCost = p.getTotalCost().toString();
        String actualPaidAmount = p.getActualPaidAmount().toString();
        String paymentMethod = p.getPaymentMethod();
        String reviewedStatus = Boolean.toString(p.getReviewedStatus());

        System.out.println("║ Purchase's date: " + date + " ".repeat(BOX_LENGTH - 18 - date.length()) + "║");
        System.out.println("║ Purchase's products                                                ║");
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            String display = (product.getName() + " (ID: " + product.getId() + "): " + amount + " item(s)");
            System.out.println("║    - " + display + " ".repeat(BOX_LENGTH - 6 - display.length()) + "║");
        }
        System.out.println("║ Purchase's total: " + totalCost + " ".repeat(BOX_LENGTH - 19 - totalCost.length()) + "║");
        System.out.println("║ Purchase's received payment: " + actualPaidAmount
                + " ".repeat(BOX_LENGTH - 30 - actualPaidAmount.length()) + "║");
        System.out.println("║ Purchase's payment method: " + paymentMethod
                + " ".repeat(BOX_LENGTH - 28 - paymentMethod.length()) + "║");
        System.out.println("║ Product's reviewed status: " + reviewedStatus
                + " ".repeat(BOX_LENGTH - 28 - reviewedStatus.length()) + "║");
        printBottom();
    }

    // EFFECTS: return the profit in between given dates
    public void getProfit() {
        printTop();
        printTitle("get profit");
        printBottom();

        System.out.println("Please enter the starting year: ");
        Integer startYear = Integer.valueOf(this.scanner.nextLine());
        System.out.println("Please enter the starting month: ");
        Integer startMonth = Integer.valueOf(this.scanner.nextLine());
        System.out.println("Please enter the starting day: ");
        Integer startDay = Integer.valueOf(this.scanner.nextLine());

        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

        LocalDate endDate = LocalDate.of(-9999, 1, 1);
        while (endDate.isBefore(startDate)) {
            System.out.println("Please enter the ending year: ");
            Integer endYear = Integer.valueOf(this.scanner.nextLine());
            System.out.println("Please enter the ending month: ");
            Integer endMonth = Integer.valueOf(this.scanner.nextLine());
            System.out.println("Please enter the ending day: ");
            Integer endDay = Integer.valueOf(this.scanner.nextLine());
            endDate = LocalDate.of(endYear, endMonth, endDay);
            if (endDate.isBefore(startDate)) {
                System.out.println(" ERROR: End date must not be before start date. Please try again.");
            }
        }

        Double profit = workSpace.getPurchaseRecord().getProfit(startDate, endDate);
        System.out.println("Profit made from " + startDate.toString() + " to " + endDate.toString() + ": " + profit);
    }

    // EFFECTS: saves the workspace to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(workSpace);
            jsonWriter.close();
            System.out.println("Saved workspace to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the workspace from file
    private void load() {
        try {
            workSpace = jsonReader.read();
            System.out.println("Loaded workspace from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Marks the program as not running
    public void quitApplication() {
        System.out.println(" Thanks for using the Sales Management app!");
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

    // EFFECTS: print out the top title for each menu
    private void printTitle(String title) {
        int length = title.length();
        if ((length % 2) == 1) {
            System.out.println("║" + " ".repeat(BOX_LENGTH / 2 - (length + 1) / 2) + title.toUpperCase()
                    + " ".repeat(BOX_LENGTH / 2 - (length - 1) / 2) + "║");
        } else {
            System.out.println("║" + " ".repeat(BOX_LENGTH / 2 - length / 2) + title.toUpperCase()
                    + " ".repeat(BOX_LENGTH / 2 - length / 2) + "║");
        }
    }
}
