package ui;

//Import needed packages

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.text.MaskFormatter;

public class GUI extends JFrame {

    private static final int WINDOW_WIDTH = 900;    // Width of GUI frame
    private static final int WINDOW_LENGTH = 700;    // Length of GUI frame


    private JPanel itemsPanel;            //Holds all the items
    private JPanel buttonsPanel;        //Has add/remove/checkout buttons
    private JPanel shoppingCartPanel;    //To hold books added by user
    private JPanel searchButtonsPanel;    //Holds search/showall buttons

    private JList itemsList;            //List with all item names
    private JList selectedList;            //List in shopping cart

    private JButton addSelected;        //Adds item to shopping cart
    private JLabel selectedProductImg;    //place holder for item image cart
    private JButton removeSelected;        //Removes item from shopping cart
    private JButton checkOut;            //Adds all item prices + taxes
    private JButton searchButton;        //Searches for desired book
    private JButton showAllButton;        //shows all item available
    private final JButton confirm = new JButton("Confirm Order");
    private final JButton save = new JButton("Save");


    private ReadProductInfo productInfo = new ReadProductInfo();        //ReadProductInfo object
    private String[] itemNames = productInfo.getItemNames();            //Array that Holds all item names
    private double[] prices = productInfo.getItemPrices();              //Array that holds all item prices


    JLabel fullName = new JLabel("Enter your name:");
    JTextField nf = new JTextField(10);
    JLabel nameOnCard = new JLabel("Name on Card:");
    JTextField noc = new JTextField(10);
    JLabel email = new JLabel("Enter your email address:");
    JTextField ef = new JTextField(10);
    JLabel creditCard = new JLabel("Enter your credit card number");
    MaskFormatter mf1 = new MaskFormatter("####-####-####-####");
    JTextField cf = new JFormattedTextField(mf1);
    JLabel expiry = new JLabel("Expiry date (MM/YY)");
    MaskFormatter mf2 = new MaskFormatter("##/##");
    JTextField ed = new JFormattedTextField(mf2);
    JLabel cvv = new JLabel("cvv/security code");
    JPasswordField cvvf = new JPasswordField(3);
    JLabel address = new JLabel("Enter your Street Number and name:");
    JTextField sf = new JTextField(10);
    JLabel province = new JLabel("Province/State");
    JTextField pff = new JTextField(5);
    JLabel postal = new JLabel("Postal Code");
    JTextField pc = new JTextField(5);


    private JScrollPane scrollPane1;    //Holds available item list
    private JScrollPane scrollPane2;    //Holds selected item list

    JToolBar bar = new JToolBar();      //Holds toolbar for save and load functions

    private JLabel panelTitle;          //Panel title
    private JLabel cartTitle;           //Panel title
    private JLabel subTotalTitle;


    private JTextField searchField;     //Allows user to input search

    private int element = -1;            //control variable
    private int selectedIndex;            //Index selected among available items
    private int index;                    //Int that holds selected index.
    private int count;

    private double subTotal;
    private double total;                            //Total of prices
    private double itemPrice;                        //Hold item prices
    private final double tax = 0.10;        //Constant for tax value

    private ListModel items;            //List model for item name list
    private ListModel shoppingCart;        //List model for shopping cart list
    private DefaultListModel shoppingCartDFM;

    private DecimalFormat money;        //Money format
    private Object selectedItemName;    //Selected item

    private String searchResults;                        //Hold search results
    private String notFound = "Item name not found";    //Holds search results

    private boolean found = false;        //Boolean holds false if search results not found

    //Constructor
    //OnlineStoreGUI - Builds a GUI with multiple panels
    public GUI() throws IOException, ParseException {
        //Title of GUI
        setTitle("Online Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_LENGTH));


        bar.add(save);
        bar.add(new JToolBar.Separator());

        //BuildPanels
        buildItemsPanel();
        buildButtonsPanel();
        buildShoppingCartPanel();
        buildSearchButtonsPanel();

        //Add panels to GUI frame
        add(itemsPanel, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.CENTER);
        add(shoppingCartPanel, BorderLayout.EAST);
        add(searchButtonsPanel, BorderLayout.NORTH);
        add(bar, BorderLayout.SOUTH);


        //set visibility
        setVisible(true);
        pack();
    }


    //METHODS
    /*
     *buildBooksPanel() - Builds panel containing a JList/ScrollPane
     */
    public void buildItemsPanel() {

        //Create panel to hold list of items
        itemsPanel = new JPanel();

        //Set Panel layout
        itemsPanel.setLayout(new BorderLayout());

        //Create the list
        itemsList = new JList();
        itemsList.addListSelectionListener(new ListSelectionListener());


        //Set selection preference
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //Visible item names
        itemsList.setVisibleRowCount(5);

        //Create scroll pane containing items list
        scrollPane1 = new JScrollPane(itemsList);
        scrollPane1.setPreferredSize(new Dimension(175, 50));

        //JLabel/Panel title
        panelTitle = new JLabel("Available Products");

        //Add JLabel and scroll to panel
        itemsPanel.add(panelTitle, BorderLayout.NORTH);
        itemsPanel.add(scrollPane1);
    }

    /*
     * buildButtonsPanel - builds panel containing add/remove/checkout buttons
     */
    public void buildButtonsPanel() {
        //Create panel to hold buttons
        buttonsPanel = new JPanel();
        //Set Layout
        buttonsPanel.setLayout(new GridLayout(3, 1));
        //Create Buttons
        addSelected = new JButton("Add Selected Item");
        selectedProductImg = new JLabel();
        removeSelected = new JButton("Remove Selected Item");
        checkOut = new JButton("Checkout");


        //add Listeners
        addSelected.addActionListener(new AddButtonListener());
        removeSelected.addActionListener(new RemoveButtonListener());
        checkOut.addActionListener(new CheckOutButtonListener());
        save.addActionListener(new SaveButtonListener());


        //Add button panel to GUI
        buttonsPanel.add(selectedProductImg);
        buttonsPanel.add(addSelected, BorderLayout.SOUTH);
        buttonsPanel.add(removeSelected, BorderLayout.SOUTH);
        buttonsPanel.add(checkOut, BorderLayout.SOUTH);
    }

    // buildShoppingCartPanel builds panel containing JList/Scroll pane

    public void buildShoppingCartPanel() {
        //Create panel
        shoppingCartPanel = new JPanel();

        //Set panel layout
        shoppingCartPanel.setLayout(new BorderLayout());

        //Create shopping cart list
        selectedList = new JList();

        //Set row visibility
        selectedList.setVisibleRowCount(5);

        //Create scrollpane containing selected list items
        scrollPane2 = new JScrollPane(selectedList);

        scrollPane2.setPreferredSize(new Dimension(175, 50));
        //JLabel/Panel title


        cartTitle = new JLabel("Shopping Cart ");
        subTotalTitle = new JLabel("Sub Total: " + subTotal);


        //Add JLabel and scroll pane to panel
        shoppingCartPanel.add(cartTitle, BorderLayout.NORTH);
        shoppingCartPanel.add(subTotalTitle, BorderLayout.SOUTH);


        shoppingCartPanel.add(scrollPane2);
    }


    // buildSearchButtonsPanel - builds panel containing search and showall buttons
    public void buildSearchButtonsPanel() {
        //Create panel
        searchButtonsPanel = new JPanel();

        //Set Border layout
        searchButtonsPanel.setLayout(new GridLayout(1, 3, 5, 5));
        //Create buttons
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show All");

        //Create text field
        searchField = new JTextField(15);

        //Add listeners
        searchButton.addActionListener(new SearchButtonListener());
        showAllButton.addActionListener(new ShowAllButtonListener());

        //Add buttons and text field to panel
        searchButtonsPanel.add(searchField);
        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(showAllButton);
    }

    //ACTION LISTENERS
    /*
     * AddButtonListener - adds selected item to shopping cart upon selection
     */
    public class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            playSound("./data/mouseclick.wav");
            selectedIndex = itemsList.getSelectedIndex();
            selectedItemName = itemsList.getSelectedValue();

            items = itemsList.getModel();
            shoppingCart = selectedList.getModel();

            shoppingCartDFM = new DefaultListModel();

            for (count = 0; count < shoppingCart.getSize(); count++) {
                shoppingCartDFM.addElement(shoppingCart.getElementAt(count));
            }

            if (element == -1) {
                itemPrice += prices[selectedIndex];
            } else {
                itemPrice += prices[element];

                shoppingCartDFM.addElement(selectedItemName);
                selectedList.setModel(shoppingCartDFM);
            }

            subTotal = itemPrice;
            updateLabels();

        }
    }

    //RemoveButtonListener - Removes selected item from shopping cart upon selection

    public class RemoveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            playSound("./data/mouseclick.wav");
            index = selectedList.getSelectedIndex();
            ((DefaultListModel) selectedList.getModel()).remove(index);

            if (element == -1) {
                if (prices[selectedIndex] <= itemPrice) {
                    itemPrice -= (prices[selectedIndex]);
                } else {
                    itemPrice = (prices[index]) - itemPrice;
                }
            } else {
                if (prices[element] <= itemPrice) {
                    itemPrice -= (prices[element]);
                } else {
                    itemPrice = (prices[index]) - itemPrice;
                }
            }

            subTotal = itemPrice;
            updateLabels();

        }
    }

    /*
     * CheckOutButtonListener - Calculates total and displays it to user
     */
    public class CheckOutButtonListener extends JFrame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            playSound("./data/mouseclick.wav");

            money = new DecimalFormat("#,##0.00");
            subTotal = itemPrice;
            total = (itemPrice + (itemPrice * tax));


            int response = JOptionPane.showConfirmDialog(null, "Subtotal: $" + (money.format(itemPrice))
                    + "\n" + "Tax: $" + (money.format((itemPrice * tax))) + "\n" + "Total: $" + (money.format(total))
                    + "\n" + "Do you want to proceed to shipping and payment?");
            if (response == JOptionPane.YES_OPTION) {
                shippingProcess();
            }

        }

        private void shippingProcess() {
            JFrame frame = new JFrame("Shipping");
            setBounds(200, 200, 200, 200);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setOpaque(true);

            nf.setHorizontalAlignment(SwingConstants.CENTER);
            sf.setHorizontalAlignment(SwingConstants.CENTER);
            pff.setHorizontalAlignment(SwingConstants.CENTER);
            pc.setHorizontalAlignment(SwingConstants.CENTER);
            noc.setHorizontalAlignment(SwingConstants.CENTER);
            ef.setHorizontalAlignment(SwingConstants.CENTER);
            cf.setHorizontalAlignment(SwingConstants.CENTER);
            ed.setHorizontalAlignment(SwingConstants.CENTER);
            cvvf.setHorizontalAlignment(SwingConstants.CENTER);
            cvvf.setEchoChar('*');
            mf1.setPlaceholderCharacter('_');
            addToPanel(panel);
            confirm.addActionListener(new ConfirmButtonListener());
            panel.add(confirm, BorderLayout.SOUTH);
            add(panel);
            setVisible(true);


        }
    }

    private void addToPanel(JPanel panel) {
        panel.add(fullName);
        panel.add(nf);
        panel.add(address);
        panel.add(sf);
        panel.add(province);
        panel.add(pff);
        panel.add(postal);
        panel.add(pc);
        panel.add(nameOnCard);
        panel.add(noc);
        panel.add(email);
        panel.add(ef);
        panel.add(creditCard);
        panel.add(cf);
        panel.add(expiry);
        panel.add(ed);
        panel.add(cvv);
        panel.add(cvvf);
    }

    private void updateLabels() {

        subTotalTitle.setText(String.format("Sub Total: $%.2f", subTotal));

    }

    /*
     * SearchButtonListener - searches for user desired item
     */
    public class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            playSound("./data/mouseclick.wav");

            index = 0;

            while (!found && index < itemNames.length) {
                if (itemNames[index].equalsIgnoreCase(searchField.getText())) {
                    found = true;
                    element = index;
                } else if (itemNames[index].contains(searchField.getText())) {
                    found = true;
                    element = index;
                }
                index++;
            }

            if (element == -1) {
                itemsList.setModel(new DefaultListModel());
                ((DefaultListModel) itemsList.getModel()).addElement(notFound);
            } else {
                searchResults = itemNames[element];
                itemsList.setModel(new DefaultListModel());

                ((DefaultListModel) itemsList.getModel()).addElement(searchResults);
            }

        }
    }

    /*
     * ShowsAllButtonListener - shows all available items
     */
    public class ShowAllButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            playSound("./data/mouseclick.wav");

            itemsList.setModel(new DefaultListModel());

            //Control variables
            int i;
            for (i = 0; i < itemNames.length; i++) {
                ((DefaultListModel) itemsList.getModel()).addElement(itemNames[i]);

            }
        }
    }

    private class ConfirmButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (cf.getText().isEmpty() || ed.getText().isEmpty() || nf.getText().isEmpty() || sf.getText().isEmpty()
                    || pff.getText().isEmpty() || pc.getText().isEmpty() || noc.getText().isEmpty()
                    || ef.getText().isEmpty() || cvvf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Empty fields!", "Missing Information", JOptionPane.ERROR_MESSAGE);
            } else if (!ed.getText().matches("(?:0[1-9]|1[0-2])/[0-9]{2}")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid expiry date", "Missing Information", JOptionPane.ERROR_MESSAGE);
            } else if (!pc.getText().matches("[ABCEGHJKLMNPRSTVXY][0-9]"
                    + "[ABCEGHJKLMNPRSTVWXYZ] ?[0-9]" + "[ABCEGHJKLMNPRSTVWXYZ][0-9]")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid postal code", "Missing Information", JOptionPane.ERROR_MESSAGE);
            } else if (!ef.getText().matches("[a-zA-Z0-9][a-zA-Z0-9_.]"
                    + "+@[a-zA-Z0-9_]+.[a-zA-Z0-9_.]+[a-zA-Z0-9]{2}")) {
                JOptionPane.showMessageDialog(null,
                        "Invalid email address", "Missing Information", JOptionPane.ERROR_MESSAGE);
            } else {
                playSound("./data/successsound.wav");
                JOptionPane.showMessageDialog(null,
                        "Success! Please check your email for confirmation");
            }
        }
    }



    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public class ListSelectionListener implements javax.swing.event.ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                String filePath = String.format("src/%s.png", itemsList.getSelectedValue().toString());
                selectedProductImg.setIcon(new ImageIcon(filePath));
            }
        }
    }

    public class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                FileWriter fw = new FileWriter("out.txt");
                fw.write(String.valueOf((selectedList.getModel())));
                fw.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("Cart saved Successfully");

        }
    }





    public String format(String data, MaskFormatter mf) throws ParseException {
        return mf.valueToString(data);
    }


}


