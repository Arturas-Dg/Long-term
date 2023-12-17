import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VendingMachineExample implements Runnable {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new VendingMachineExample());
    }

    private ButtonGroup buttonGroup;

    private JFrame frame;

    private JTextField balanceField, changeField;

    private final VendingMachineModel model;

    public VendingMachineExample() {
        this.model = new VendingMachineModel();
    }

    @Override
    public void run() {
        frame = new JFrame("Vending Machine with a Four Dillabyte Crossfade");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(createHeaderPanel(), BorderLayout.NORTH);
        frame.add(createBodyPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel label = new JLabel("Vending Machine");
        label.setFont(new Font("Helvetica Neue", 3, 48));
        panel.add(label);

        return panel;
    }

    private JPanel createBodyPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new FlowLayout());

        panel.add(createVendingPanel());
        panel.add(createDisplayPanel());
        panel.add(createCoinKeypad());

        return panel;
    }

    private JPanel createVendingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Select an item"));

        buttonGroup = new ButtonGroup();
        for (Item item : model.getItems()) {
            double price = 0.01 * item.getPrice();
            String text = item.getName() + ": £" + String.format("%.2f", price);
            JRadioButton button = new JRadioButton(text);
            text = item.getName() + ";;;" + item.getPrice();
            button.setActionCommand(text);
            buttonGroup.add(button);
            panel.add(button);
        }

        JButton button = new JButton("Purchase");
        button.addActionListener(new PurchaseListener(this, model));
        panel.add(button);

        button = new JButton("Clear");
        button.addActionListener(event -> {
            buttonGroup.clearSelection();
        });
        panel.add(button);

        button = new JButton("Cancel");
        button.addActionListener(event -> {
            buttonGroup.clearSelection();
            int amount = model.getBalance();
            model.setBalance(0);
            model.setChange(amount);
            updateDisplayPanel();
        });
        panel.add(button);

        return panel;
    }

    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Cash"));
        balanceField = new JTextField(10);
        balanceField.setEditable(false);
        balanceField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(balanceField);

        panel.add(new JLabel("Change"));
        changeField = new JTextField(10);
        changeField.setEditable(false);
        changeField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(changeField);

        updateDisplayPanel();
        return panel;
    }

    public void updateDisplayPanel() {
        double amount = 0.01 * model.getBalance();
        String text = "£" + String.format("%.2f", amount);
        balanceField.setText(text);

        amount = 0.01 * model.getChange();
        text = "£" + String.format("%.2f", amount);
        changeField.setText(text);
    }

    private JPanel createCoinKeypad() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Coins"));
        panel.setLayout(new GridLayout(0, 2, 5, 5));

        for (Item item : model.getCoins()) {
            JButton button = new JButton(item.getName());
            button.setActionCommand(Integer.toString(item.getPrice()));
            button.addActionListener(event -> {
                model.setChange(0);
                model.addCoin(item.getPrice());
                updateDisplayPanel();
            });
            panel.add(button);
        }

        return panel;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public JFrame getFrame() {
        return frame;
    }

    public class PurchaseListener implements ActionListener {

        private final VendingMachineExample view;

        private final VendingMachineModel model;

        public PurchaseListener(VendingMachineExample view, VendingMachineModel model) {
            this.view = view;
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            ButtonModel buttonModel = view.getButtonGroup().getSelection();
            if (buttonModel == null) {
                JOptionPane.showMessageDialog(view.getFrame(),
                        "Please select an item", "Select an item",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String text = buttonModel.getActionCommand();
                String[] parts = text.split(";;;");
                String name = parts[0];
                int amount = Integer.valueOf(parts[1]);
                int balance = model.getBalance();

                if (balance >= amount) {
                    displayPurchaseMessage(name, amount, balance);
                } else {
                    displayDifferenceMessage(amount, balance);
                }
            }
        }

        private void displayPurchaseMessage(String name, int amount, int balance) {
            String text;
            text = "You purchased a " + name;
            JOptionPane.showMessageDialog(view.getFrame(), text, "Item purchased",
                    JOptionPane.INFORMATION_MESSAGE);
            model.purchaseProduct(amount);
            int change = balance - amount;
            model.setBalance(0);
            ;
            model.setChange(change);
            view.updateDisplayPanel();
            view.getButtonGroup().clearSelection();
        }

        private void displayDifferenceMessage(int amount, int balance) {
            String text;
            int difference = amount - balance;
            double price = 0.01 * difference;
            text = "Please deposit an additional £" +
                    String.format("%.2f", price);
            JOptionPane.showMessageDialog(view.getFrame(), text, "Add Coins",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public class VendingMachineModel {

        private int balance, change;

        private final List<Item> coins, items;

        public VendingMachineModel() {
            this.balance = 0;
            this.change = 0;

            this.coins = new ArrayList<>();
            this.coins.add(new Item("5p", 5));
            this.coins.add(new Item("10p", 10));
            this.coins.add(new Item("20p", 20));
            this.coins.add(new Item("50p", 50));
            this.coins.add(new Item("£1", 100));
            this.coins.add(new Item("£2", 200));

            this.items = new ArrayList<>();
            this.items.add(new Item("Coke", 150));
            this.items.add(new Item("Lemonade", 120));
            this.items.add(new Item("Tango", 140));
            this.items.add(new Item("Water", 100));
            this.items.add(new Item("Pepsi", 130));
            this.items.add(new Item("Sprite", 120));
        }

        public List<Item> getCoins() {
            return coins;
        }

        public List<Item> getItems() {
            return items;
        }

        public void addCoin(int amount) {
            this.balance += amount;
        }

        public void purchaseProduct(int amount) {
            this.balance -= amount;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }

        public void setChange(int change) {
            this.change = change;
        }

        public int getChange() {
            return change;
        }

    }

    public class Item {

        private final int price;

        private final String name;

        public Item(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

    }

}