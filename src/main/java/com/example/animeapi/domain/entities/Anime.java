package com.example.animeapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;

    @Column(columnDefinition="LONGTEXT")
    private String description;
    private int episodes;
    private float rating;

    @JsonFormat(pattern = "dd/MM/YYYY")
    private Date releaseDate;

    private String finished;
    private String url_photo;
    private String url_video;

    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd/MM/YYYY HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
