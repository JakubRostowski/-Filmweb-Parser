import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import java.util.Map;

public class Main {
    private final MovieService movieService = new MovieService();

    private static final UserListener userListener = new UserListener();

    public static void main(String[] args) throws IOException {

        boolean exportToExcel = false;
        boolean newExcelFormat = true;

        if (!userListener.useDefaultSettings()) {
            if (userListener.askAboutExcelOutput()) {
                exportToExcel = true;
                newExcelFormat = userListener.askAboutExcelFormat();
            }
        }

        Map<Integer, Movie> movieMap = MovieService.downloadData();
        if (!MovieService.populateDatabaseIfEmpty(movieMap)) {
            System.out.println("...so I am checking differences");
            MovieService.checkDifferences(movieMap);
        }

        if (exportToExcel) {
            MovieService.ExportFile(movieMap, newExcelFormat);
        }

        System.out.println("Done!");
    }
}