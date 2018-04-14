package integration.services;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.services.PersonService;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import junit.framework.TestCase;
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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContextTest.xml" })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/datasets/all.xml")
public class PersonServiceTest {
    @Autowired
    private PersonService personService;

    @Test
    public void testSave() {
        Person person = new Person();
        person.setUsername(" joaodasneveS ");
        person.setFullName(" João das Neves ");
        person.setPassword("youknownothing");
        personService.save(person);
        assertNotNull("Person not saved.", person.getId());
        assertEquals("Person username not trimmed or lowered case.", "joaodasneves", person.getUsername());
        assertEquals("Person full name not trimmed.", "João das Neves", person.getFullName());
    }

    @Test
    public void testFindByUsername() {
        Person personFind = personService.findByUsername("marionakani");
        assertNotNull("Person not found.", personFind);
    }

    @Test
    public void testFindAll() {
        List<Person> people = personService.findAll();
        assertTrue("People not found.", people.size() > 0);
    }

    @Test
    public void testFindAllFilters() {
        List<Person> people = personService.findAllFilters("marionakani", "Jeyne Waters");
        assertEquals("Must return 2 people", 2, people.size());
    }
}



