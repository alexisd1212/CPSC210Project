package persistence;

import model.Category;
import model.ProductItem;
import model.ProductList;
import model.exception.NotInStockException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads productList (cart) JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ProductList read() throws IOException, NotInStockException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseProductList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private ProductList parseProductList(JSONObject jsonObject) throws NotInStockException {
        ProductList productList = new ProductList();
        addItems(productList, jsonObject);
        return productList;
    }

    // MODIFIES: wr
    // EFFECTS: parses items from JSON object and adds them to productList (cart)
    private void addItems(ProductList productList, JSONObject jsonObject) throws NotInStockException {
        JSONArray jsonArray = jsonObject.getJSONArray("cart items");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addItem(productList, nextThingy);
        }
    }

    // MODIFIES: productList
    // EFFECTS: parses thingy from JSON object and adds it to productList (cart)
    private void addItem(ProductList productList, JSONObject jsonObject) throws NotInStockException {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        String description = jsonObject.getString("description");
        Category.Categories category = Category.Categories.valueOf(jsonObject.getString("category"));
        int inventory = jsonObject.getInt("inventory");


        ProductItem item = new ProductItem(name, price, description, category, inventory);
        productList.addItems(item);
    }

}
