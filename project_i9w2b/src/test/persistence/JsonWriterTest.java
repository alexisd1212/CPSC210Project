package persistence;

import model.Category;
import model.ProductItem;
import model.ProductList;
import model.exception.NotInStockException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ProductList pl = new ProductList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCart() throws NotInStockException {
        try {
            ProductList pl = new ProductList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCart.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCart.json");
            pl = reader.read();
            assertEquals(0, pl.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCart() {
        try {
            ProductList pl = new ProductList();
            pl.addItems(new ProductItem("Blender", 50.99, "Metal multipurpose blender",
                    Category.Categories.APPLIANCES, 4));
            pl.addItems(new ProductItem("Mozzarella cheese", 5.99, "Gourmet aged mozzarella cheese",
                    Category.Categories.FOOD, 25));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCart.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCart.json");
            pl = reader.read();
            List<ProductItem> items = pl.getItems();
            assertEquals(2, items.size());
            checkItem("Blender", 50.99, "Metal multipurpose blender",
                    Category.Categories.APPLIANCES, 4, items.get(0));
            checkItem("Mozzarella cheese", 5.99, "Gourmet aged mozzarella cheese",
                    Category.Categories.FOOD, 25, items.get(1));

        } catch (IOException | NotInStockException e) {
            fail("Exception should not have been thrown");
        }
    }
}
