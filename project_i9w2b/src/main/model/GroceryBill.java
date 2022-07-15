package model;

import java.util.*;

public class GroceryBill {
    private Map<String, List<ProductItem>> groceryItems;

    // EFFECTS: constructs an empty grocery bill
    public GroceryBill() {
        groceryItems = new LinkedHashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a purchase to the grocery bill where item is the grocery item purchased
    // and quantity is the quantity of that item purchased; if item has previously been added
    // to this bill, quantity of item purchased is increased on previous entry, rather than
    // adding a new entry
    public void addPurchase(ProductItem item, int quantity) {
        String itemName = item.getItemName();

        List<ProductItem> itemsForDescription = groceryItems.get(itemName);

        if (itemsForDescription == null) {
            itemsForDescription = new ArrayList<>();
            groceryItems.put(itemName, itemsForDescription);
        }

        for (int count = 0; count < quantity; count++) {
            itemsForDescription.add(item);
        }
    }

    // EFFECTS: returns total number of items purchased
    public int getTotalNumberOfItemsPurchased() {
        Collection<List<ProductItem>> values = groceryItems.values();
        int totalItems = 0;

        for (List<ProductItem> next : values) {
            totalItems += next.size();
        }

        return totalItems;
    }

    // EFFECTS: returns number of line items on this bill
    public int getNumLineItems() {
        return groceryItems.size();
    }

    // EFFECTS: returns total quantity of item purchased on this bill;
    // returns 0 if the item has not been added to bill
    public int getTotalQuantityOfItemPurchased(ProductItem item) {
        List<ProductItem> items = groceryItems.get(item.getItemName());
        return items == null ? 0 : items.size();
    }

    // EFFECTS: returns a string representing this bill where each
    // line item is recorded on a line of its own in the format
    // (quantity)x (item)
    // For example:
    // 2x cheesies @ $1.49
    // 4x red bull @ @2.99
    // 1x macaroni cheese @ $3.55
    public String toString() {
        String bill = "";

        Collection<List<ProductItem>> values = groceryItems.values();

        for (List<ProductItem> items : values) {
            ProductItem item = items.get(0);  // first item is the same as the rest in list
            int quantityPurchased = items.size();
            String priceInDollarsAsStr = String.format("%.2f", item.getPrice());

            bill += quantityPurchased + "x " + item.getItemName() + " @ $"
                    + priceInDollarsAsStr + "\n";
        }

        return bill;
    }

}

