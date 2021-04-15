package com.example.animeapi;

import com.example.animeapi.domain.dto.AnimeDTO;
import com.example.animeapi.domain.entities.Anime;
import com.example.animeapi.services.AnimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Optional;

@SpringBootTest
class AnimesServiceTest {
    @Autowired
    private AnimeService service;

    @Test
    public void testCreate() {
        Anime anime = new Anime();
        anime.setName("Death Note");
        anime.setCategory("Drama");
        anime.setEpisodes(37);
        anime.setDescription("A story about a boy who find a book which it can kill people by writing the name of it.");
        anime.setRating(100.00F);
        AnimeDTO an = service.create(anime);

        Assert.notNull(an,"test failed");

        Long id = an.getId();
        assertNotNull(id);

        //Find the object
        Optional<AnimeDTO> op = service.getAnime(id);
        assertTrue(op.isPresent());

        an = op.get();

        assertEquals("Death Note", an.getName());
        assertEquals("Drama", an.getCategory());
        assertEquals(37, an.getEpisodes());
        assertEquals
                ("A story about a boy who find a book which it can kill people by writing the name of it.",
                an.getDescription());

        assertEquals(100.00F, an.getRating());

        //Delete the object
        service.delete(id);

        //Check if the object was deleted indeed
        assertFalse(service.getAnime(id).isPresent());

    }

    @Test
    public void testlist() {
        List<AnimeDTO> list = service.getAnimes();
        assertFalse(list.isEmpty());
        assertEquals(5, list.size());
    }

    @Test
    public void testGet() {
        for (int i = 1; i <= service.getAnimes().size(); i++) {
            assertTrue(service.getAnime(i).isPresent());
        }

        Optional<AnimeDTO> anime = service.getAnime(4);
        AnimeDTO an = anime.get();

        assertTrue(anime.isPresent());
        assertEquals("Berserk", an.getName());

    }
}
