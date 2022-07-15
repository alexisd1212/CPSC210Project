package ui;

import model.exception.NotInStockException;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new OnlineStore();
        } catch (FileNotFoundException | NotInStockException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
