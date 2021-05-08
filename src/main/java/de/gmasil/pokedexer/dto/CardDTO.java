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
package de.gmasil.pokedexer.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import de.gmasil.pokedexer.jpa.Card;
import de.gmasil.pokedexer.jpa.Series;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO implements Serializable {

    private Long id;

    @NotNull
    @NotBlank(message = "The name must not be empty")
    private String name;

    private Long certNumber;

    private Integer grade;

    private Integer population;

    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate purchaseDate;

    private Double purchasePrice;

    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate gradingSendOffDate;

    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate gradingReceivedDate;

    private Series series;

    private Integer cardNumber;

    private String status;

    @Min(0)
    @Max(5)
    @Default
    private int progress = 0;

    public String getProgressValue() {
        return Card.getProgressValue(progress);
    }
}
