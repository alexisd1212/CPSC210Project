package ui;

import model.Category;
import model.ProductItem;
import model.ProductList;
import model.exception.NotInStockException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;




// this ui makes reference to TellerApp ui
// Online store application
public class OnlineStore {
    private static final String JSON_STORE = "./data/cart.json";
    private ProductItem blender;
    private ProductItem advil;
    private ProductItem coke;
    private ProductItem cheese;
    private ProductItem deodorant;
    private ProductList cartItems;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs productList (cartItems) and runs application
    public OnlineStore() throws FileNotFoundException, NotInStockException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runOnlineStore();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runOnlineStore() throws NotInStockException {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        initP();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addItems();
        } else if (command.equals("d")) {
            viewProduct();
        } else if (command.equals("p")) {
            selectItem();
        } else if (command.equals("v")) {
            viewCart();
        } else if (command.equals("c")) {
            checkout();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes product item in search engine and an empty cart (cartItems)
    private void initP() throws NotInStockException {
        blender = new ProductItem("Blender", 50.99, "Metal multipurpose blender",
                Category.Categories.APPLIANCES, 4);
        advil = new ProductItem("Advil", 21.99, "Pain relief medicine",
                Category.Categories.MEDICAL, 10);
        coke =  new ProductItem("Coca Cola", 5.99, "Classic soft drink",
                Category.Categories.DRINKS, 25);
        cheese = new ProductItem("Mozzarella cheese", 5.99, "Gourmet aged mozzarella cheese",
                Category.Categories.FOOD, 25);
        deodorant = new ProductItem("Deodorant", 6.99,
                "Dove Antiperspirant deodorant spray for 48-hour protection",
                Category.Categories.TOILETRIES, 10);
        input = new Scanner(System.in);
        cartItems = new ProductList();



    }

    // EFFECTS: displays options to user after selecting a product item or adding it to cart
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add Item to Cart");
        System.out.println("\td -> View product details");
        System.out.println("\tp -> Go back to Products page");
        System.out.println("\tv -> View Cart");
        System.out.println("\tc -> Checkout");
        System.out.println("\tq -> Quit");
    }




    // MODIFIES: this
    // EFFECTS: adds Item(s) to cart
    private void addItems() {
        System.out.println("Please enter name of thingy: ");
        ProductItem selected = selectItem();
        cartItems.addItems(selected);
        System.out.println("Item added to cart");
        printPrice(selected);
    }

    // EFFECTS: prints all the details of a selected item
    private void viewProduct() {
        ProductItem selected = selectItem();
        System.out.println("Item name:" +  selected.getItemName());
        System.out.println("Item category:" +  selected.getItemCategory());
        System.out.println("Item description:" +  selected.getItemDescription());
        System.out.println("Item price:" +  selected.getPrice());

    }


    // EFFECTS: prints all the items added to your cart so far
    private void viewCart() {
        List<ProductItem> items = cartItems.getItems();

        for (ProductItem p : items) {
            System.out.println(p);
            System.out.println(p.getItemName());
        }
    }

    // MODIFIES: this
    // EFFECTS: Goes straight to checking out (makePurchase) for item
    private void checkout() {
        cartItems.makePurchase();
        System.out.println("Order confirmed");
        printBalance(cartItems);
    }


    // EFFECTS: prompts user to select a product item
    private ProductItem selectItem() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("blender") || selection.equals("advil") || selection.equals("cheese")
                || selection.equals("coke") || selection.equals("deodorant"))) {
            System.out.println("Featured Products:");
            System.out.println("blender for Electric blender");
            System.out.println("advil for Advil Medication");
            System.out.println("cheese for Mozzarella cheese");
            System.out.println("coke for Coca Cola");
            System.out.println("deodorant for Deodorant");
            selection = input.next();
        }


        switch (selection) {
            case "advil":
                return advil;
            case "coke":
                return coke;
            case "cheese":
                return cheese;
            case "deodorant":
                return deodorant;
            default:
                return blender;
        }
    }

    // EFFECTS: prints price of item added to cart to the screen
    private void printPrice(ProductItem selected) {
        System.out.printf("Balance: $%.2f\n", selected.getPrice());
    }

    // EFFECTS: prints balance of overall purchase including shipping and tax costs
    private void printBalance(ProductList cartItems) {
        System.out.printf("Total amount: $%.2f\n", cartItems.makePurchase());
    }



}






















