package com.xiaoyu.movie.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "genres")
public class Genre {

    @Id
    @Column(name = "genreId")
    private int genreId;

    @Column(name = "genre", nullable = false)
    private String genre;
}
