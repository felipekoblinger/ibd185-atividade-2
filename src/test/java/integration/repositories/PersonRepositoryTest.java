package integration.repositories;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.repositories.PersonRepository;
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
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    /* Repository Create Delete Update Methods Tests */
    @Test
    public void testSave() {
        Person person = new Person();
        person.setUsername("joaodasneves");
        person.setFullName("João das Neves");
        person.setPassword("youknownothing");
        personRepository.save(person);
        assertNotNull("Person not persisted.", person.getId());
    }
    @Test
    public void testDelete() {
        Person person = personRepository.findById(1L).orElse(null);

        if (person == null) {
            fail("Person not found to delete in test case.");
        } else {
            personRepository.delete(person);
        }
        assertEquals("Person not deleted.",
                Optional.empty(),
                personRepository.findById(person.getId()));
    }
    @Test
    public void testUpdate() {
        Person person = personRepository.findById(1L).orElse(null);

        if (person == null) {
            fail("Person not found to delete in test case.");
        } else {
            person.setUsername("marionakaniko");
            personRepository.save(person);
        }
        assertEquals("Person username didn't update.",
                person.getUsername(),
                personRepository.findById(person.getId()).orElse(new Person()).getUsername());
    }

    /* Query Methods Tests */
    @Test
    public void testFindByUsername() {
        Person person = new Person();
        person.setUsername("joaodasneves");
        person.setFullName("João das Neves");
        person.setPassword("youknownothing");
        personRepository.save(person);

        Person personFind = personRepository.findByUsername(person.getUsername());
        assertNotNull("Person not found.", personFind);
    }

    @Test
    public void testFindAllByReviewsScoreIsGreaterThanEqual() {
        List<Person> people = personRepository.findAllByReviewsScoreIsGreaterThanEqual(4);
        assertTrue("People not found.", people.size() > 0);
    }

    @Test
    public void testFindAllBy() {
        List<Person> people = personRepository.findAll();
        assertTrue("People not found.", people.size() > 0);
    }

    /* @Query Methods */
    @Test
    public void testSearchByUsername() {
        Person person = personRepository.searchByUsername("   MARIOnAkANI   ");
        assertNotNull("Person not found.", person);
    }
    @Test
    public void testSearchAllWithReviews() {
        List<Person> people = personRepository.searchAllWithReviews();
        assertTrue("People not found.",people.size() > 0);
        assertTrue("People found but without review relationship.",people.get(0).getReviews().size() > 0);
    }
    @Test
    public void testRandom() {
        Person person = personRepository.random();
        assertNotNull("Person not found.", person);
    }
}
