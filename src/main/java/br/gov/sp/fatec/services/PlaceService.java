package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Place;

public interface PlaceService {
    void printMostReviews();
    void save(Place place);
    Place findByName(String name);
}