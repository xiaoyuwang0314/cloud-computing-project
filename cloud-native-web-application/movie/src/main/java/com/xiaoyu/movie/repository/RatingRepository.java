package com.xiaoyu.movie.repository;

import com.xiaoyu.movie.entity.Rating;
//import com.xiaoyu.movie.entity.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    // avg calculate
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.movieId = :movieId")
    Double findAverageRatingByMovieId(@Param("movieId") int movieId);
}
