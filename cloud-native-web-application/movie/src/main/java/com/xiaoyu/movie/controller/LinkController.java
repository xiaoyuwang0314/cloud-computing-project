package com.xiaoyu.movie.controller;

import com.xiaoyu.movie.entity.Link;
import com.xiaoyu.movie.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class LinkController {

    @Autowired
    private LinkService linkService;

    // Get movie links by movieId
    @GetMapping("/v1/link/{movieId}")
    public ResponseEntity<Map<String, Object>> getMovieLinks(@PathVariable int movieId) {
        Optional<Link> link = linkService.getLinksByMovieId(movieId);

        if (link.isPresent()) {
            // Return the movie links
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("movieId", link.get().getMovieId());
            response.put("imdbId", link.get().getImdbId().trim());
            response.put("tmdbId", link.get().getTmdbId().trim());

            return ResponseEntity.ok().body(response);
        } else {
            // Return error if no links are found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "No links found for this movie");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
}
