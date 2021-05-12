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

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.gmasil.pokedexer.jpa.Language;
import de.gmasil.pokedexer.jpa.LanguageRepository;

@Component
public class MasterDataCreator {

    @Value("${masterdata.languages}")
    private String languages;

    @Autowired
    private LanguageRepository languageRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createMasterData() {
        Arrays.asList(languages.split(",")).forEach(this::ensureLanguageExist);
    }

    private void ensureLanguageExist(String name) {
        if (!languageRepository.findByName(name).isPresent()) {
            languageRepository.save(Language.builder().name(name).build());
        }
    }
}
