package br.gov.sp.fatec.repositories;

import br.gov.sp.fatec.models.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    /* Query Method: find by username exactly */
    Person findByUsername(String username);

    /* (*) Query Method: show all people with reviews */
    List<Person> findAllByReviewsScoreIsGreaterThanEqual(int score);

    /* @Query: search username with case-insensitive and ignoring leading/trailing spaces */
    @Query("SELECT p FROM Person p WHERE TRIM(LOWER(p.username)) = TRIM(LOWER(:username))")
    Person searchByUsername(@Param("username") String username);

    /* (*) @Query: search all people with at least one review */
    @Query("SELECT DISTINCT(p) FROM Person p JOIN p.reviews r WHERE p.id = r.personId")
    List<Person> searchAllWithReviews();

    /* @NativeQuery: random people */
    @Query(nativeQuery = true, value = "SELECT * FROM people ORDER BY random() LIMIT 1")
    Person random();
}
