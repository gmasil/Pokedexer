package de.gmasil.collection.series;

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
@RequestMapping("/api/series")
public class SeriesRestController {
    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping("")
    public List<Series> getAll() {
        return seriesRepository.findAll();
    }

    @PostMapping("")
    public Series create(@Valid @RequestBody Series series) {
        return seriesRepository.save(series);
    }

    @GetMapping("/{id}")
    public Series getById(@PathVariable(value = "id") Long seriesId) {
        return seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", seriesId));
    }

    @PutMapping("/{id}")
    public Series update(@PathVariable(value = "id") Long cardId, @Valid @RequestBody Series seriesDetails) {
        Series card = seriesRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Set", "id", cardId));
        if (seriesDetails.getName() != null) {
            card.setName(seriesDetails.getName());
        }
        return seriesRepository.save(card);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long seriesId) {
        Series note = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Set", "id", seriesId));
        seriesRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
