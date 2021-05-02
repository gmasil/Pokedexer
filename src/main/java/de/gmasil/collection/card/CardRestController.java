package de.gmasil.collection.card;

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

import de.gmasil.collection.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/card")
public class CardRestController {
    @Autowired
    private CardRepository cardRepository;

    @GetMapping("")
    public List<Card> getAll() {
        return cardRepository.findAll();
    }

    @PostMapping("")
    public Card create(@Valid @RequestBody Card card) {
        return cardRepository.save(card);
    }

    @GetMapping("/{id}")
    public Card getById(@PathVariable(value = "id") Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));
    }

    @PutMapping("/{id}")
    public Card update(@PathVariable(value = "id") Long cardId, @Valid @RequestBody Card cardDetails) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));
        if (cardDetails.getName() != null) {
            card.setName(cardDetails.getName());
        }
        if (cardDetails.getCertNumber() != null) {
            card.setCertNumber(cardDetails.getCertNumber());
        }
        if (cardDetails.getGrade() != null) {
            card.setGrade(cardDetails.getGrade());
        }
        if (cardDetails.getPopulation() != null) {
            card.setPopulation(cardDetails.getPopulation());
        }
        if (cardDetails.getPurchaseDate() != null) {
            card.setPurchaseDate(cardDetails.getPurchaseDate());
        }
        if (cardDetails.getPurchasePrice() != null) {
            card.setPurchasePrice(cardDetails.getPurchasePrice());
        }
        if (cardDetails.getGradingSendOffDate() != null) {
            card.setGradingSendOffDate(cardDetails.getGradingSendOffDate());
        }
        if (cardDetails.getGradingReceivedDate() != null) {
            card.setGradingReceivedDate(cardDetails.getGradingReceivedDate());
        }
        if (cardDetails.getSeries() != null) {
            card.setSeries(cardDetails.getSeries());
        }
        if (cardDetails.getCardNumber() != null) {
            card.setCardNumber(cardDetails.getCardNumber());
        }
        if (cardDetails.getStatus() != null) {
            card.setStatus(cardDetails.getStatus());
        }
        return cardRepository.save(card);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long cardId) {
        Card note = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", cardId));
        cardRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
