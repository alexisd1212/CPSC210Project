package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.exception.NotInStockException;

public class ProductItemTest {

    @Test
    public void testConstructorNoExceptionExpected() {

        try {
            ProductItem p1 = new ProductItem("Blender", 50.99, "Metal multipurpose blender",
                    Category.Categories.APPLIANCES, 3);
            assertEquals("Blender", p1.getItemName());
            assertEquals(50.99, p1.getPrice());
        } catch (NotInStockException e) {
            fail("NotInStockException not expected");
        }
    }

    @Test
    public void testConstructorExceptionExpected() {

        try {
            new ProductItem("Cheetos", 3.99, "Maize cheese snack",
                    Category.Categories.APPLIANCES, 0);
            fail("NotInStockException expected");
        } catch (NotInStockException e) {
            //expected
        }
    }


}
