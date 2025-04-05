package com.xiaoyu.movie.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "links")
@Entity
public class Link {

    @Id
    @Column(name = "movieId")
    private int movieId;

    @Column(name = "imdbId")
    private String imdbId;

    @Column(name = "tmdbId")
    private String tmdbId;
}
