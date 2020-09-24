public class Movie {
    private final String title;
    private final int year;
    private final String originalTitle;
    private final double rate;
    private final double criticsRate;
    private final String length;
    private final String director;
    private final String screenwriter;
    private final String genre;
    private final String countryOfOrigin;

    public Movie(String title, int year, String originalTitle, double rate, double criticsRate, String length, String director, String screenwriter, String genre, String countryOfOrigin) {
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

    public double getCriticsRate() {
        return criticsRate;
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

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", originalTitle='" + originalTitle + '\'' +
                ", rate=" + rate +
                ", criticsRate=" + criticsRate +
                ", length='" + length + '\'' +
                ", director='" + director + '\'' +
                ", screenwriter='" + screenwriter + '\'' +
                ", genre='" + genre + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                '}';
    }
}
