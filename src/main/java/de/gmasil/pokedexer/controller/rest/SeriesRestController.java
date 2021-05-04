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
package de.gmasil.pokedexer.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.gmasil.pokedexer.dto.SeriesDTO;
import de.gmasil.pokedexer.exception.ResourceNotFoundException;
import de.gmasil.pokedexer.jpa.Series;
import de.gmasil.pokedexer.jpa.SeriesRepository;
import de.gmasil.pokedexer.services.EntityMapper;

@RestController
@RequestMapping("/api/series")
public class SeriesRestController {
    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping("")
    public List<SeriesDTO> getAll() {
        return entityMapper.mapSeries(seriesRepository.findAll());
    }

    @PostMapping("")
    public SeriesDTO create(@Valid @RequestBody SeriesDTO seriesDTO) {
        Series series = entityMapper.mapSeriesDTO(seriesDTO);
        series = seriesRepository.save(series);
        return entityMapper.mapSeries(series);
    }

    @GetMapping("/{id}")
    public SeriesDTO getById(@PathVariable(value = "id") Long seriesId) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series", "id", seriesId));
        return entityMapper.mapSeries(series);
    }

    @PutMapping("/{id}")
    public SeriesDTO update(@PathVariable(value = "id") Long seriesId, @Valid @RequestBody SeriesDTO seriesDTO) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series", "id", seriesId));
        entityMapper.patchSeries(seriesDTO, series);
        series = seriesRepository.save(series);
        return entityMapper.mapSeries(series);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long seriesId) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series", "id", seriesId));
        seriesRepository.delete(series);
        return ResponseEntity.ok().build();
    }
}
