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
package de.gmasil.pokedexer.frontend;

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

import de.gmasil.pokedexer.frontend.advisor.Template;
import de.gmasil.pokedexer.series.Series;
import de.gmasil.pokedexer.series.SeriesRepository;

@Controller
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping("")
    public String index(Template template) {
        List<Series> series = seriesRepository.findAll();
        return template.makeSeriesList(series);
    }

    @GetMapping("/add")
    public String showForm(Template template, Series series) {
        return template.makeSeriesAdd();
    }

    @PostMapping("/add")
    public String addSeries(Template template, @Valid Series series, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template.makeSeriesAdd();
        }
        try {
            seriesRepository.save(series);
        } catch (DataIntegrityViolationException e) {
            return template.makeSeriesAdd(true);
        }
        return "redirect:";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(Template template, @PathVariable("id") long id) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid series id:" + id));
        return template.makeSeriesEdit(series);
    }

    @PostMapping("/edit/{id}")
    public String updateSeries(Template template, @PathVariable("id") long id, @Valid Series series,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template.makeSeriesEdit(series);
        }
        try {
            seriesRepository.save(series);
        } catch (DataIntegrityViolationException e) {
            return template.makeSeriesEdit(series, true);
        }
        return "redirect:/series";
    }

    @GetMapping("/delete/{id}")
    public String deleteSeriesConfirm(Template template, @PathVariable("id") long id, Model model) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid series id:" + id));
        return template.makeSeriesDeleteConfirm(series);
    }

    @PostMapping("/delete/{id}")
    public String deleteSeries(Template template, @PathVariable("id") long id, Model model) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid series id:" + id));
        seriesRepository.delete(series);
        return "redirect:/series";
    }
}
