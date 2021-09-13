import java.io.IOException;

import java.util.Map;

public class Main {
    private static final MovieRepository movieRepository = new MovieRepository();

    public static void main(String[] args) throws IOException {

        UserListener userListener = new UserListener();

        int moviesToGet;
        boolean newExcelFormat;

        if (userListener.isDefaultSettings()) {
            moviesToGet = 500;
            newExcelFormat = true;
        } else {
            moviesToGet = userListener.askAboutNumberOfMovies();
            newExcelFormat = userListener.askAboutExcelFormat();
        }

        System.out.println("Downloading the data from Filmweb.pl...");
        Map<Integer, Movie> movieMap = movieRepository.getTopList(moviesToGet);
        System.out.println("Exporting the data to database...");
        movieRepository.addToDatabase(movieMap);
        System.out.println("Exporting the data to excel format...");
        movieRepository.exportToExcel(movieMap, newExcelFormat);
        System.out.println("Done!");
        UserListener.closeProgram();
    }

}