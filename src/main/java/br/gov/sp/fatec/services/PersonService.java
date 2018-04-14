package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Person;

import java.util.List;

public interface PersonService {
    void save(Person person);
    Person findByUsername(String username);
    Person findById(Long id);
    List<Person> findAll();
    List<Person> findAllFilters(String username, String fullName);
}