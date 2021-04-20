package com.example.animeapi.domain.dto;

import com.example.animeapi.domain.entities.Anime;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class AnimeDTO {
    private Long id;
    private String name;
    private String category;
    private String description;
    private int episodes;
    private float rating;

    public static AnimeDTO create(Anime anime) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(anime, AnimeDTO.class);
    }
}
