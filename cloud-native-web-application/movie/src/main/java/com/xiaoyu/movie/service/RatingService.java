package com.xiaoyu.movie.service;

import com.xiaoyu.movie.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Double getAverageRating(int movieId) {
        return ratingRepository.findAverageRatingByMovieId(movieId);
    }
}

