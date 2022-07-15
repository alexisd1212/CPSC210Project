package model;

public class Category {

    public enum Categories {
        TOILETRIES,
        APPLIANCES,
        MEDICAL,
        FOOD,
        DRINKS,



    }

    Categories cname;

    // EFFECTS: constructs the Category
    public Categories getCategory() {
        return cname;
    }
}
