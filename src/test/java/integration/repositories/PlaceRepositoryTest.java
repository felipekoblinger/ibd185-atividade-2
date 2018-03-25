package integration.repositories;

import br.gov.sp.fatec.models.Place;
import br.gov.sp.fatec.repositories.PlaceRepository;
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
public class PlaceRepositoryTest {
    @Autowired
    private PlaceRepository placeRepository;

    /* Repository Create Delete Update Methods Tests */
    @Test
    public void testSave() {
        Place place = new Place();
        place.setName("Alameda dos Anjos");
        place.setLocation("Califórnia");
        placeRepository.save(place);
        assertNotNull("Place not persisted.", place.getId());
    }
    @Test
    public void testDelete() {
        Place place = placeRepository.findById(1L).orElse(null);

        if (place == null) {
            fail("Place not found to delete in test case.");
        } else {
            placeRepository.delete(place);
        }
        assertEquals("Place not deleted.", Optional.empty(), placeRepository.findById(place.getId()));
    }
    @Test
    public void testUpdate() {
        Place place = placeRepository.findById(1L).orElse(null);

        if (place == null) {
            fail("Place not found to delete in test case.");
        } else {
            place.setName("Ilvermorny");
            placeRepository.save(place);
        }
        assertEquals("Place name didn't update.",
                place.getName(),
                placeRepository.findById(place.getId()).orElse(new Place()).getName());
    }

    /* Query Methods Tests */
    @Test
    public void testFindByName() {
        Place place = placeRepository.findByName("Myr of Norbert Hephaestus Aion");
        assertNotNull("Place not found.", place);
    }
    @Test
    public void testFindAllByReviewsPersonFullNameContaining() {
        List<Place> places = placeRepository.findAllByReviewsPersonFullNameContaining("Nakani");
        assertTrue("Places not found.", places.size() > 0);
        assertTrue("Reviews not found.", places.get(0).getReviews().size() > 0);
        assertNotNull("Person of review not found.", places.get(0).getReviews().iterator().next().getPerson());
    }
    /* @Query Methods */
    @Test
    public void testSearchByName() {
        Place place = placeRepository.searchByName("   myr of NORBERT hephaestus AION   ");
        assertNotNull("Place not found.", place);
    }
    @Test
    public void testSelectMostReviews() {
        List<Place> places = placeRepository.selectMostReviews();
        assertTrue("Places not found.", places.size() > 0);
    }
    @Test
    public void testCountReviews() {
        assertTrue("Wrong counting found.", placeRepository.countReviews(1L) > placeRepository.countReviews(2L));
    }
    @Test
    public void testRandom() {
        Place place = placeRepository.random();
        assertNotNull("Place not found.", place);
    }
}
