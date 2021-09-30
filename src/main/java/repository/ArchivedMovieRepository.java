package repository;

import entity.ArchivedMovie;
import entity.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ArchivedMovieRepository {

    private static EntityManager em;

    public ArchivedMovieRepository(EntityManager em) {
        this.em = em;
    }

    public void addArchivedMovie(Movie movieData) {
        ArchivedMovie movie = movieData.getArchivedMovieObject();
        movie.setMovieId(movieData);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(movie);
        transaction.commit();
    }
}
