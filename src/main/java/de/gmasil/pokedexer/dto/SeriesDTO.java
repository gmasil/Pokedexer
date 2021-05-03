package de.gmasil.pokedexer.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class SeriesDTO implements Serializable {
    private Long id;

    @NotBlank(message = "The name must not be empty")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
