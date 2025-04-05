package com.xiaoyu.movie.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    @Column(name = "movieId")
    private int movieId;

    @Column(name = "title")
    private String title;

    // connect genres
    @ManyToMany
    @JoinTable(
            name = "movies_genres",
            joinColumns = @JoinColumn(name = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "genreId")
    )
    private Set<Genre> genres;
}
