package de.gmasil.pokedexer.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;

import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.dto.SeriesDTO;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.Series;

@Service
public class ModelConverter {
    private ModelMapper mapper = new ModelMapper();

    @PostConstruct
    private void configureModelMapper() {
        mapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
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
}
