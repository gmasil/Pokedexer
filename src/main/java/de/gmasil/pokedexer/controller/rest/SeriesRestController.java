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

import org.springframework.beans.factory.annotation.Autowired;
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
import de.gmasil.pokedexer.jpa.SeriesService;
import de.gmasil.pokedexer.services.EntityMapper;

@RestController
@RequestMapping("/api/series")
public class SeriesRestController {

    @Autowired
    private SeriesService repo;

    @Autowired
    private EntityMapper mapper;

    @GetMapping("")
    public List<Series> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Series get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("series", "id", id));
    }

    @PostMapping("/")
    public Series add(@RequestBody SeriesDTO seriesDTO) {
        return repo.save(mapper.mapSeriesDTO(seriesDTO));
    }

    @PutMapping("/{id}")
    public Series put(@RequestBody SeriesDTO seriesDTO, @PathVariable Long id) {
        Series series = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("series", "id", id));
        return repo.save(mapper.patchSeries(seriesDTO, series));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("series", "id", id)));
    }
}
