package de.gmasil.collection.card;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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

import de.gmasil.collection.tesutils.EnableDatabaseCleanup;
import de.gmasil.collection.tesutils.RestTemplateFactory;

@EnableDatabaseCleanup
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CardRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateFactory factory;

    @Test
    void testCreate() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        Card card = new Card();
        card.setName("Misty's Tears");
        template.postForObject(url("/api/cards"), card, Card.class);
        List<Card> cards = getCards(template);
        assertThat(cards, hasSize(1));
        card = cards.get(0);
        assertThat(card.getName(), is(equalTo("Misty's Tears")));
    }

    @Test
    void testGetById() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card = template.postForObject(url("/api/cards"), card, Card.class);
        Long id = card.getId();
        assertThat(id, is(notNullValue()));
        // get card by id
        card = template.getForObject(url("/api/cards/" + id), Card.class);
        assertThat(card.getId(), is(equalTo(id)));
        assertThat(card.getName(), is(equalTo("Misty's Tears")));
    }

    @Test
    void testGetByIncorrectId() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // get card by id
        Long id = 3867245L;
        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            template.getForObject(url("/api/cards/" + id), Card.class);
        });
    }

    @Test
    void testUpdate() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card.setSetName("1998 P.M. Japanese Gym");
        card.setCardNumber(123);
        card = template.postForObject(url("/api/cards"), card, Card.class);
        Long id = card.getId();
        assertThat(id, is(notNullValue()));
        // update card
        card = new Card();
        card.setName("New Name");
        card.setSetName("New Set");
        template.put(url("/api/cards/" + id), card);
        card = template.getForObject(url("/api/cards/" + id), Card.class);
        assertThat(card.getId(), is(equalTo(id)));
        assertThat(card.getName(), is(equalTo("New Name")));
        assertThat(card.getSetName(), is(equalTo("New Set")));
        assertThat(card.getCardNumber(), is(equalTo(123)));
    }

    @Test
    void testDelete() {
        RestTemplate template = factory.getAuthenticatedRestTemplate();
        // create card
        Card card = new Card();
        card.setName("Misty's Tears");
        card = template.postForObject(url("/api/cards"), card, Card.class);
        Long id = card.getId();
        assertThat(id, is(notNullValue()));
        // delete card
        template.delete(url("/api/cards/" + id));
        // check
        List<Card> cards = getCards(template);
        assertThat(cards, hasSize(0));
    }

    // HELPER

    private List<Card> getCards(RestTemplate template) {
        ResponseEntity<List<Card>> responseEntity = template.exchange(url("/api/cards"), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Card>>() {
                });
        return responseEntity.getBody();
    }

    private String url(String uri) {
        return "http://localhost:" + port + uri;
    }
}
