package ui;

import java.io.IOException;
import java.text.ParseException;

public class OnlineGuiMain {
    public static void main(String[] args) throws IOException  {
        try {
            new GUI();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
