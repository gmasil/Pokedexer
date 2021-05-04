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

import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.exception.ResourceNotFoundException;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.CardRepository;
import de.gmasil.pokedexer.services.EntityMapper;

@RestController
@RequestMapping("/api/card")
public class CardRestController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping("")
    public List<CardDTO> getAll() {
        List<Card> cards = cardRepository.findAll();
        return entityMapper.mapCard(cards);
    }

    @PostMapping("")
    public CardDTO create(@Valid @RequestBody CardDTO cardDTO) {
        Card card = entityMapper.mapCardDTO(cardDTO);
        card = cardRepository.save(card);
        return entityMapper.mapCard(card);
    }

    @GetMapping("/{id}")
    public CardDTO getById(@PathVariable(value = "id") Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));
        return entityMapper.mapCard(card);
    }

    @PutMapping("/{id}")
    public CardDTO update(@PathVariable(value = "id") Long cardId, @Valid @RequestBody CardDTO cardDTO) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));
        entityMapper.patchCard(cardDTO, card);
        card = cardRepository.save(card);
        return entityMapper.mapCard(card);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long cardId) {
        Card note = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));
        cardRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
