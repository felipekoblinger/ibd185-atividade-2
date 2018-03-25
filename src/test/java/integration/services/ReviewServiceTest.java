package integration.services;

import br.gov.sp.fatec.models.Review;
import br.gov.sp.fatec.repositories.PersonRepository;
import br.gov.sp.fatec.repositories.PlaceRepository;
import br.gov.sp.fatec.services.ReviewService;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/all.xml")
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    public void testSave() {
        Review review = new Review();
        review.setMessage("Vários megazords excelentes! Recomendo!");
        review.setScore(11);
        review.setPerson(personRepository.findById(1L).orElse(null));
        review.setPlace(placeRepository.findById(1L).orElse(null));
        reviewService.save(review);

        assertNotNull("Review not saved.", review.getId());
        assertEquals("Review score above 10 not 10", 10, review.getScore());

        review.setScore(-10);
        reviewService.save(review);
        assertEquals("Review score below 0 not 0", 0, review.getScore());
    }

    @Test
    public void testContainingLocation() {
        List<Review> reviews = reviewService.containingLocation("Azkaban");
        assertTrue("Reviews not found.", reviews.size() > 0);
        assertEquals("Review location isn't the same.", "Azkaban", reviews.get(0).getPlace().getLocation());
    }
}



