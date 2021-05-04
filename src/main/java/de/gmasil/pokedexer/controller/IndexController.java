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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import de.gmasil.pokedexer.controller.advisor.Template;
import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.exception.ResourceNotFoundException;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.CardRepository;
import de.gmasil.pokedexer.jpa.SeriesRepository;
import de.gmasil.pokedexer.services.EntityMapper;
import de.gmasil.pokedexer.services.ValidationService;

@Controller
public class IndexController {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping("/")
    public String index(Template template) {
        List<Card> cards = cardRepository.findAll();
        return template.makeCardList(entityMapper.mapCard(cards));
    }

    @GetMapping("/add")
    public String showForm(Template template, CardDTO cardDTO) {
        return template.makeCardAdd(seriesRepository.findAll());
    }

    @PostMapping("/add")
    public String addCard(Template template, @Valid CardDTO cardDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            handleCardBindingErrors(bindingResult);
            return template.makeCardAdd(seriesRepository.findAll());
        }
        cardRepository.save(entityMapper.mapCardDTO(cardDTO));
        return "redirect:";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(Template template, @PathVariable("id") long id) {
        Card card = findCardById(id);
        return template.makeCardEdit(entityMapper.mapCard(card), seriesRepository.findAll());
    }

    @PostMapping("/edit/{id}")
    public String updateCard(Template template, @PathVariable("id") long id, @Valid CardDTO cardDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            handleCardBindingErrors(bindingResult);
            return template.makeCardEdit(cardDTO, seriesRepository.findAll());
        }
        Card card = findCardById(id);
        entityMapper.patchCard(cardDTO, card);
        cardRepository.save(card);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCardConfirm(Template template, @PathVariable("id") long id, Model model) {
        Card card = findCardById(id);
        return template.makeCardDeleteConfirm(entityMapper.mapCard(card));
    }

    @PostMapping("/delete/{id}")
    public String deleteCard(Template template, @PathVariable("id") long id, Model model) {
        Card card = findCardById(id);
        cardRepository.delete(card);
        return "redirect:/";
    }

    private void handleCardBindingErrors(BindingResult bindingResult) {
        validationService.handleException(bindingResult.getFieldError("certNumber"), "cert number");
        validationService.handleException(bindingResult.getFieldError("grade"));
        validationService.handleException(bindingResult.getFieldError("population"));
        validationService.handleException(bindingResult.getFieldError("purchasePrice"), "purchase price");
        validationService.handleException(bindingResult.getFieldError("cardNumber"), "card number");
    }

    private Card findCardById(long id) {
        return cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card", "id", id));
    }
}
