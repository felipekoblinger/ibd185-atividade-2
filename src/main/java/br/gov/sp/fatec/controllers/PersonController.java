package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.services.PersonService;
import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/people/")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(View.Common.class)
    public ResponseEntity<Person> create(@RequestBody Person person, HttpServletRequest request,
                                         HttpServletResponse response) {
        /*  TODO:
            1. [OK] JSON only
            2. [OK] Status 201 CREATED must be RETURNED
            3. [OK] Header with Location for show
            4. [OK] In case of error, a STATUS should be RETURNED
         */
        personService.save(person);

        response.addHeader("Location", String.format("%s:%s%s/people/%d",
                request.getServerName(),
                request.getServerPort(),
                request.getContextPath(),
                person.getId()));
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @JsonView(View.Common.class)
    public ResponseEntity<Person> show(@PathVariable Long id) {
        /* TODO:
            1. Returning JSON or XML
            2. Use JSON View to not show ID
         */
        Person person = personService.findById(id);
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @JsonView(View.CommonWithId.class)
    public ResponseEntity<List<Person>> list(@RequestParam(value="username", required=false) String username,
                                             @RequestParam(value="fullName", required=false) String fullName) {
        /* TODO:
            1. Return more than one result
            2. One or more search params
            3. JSON View to not show some attributes. *** ID MUST BE SERIALIZED ***
         */
        List<Person> people;
        if (username != null || fullName != null) {
            people = personService.findAllFilters(username, fullName);
        } else {
            people = personService.findAll();
        }
        if (people == null || people.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(people, HttpStatus.OK);
    }
}
