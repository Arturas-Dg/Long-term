import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class BudgetTracker extends JFrame {

    JLabel budgetLabel, planLabel, timePeriod;
    JTextField budgetField;
    JRadioButton plan503020, planPriority;
    JComboBox timePeriodComboBox;
    JButton confirm, clear;
    double percent50, percent30, percent20, costOfliving, moneyLeft;
    JLabel outputJLabel;
    int count = 0;

    public void initialize() {

        // THESE FUNCTIONS CREATE THE PANEL OF SALARY AND SELECTION OF TYPE OF BUDGET
        // PLAN

        budgetLabel = new JLabel("What's your salary (€):");
        budgetField = new JTextField(10);
        budgetField.setBorder(null);

        planLabel = new JLabel("Select the budgeting plan down below:");

        plan503020 = new JRadioButton("50/30/20 % plan");
        plan503020.setActionCommand("503020");
        plan503020.setBackground(Color.decode("#EBE3D5"));

        planPriority = new JRadioButton("Priorities plan");
        planPriority.setActionCommand("priority");
        planPriority.setBackground(Color.decode("#EBE3D5"));

        ButtonGroup bg = new ButtonGroup();
        bg.add(plan503020);
        bg.add(planPriority);

        JPanel budget = new JPanel();
        budget.add(budgetLabel);
        budget.add(budgetField);
        budget.setBackground(Color.decode("#EBE3D5"));

        JPanel plan = new JPanel();
        plan.add(planLabel);
        plan.add(plan503020);
        plan.add(planPriority);
        plan.setLayout(new BoxLayout(plan, BoxLayout.Y_AXIS));
        plan.setBackground(Color.decode("#EBE3D5"));

        JPanel userChoice = new JPanel();

        userChoice.add(budget);
        userChoice.add(plan);
        userChoice.setBorder(BorderFactory.createLineBorder(Color.black));
        userChoice.setLayout(new BoxLayout(userChoice, BoxLayout.Y_AXIS));
        userChoice.setBackground(Color.decode("#EBE3D5"));

        // CREATING THE TIME PERIOD/ HOW OFTEN DOES THE PERSON GET HIS SALARY

        String s1[] = { "", "Daily", "Weekly", "Monthly" };

        timePeriod = new JLabel("How often are you being paid:");
        timePeriodComboBox = new JComboBox<>(s1);

        JPanel period = new JPanel();
        period.add(timePeriod);
        period.add(timePeriodComboBox);

        // CREATING AND HIDING EXPENSES FOR PLAN 2

        JPanel expenses = new JPanel();
        JLabel exJLabel = new JLabel("Expenses in given time period:");
        JTextField eJTextField = new JTextField(10);
        expenses.add(exJLabel);
        expenses.add(eJTextField);
        expenses.setVisible(false);

        // THE BOTTOM OF GUI, CONFIRM AND CLEAR BUTTONS

        confirm = new JButton("CONFIRM");

        clear = new JButton("CLEAR");

        JPanel buttons = new JPanel();

        buttons.add(clear);
        buttons.add(confirm);

        // ONE FOR PLAN 1, ONE FOR PLAN 2

        JPanel plan1 = new JPanel();
        JLabel plan1JLabel = new JLabel();
        plan1.add(plan1JLabel);
        plan1.setLayout(new BoxLayout(plan1, BoxLayout.PAGE_AXIS));

        JPanel plan2 = new JPanel();
        JLabel plan2Nec = new JLabel();
        JLabel plan2Ent = new JLabel();
        JLabel plan2Inv = new JLabel();

        plan2.add(plan2Nec);
        plan2.add(plan2Ent);
        plan2.add(plan2Inv);
        plan2.setLayout(new BoxLayout(plan2, BoxLayout.PAGE_AXIS));

        // CREATING FUNCTIONS OF TAKING VALUES FROM TEXT FIELD, RADIO BUTTONS, JCOmboBox

        clear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                budgetField.setText("");
                bg.clearSelection();
                timePeriodComboBox.setSelectedItem("");
                plan1JLabel.setText("");
                plan2.setVisible(false);

            }
        });

        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Object userPeriod = timePeriodComboBox.getSelectedItem();
                String stPeriod = userPeriod.toString();
                String budget = budgetField.getText();
                String cost = eJTextField.getText();
                String radioChoice = bg.getSelection().getActionCommand();

                if (plan(radioChoice)) {
                    count++;
                    expenses.setVisible(true);
                    plan1.setVisible(true);
                    plan2.setVisible(false);
                    if (count > 1) {
                        if (priority(budget, cost) > 0) {
                            plan1JLabel
                                    .setText(stPeriod + " Free for to use money after paying for expenses:"
                                            + priority(budget, cost)
                                            + "$");
                        } else {
                            plan1JLabel
                                    .setText("ERROR! " + stPeriod + " expences are more than income by "
                                            + -priority(budget, cost)
                                            + "$");
                        }
                    }
                } else {
                    expenses.setVisible(false);
                    double[] percentages = plan503020(budget);
                    percent50 = percentages[0];
                    percent30 = percentages[1];
                    percent20 = percentages[2];
                    plan2.setVisible(true);
                    plan1.setVisible(false);
                    plan2Nec.setText("Money for necessities " + stPeriod + " : " + decfor.format(percent50) + "$");
                    plan2Ent.setText("Money for entertainment " + stPeriod + " : " + decfor.format(percent30) + "$");
                    plan2Inv.setText(
                            "Money for investment/ debt payoff " + stPeriod + " : " + decfor.format(percent20) + "$");
                }
            }
        });

        // CREATING THE MAIN PANEL

        JPanel mainPanel = new JPanel();

        // ADDING DIFFERENT JPANELS TO THE MAIN PANEL

        mainPanel.add(userChoice);
        mainPanel.add(period);
        mainPanel.add(expenses);
        mainPanel.add(buttons);
        mainPanel.add(plan1);
        mainPanel.add(plan2);
        mainPanel.setBackground(Color.decode("#F3EEEA"));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        // THE LOOKS OF THE MAIN PANEL ARE CHANGED

        setTitle("Hello world");
        setSize(400, 350);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        add(mainPanel);

    }

    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    public static boolean plan(String plan) {

        if (plan == "priority") {
            return true;
        }

        return false;
    }

    public static double[] plan503020(String cash) {
        double income = Double.parseDouble(cash);
        double[] money = new double[3];
        money[0] = income * 0.5;
        money[1] = income * 0.3;
        money[2] = income * 0.2;
        return money;
    }

    public static double priority(String cash, String cost) {
        double price = 0;
        double income = Double.parseDouble(cash);
        double expense = Double.parseDouble(cost);
        price = income - expense;
        return price;
    }

    public static void main(String[] args) {
        BudgetTracker myFrame = new BudgetTracker();
        myFrame.initialize();
    }

}
