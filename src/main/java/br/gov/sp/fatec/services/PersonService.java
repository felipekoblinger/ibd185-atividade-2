package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Person;

public interface PersonService {
    void save(Person person);
    Person findByUsername(String username);
}