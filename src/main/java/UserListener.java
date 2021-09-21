import java.util.Scanner;

public class UserListener {

    private static final Scanner scanner = new Scanner(System.in);

    public boolean useDefaultSettings() {
        System.out.println("Hello! This program takes the Filmweb TOP500 list and exports it to an excel file." +
                "\nWould like to use the default settings? Please type \"yes\" or \"no\".");

        while (true) {
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                return true;
            } else if (answer.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please type again.");
            }
        }
    }

    public boolean askAboutExcelOutput() {
        System.out.println("Would you like to export results as an excel file?");
        while (true) {
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                return true;
            } else if (answer.equals("no")) {
                return false;
            } else {
                System.out.println("Incorrect input. Please type again.");
            }
        }
    }

    public boolean askAboutExcelFormat() {
        System.out.println("Please choose excel format:" +
                "\n\t.xls - Old format. Best for compatibility." +
                "\n\t.xlsx - New format. May be not supported by external or old viewers.");

        while (true) {
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals(".xlsx") || answer.equals("xlsx")) {
                return true;
            } else if (answer.equals(".xls") || answer.equals("xls")) {
                return false;
            } else {
                System.out.println("Incorrect input. Please type again.");
            }
        }
    }

    public static void closeProgram() {
        System.out.println("Press enter to quit.");
        scanner.nextLine();
        System.exit(0);
    }


}
