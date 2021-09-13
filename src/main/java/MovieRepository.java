import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MovieRepository {
    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("thePersistenceUnit");
    private static final EntityManager em = factory.createEntityManager();
    private final String URL = "https://www.filmweb.pl";

    public Map<Integer, Movie> getTopList(int moviesCount) throws IOException {
        if (moviesCount > 500 || moviesCount <= 0) {
            System.out.println("Invalid input");
            return null;
        } else {
            double maxPage = setMaxPage(moviesCount);
            Elements urls = new Elements();
            Elements ranks = new Elements();
            for (int i = 1; i <= maxPage; i++) {
                Connection connectList = Jsoup.connect(URL + "/ajax/ranking/film/" + i);
                Document documentList = connectList.get();
                Elements pageRanks = documentList.select("span.rankingType__position");
                Elements pageUrls = documentList.select("div.rankingType__card > div.rankingType__header > div > h2 > a");
                urls.addAll(pageUrls);
                ranks.addAll(pageRanks);
            }

            Elements finalUrls = deleteRedundantMovies(urls, moviesCount);

            Map<Integer, Movie> listOfMovies = new ConcurrentHashMap<>();
            finalUrls.parallelStream().forEach((href) -> {
                int rankOfMovie = Integer.parseInt(ranks.get(finalUrls.indexOf(href)).text());
                try {
                    listOfMovies.put(rankOfMovie, getMovieData(href));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return listOfMovies;
        }
    }

    private double setMaxPage(int moviesCount) {
        double maxPage = (double) moviesCount / 25;
        if (moviesCount % maxPage != 0) {
            maxPage++;
        }
        maxPage = (int) maxPage;
        return maxPage;
    }

    private Movie getMovieData(Element href) throws IOException {
        Connection connectMovie = Jsoup.connect(URL + href.attr("href"));
        Document documentMovie = connectMovie.get();

        String title = documentMovie.select(".filmCoverSection__title > span:nth-child(1)").text();
        int year = Integer.parseInt(documentMovie.select(".filmCoverSection__year").text());
        String originalTitle = documentMovie.select(".filmCoverSection__orginalTitle").text();
        double rate = Double.parseDouble(documentMovie.select("span.filmRating__rateValue:nth-child(2)").text().replaceAll(",", "."));
        double criticsRate;
        if (documentMovie.select("span.filmRating__rateValue:nth-child(1)").text().contains(",")) {
            criticsRate = Double.parseDouble(documentMovie.select("span.filmRating__rateValue:nth-child(1)").text().replaceAll(",", "."));
        } else {
            criticsRate = -1;
        }
        String length = documentMovie.select(".filmCoverSection__filmTime").text().replaceAll("godz.", "h").replaceAll("min.", "min");
        String director = documentMovie.select("div.filmInfo__info:nth-child(3)").text().replaceAll("więcej", "");
        String screenwriter;
        String genre;
        String countryOfOrigin;
        if (director.isEmpty()) {
            director = documentMovie.select(".filmPosterSection__info > div:nth-child(4)").text().replaceAll("więcej", "");
            screenwriter = documentMovie.select("div.filmInfo__info:nth-child(6)").text().replaceAll("więcej", "");
            genre = documentMovie.select("div.filmInfo__info:nth-child(8)").text();
            countryOfOrigin = documentMovie.select("div.filmInfo__info:nth-child(10)").text();
        } else {
            screenwriter = documentMovie.select("div.filmInfo__info:nth-child(5)").text().replaceAll("więcej", "");
            genre = documentMovie.select("div.filmInfo__info:nth-child(7)").text();
            countryOfOrigin = documentMovie.select("div.filmInfo__info:nth-child(9)").text();
        }
        return new Movie(title, year, originalTitle, rate, criticsRate, length, director, screenwriter, genre, countryOfOrigin);
    }

    public void addToDatabase(Map<Integer, Movie> movies) {
        for (Map.Entry<Integer, Movie> movie : movies.entrySet()) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(movie.getValue());
            transaction.commit();
        }
    }

    public void exportToExcel(Map<Integer, Movie> map, boolean IsNewExcelFormat) throws IOException {
        Workbook workbook = createWorkbookObject(IsNewExcelFormat);
        Sheet sheet = workbook.createSheet("Toplist");
        setHeaders(sheet);
        writeRows(map, sheet);
        autoSizeColumns(sheet);

        FileOutputStream fileOut = getFileExtension(IsNewExcelFormat);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    private void writeRows(Map<Integer, Movie> map, Sheet sheet) {
        map.forEach((rank, movie) -> {
            Row row = sheet.createRow(rank);

            for (int i = 0; i <= 10; i++) {
                Cell cell = row.createCell(i);
                switch (i) {
                    case 0:
                        cell.setCellValue(rank + ".");
                        break;
                    case 1:
                        cell.setCellValue(movie.getTitle());
                        break;
                    case 2:
                        cell.setCellValue(movie.getYear());
                        break;
                    case 3:
                        cell.setCellValue(movie.getOriginalTitle());
                        break;
                    case 4:
                        cell.setCellValue(movie.getRate());
                        break;
                    case 5:
                        cell.setCellValue(movie.getCriticsRate());
                        break;
                    case 6:
                        cell.setCellValue(movie.getLength());
                        break;
                    case 7:
                        cell.setCellValue(movie.getDirector());
                        break;
                    case 8:
                        cell.setCellValue(movie.getScreenwriter());
                        break;
                    case 9:
                        cell.setCellValue(movie.getGenre());
                        break;
                    case 10:
                        cell.setCellValue(movie.getCountryOfOrigin());
                        break;
                }
            }
        });
    }

    private Elements deleteRedundantMovies(Elements rawList, int moviesToKeep) {
        if (moviesToKeep % 25 != 0) {
            Elements readyList = new Elements();
            for (Element url : rawList) {
                if (moviesToKeep == 0) {
                    break;
                }
                readyList.add(url);
                moviesToKeep--;
            }
            return readyList;
        }
        return rawList;
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < getHeaders().length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void setHeaders(Sheet sheet) {
        Row rowHeader = sheet.createRow(0);
        rowHeader.setHeightInPoints(30);
        String[] headers = getHeaders();
        for (int i = 0; i < 11; i++) {
            rowHeader.createCell(i).setCellValue(headers[i]);
        }
    }

    private Workbook createWorkbookObject(boolean IsNewExcelFormat) {
        if (IsNewExcelFormat) {
            return new XSSFWorkbook();
        } else {
            return new HSSFWorkbook();
        }
    }

    private FileOutputStream getFileExtension(boolean IsNewExcelFormat) throws IOException {
        if (IsNewExcelFormat) {
            return new FileOutputStream("toplist.xlsx");
        } else {
            return new FileOutputStream("toplist.xls");
        }
    }

    private String[] getHeaders() {
        return new String[]{
                "Rank", "Title", "Year", "Original title", "Rate", "Critics' rate",
                "Length", "Director", "Screenwriter", "Genre", "Country of origin"
        };
    }
}
