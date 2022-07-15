package model;

import model.exception.NotInStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductListTest {
    private ProductList pl;
    private ProductItem p1;
    private ProductItem p2;
    private ProductItem p3;

    @BeforeEach
    public void runBefore() {

        pl = new ProductList();
        try {
            p1 = new ProductItem("Blender", 50.99, "Metal multipurpose blender",
                    Category.Categories.APPLIANCES, 4);
            p2 = new ProductItem("Advil", 21.99, "Pain relief medicine",
                    Category.Categories.MEDICAL, 10);
            p3 = new ProductItem("Doritos", 4.99, "Corn tortilla snack",
                    Category.Categories.FOOD, 3);
        } catch (NotInStockException e) {
            fail("Cannot run tests: failed to construct test items");
        }
    }

    @Test
    public void testEmpty() {
        assertEquals(0, pl.size());
    }

    @Test
    public void testAddItemEmpty() {
        pl.addItems(p1);
        pl.addItems(p2);
        assertEquals(2, pl.size());
    }

    @Test
    public void testAddItemInStock() {
        pl.addItems(p1);
        pl.addItems(p2);
        ProductItem productItem = pl.getItems().get(1);
        assertEquals(21.99, productItem.getPrice());
        assertTrue(pl.inStock(productItem));
        assertEquals("Advil", productItem.getItemName());
        productItem.changeInventory();
        assertEquals(9, productItem.changeInventory());
        assertEquals(2, pl.size());
    }


    @Test
    public void testTotalPurchaseAmount() {
        pl.addItems(p1);
        pl.addItems(p2);
        assertEquals(72.98, pl.totalPurchaseAmount());
    }

    @Test
    public void testMakePurchase() {
        pl.addItems(p1);
        pl.addItems(p2);
        pl.addItems(p3);
        assertEquals(95.767, pl.makePurchase());
    }
}

