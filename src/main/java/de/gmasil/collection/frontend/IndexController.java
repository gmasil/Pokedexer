package de.gmasil.collection.frontend;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import de.gmasil.collection.card.Card;
import de.gmasil.collection.card.CardRepository;
import de.gmasil.collection.frontend.advisor.Template;
import de.gmasil.collection.series.SeriesRepository;

@Controller
public class IndexController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping("/")
    public String index(Template template) {
        List<Card> cards = cardRepository.findAll();
        return template.makeCardList(cards);
    }

    @GetMapping("/add")
    public String showForm(Template template, Card card) {
        return template.makeCardAdd(seriesRepository.findAll());
    }

    @PostMapping("/add")
    public String addCard(Template template, @Valid Card card, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            handleCardBindingErrors(bindingResult);
            return template.makeCardAdd(seriesRepository.findAll());
        }
        cardRepository.save(card);
        return "redirect:";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(Template template, @PathVariable("id") long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card id:" + id));
        return template.makeCardEdit(card, seriesRepository.findAll());
    }

    @PostMapping("/edit/{id}")
    public String updateCard(Template template, @PathVariable("id") long id, @Valid Card card,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            handleCardBindingErrors(bindingResult);
            return template.makeCardEdit(card, seriesRepository.findAll());
        }
        cardRepository.save(card);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCard(@PathVariable("id") long id, Model model) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card id:" + id));
        cardRepository.delete(card);
        return "redirect:/";
    }

    private void handleCardBindingErrors(BindingResult bindingResult) {
        ValidationUtils.handleException(bindingResult.getFieldError("certNumber"), "cert number");
        ValidationUtils.handleException(bindingResult.getFieldError("grade"));
        ValidationUtils.handleException(bindingResult.getFieldError("population"));
        ValidationUtils.handleException(bindingResult.getFieldError("purchasePrice"), "purchase price");
        ValidationUtils.handleException(bindingResult.getFieldError("cardNumber"), "card number");
    }
}
