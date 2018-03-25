package integration.services;

import br.gov.sp.fatec.models.Place;
import br.gov.sp.fatec.services.PlaceService;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/all.xml")
public class PlaceServiceTest {
    @Autowired
    private PlaceService placeService;

    @Test
    public void testSave() {
        Place place = new Place();
        place.setName("  Alameda dos Anjos  ");
        place.setLocation("  Califórnia  ");
        placeService.save(place);

        assertNotNull("Place not persisted.", place.getId());
        assertEquals("Place name not trimmed.", "Alameda dos Anjos", place.getName());
        assertEquals("Place location not trimmed.", "Califórnia", place.getLocation());
    }

    @Test
    public void testFindByUsername() {
        Place place = placeService.findByName("Bhorash of Enid Zeus Phanes");
        assertNotNull("Place not found.", place);
    }
}



