import java.util.Scanner;

public class BudgetTrackerInsides {

    public static void budgetType(int income, int choice) {
        Scanner sc = new Scanner(System.in);

        if (choice == 1) {
            System.out.println("Amount allocated to needs: " + income * 0.5 + "$");
            System.out.println("Amount allocated to wants: " + income * 0.3 + "$");
            System.out.println("Amount allocated to investment/ paying off debt: " + income * 0.2 + "$");
        } else if (choice == 2) {
            System.out.print("How much do your necessities cost on average: ");
            String necessities = sc.nextLine();
            int necessityCost = Integer.parseInt(necessities);
            income -= necessityCost;
            if (income >= 0) {
                System.out.println("This month you will have " + income + "$ for your personal wants/ investment");
            } else {
                System.out.println(
                        "This month you will have to live cheaper than before, as your income doesn't cover your necessary expences");
            }
        } else {
            System.out.println("You've chosen an invalid budgeting option");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.print("Please type in your monthly income ($):");
        String userInput = sc.nextLine();
        int income = Integer.parseInt(userInput);

        System.out.println();
        System.out.println("Select the type of rule:");
        System.out.println("1. 50/30/20 % rule");
        System.out.println("2. Priority-based budget");

        String userChoice = sc.nextLine();
        int choiceOfBudget = Integer.parseInt(userChoice);

        budgetType(income, choiceOfBudget);

    }
}
