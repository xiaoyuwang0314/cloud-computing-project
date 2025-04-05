package com.xiaoyu.movie.service;

import com.xiaoyu.movie.entity.Link;
import com.xiaoyu.movie.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    // Get links for a movie by movieId
    public Optional<Link> getLinksByMovieId(int movieId) {
        return linkRepository.findById(movieId);
    }
}
