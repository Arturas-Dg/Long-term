import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.plaf.DimensionUIResource;

public class MainFrame extends JFrame {

    JTextField tfFirst, tfLast;
    JLabel lbWelcome;

    public void initialize() {

        JLabel lbFirst = new JLabel("First name :");
        tfFirst = new JTextField();

        JLabel lbLast = new JLabel("Last name :");
        tfLast = new JTextField();

        JPanel formPanel = new JPanel();
        formPanel.add(lbFirst);
        formPanel.add(tfFirst);
        formPanel.add(lbLast);
        formPanel.add(tfLast);
        formPanel.setLayout(new GridLayout(4, 1, 5, 5));

        /*********** WELCOME LABEL ***********/

        lbWelcome = new JLabel();

        /*********** Buttons Panel ***********/

        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = tfFirst.getName();
                String lastName = tfLast.getName();
                lbWelcome.setText("Hello " + firstName + " " + lastName);
            }

        });

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tfFirst.setText("");
                tfLast.setText("");
                lbWelcome.setText("");
            }

        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.add(btnOK);
        buttonsPanel.add(btnClear);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(lbWelcome, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setTitle("Hello world");
        setSize(400, 600);
        setMinimumSize(new DimensionUIResource(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
        myFrame.initialize();
    }
}
