package integration.repositories;

import br.gov.sp.fatec.models.Review;
import br.gov.sp.fatec.repositories.PersonRepository;
import br.gov.sp.fatec.repositories.PlaceRepository;
import br.gov.sp.fatec.repositories.ReviewRepository;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/all.xml")
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PlaceRepository placeRepository;

    /* Repository Create Delete Update Methods Tests */
    @Test
    public void testSave() {
        Review review = new Review();
        review.setMessage("Vários megazords excelentes! Recomendo!");
        review.setScore(10);
        review.setPerson(personRepository.findById(1L).orElse(null));
        review.setPlace(placeRepository.findById(1L).orElse(null));
        reviewRepository.save(review);
        assertNotNull("Review not saved.", review.getId());
    }
    @Test
    public void testDelete() {
        Review review = reviewRepository.findById(1L).orElse(null);

        if (review == null) {
            fail("Review not found to delete in test case.");
        } else {
            reviewRepository.delete(review);
        }

        assertEquals("Review not deleted.",
                Optional.empty(),
                reviewRepository.findById(review.getId()));
    }
    @Test
    public void testUpdate() {
        Review review = reviewRepository.findById(1L).orElse(null);

        if (review == null) {
            fail("Review not found to delete in test case.");
        } else {
            review.setMessage("You're a wizard, Harry.");
            reviewRepository.save(review);
        }
        assertEquals("Review message didn't update.",
                review.getMessage(),
                reviewRepository.findById(review.getId()).orElse(new Review()).getMessage());
    }

    /* Query Methods Tests */
    @Test
    public void testFindAllByPersonUsernameContains() {
        List<Review> reviews = reviewRepository.findAllByPersonUsernameContains("rodrikflint");
        assertTrue("Reviews not found.", reviews.size() > 0);
    }

    /* @Query Methods */
    @Test
    public void testSearchAllByPlaceLocation() {
        List<Review> reviews = reviewRepository.searchAllByPlaceLocation("Azkaban");
        assertTrue("Reviews not found.", reviews.size() > 0);
        assertEquals("Location isn't the same.", reviews.get(0).getPlace().getLocation(), "Azkaban");
    }
}
