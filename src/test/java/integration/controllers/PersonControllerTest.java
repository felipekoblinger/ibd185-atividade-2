package integration.controllers;

import br.gov.sp.fatec.models.Person;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContextTest.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@Transactional
@DatabaseSetup("/datasets/all.xml")
public class PersonControllerTest {
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testCreate() throws Exception {
        Person person = new Person();
        person.setUsername("marciotoledo");
        person.setFullName("Márcio Toledo");
        person.setPassword("rand1090");
        String personJson = new Gson().toJson(person);
        mockMvc.perform(post("/people/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, containsString("/people/")))
        ;
    }
    @Test
    public void testDuplicate() throws Exception {
        /* Need to commit to test duplicated */
        TestTransaction.flagForCommit();
        TestTransaction.end();

        Person person = new Person();
        person.setUsername("marionakani");
        person.setFullName("Mario Nakani");
        person.setPassword("ma12345");
        String personJson = new Gson().toJson(person);

        mockMvc.perform(post("/people/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isConflict())
        ;
    }

    @Test
    public void testShowJson() throws Exception {
        mockMvc.perform(get("/people/1.json").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("marionakani")))
                .andExpect(jsonPath("$.fullName", is("Mario Nakani")))
        ;
    }
    @Test
    public void testShowJsonWithInvalid() throws Exception {
        mockMvc.perform(get("/people/1234123123124124.json").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void testShowXml() throws Exception {
        mockMvc.perform(get("/people/1.xml").accept(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<id>1</id>")))
                .andExpect(content().string(containsString("<username>marionakani</username>")))
                .andExpect(content().string(containsString("<fullName>Mario Nakani</fullName>")))
        ;
    }
    @Test
    public void testShowXmlWithInvalid() throws Exception {
        mockMvc.perform(get("/people/12341234123.xml").accept(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/people/").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThan(1)))
        ;
    }

    @Test
    public void testListWithFilters() throws Exception {
        mockMvc.perform(get("/people/?username=marionakani&fullName=Rodrik Flint").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
        ;
    }
    @Test
    public void testListWithWrongFilters() throws Exception {
        mockMvc.perform(get("/people/?username=wronguser&fullName=Wrong Name").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
        ;
    }

}
