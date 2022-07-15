package persistence;

import model.Category;
import model.ProductItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String name, double price, String description, Category.Categories category,
                               int inventory, ProductItem pi) {
        assertEquals(name, pi.getItemName());
        assertEquals(price, pi.getPrice());
        assertEquals(description, pi.getItemDescription());
        assertEquals(category, pi.getItemCategory());
        assertEquals(inventory, pi.getProductInventory());
    }
}

