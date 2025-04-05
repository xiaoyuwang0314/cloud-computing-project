package com.xiaoyu.movie.service;

import com.xiaoyu.movie.entity.Movie;
import com.xiaoyu.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Optional<Movie> getMovieById(int id) {
        return movieRepository.findMovieWithGenres(id);
    }
}
