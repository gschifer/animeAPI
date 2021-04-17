package com.example.animeapi.controllers;

import com.example.animeapi.domain.dto.AnimeDTO;
import com.example.animeapi.domain.entities.Anime;
import com.example.animeapi.services.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/animes")
public class AnimeController {
    @Autowired
    AnimeService service;

    //GET
    @GetMapping
    public ResponseEntity get() {
        List<AnimeDTO> animes = service.getAnimes();

        return animes.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(animes);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {
        List<Anime> animes = service.getAnimesAll();

        return animes.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(animes);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnime(@PathVariable("id") long id) {
        Optional<AnimeDTO> anime = service.getAnime(id);

        return ResponseEntity.ok(anime);
    }

    @GetMapping("category/{category}")
    public ResponseEntity getAnimesByCategory(@PathVariable("category") String category) {
        List<AnimeDTO> list = service.getAnimesByCategory(category);

        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(list);
    }

    @GetMapping("name/{name}")
    public ResponseEntity getAnimesByNome(@PathVariable("name") String name) {
        List<AnimeDTO> list = service.findByName(name);

        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(list);
    }

    @GetMapping("rating/{rating}")
    public ResponseEntity getAnimesByRating(@PathVariable("rating") float rating) {
        List<AnimeDTO> animes = service.getAnimesByRating(rating);

        return animes.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(animes);
    }

    //POST
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity create(@RequestBody Anime anime) {
            AnimeDTO an = service.create(anime);
            URI location = getUri(an.getId());

            return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Anime anime, @PathVariable("id") long id) {
        AnimeDTO existingAnime = service.update(anime, id);

        return existingAnime != null ?
                ResponseEntity.ok(existingAnime) :
                ResponseEntity.notFound().build();
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }
}
