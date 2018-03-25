package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("personService")
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void save(Person person) {
        /* Trim to avoid leading and trailing spaces */
        person.setFullName(person.getFullName().trim());
        person.setUsername(person.getUsername().trim().toLowerCase());
        personRepository.save(person);
    }

    public Person findByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}
