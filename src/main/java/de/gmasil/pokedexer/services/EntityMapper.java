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

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.dto.SeriesDTO;
import de.gmasil.pokedexer.dto.UserDTO;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.Series;
import de.gmasil.pokedexer.jpa.User;

@Service
public class EntityMapper {
    private ModelMapper mapper = new ModelMapper();

    public EntityMapper() {
        mapper.getConfiguration() //
                .setFieldMatchingEnabled(true) //
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // user

    public User mapUserDTO(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }

    public List<User> mapUserDTO(List<UserDTO> userDTO) {
        return mapper.map(userDTO, new TypeToken<List<User>>() {
        }.getType());
    }

    public UserDTO mapUser(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public List<UserDTO> mapUser(List<User> user) {
        return mapper.map(user, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    public UserDTO patchUserDTO(User src, UserDTO dest) {
        mapper.map(src, dest);
        return dest;
    }

    public User patchUser(UserDTO src, User dest) {
        mapper.map(src, dest);
        return dest;
    }

    // card

    public Card mapCardDTO(CardDTO cardDTO) {
        return mapper.map(cardDTO, Card.class);
    }

    public List<Card> mapCardDTO(List<CardDTO> cardDTO) {
        return mapper.map(cardDTO, new TypeToken<List<Card>>() {
        }.getType());
    }

    public CardDTO mapCard(Card card) {
        return mapper.map(card, CardDTO.class);
    }

    public List<CardDTO> mapCard(List<Card> card) {
        return mapper.map(card, new TypeToken<List<CardDTO>>() {
        }.getType());
    }

    public CardDTO patchCardDTO(Card src, CardDTO dest) {
        mapper.map(src, dest);
        return dest;
    }

    public Card patchCard(CardDTO src, Card dest) {
        mapper.map(src, dest);
        return dest;
    }

    // series

    public Series mapSeriesDTO(SeriesDTO seriesDTO) {
        return mapper.map(seriesDTO, Series.class);
    }

    public List<Series> mapSeriesDTO(List<SeriesDTO> seriesDTO) {
        return mapper.map(seriesDTO, new TypeToken<List<Series>>() {
        }.getType());
    }

    public SeriesDTO mapSeries(Series series) {
        return mapper.map(series, SeriesDTO.class);
    }

    public List<SeriesDTO> mapSeries(List<Series> series) {
        return mapper.map(series, new TypeToken<List<SeriesDTO>>() {
        }.getType());
    }

    public SeriesDTO patchSeriesDTO(Series src, SeriesDTO dest) {
        mapper.map(src, dest);
        return dest;
    }

    public Series patchSeries(SeriesDTO src, Series dest) {
        mapper.map(src, dest);
        return dest;
    }
}
