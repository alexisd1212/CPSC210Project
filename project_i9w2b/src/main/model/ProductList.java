package model;

import model.exception.NotInStockException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductList implements Writable {
    private List<ProductItem> cartItems;
    private static int shippingCosts = 10;
    //private static ProductItem pi;




    // EFFECTS: constructs an empty collection of product items (an empty cart)
    public ProductList() {
        cartItems = new ArrayList<>();
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: adds a new product item to the cart if it is in stock
    public void addItems(String in, double p, String id, Category.Categories ic, int ii) throws NotInStockException {
        ProductItem pi = new ProductItem(in, p, id, ic, ii);
        cartItems.add(pi);
    }

    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: adds a new product item to the cart if it in stock
    public void addItems(ProductItem productItem) {
        cartItems.add(productItem);
    }



    // REQUIRES: price > 0
    // MODIFIES: this
    // EFFECTS: returns true if product is in stock and can be added to the cart and false if otherwise
    public boolean inStock(ProductItem pi) {
        return pi.getProductInventory() > 0;
    }

    // MODIFIES: this
    // EFFECTS: returns added total price of all items in the cart
    public double totalPurchaseAmount() {
        double price = 0;
        for (ProductItem pi : cartItems) {
            price = price + pi.getPrice();
        }
        return price;
    }

    // MODIFIES: this
    // EFFECTS: returns added total price of all items in the cart with tax and shipping costs included
    public double makePurchase() {
        double tax = 0;
        for (ProductItem pi : cartItems) {
            tax = tax + 0.10 * pi.getPrice();
        }
        return totalPurchaseAmount() + tax + shippingCosts;
    }

    // EFFECTS: returns number of product items in this ProductList
    public int size() {
        return cartItems.size();
    }

    // EFFECTS: returns an unmodifiable list of cartItems in this workroom
    public List<ProductItem> getItems() {
        return Collections.unmodifiableList(cartItems);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cart items", cartItemsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray cartItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ProductItem productItem : cartItems) {
            jsonArray.put(productItem.toJson());
        }

        return jsonArray;
    }
}



























