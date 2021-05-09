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
package de.gmasil.pokedexer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.gmasil.pokedexer.controller.advisor.Template;
import de.gmasil.pokedexer.dto.SeriesDTO;
import de.gmasil.pokedexer.exception.ResourceNotFoundException;
import de.gmasil.pokedexer.jpa.Series;
import de.gmasil.pokedexer.jpa.SeriesService;
import de.gmasil.pokedexer.services.EntityMapper;

@Controller
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping("")
    public String index(Template template) {
        List<Series> series = seriesService.findAll();
        return template.makeSeriesList(entityMapper.mapSeries(series));
    }

    @GetMapping("/add")
    public String showForm(Template template, SeriesDTO seriesDTO) {
        return template.makeSeriesAdd();
    }

    @PostMapping("/add")
    public String addSeries(Template template, @Valid SeriesDTO seriesDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template.makeSeriesAdd();
        }
        try {
            seriesService.save(entityMapper.mapSeriesDTO(seriesDTO));
        } catch (DataIntegrityViolationException e) {
            return template.makeSeriesAdd(true);
        }
        return "redirect:";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(Template template, @PathVariable("id") long id) {
        Series series = findSeriesById(id);
        return template.makeSeriesEdit(entityMapper.mapSeries(series));
    }

    @PostMapping("/edit/{id}")
    public String updateSeries(Template template, @PathVariable("id") long id, @Valid SeriesDTO seriesDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template.makeSeriesEdit(seriesDTO);
        }
        Series series = findSeriesById(id);
        entityMapper.patchSeries(seriesDTO, series);
        try {
            seriesService.save(series);
        } catch (DataIntegrityViolationException e) {
            return template.makeSeriesEdit(seriesDTO, true);
        }
        return "redirect:/series";
    }

    @GetMapping("/delete/{id}")
    public String deleteSeriesConfirm(Template template, @PathVariable("id") long id, Model model) {
        Series series = findSeriesById(id);
        return template.makeSeriesDeleteConfirm(entityMapper.mapSeries(series));
    }

    @PostMapping("/delete/{id}")
    public String deleteSeries(Template template, @PathVariable("id") long id, Model model) {
        Series series = findSeriesById(id);
        seriesService.delete(series);
        return "redirect:/series";
    }

    private Series findSeriesById(long id) {
        return seriesService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Series", "id", id));
    }
}
