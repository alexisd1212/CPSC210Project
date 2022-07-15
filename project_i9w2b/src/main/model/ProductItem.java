package model;

import model.exception.NotInStockException;
import org.json.JSONObject;
import persistence.Writable;


// Represents a product item with an associated name, its price,
// a description of the item and its corresponding category
public class ProductItem implements Writable {
    private String itemName;
    private double price;
    private String itemDescription;
    private Category.Categories itemCategory;
    private int productInventory;

    // EFFECTS: constructs a product item with an associated name, its price,
    // a description of the item and its corresponding category
    public ProductItem(String itemName,
                       double price,
                       String itemDescription,
                       Category.Categories itemCategory,
                       int productInventory) throws NotInStockException {
        if (productInventory == 0) {
            throw new NotInStockException();
        }

        this.itemName = itemName;
        this.price = price;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.productInventory = productInventory;
    }


    // EFFECTS: returns the product item's name
    public String getItemName() {
        return itemName;
    }

    // REQUIRES: price > 0
    // EFFECTS: returns the product item's price
    public double getPrice() {
        return price;
    }

    // EFFECTS: returns the product item's description
    public String getItemDescription() {
        return itemDescription;
    }

    // EFFECTS: returns the product item's category
    public Category.Categories getItemCategory() {
        return itemCategory;
    }

    public int getProductInventory() {
        return productInventory;
    }

    // MODIFIES: this
    // EFFECTS: decreases the inventory of a product item by one once it's added to a cart
    public int changeInventory() {
        return this.productInventory--;
    }


    //Sets the image to be used for the product.
    //If this method is never called, a default "no image" icon is used.
	// Produces The URL of the image, it will be loaded from the internet and resized.



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", itemName);
        json.put("price", price);
        json.put("description", itemDescription);
        json.put("category", itemCategory);
        json.put("inventory", productInventory);
        return json;
    }

}








