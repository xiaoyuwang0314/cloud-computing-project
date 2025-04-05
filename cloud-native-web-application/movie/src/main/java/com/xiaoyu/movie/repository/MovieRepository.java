package com.xiaoyu.movie.repository;

import com.xiaoyu.movie.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.genres WHERE m.movieId = :movieId")
    Optional<Movie> findMovieWithGenres(@Param("movieId") int movieId);
}
