package br.gov.sp.fatec.models.specifications;

import br.gov.sp.fatec.models.Person;
import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {
    public static Specification<Person> fullName(String fullName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("fullName"), fullName);
    }

    public static Specification<Person> username(String username) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("username"), username);
    }
}
