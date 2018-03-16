package br.gov.sp.fatec.repositories;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.models.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends CrudRepository<Place, Long> {
    /* Query Method: find a place name */
    Place findByName(String name);

    /* ### Query Method: find places with reviews containing name */
    List<Place> findAllByReviewsPersonFullNameContaining(String fullName);

    /* @Query: search names by name in lower case/trimmed */
    @Query("SELECT p FROM Place p WHERE TRIM(LOWER(p.name)) = TRIM(LOWER(:name))")
    Place searchByName(@Param("name") String name);

    /* ### @Query: select places with most reviews */
    @Query("SELECT p FROM Place p JOIN p.reviews r GROUP BY p ORDER BY COUNT(p) DESC")
    List<Place> selectMostReviews();

    /* @Query: Quantity of reviews of a place */
    @Query("SELECT COUNT(p) FROM Place p JOIN p.reviews r WHERE r.placeId = :id")
    int countReviews(@Param("id") Long id);

    /* @NativeQuery: random place */
    @Query(nativeQuery = true, value = "SELECT * FROM places ORDER BY random() LIMIT 1")
    Place random();
}