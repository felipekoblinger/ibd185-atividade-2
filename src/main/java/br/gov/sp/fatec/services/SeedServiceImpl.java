package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.models.Place;
import br.gov.sp.fatec.models.Review;
import br.gov.sp.fatec.repositories.PersonRepository;
import br.gov.sp.fatec.repositories.PlaceRepository;
import br.gov.sp.fatec.repositories.ReviewRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("seedService")
public class SeedServiceImpl implements SeedService {
    private final PersonRepository personRepository;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public SeedServiceImpl(PersonRepository personRepository,
                           PlaceRepository placeRepository,
                           ReviewRepository reviewRepository) {
        this.personRepository = personRepository;
        this.placeRepository = placeRepository;
        this.reviewRepository = reviewRepository;
    }

    public void populate() {
        /* Faker Instance */
        Faker faker = new Faker(new Locale("pt-BR"));
        
        /* Creating People: 20 */
        for (int i = 0; i < 20; i++) {
            Person person = new Person();
            person.setFullName(faker.gameOfThrones().character());
            person.setUsername(faker.name().username());
            person.setPassword(faker.lorem().fixedString(50));
            System.out.println("Person: " + person.getFullName());
            personRepository.save(person);
        }

        /* Creating Places */
        for (int i = 0; i < 50; i++) {
            Place place = new Place();
            place.setLocation(faker.harryPotter().location());
            place.setName(String.format("%s of %s %s %s",
                    faker.gameOfThrones().city(),
                    faker.harryPotter().character(),
                    faker.ancient().god(),
                    faker.ancient().primordial()
            ));
            System.out.println("Place: " + place.getName());
            placeRepository.save(place);
        }

        /* Creating Reviews */
        for (int i = 0; i < 100; i++) {
            Review review = new Review();
            review.setMessage(faker.harryPotter().quote());
            review.setScore(faker.number().numberBetween(0, 10));
            review.setPerson(personRepository.random());
            review.setPlace(placeRepository.random());
            System.out.println("Review: " + review.getMessage());
            reviewRepository.save(review);
        }
    }
}
