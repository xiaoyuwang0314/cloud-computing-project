package com.xiaoyu.movie.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ratings")
@Data
public class Rating {

    @Id
    @Column(name = "movieId")
    private int movieId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "rating")
    private Double rating;

}
