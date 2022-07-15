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

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() throws NotInStockException {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ProductList pl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCart() throws NotInStockException {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCart.json");
        try {
            ProductList pl = reader.read();
            assertEquals(0, pl.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCart() throws NotInStockException {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCart.json");
        try {
            ProductList pl = reader.read();
            List<ProductItem> items = pl.getItems();
            assertEquals(2, items.size());
            checkItem("Mozzarella cheese", 5.99, "Gourmet aged mozzarella cheese",
                    Category.Categories.FOOD, 25, items.get(0));
            checkItem("Blender", 50.99, "Metal multipurpose blender",
                    Category.Categories.APPLIANCES, 5, items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
