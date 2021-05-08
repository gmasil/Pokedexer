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
package de.gmasil.pokedexer.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import de.gmasil.gherkin.extension.GherkinTest;
import de.gmasil.gherkin.extension.Reference;
import de.gmasil.gherkin.extension.Scenario;
import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.dto.SeriesDTO;
import de.gmasil.pokedexer.dto.UserDTO;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.Series;
import de.gmasil.pokedexer.jpa.User;

class EntityMapperTest extends GherkinTest {

    private EntityMapper entityMapper = new EntityMapper();

    // card

    @Scenario("The id of a CardDTO is not patched to Entity")
    void testCardIdDtoToEntity(Reference<CardDTO> cardDTO, Reference<Card> card) {
        given("a DTO with id 5 exists", () -> {
            cardDTO.set(CardDTO.builder().id(5L).name("DTO").build());
        });
        and("an entity with id 27 exists", () -> {
            card.set(Card.builder().id(27L).name("Entity").build());
        });
        when("the DTO is patched to the entity", () -> {
            entityMapper.patchCard(cardDTO.get(), card.get());
        });
        then("the id of the entity is still 27", () -> {
            assertThat(card.get().getId(), is(equalTo(27L)));
        });
    }

    // series

    @Scenario("The id of a SeriesDTO is not patched to Entity")
    void testSeriesIdDtoToEntity(Reference<SeriesDTO> seriesDTO, Reference<Series> series) {
        given("a DTO with id 5 exists", () -> {
            seriesDTO.set(SeriesDTO.builder().id(5L).name("DTO").build());
        });
        and("an entity with id 27 exists", () -> {
            series.set(Series.builder().id(27L).name("Entity").build());
        });
        when("the DTO is patched to the entity", () -> {
            entityMapper.patchSeries(seriesDTO.get(), series.get());
        });
        then("the id of the entity is still 27", () -> {
            assertThat(series.get().getId(), is(equalTo(27L)));
        });
    }

    // user

    @Scenario("The id of a UserDTO is not patched to Entity")
    void testUserIdDtoToEntity(Reference<UserDTO> userDTO, Reference<User> user) {
        given("a DTO with id 5 exists", () -> {
            userDTO.set(UserDTO.builder().id(5L).name("DTO").build());
        });
        and("an entity with id 27 exists", () -> {
            user.set(User.builder().id(27L).name("Entity").build());
        });
        when("the DTO is patched to the entity", () -> {
            entityMapper.patchUser(userDTO.get(), user.get());
        });
        then("the id of the entity is still 27", () -> {
            assertThat(user.get().getId(), is(equalTo(27L)));
        });
    }
}
