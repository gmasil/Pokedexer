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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.gmasil.pokedexer.controller.advisor.Template;
import de.gmasil.pokedexer.dto.CardDTO;
import de.gmasil.pokedexer.exception.ResourceNotFoundException;
import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.CardService;
import de.gmasil.pokedexer.jpa.LanguageRepository;
import de.gmasil.pokedexer.jpa.SeriesService;
import de.gmasil.pokedexer.psa.PsaService;
import de.gmasil.pokedexer.services.EntityMapper;
import de.gmasil.pokedexer.services.ValidationService;

@Controller
public class IndexController {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private CardService cardService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private PsaService psaService;

    @Autowired
    private LanguageRepository languageRepository;

    private static Map<String, String> sortLookup = new HashMap<>();

    @PostConstruct
    private void initLookupMap() {
        sortLookup.put("Name", "name");
        sortLookup.put("Cert", "certNumber");
        sortLookup.put("Grade", "grade");
        sortLookup.put("Population", "population");
        sortLookup.put("Purchase Date", "purchaseDate");
        sortLookup.put("Purchase Price", "purchasePrice");
        sortLookup.put("Grading Sendoff", "gradingSendOffDate");
        sortLookup.put("Grading Received", "gradingReceivedDate");
        sortLookup.put("Series", "seriesName");
        sortLookup.put("Card Number", "cardNumber");
        sortLookup.put("Language", "language");
        sortLookup.put("Status", "status");
        sortLookup.put("Progress", "progress");
        sortLookup.put("Last Updated", "updatedAt");
        sortLookup.put("Created", "createdAt");
    }

    @GetMapping("/")
    public String index(Template template,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortParam,
            @RequestParam(name = "desc") Optional<String> descParam) {
        String sort = sortLookup.getOrDefault(sortParam, "name");
        boolean desc = descParam.isPresent();
        List<Card> cards = cardService.findAll(Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sort));
        template.addAttribute("sort", sortParam);
        template.addAttribute("desc", desc);
        return template.makeCardList(entityMapper.mapCard(cards));
    }

    @GetMapping("/add")
    public String showForm(Template template, CardDTO cardDTO,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortParam,
            @RequestParam(name = "desc") Optional<String> descParam) {
        template.addAttribute("sort", sortParam);
        template.addAttribute("desc", descParam.isPresent());
        return template.makeCardAdd(seriesService.findAll(), languageRepository.findAll());
    }

    @PostMapping("/add")
    public String addCard(Template template, @Valid CardDTO cardDTO, BindingResult bindingResult,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortParam,
            @RequestParam(name = "desc") Optional<String> descParam,
            @RequestParam(name = "autoPopulation", required = false, defaultValue = "false") boolean autoPopulation) {
        template.addAttribute("sort", sortParam);
        template.addAttribute("desc", descParam.isPresent());
        try {
            if (autoPopulation && cardDTO.getCertNumber() != null) {
                int population = psaService.getPopulation("" + cardDTO.getCertNumber());
                cardDTO.setPopulation(population);
            }
        } catch (Exception e) {
            // TODO: inform user that population could not be determined
        }
        if (bindingResult.hasErrors()) {
            handleCardBindingErrors(bindingResult);
            return template.makeCardAdd(seriesService.findAll(), languageRepository.findAll());
        }
        cardService.save(entityMapper.mapCardDTO(cardDTO));
        return "redirect:?sort=" + sortParam + (descParam.isPresent() ? "&desc" : "");
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(Template template, @PathVariable("id") long id,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortParam,
            @RequestParam(name = "desc") Optional<String> descParam) {
        template.addAttribute("sort", sortParam);
        template.addAttribute("desc", descParam.isPresent());
        Card card = findCardById(id);
        return template.makeCardEdit(entityMapper.mapCard(card), seriesService.findAll(), languageRepository.findAll());
    }

    @PostMapping("/edit/{id}")
    public String updateCard(Template template, @PathVariable("id") long id, @Valid CardDTO cardDTO,
            BindingResult bindingResult,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortParam,
            @RequestParam(name = "desc") Optional<String> descParam,
            @RequestParam(name = "autoPopulation", required = false, defaultValue = "false") boolean autoPopulation) {
        template.addAttribute("sort", sortParam);
        template.addAttribute("desc", descParam.isPresent());
        try {
            if (autoPopulation && cardDTO.getCertNumber() != null) {
                int population = psaService.getPopulation("" + cardDTO.getCertNumber());
                cardDTO.setPopulation(population);
            }
        } catch (Exception e) {
            // TODO: inform user that population could not be determined
        }
        if (bindingResult.hasErrors()) {
            handleCardBindingErrors(bindingResult);
            return template.makeCardEdit(cardDTO, seriesService.findAll(), languageRepository.findAll());
        }
        Card card = findCardById(id);
        entityMapper.patchCard(cardDTO, card);
        cardService.save(card);
        return "redirect:/?sort=" + sortParam + (descParam.isPresent() ? "&desc" : "");
    }

    @GetMapping("/delete/{id}")
    public String deleteCardConfirm(Template template, @PathVariable("id") long id, Model model,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortParam,
            @RequestParam(name = "desc") Optional<String> descParam) {
        template.addAttribute("sort", sortParam);
        template.addAttribute("desc", descParam.isPresent());
        Card card = findCardById(id);
        return template.makeCardDeleteConfirm(entityMapper.mapCard(card));
    }

    @PostMapping("/delete/{id}")
    public String deleteCard(Template template, @PathVariable("id") long id, Model model,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sortParam,
            @RequestParam(name = "desc") Optional<String> descParam) {
        Card card = findCardById(id);
        cardService.delete(card);
        return "redirect:/?sort=" + sortParam + (descParam.isPresent() ? "&desc" : "");
    }

    @GetMapping("/updatepopulation")
    public String updatePopulation(Template template) {
        List<Card> cards = cardService.findAll();
        cards.stream().filter(card -> card.getCertNumber() != null).forEach(card -> {
            int population = psaService.getPopulation("" + card.getCertNumber());
            card.setPopulation(population);
            cardService.save(card);
        });
        template.addAttribute("sort", "name");
        template.addAttribute("desc", false);
        return template.makeCardList(entityMapper.mapCard(cards));
    }

    private void handleCardBindingErrors(BindingResult bindingResult) {
        validationService.handleException(bindingResult.getFieldError("certNumber"), "cert number");
        validationService.handleException(bindingResult.getFieldError("grade"));
        validationService.handleException(bindingResult.getFieldError("population"));
        validationService.handleException(bindingResult.getFieldError("purchasePrice"), "purchase price");
        validationService.handleException(bindingResult.getFieldError("cardNumber"), "card number");
    }

    private Card findCardById(long id) {
        return cardService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card", "id", id));
    }
}
