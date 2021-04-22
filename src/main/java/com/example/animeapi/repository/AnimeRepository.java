package com.example.animeapi.repository;

import com.example.animeapi.domain.entities.Anime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByCategoryContaining(String category, Pageable pageable);

    List<Anime> findByNameContaining(String name);

    List<Anime> findByRatingGreaterThanEqual(float rating, Pageable pageable);
}
