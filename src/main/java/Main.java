import java.io.IOException;

import java.util.Map;

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

//        System.out.println("Populating database if empty...");
//        movieRepository.createDatabase(movieMap);

        System.out.println("Looking for differences...");
        for (Map.Entry<Integer, Movie> movie : movieMap.entrySet()) {
            Movie checkedMovie = movieRepository.findById(movie.getKey());
            if (movie.getValue().hashCode() == checkedMovie.hashCode()) {
                movieRepository.updateTimeOfModification(checkedMovie);
            } else {
                System.out.println(movie.getValue().getPosition() + ". " + movie.getValue().getTitle() + " changed.");
            }
        }

        System.out.println("Exporting the data to excel format...");
        movieRepository.exportToExcel(movieMap, newExcelFormat);
        System.out.println("Done!");
        UserListener.closeProgram();
    }

}