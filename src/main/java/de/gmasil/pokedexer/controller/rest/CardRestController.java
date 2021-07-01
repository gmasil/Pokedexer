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

import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.exception.ResourceNotFoundException;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.CardService;
import de.gmasil.pokedexer.services.EntityMapper;

@RestController
@RequestMapping("/api/cards")
public class CardRestController {

    @Autowired
    private CardService repo;

    @Autowired
    private EntityMapper mapper;

    @GetMapping("")
    public List<Card> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Card get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("card", "id", id));
    }

    @PostMapping("/")
    public Card add(@RequestBody CardDTO cardDTO) {
        return repo.save(mapper.mapCardDTO(cardDTO));
    }

    @PutMapping("/{id}")
    public Card put(@RequestBody CardDTO cardDTO, @PathVariable Long id) {
        Card card = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("card", "id", id));
        return repo.save(mapper.patchCard(cardDTO, card));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.delete(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("card", "id", id)));
    }
}
