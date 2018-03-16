package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Person;
import br.gov.sp.fatec.models.Place;
import br.gov.sp.fatec.repositories.PersonRepository;
import br.gov.sp.fatec.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("placeService")
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Transactional
    public void save(Place place) {
        /* Trim to avoid leading and trailing spaces */
        place.setName(place.getName().trim());
        place.setLocation(place.getLocation().trim());
        placeRepository.save(place);
    }

    public Place findByName(String name) {
        return placeRepository.findByName(name);
    }

    public void printMostReviews() {
        /* Trim to avoid leading and trailing spaces */
        List<Place> places = placeRepository.selectMostReviews();
        System.out.println("= Listing the most reviewed places =");
        for (Place place : places) {
            int countReviews = placeRepository.countReviews(place.getId());
            System.out.println(String.format("[%d] Place: %s", countReviews, place.getName()));
        }
    }
}
