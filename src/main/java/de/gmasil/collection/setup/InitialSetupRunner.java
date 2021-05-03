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
 * along with Pokédexer.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.collection.setup;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.gmasil.collection.card.Card;
import de.gmasil.collection.card.CardRepository;
import de.gmasil.collection.security.User;
import de.gmasil.collection.security.UserRepository;
import de.gmasil.collection.security.UserService;
import de.gmasil.collection.series.Series;
import de.gmasil.collection.series.SeriesRepository;

@Component
public class InitialSetupRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${setup.user:false}")
    private boolean setupUser;

    @Value("${setup.user.name:#{null}}")
    private String username;

    @Value("${setup.user.password:#{null}}")
    private String password;

    @EventListener(ApplicationReadyEvent.class)
    public void setupInitialUser() {
        if (setupUser && userRepository.count() == 0 && username != null && password != null) {
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            userService.encodePassword(user);
            userService.save(user);
        }
    }

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private SeriesRepository seriesRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void createTestData() {
        Series initialSeries = null;
        if (seriesRepo.count() == 0) {
            Series series = new Series();
            series.setName("1998 P.M. Japanese Gym");
            initialSeries = seriesRepo.save(series);
            series = new Series();
            series.setName("2020 Pokemon SWSH");
            seriesRepo.save(series);
        }
        if (cardRepo.count() == 0) {
            Card card = new Card();
            card.setName("Misty's Tears");
            card.setPurchasePrice(79.99D);
            cardRepo.save(card);
            card = new Card();
            card.setName("Misty's Gyarados");
            card.setPurchasePrice(62.0D);
            card.setCardNumber(130);
            card.setSeries(initialSeries);
            cardRepo.save(card);
            card = new Card();
            card.setName("Misty's Seadra");
            card.setPurchasePrice(83.34D);
            card.setCardNumber(117);
            card.setSeries(initialSeries);
            card.setPurchaseDate(LocalDate.of(2021, 04, 11));
            card.setSeries(initialSeries);
            card.setCertNumber(44728823L);
            card.setGrade(9);
            card.setPopulation(74);
            card.setProgress(5);
            cardRepo.save(card);
        }
    }
}
