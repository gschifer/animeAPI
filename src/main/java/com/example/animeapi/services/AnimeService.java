package com.example.animeapi.services;

import com.example.animeapi.domain.dto.AnimeDTO;
import com.example.animeapi.domain.entities.Anime;
import com.example.animeapi.exception.ObjectNotFoundException;
import com.example.animeapi.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimeService {
    @Autowired
    AnimeRepository repository;

    public List<AnimeDTO> getAnimes(Pageable pageable) {
        List<AnimeDTO> animes = repository.findAll(pageable).stream().map(AnimeDTO::create).collect(Collectors.toList());
        return animes;
    }

    public List<Anime> getAnimesAll(Pageable pageable) {
        return repository.findAll(pageable).toList();
    }

    public Optional<AnimeDTO> getAnime(long id) {
        Optional<AnimeDTO> anime = repository.findById(id).map(AnimeDTO::create);

        return Optional.ofNullable(anime.orElseThrow(
                () -> new ObjectNotFoundException("Anime with the ID " + id + " was not found."  )));
    }

    public List<AnimeDTO> getAnimesByRating(float rating, Pageable pageable) {
        List<AnimeDTO> animes = repository.findByRatingGreaterThanEqual(rating, pageable).stream().map(AnimeDTO::create).
                collect(Collectors.toList());

        return animes;
    }

    public List<AnimeDTO> getAnimesByCategory(String category, Pageable pageable) {
        List<AnimeDTO> animes = repository.findByCategoryContaining(category, pageable).stream().map(AnimeDTO::create).
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

    public void delete(long id) {
        repository.deleteById(id);
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
