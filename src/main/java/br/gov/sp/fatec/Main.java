package br.gov.sp.fatec;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.models.Place;
import br.gov.sp.fatec.models.Review;
import br.gov.sp.fatec.repositories.PersonRepository;
import br.gov.sp.fatec.repositories.PlaceRepository;
import br.gov.sp.fatec.repositories.ReviewRepository;
import br.gov.sp.fatec.services.PersonService;
import br.gov.sp.fatec.services.PlaceService;
import br.gov.sp.fatec.services.ReviewService;
import br.gov.sp.fatec.services.SeedService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /* Loading Spring Context */
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");

        /* Run just once - can cause duplicated error */
//        SeedService seedService = (SeedService) context.getBean("seedService");
//        seedService.populate();

        PersonService personService = (PersonService) context.getBean("personService");
        /* Create a Person */
        Person person = new Person();
        person.setUsername(" joaodasneves ");
        person.setFullName(" João das Neves ");
        person.setPassword("youknownothing");

        /* Saving Person */
        try {
            personService.save(person);
        } catch (DataIntegrityViolationException e) {
            System.out.println(String.format("Warning: username %s is already in database, try another username.", person.getUsername()));
        }

        PlaceService placeService = (PlaceService) context.getBean("placeService");
        /* Creating a Place */
        Place place = new Place();
        place.setName("Alameda dos Anjos");
        place.setLocation("Califórnia");

        /* Saving Place */
        try {
            placeService.save(place);
        } catch (DataIntegrityViolationException e) {
            System.out.println(String.format("Warning: name %s is already in database, try another name.", place.getName()));
        }

        /* Print places with most reviews */
        placeService.printMostReviews();

        ReviewService reviewService = (ReviewService) context.getBean("reviewService");
        /* Creating a Review */
        Review review = new Review();
        review.setMessage("Vários megazords excelentes! Recomendo!");
        review.setScore(10);
        review.setPerson(personService.findByUsername(person.getUsername()));
        review.setPlace(placeService.findByName(place.getName()));

        /* Saving Review */
        reviewService.save(review);

        /* All Reviews of place location containing: Azkaban */
        List<Review> azkabanReviews = reviewService.containingLocation("Azkaban");
        for(Review azkabanReview : azkabanReviews) {
            System.out.println(String.format("Person: %s", azkabanReview.getPerson().getFullName()));
            System.out.println(String.format("Score: %d | Review Message: %s", azkabanReview.getScore(), azkabanReview.getMessage()));
        }

    }
}
