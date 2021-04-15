package com.example.animeapi.services;

import com.example.animeapi.domain.dto.AnimeDTO;
import com.example.animeapi.domain.entities.Anime;
import com.example.animeapi.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeService {
    @Autowired
    AnimeRepository repository;

    public List<AnimeDTO> getAnimes() {
        List<AnimeDTO> animes = repository.findAll().stream().map(AnimeDTO::create).collect(Collectors.toList());
        return animes;
    }

    public List<Anime> getAnimesAll() {
        return repository.findAll();
    }

    public Optional<AnimeDTO> getAnime(long id) {
        Optional<AnimeDTO> anime = repository.findById(id).map(AnimeDTO::create);

        return anime;
    }

    public List<AnimeDTO> getAnimesByRating(float rating) {
        List<AnimeDTO> animes = repository.findByRatingGreaterThanEqual(rating).stream().map(AnimeDTO::create).
                collect(Collectors.toList());

        return animes;
    }

    public List<AnimeDTO> getAnimesByCategory(String category) {
        List<AnimeDTO> animes = repository.findByCategoryContaining(category).stream().map(AnimeDTO::create).
                collect(Collectors.toList());

        return animes;
    }

    public AnimeDTO create(Anime anime) {
        Assert.isNull(anime.getId(), "ID must not be passed.");

        return AnimeDTO.create(repository.save(anime));
    }

    public AnimeDTO update(Anime anime, long id) {
        Optional<Anime> animeExist = repository.findById(id);

        if (animeExist.isPresent()) {
            Anime an = animeExist.get();
            an.setName(anime.getName());
            an.setReleaseDate(anime.getReleaseDate());
            an.setEpisodes(anime.getEpisodes());
            an.setCategory(anime.getCategory());
            an.setFinished(anime.getFinished());
            an.setRating(anime.getRating());
            an.setDescription(anime.getDescription());
            an.setUrl_photo(anime.getUrl_photo());
            an.setUrl_video(anime.getUrl_video());

            return AnimeDTO.create(repository.save(an));
        }
        return null;
    }

    public Optional<AnimeDTO> delete(long id) {
        Optional<AnimeDTO> anime = getAnime(id);

        if (anime.isPresent()) {
            repository.deleteById(id);
        }

        return anime;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<AnimeDTO> findByName(String name) {
        List<AnimeDTO> anime = repository.findByNameContaining(name).stream().map(AnimeDTO::create).
                collect(Collectors.toList());

        return anime;
    }
}
