import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.plaf.DimensionUIResource;

public class BudgetTracker extends JFrame {

    JLabel budgetLabel, planLabel, timePeriod;
    JTextField budgetField;
    JRadioButton plan503020, planPriority;
    JComboBox timePeriodComboBox;
    JButton confirm, clear;

    public void initialize() {

        // THESE FUNCTIONS CREATE THE PANEL OF SALARY AND SELECTION OF TYPE OF BUDGET
        // PLAN

        budgetLabel = new JLabel("What's your salary (€):");
        budgetField = new JTextField(10);
        budgetField.setBorder(null);

        planLabel = new JLabel("Select the budgeting plan down below:");
        plan503020 = new JRadioButton("50/30/20 % plan");
        plan503020.setBackground(Color.decode("#EBE3D5"));
        planPriority = new JRadioButton("Priorities plan");
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

        solve s = new solve();

        String s1[] = { "Daily", "Weekly", "Monthly" };

        timePeriod = new JLabel("How often are you being paid:");
        timePeriodComboBox = new JComboBox<>(s1);

        Object userPeriod = timePeriodComboBox.getSelectedItem();

        System.out.println(userPeriod);

        JPanel period = new JPanel();
        period.add(timePeriod);
        period.add(timePeriodComboBox);

        timePeriodComboBox.addItemListener(s);

        // THE BOTTOM OF GUI, CONFIRM AND CLEAR BUTTONS

        confirm = new JButton("CONFIRM");

        clear = new JButton("CLEAR");

        JPanel buttons = new JPanel();

        buttons.add(clear);
        buttons.add(confirm);

        // CREATING THE MAIN PANEL

        JPanel mainPanel = new JPanel();

        // ADDING DIFFERENT JPANELS TO THE MAIN PANEL

        mainPanel.add(userChoice);
        mainPanel.add(period);
        mainPanel.add(buttons);
        mainPanel.setBackground(Color.decode("#F3EEEA"));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        // THE LOOKS OF THE MAIN PANEL ARE CHANGED

        setTitle("Hello world");
        setSize(400, 600);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        add(mainPanel);
    }

    public static void main(String[] args) {
        BudgetTracker myFrame = new BudgetTracker();
        myFrame.initialize();
    }

}
