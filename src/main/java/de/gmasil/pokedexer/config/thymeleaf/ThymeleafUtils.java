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
package de.gmasil.pokedexer.config.thymeleaf;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ThymeleafUtils {

    @Value("${format.date:yyyy-MM-dd}")
    private String dateFormat;

    @Value("${format.currency.comma:false}")
    private boolean currencyComma;

    @Value("${format.currency.symbol:$}")
    private String currencySymbol;

    public String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public String formatCurrency(Double value) {
        if (value == null) {
            return "";
        }
        String s = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US)).format(value);
        if (currencyComma) {
            s = s.replace('.', ',');
        }
        return s + currencySymbol;
    }
}
