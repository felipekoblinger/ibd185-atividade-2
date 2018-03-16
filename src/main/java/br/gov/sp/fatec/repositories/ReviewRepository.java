package br.gov.sp.fatec.repositories;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.models.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    /* ### Query Methods */
    List<Review> findAllByPersonUsernameContains(String username);

    /* ### @Query */
    @Query("SELECT r FROM Review r JOIN r.place p WHERE p.location LIKE %:placeLocation%")
    List<Review> searchAllByPlaceLocation(@Param("placeLocation") String placeLocation);
}
