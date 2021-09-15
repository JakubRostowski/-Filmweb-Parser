import java.io.IOException;

import java.util.Map;

public class Main {
    private static final MovieRepository movieRepository = new MovieRepository();

    public static void main(String[] args) throws IOException {

        UserListener userListener = new UserListener();

        boolean newExcelFormat;

        if (userListener.isDefaultSettings()) {
            newExcelFormat = true;
        } else {
            newExcelFormat = userListener.askAboutExcelFormat();
        }

        System.out.println("Downloading the data from Filmweb.pl...");
        Map<Integer, Movie> movieMap = movieRepository.getTopList();
        System.out.println("Looking for differences...");
        // check what to add and what to update

        System.out.println("Exporting the data to database...");
        movieRepository.addToDatabase(movieMap);
        System.out.println("Exporting the data to excel format...");
        movieRepository.exportToExcel(movieMap, newExcelFormat);
        System.out.println("Done!");
        UserListener.closeProgram();
    }

}