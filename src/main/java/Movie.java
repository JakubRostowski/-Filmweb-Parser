import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long movieId;

    private int position;
    private String title;
    private int year;
    private String originalTitle;
    private double rate;
    private double criticsRate;
    private String length;
    private String director;
    private String screenwriter;
    private String genre;
    private String countryOfOrigin;
    private Timestamp timeOfCreation;
    private Timestamp timeOfModification;

    @OneToMany(mappedBy="movieId")
    private List<ArchivedMovie> archivedMovies = new ArrayList<>();

    public Movie(int position, String title, int year, String originalTitle, double rate, double criticsRate,
                 String length, String director, String screenwriter, String genre, String countryOfOrigin) {
        this.position = position;
        this.title = title;
        this.year = year;
        this.originalTitle = originalTitle;
        this.rate = rate;
        this.criticsRate = criticsRate;
        this.length = length;
        this.director = director;
        this.screenwriter = screenwriter;
        this.genre = genre;
        this.countryOfOrigin = countryOfOrigin;
        this.timeOfCreation = new Timestamp(System.currentTimeMillis());
        this.timeOfModification = new Timestamp(System.currentTimeMillis());
    }

    public Movie() {

    }

    public ArchivedMovie getArchivedMovie() {
        return new ArchivedMovie(getPosition(), getTitle(), getRate(), getCriticsRate(), getTimeOfCreation());
    }

    public Long getMovieId() {
        return movieId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getCriticsRate() {
        return criticsRate;
    }

    public void setCriticsRate(double criticsRate) {
        this.criticsRate = criticsRate;
    }

    public String getLength() {
        return length;
    }

    public String getDirector() {
        return director;
    }

    public String getScreenwriter() {
        return screenwriter;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public Timestamp getTimeOfCreation() {
        return timeOfCreation;
    }

    public Timestamp getTimeOfModification() {
        return timeOfModification;
    }

    public void setTimeOfModification(Timestamp timeOfModification) {
        this.timeOfModification = timeOfModification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getPosition() == movie.getPosition() && Double.compare(movie.getRate(), getRate()) == 0 && Double.compare(movie.getCriticsRate(), getCriticsRate()) == 0 && getTitle().equals(movie.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getTitle(), getRate(), getCriticsRate());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + movieId +
                ", position=" + position +
                ", title='" + title +
                ", year=" + year +
                ", originalTitle='" + originalTitle +
                ", rate=" + rate +
                ", criticsRate=" + criticsRate +
                ", length='" + length +
                ", director='" + director +
                ", screenwriter='" + screenwriter +
                ", genre='" + genre +
                ", countryOfOrigin='" + countryOfOrigin +
                ", timeOfCreation=" + timeOfCreation +
                ", timeOfModification=" + timeOfModification +
                '}';
    }
}
