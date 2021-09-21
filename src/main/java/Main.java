import java.io.IOException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Main {
    private static final MovieRepository movieRepository = new MovieRepository();
    private static final UserListener userListener = new UserListener();

    public static void main(String[] args) throws IOException {

        boolean newExcelFormat;

        if (userListener.isDefaultSettings()) {
            newExcelFormat = true;
        } else {
            newExcelFormat = userListener.askAboutExcelFormat();
        }

        System.out.println("Downloading the data from Filmweb.pl...");
        Map<Integer, Movie> movieMap = movieRepository.getTopList();

//        System.out.println("Exporting the data to database...");
//        movieRepository.addToDatabase(movieMap);

        // check what to add and what to update

        System.out.println("Looking for differences...");
        int changedRecords = 0;
        for (Map.Entry<Integer, Movie> movie : movieMap.entrySet()) {
            if (!(movie.getValue().hashCode() == movieRepository.findById(movie.getKey()).hashCode())) {
                System.out.println(movie.getValue().getPosition() + ". " + movie.getValue().getTitle() + " changed.");
                changedRecords++;
            }
        }
        System.out.println("Movies changed: " + changedRecords);

        System.out.println("Exporting the data to excel format...");
        movieRepository.exportToExcel(movieMap, newExcelFormat);
        System.out.println("Done!");
        UserListener.closeProgram();
    }

}