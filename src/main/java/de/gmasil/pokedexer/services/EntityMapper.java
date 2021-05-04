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

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;

import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.dto.SeriesDTO;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.Series;

@Service
public class EntityMapper {
    private ModelMapper mapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {
        mapper.getConfiguration() //
                .setFieldMatchingEnabled(true) //
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        // do not set ID of entity when mapping data from DTO to DAO
        mapper.addMappings(new PropertyMap<SeriesDTO, Series>() {
            @Override
            protected void configure() {
                skip().setId(source.getId());
            }
        });
        mapper.addMappings(new PropertyMap<CardDTO, Card>() {
            @Override
            protected void configure() {
                skip().setId(source.getId());
            }
        });
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

    public CardDTO patchCard(Card src, CardDTO dest) {
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

    public SeriesDTO patchSeries(Series src, SeriesDTO dest) {
        mapper.map(src, dest);
        return dest;
    }

    public Series patchSeries(SeriesDTO src, Series dest) {
        mapper.map(src, dest);
        return dest;
    }
}
