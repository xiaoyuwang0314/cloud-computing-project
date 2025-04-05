package com.xiaoyu.movie.controller;

import com.xiaoyu.movie.entity.Movie;
import com.xiaoyu.movie.entity.Genre;
import com.xiaoyu.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    // (PathVariable)
    @GetMapping("/v1/movie/{id}")
    public ResponseEntity<Map<String, Object>> getMovieById(@PathVariable int id) {
        return getMovieResponse(id);
    }

    // (RequestParam)
    @GetMapping("/v1/movie")
    public ResponseEntity<Map<String, Object>> getMovieByQuery(@RequestParam int id) {
        return getMovieResponse(id);
    }

    //get movie
    private ResponseEntity<Map<String, Object>> getMovieResponse(int id) {
        Optional<Movie> movie = movieService.getMovieById(id);

        if (movie.isPresent()) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("movieId", movie.get().getMovieId());
            response.put("title", movie.get().getTitle());
            response.put("genres", getGenreList(movie.get().getGenres()));
            return ResponseEntity.ok().body(response);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Movie not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // convert Set<Genre> to List<String>
    private List<String> getGenreList(Set<Genre> genres) {
        List<String> genreList = new ArrayList<>();
        for (Genre genre : genres) {
            genreList.add(genre.getGenre());
        }

        Collections.sort(genreList);
        return genreList;
    }
}
