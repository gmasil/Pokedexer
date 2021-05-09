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

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.CardRepository;
import de.gmasil.pokedexer.jpa.Series;
import de.gmasil.pokedexer.jpa.SeriesRepository;
import de.gmasil.pokedexer.jpa.User;
import de.gmasil.pokedexer.jpa.UserService;
import de.gmasil.pokedexer.setup.InitialSetupRunner;

@Component
@Profile("dev")
public class TestDataCreator {

    @Autowired
    private InitialSetupRunner initialSetupRunner;

    @Autowired
    private UserService userService;

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private SeriesRepository seriesRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void createTestData() {
        initialSetupRunner.setupInitialUser();
        List<User> users = userService.findAll();
        if (users.size() == 1) {
            User initialUser = users.get(0);
            Series initialSeries = null;
            if (seriesRepo.count() == 0) {
                initialSeries = seriesRepo
                        .save(Series.builder().name("1998 P.M. Japanese Gym").user(initialUser).build());
                seriesRepo.save(Series.builder().name("2020 Pokemon SWSH").user(initialUser).build());
            }
            if (cardRepo.count() == 0) {
                cardRepo.save(Card.builder().name("Misty's Tears").purchasePrice(79.99D).user(initialUser).build());
                cardRepo.save(Card.builder().name("Misty's Gyarados").purchasePrice(62.0D).cardNumber(130)
                        .series(initialSeries).user(initialUser).build());
                cardRepo.save(Card.builder().name("Misty's Seadra").purchasePrice(83.34D).cardNumber(117)
                        .series(initialSeries).purchaseDate(LocalDate.of(2021, 04, 11)).certNumber(44728823L).grade(9)
                        .population(74).progress(5).user(initialUser).build());
            }
        }
    }
}
