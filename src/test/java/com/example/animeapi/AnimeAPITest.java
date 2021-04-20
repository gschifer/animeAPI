package com.example.animeapi;

import com.example.animeapi.domain.dto.AnimeDTO;
import com.example.animeapi.domain.entities.Anime;
import com.example.animeapi.services.AnimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnimeApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimeAPITest {

    @Autowired
    protected TestRestTemplate rest;
    private static final String URL = "http://localhost:8080/api/v1/animes/";

    private ResponseEntity<AnimeDTO> getAnime(String url) {
        return rest.withBasicAuth("user","123").getForEntity(url, AnimeDTO.class);
    }

    private ResponseEntity<List<AnimeDTO>> getAnimes(String url) {
        return rest.withBasicAuth("user","123").exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
    }

    @Test
    public void testList() {
        List<AnimeDTO> animes = getAnimes(URL).getBody();
        assertNotNull(animes);
        assertEquals(10, animes.size());
    }

    @Test
    public void testGet() {
        AnimeDTO anime = getAnime(URL + "1").getBody();
        assertNotNull(anime);
        assertEquals("Attack on Titan", anime.getName());
    }

    @Test
    public void testListByRating() {
        List<AnimeDTO> animes = getAnimes(URL + "/rating/90").getBody();
        assertEquals(3, animes.size());
        assertEquals(8, getAnimes(URL + "/rating/85").getBody().size());
        assertEquals(10, getAnimes(URL + "/rating/80").getBody().size());
    }

    @Test
    public void testGetOk() {
        assertEquals(HttpStatus.OK, getAnime(URL + "2").getStatusCode());
        assertEquals("Jujutsu Kaisen", getAnime(URL + "2").getBody().getName());
        assertEquals(24, getAnime(URL + "2").getBody().getEpisodes());
        assertEquals(87.9F, getAnime(URL + "2").getBody().getRating());

        assertEquals(HttpStatus.OK, getAnime(URL + "8").getStatusCode());
        assertEquals("Baki", getAnime(URL + "8").getBody().getName());
        assertEquals(52, getAnime(URL + "8").getBody().getEpisodes());
        assertEquals(82.91F, getAnime(URL + "8").getBody().getRating());
    }

    @Test
    public void testNotFound() {
        assertEquals(HttpStatus.NOT_FOUND, getAnime(URL + "89").getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getAnime(URL + "11").getStatusCode());

    }

    @Test
    public void testSave() {
        Anime anime = new Anime();
        anime.setName("One Punch Man");
        anime.setEpisodes(24);
        anime.setRating(100F);

        // Insert
        ResponseEntity response = rest.withBasicAuth("admin","123").
                postForEntity(URL, anime, null);
        System.out.println(response);

        // Check if it was created
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Search for the object
        String location = response.getHeaders().get("location").get(0);
        AnimeDTO an = getAnime(location).getBody();

        assertNotNull(an);
        assertEquals("One Punch Man", an.getName());
        assertEquals(24, an.getEpisodes());
        assertEquals(100F, an.getRating());

        // Delete the object
        rest.withBasicAuth("admin","123").delete(location);

        // Check if it was deleted
        assertEquals(HttpStatus.NOT_FOUND, getAnime(location).getStatusCode());
    }




}
