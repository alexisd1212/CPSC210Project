package ui;


import java.util.Scanner;
import java.io.*;


public class ReadProductInfo {

    public String[] getItemNames() throws IOException {
        int count = 0;
        final int SIZE = 9;
        String[] itemName = new String[SIZE];

        File myFile = new File("src/productItems");
        Scanner inputFile = new Scanner(myFile);

        while (inputFile.hasNext() && count < itemName.length) {
            String str;

            str = inputFile.nextLine();

            String[] parts = str.split(",");
            itemName[count] = parts[0];
            count++;
        }
        inputFile.close();

        return itemName;
    }

    public double[] getItemPrices() throws IOException {

        int count = 0;
        final int SIZE = 9;
        double[] prices = new double[SIZE];

        File myFile = new File("src/productItems");
        Scanner inputFile = new Scanner(myFile);

        while (inputFile.hasNext() && count < prices.length) {
            String str;

            str = inputFile.nextLine();
            String[] parts = str.split(",");

            prices[count] = Double.parseDouble(parts[1]);
            count++;
        }
        inputFile.close();

        return prices;
    }
}
