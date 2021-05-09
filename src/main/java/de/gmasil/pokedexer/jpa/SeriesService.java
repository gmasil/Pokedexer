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
package de.gmasil.pokedexer.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.gmasil.pokedexer.services.UserProvider;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private UserProvider userProvider;

    public Optional<Series> findById(Long id) {
        return seriesRepository.findByIdAndUser(id, userProvider.getCurrent());
    }

    public List<Series> findAll() {
        return seriesRepository.findAllByUser(userProvider.getCurrent());
    }

    public Series save(Series series) {
        series.setUser(userProvider.getCurrent());
        return seriesRepository.save(series);
    }

    public void delete(Series series) {
        seriesRepository.delete(series);
    }
}
