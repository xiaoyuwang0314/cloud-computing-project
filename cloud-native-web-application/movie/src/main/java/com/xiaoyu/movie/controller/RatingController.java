package com.xiaoyu.movie.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaoyu.movie.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Get the average rating for a given movieId
    @GetMapping("/v1/rating/{movieId}")
    public ResponseEntity<?> getAverageRating(@PathVariable int movieId) {
        // Fetch the average rating
        Double averageRating = ratingService.getAverageRating(movieId);

        // If ratings are found, return a 200 OK response with rating information
        if (averageRating != null) {
            // Return movie id and average rating
            return ResponseEntity.ok().body(new RatingResponse(movieId, averageRating));
        } else {
            // If no ratings found, return a 404 Not Found response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "No ratings found for this movie");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // Inner class: Encapsulates the response data
    @lombok.Data
    public static class RatingResponse {
        private int movieId;
        @JsonProperty("average_rating")
        private Double averageRating;

        public RatingResponse(int movieId, Double averageRating) {
            this.movieId = movieId;
            this.averageRating = averageRating;
        }
    }
}