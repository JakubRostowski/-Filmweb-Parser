import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "archived_movie")
public class ArchivedMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private int position;
    private String title;
    private double rate;
    private double criticsRate;
    private Timestamp timeOfCreation;
    private Timestamp timeOfModification;

    public ArchivedMovie(int position, String title, double rate, double criticsRate, Timestamp timeOfCreation) {
        this.position = position;
        this.title = title;
        this.rate = rate;
        this.criticsRate = criticsRate;
        this.timeOfCreation = timeOfCreation;
        this.timeOfModification = new Timestamp(System.currentTimeMillis());
    }

    public ArchivedMovie() {

    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", position=" + position +
                ", title='" + title +
                ", rate=" + rate +
                ", criticsRate=" + criticsRate +
                ", timeOfCreation=" + timeOfCreation +
                ", timeOfModification=" + timeOfModification +
                '}';
    }
}