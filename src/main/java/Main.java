import java.io.IOException;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static MovieRepository movieRepository = new MovieRepository();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Hello! This program takes the Filmweb TOP500 list and exports it to an excel file." +
                "\nWould like to get the whole toplist? Please type \"yes\" or \"no\".");

        boolean isWhole = true;
        boolean newExcelFormat;
        int moviesToGet = 500;

        while(true) {
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                break;
            } else if (answer.equals("no")) {
                isWhole = false;
                break;
            } else {
                System.out.println("Invalid input. Please type again.");
            }
        }

        if (!isWhole) {
            System.out.println("How many movies would you like to get from the toplist?");
            while(true) {
                if (scanner.hasNextInt()) {
                    moviesToGet = scanner.nextInt();
                    scanner.nextLine();
                    if (moviesToGet >= 1 || moviesToGet <= 500) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please type number from 1 to 500");
                    }
                }
            }

        }

        System.out.println("Please choose excel format:" +
                "\n\t.xls - Old format. Best for compatibility." +
                "\n\t.xlsx - New format. May be not supported by external or old viewers.");

        while(true) {
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals(".xlsx") || answer.equals("xlsx")) {
                newExcelFormat = true;
                break;
            } else if (answer.equals(".xls") || answer.equals("xls")) {
                newExcelFormat = false;
                break;
            } else {
                System.out.println("Incorrect input. Please type again.");
            }
        }

        System.out.println("Downloading the data from Filmweb.pl...");
        Map<Integer,Movie> movieMap = movieRepository.getTopList(moviesToGet);
        System.out.println("Exporting the data to excel format...");
        movieRepository.exportToExcel(movieMap, newExcelFormat);
        System.out.println("Done!");
        System.out.println("Press enter to quit.");
        scanner.nextLine();
        System.exit(0);
    }
}