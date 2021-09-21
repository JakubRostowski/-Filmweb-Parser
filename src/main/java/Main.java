import java.io.IOException;

import java.util.Map;

public class Main {
    private static final MovieRepository movieRepository = new MovieRepository();
    private static final UserListener userListener = new UserListener();

    public static void main(String[] args) throws IOException {

        boolean exportToExcel = false;
        boolean newExcelFormat = true;

        if (!userListener.useDefaultSettings()) {
            if (userListener.askAboutExcelOutput()) {
                newExcelFormat = userListener.askAboutExcelFormat();
            }
        }

        System.out.println("Downloading the data from Filmweb.pl...");
        Map<Integer, Movie> movieMap = movieRepository.getTopList();

//        System.out.println("Populating database if empty...");
//        movieRepository.createDatabase(movieMap);

        System.out.println("Looking for differences...");
        movieRepository.verifyWithDatabase(movieMap);

        if (exportToExcel) {
            System.out.println("Exporting the data to excel format...");
            movieRepository.exportToExcel(movieMap, newExcelFormat);
        }
        System.out.println("Done!");
        UserListener.closeProgram();
    }

}