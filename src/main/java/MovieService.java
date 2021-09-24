import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MovieService {
    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static final EntityManager em = factory.createEntityManager();

    private static final MovieRepository movieRepository = new MovieRepository(em);
    private static final ArchivedMovieRepository archivedMovieRepository = new ArchivedMovieRepository(em);

    public static Map<Integer, Movie> downloadData() throws IOException {
        System.out.println("Downloading the data from Filmweb.pl...");
        return movieRepository.getTopList();
    }

    public static boolean populateDatabaseIfEmpty(Map<Integer, Movie> movieMap) {
        if (movieRepository.checkIfEmpty()) {
            System.out.println("Populating empty database...");
            movieRepository.createDatabase(movieMap);
            return true;
        } else {
            System.out.println("Database is not empty...");
            return false;
        }
    }

    public static void checkDifferences(Map<Integer, Movie> movieMap) {
        System.out.println("Looking for differences...");

        List<Movie> databaseMovies = MovieRepository.getMoviesFromDatabase();
        for (Map.Entry<Integer, Movie> movie : movieMap.entrySet()) {
            Movie checkedMovie = databaseMovies.get(movie.getValue().getPosition()-1);
            if (movie.getValue().hashCode() == checkedMovie.hashCode()) {
                MovieRepository.updateTimeOfModification(checkedMovie);
            } else {
                System.out.println(checkedMovie.getPosition() + ". " + checkedMovie.getTitle() + " changed.");
                MovieRepository.deleteMovie(checkedMovie);
                archivedMovieRepository.addArchivedMovie(checkedMovie.getArchivedMovie());
            }
        }
    }

    public static void ExportFile(Map<Integer, Movie> movieMap, boolean newExcelFormat) throws IOException {
        System.out.println("Exporting the data to excel format...");
        movieRepository.exportToExcel(movieMap, newExcelFormat);
    }

}
