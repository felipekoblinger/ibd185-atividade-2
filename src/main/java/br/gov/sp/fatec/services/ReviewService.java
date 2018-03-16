package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Review;

import java.util.List;

public interface ReviewService {
    void save(Review review);
    List<Review> containingLocation(String location);
}