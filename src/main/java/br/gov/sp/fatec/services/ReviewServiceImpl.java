package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Review;
import br.gov.sp.fatec.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void save(Review review) {
        /* Trim to avoid leading and trailing spaces */
        review.setMessage(review.getMessage().trim());

        /* Minimum score is 0 and maximum is 10 */
        if (review.getScore() > 10) review.setScore(10);
        if (review.getScore() < 0) review.setScore(0);

        reviewRepository.save(review);
    }

    public List<Review> containingLocation(String location) {
        return reviewRepository.searchAllByPlaceLocation(location);
    }

}
