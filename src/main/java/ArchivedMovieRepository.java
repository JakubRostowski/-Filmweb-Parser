import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ArchivedMovieRepository {

    private static EntityManager em;

    public ArchivedMovieRepository(EntityManager em) {
        this.em = em;
    }

    public static void addArchivedMovie(ArchivedMovie movie) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(movie);
        transaction.commit();
    }
}
