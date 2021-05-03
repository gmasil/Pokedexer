/**
 * Pokédexer
 * Copyright © 2021 Gmasil
 *
 * This file is part of Pokédexer.
 *
 * Pokédexer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pokédexer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pokédexer. If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.pokedexer.card;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import de.gmasil.pokedexer.card.Card;
import de.gmasil.pokedexer.series.Series;
import de.gmasil.pokedexer.tesutils.EnableDatabaseCleanup;
import de.gmasil.pokedexer.tesutils.RestTemplateFactory;

@EnableDatabaseCleanup
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CardRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateFactory factory;

    @Test
    void testCreateWithoutSeries() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        Card card = new Card();
        card.setName("Misty's Tears");
        template.postForObject(url("/api/card"), card, Card.class);
        List<Card> cards = getCards(template);
        assertThat(cards, hasSize(1));
        card = cards.get(0);
        assertThat(card.getName(), is(equalTo("Misty's Tears")));
        assertThat(card.getSeries(), is(nullValue()));
    }

    @Test
    void testCreateWithSeries() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create series
        Series series = new Series();
        series.setName("Gym");
        series = template.postForObject(url("/api/series"), series, Series.class);
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card.setSeries(series);
        template.postForObject(url("/api/card"), card, Card.class);
        List<Card> cards = getCards(template);
        assertThat(cards, hasSize(1));
        card = cards.get(0);
        assertThat(card.getName(), is(equalTo("Misty's Tears")));
        assertThat(card.getSeries().getName(), is(equalTo("Gym")));
    }

    @Test
    void testGetById() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card = template.postForObject(url("/api/card"), card, Card.class);
        Long id = card.getId();
        assertThat(id, is(notNullValue()));
        // get card by id
        card = template.getForObject(url("/api/card/" + id), Card.class);
        assertThat(card.getId(), is(equalTo(id)));
        assertThat(card.getName(), is(equalTo("Misty's Tears")));
    }

    @Test
    void testGetByIncorrectId() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // get card by id
        Long id = 3867245L;
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            template.getForObject(url("/api/card/" + id), Card.class);
        });
    }

    @Test
    void testUpdateWithoutSeries() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card.setStatus("home");
        card.setCardNumber(123);
        card = template.postForObject(url("/api/card"), card, Card.class);
        Long id = card.getId();
        assertThat(id, is(notNullValue()));
        // update card
        card = new Card();
        card.setName("New Name");
        card.setStatus("PSA");
        template.put(url("/api/card/" + id), card);
        card = template.getForObject(url("/api/card/" + id), Card.class);
        assertThat(card.getId(), is(equalTo(id)));
        assertThat(card.getName(), is(equalTo("New Name")));
        assertThat(card.getStatus(), is(equalTo("PSA")));
        assertThat(card.getCardNumber(), is(equalTo(123)));
        assertThat(card.getSeries(), is(nullValue()));
    }

    @Test
    void testUpdateWithSeries() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create series
        Series series = new Series();
        series.setName("Gym");
        series = template.postForObject(url("/api/series"), series, Series.class);
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card.setStatus("home");
        card.setCardNumber(123);
        card = template.postForObject(url("/api/card"), card, Card.class);
        Long id = card.getId();
        assertThat(id, is(notNullValue()));
        // update card
        card = new Card();
        card.setName("New Name");
        card.setStatus("PSA");
        card.setSeries(series);
        template.put(url("/api/card/" + id), card);
        card = template.getForObject(url("/api/card/" + id), Card.class);
        assertThat(card.getId(), is(equalTo(id)));
        assertThat(card.getName(), is(equalTo("New Name")));
        assertThat(card.getStatus(), is(equalTo("PSA")));
        assertThat(card.getCardNumber(), is(equalTo(123)));
        assertThat(card.getSeries().getName(), is(equalTo("Gym")));
    }

    @Test
    void testDelete() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card = template.postForObject(url("/api/card"), card, Card.class);
        Long id = card.getId();
        assertThat(id, is(notNullValue()));
        // delete card
        template.delete(url("/api/card/" + id));
        // check
        List<Card> cards = getCards(template);
        assertThat(cards, hasSize(0));
    }

    // HELPER

    private List<Card> getCards(RestTemplate template) {
        ResponseEntity<List<Card>> responseEntity = template.exchange(url("/api/card"), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Card>>() {
                });
        return responseEntity.getBody();
    }

    private String url(String uri) {
        return "http://localhost:" + port + uri;
    }
}
